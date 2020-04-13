package it.polimi.ingsw.PSP027.Network.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
    private Socket client = null;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    private Lobby lobby = null;
    private String nickname = "";
    private Date lastHelloTime = null;

    /**
     * Class used to decouple commands sent by each client from the thread that manages the connection with the client
     * to avoid that potential task consuming actions would block the connection thread for a time long enough to be
     * considered a potential disconnection by remote client
     */
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

                        commandsList.add(command.cloneNode(true));
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

                Node command = null;

                while (bRun) {
                   command = DequeueCommand();

                    if (command != null) {

                        System.out.println("Processing command: " + command.getTextContent());

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
                                    owner.onDeregister(cmdData);
                                    break;
                                case clt_Register:
                                    owner.onRegister(cmdData);
                                    break;
                                case clt_SearchMatchOfGivenType:
                                    owner.onSearchMatchOfGivenType(cmdData);
                                    break;
                                case clt_ChosenGods:
                                    owner.onChosenGods(cmdData);
                                    break;
                            }
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

    public String getNickname() { return nickname; }

    public String getAddress() { return (client != null) ? client.getInetAddress().getHostAddress() : ""; }

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
            lobby.deregisterPlayer(this);
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
                            // enqueue commands in a list that will be processed asyncronously within cltcmdhndl thred
                            switch(cmdID){
                                case clt_Hello:
                                    onHello();
                                    break;
                                default:
                                    cltcmdhndlr.EnqueueCommand(root);
                                    break;
                            }
                        }
                    }
                }

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

    private void onHello(){
        //System.out.println("Got hello from " + client.getInetAddress().getHostAddress());
        String ret = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Hello.toString() + "</id></cmd>";
        SendCommand(ret);
    }

    private void onChosenGods(Node data) {

    System.out.println("Received onChosenGods from " + nickname);
    Node node;
    List<String> gods = new ArrayList<String>();

    if (data.hasChildNodes()) {
        NodeList nodes = data.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("gods")) {

                NodeList nodegods = node.getChildNodes();

                for (int j = 0; j < nodegods.getLength(); j++)
                {
                    gods.add(nodegods.item(j).getTextContent());
                }
            }
        }

         lobby.SetChosenGodsOnMatch(this, gods);
    }
}

    private void onSearchMatchOfGivenType(Node data) {

        System.out.println("Received onSearchMatchOfGivenType from " + nickname);
        int nPlayers = 1;
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("playerscount")) {
                    nPlayers = Integer.parseInt(node.getTextContent());
                }
            }

            if ((nPlayers>=2) && (nPlayers<=3)) {
                lobby.searchMatch(this, nPlayers);
            }
        }
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onRegister(Node data){

        nickname = "";
        Node node;
        String strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Invalid request</reason></data></cmd>";

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
                if(lobby.registerNewPlayer(this)) {
                    strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>SUCCESS</retcode></data></cmd>";
                    SendCommand(strRet);

                    /* DEREM this if player should automatically start a match once registered rather than waiting for an explicit request from player
                    strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseMatchType.toString() + "</id></cmd>";
                    SendCommand(strRet);
                    */
                }
                else {
                    strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Nickname already present</reason></data></cmd>";
                    SendCommand(strRet);
                }
            }
            else {
                strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Registered.toString() + "</id><data><retcode>FAIL</retcode><reason>Missing nickname</reason></data></cmd>";
                SendCommand(strRet);
            }
        }
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * @return the command in string format
     */

    private void onDeregister(Node data){

        System.out.println("Received bye from " + nickname);
        lobby.deregisterPlayer(this);
        String strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Deregistered.toString() + "</id></cmd>";
        SendCommand(strRet);
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