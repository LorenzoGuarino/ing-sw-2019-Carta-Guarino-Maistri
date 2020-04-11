package it.polimi.ingsw.PSP027.Network.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.SantoriniMatch;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import it.polimi.ingsw.PSP027.Utils.Utils;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

/**
 * @author Elisa Maistri
 */

public class ClientHandler implements Runnable
{
    private Socket client;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    private Lobby lobby = null;
    private String nickname = "";
    private Lobby.Gamer gamer = null;
    private Date lastHelloTime = null;

    private class ClientCommandsHandler implements Runnable
    {
        ClientHandler owner = null;
        private boolean bRun = true;
        private ArrayList<Node> commandsList = new ArrayList<Node>();
        private ReentrantLock CommandsLock = new ReentrantLock();

        ClientCommandsHandler(ClientHandler owner)
        {
            this.owner = owner;
        }

        public void Stop()
        {
            bRun = false;
        }

        public void EnqueueCommand(Node command)
        {
            try {
                while (true) {

                    if (CommandsLock.tryLock(2L, TimeUnit.SECONDS)) {

                        commandsList.add(command);
                        break;
                    }
                    else {
                        TimeUnit.MILLISECONDS.sleep(200);
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                CommandsLock.unlock();
            }
        }

        public Node DequeueCommand()
        {
            Node command = null;
            try {
                while (true) {

                    if (CommandsLock.tryLock(2L, TimeUnit.SECONDS)) {

                        if(commandsList.size()>0)
                            command = commandsList.remove(0);
                        break;
                    }
                    else {
                        TimeUnit.MILLISECONDS.sleep(200);
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                CommandsLock.unlock();
            }

            return command;
        }

        @Override
        public void run() {

            if (owner != null) {

                String strResponseCmd = "";
                Node command = null;

                while (bRun) {
                    strResponseCmd = "";
                    command = DequeueCommand();

                    if (command != null) {
                        if (command.hasChildNodes()) {
                            NodeList nodes = command.getChildNodes();
                            Node node;

                            ProtocolTypes.protocolCommand cmdID = ProtocolTypes.protocolCommand.undefined;
                            Node cmdData = null;

                            // determine received command
                            for (int i = 0; i < nodes.getLength(); i++) {
                                node = nodes.item(i);

                                if (node.getNodeName().equals("id")) {
                                    cmdID = ProtocolTypes.protocolCommand.valueOf(node.getTextContent());
                                } else if (node.getNodeName().equals("data")) {
                                    cmdData = node;
                                }
                            }

                            // process received command and launches the corresponding method that builds the response
                            switch (cmdID) {
                                case clt_Deregister:
                                    strResponseCmd = onDeregister(cmdData);
                                    break;
                                case clt_Register:
                                    strResponseCmd = onRegister(cmdData);
                                    break;
                            }
                        }

                        if (!strResponseCmd.isEmpty()) {
                            //System.out.println("Sending data to client : " +  strResponseCmd);
                            owner.SendCommand(strResponseCmd);
                            //System.out.println("Sent data to client");
                        }
                    }
                    else {
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    ClientCommandsHandler cltcmdhndlr  = null;
    /**
     * Constructor of the client handler that will communicate with the client
     * @param client socket of the client that wants to connect with this server
     * @param lobby reference to the main lobby
     */

    ClientHandler(Socket client, Lobby lobby)
    {
        this.client = client;
        this.lobby = lobby;
    }

    /**
     * Method that defines an input and output stream for the server
     * It handles the connection with the client on server side (it is the counterpart of server handler)
     * the input is used to listen to any command that the client will send to server
     * the output is used by the server to send commands to the client
     */

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());

            cltcmdhndlr = new ClientCommandsHandler(this);

            Thread cltcmdhndlrThread = new Thread(cltcmdhndlr);
            cltcmdhndlrThread.start();

            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress().getHostAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(cltcmdhndlr != null)
        {
            cltcmdhndlr.Stop();
        }
    }


    /**
     * Method that handles the connection to the client parsing the commands received by the client in XML format
     * with this method the server keeps listening to what the client might send to it
     * It also prepares an adequate response with the data that the server provides from the Model.
     * @throws IOException
     */

    private void handleClientConnection() throws IOException {
        System.out.println("Connected to " + client.getInetAddress().getHostAddress());

        try {
            while (true) {
                /* read a String from the stream  */
                Object next = input.readObject();
                String strCmd = (String)next;
                String strResponseCmd = "";

                // convert strCmd into an XML object and evaluate received command
                // the structure is
                // <cmd>
                //      <id>COMMAND</id>
                //      <data>DATA FOR COMMAND</data>
                // </cmd>

                Document doc = Utils.ParseStringToXMLDocument( strCmd );

                if(doc.hasChildNodes()) {

                    lastHelloTime = new Date();

                    Node root = doc.getFirstChild();

                    if(root.getNodeName().equals("cmd")) {
                        if(root.hasChildNodes()) {
                            NodeList nodes = root.getChildNodes();
                            Node node;

                            ProtocolTypes.protocolCommand cmdID = ProtocolTypes.protocolCommand.undefined;
                            Node cmdData = null;

                            // determine received command
                            for(int i = 0; i < nodes.getLength(); i++)
                            {
                                node = nodes.item(i);

                                if(node.getNodeName().equals("id"))
                                {
                                    cmdID = ProtocolTypes.protocolCommand.valueOf(node.getTextContent());
                                }
                                else if(node.getNodeName().equals("data"))
                                {
                                    cmdData = node;
                                }
                            }

                            // process received command and launches the corresponding method that builds the response
                            switch(cmdID){
                                case clt_Hello:
                                    strResponseCmd = onHello();
                                    break;
                                default:
                                    cltcmdhndlr.EnqueueCommand(root);
                                    break;
                            }
                        }
                    }
                }

                // Sends the response to the client

                if(!strResponseCmd.isEmpty())
                    SendCommand(strResponseCmd);

                Date now = new Date();

                long lastHelloReceivedDelta = now.getTime() - lastHelloTime.getTime();

                // if after 6 seconds we do not receive aa message, we assume client has died

                if(lastHelloReceivedDelta > 6000)
                {
                }

            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }
    }

    /**
     * Method used by the server to send commands to its clients, the command is sent is the stream output is not null
     * @param cmd command that the server wants to send to the client
     */

    public synchronized void SendCommand(String cmd) {
        try {
            if(output != null) {
                output.writeObject(cmd);
                output.flush();
            }
        }
        catch (IOException e) {
        }
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private String onHello(){
        System.out.println("Got hello from " + client.getInetAddress().getHostAddress());
        String ret = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Hello.toString() + "</id></cmd>";
        return ret;
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private String onRegister(Node data){

        String strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Invalid request</reason></data></cmd>";
        nickname = "";
        Node node;

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("nickname"))
                {
                    nickname = node.getTextContent();
                }
            }

            if(!nickname.isEmpty())
            {
                gamer = lobby.registerNewPlayer(nickname, client.getInetAddress().getHostAddress());

                if(gamer == null)
                    strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Nickname already present</reason></data></cmd>";
                else
                    strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>SUCCESS</retcode></data></cmd>";
            }
            else
                strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Missing nickname</reason></data></cmd>";
        }

        return strRet;
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private String onDeregister(Node data){

        System.out.println("Received bye from " + nickname);
        lobby.deregisterPlayer(gamer);
        String strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Deregistered.toString() + "</id></cmd>";
        return strRet;
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onRegistered() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onEnteringMatch() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onEnteredMatch() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onLeftMatch() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onStartTurn() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onDrawBoard() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onChooseWorkerStartPosition() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onCandidateCellsForMove() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onCandidateCellsForBuild() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onUseGodPower() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onBoardUpdated() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onLoser() {

    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onWinner() {

    }

}