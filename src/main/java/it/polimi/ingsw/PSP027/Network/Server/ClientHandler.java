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
 * @author Lorenzo Guarino
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
    private class ClientCommandsHandler implements Runnable {
        ClientHandler owner = null;
        private boolean bRun = true;
        private ArrayList<Node> commandsList = new ArrayList<Node>();
        private ReentrantLock CommandsLock = new ReentrantLock();

        /**
         * Constructor: it stores this class' owner (the client handler class)
         * @param owner client handler who owns this class
         */
        ClientCommandsHandler(ClientHandler owner)
        {
            this.owner = owner;
        }

        /**
         * Method that stops the Client Handler thread
         */
        public void Stop()
        {
            bRun = false;
        }

        /**
         * Method that enqueue the commands that the Client Handler is receiving from the client
         * @param command command to process
         */
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

        /**
         * Method that dequeue the commands enqueued before
         * @return the command dequeued that is going to be processed
         */
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

        /**
         * Method that processes the command received from the client and triggers action for the server accordingly
         */
        @Override
        public void run() {

            if (owner != null) {

                Node command = null;

                while (bRun) {
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
                                    System.out.println("Processing command: " + cmdID);
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
                                case clt_ChosenGod:
                                    owner.onChosenGod(cmdData);
                                    break;
                                case clt_ChosenFirstPlayer:
                                    owner.onChosenFirstPlayer(cmdData);
                                    break;
                                case clt_ChosenWorkersFirstPositions:
                                    owner.onChosenWorkersFirstPositions(cmdData);
                                    break;
                                case clt_ChosenWorker:
                                    owner.onChosenWorker(cmdData);
                                    break;
                                case clt_Move:
                                    owner.onMove(cmdData);
                                    break;
                                case clt_MovePassed:
                                    owner.onMovePassed();
                                    break;
                                case clt_Build:
                                    owner.onBuild(cmdData);
                                    break;
                                case clt_BuildForAtlas:
                                    owner.onBuildForAtlas(cmdData);
                                    break;
                                case clt_BuildPassed:
                                    owner.onBuildPassed();
                                    break;
                                case clt_EndAction:
                                    owner.onEndAction(cmdData);
                                    break;
                                case clt_EndPassed:
                                    owner.onEndPassed();
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

    ClientHandler(Socket client, Lobby lobby) {
        this.client = client;
        this.lobby = lobby;
    }

    /**
     * Method to get the nickname with which this client has been registered on the server
     * @return the nickname
     */

    public String getNickname() { return nickname; }

    /**
     * Method to get the address from which this client has connected to the server
     * @return the address
     */

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
                            // enqueue commands in a list that will be processed asyncronously within cltcmdhndl thread
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
     * Method used by the server to send commands to its clients, the command is sent in the stream output if not null
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
            System.out.println(e.toString());
        }
    }


    /* **********************************************************************************************************************
     * METHODS THAT PREPARE THE COMMANDS THAT THE SERVER SENDS TO ITS CLIENT AND METHODS THAT PROCESS THE RECEIVED COMMANDS *
     * **********************************************************************************************************************/

    /**
     * Method that prepares the command "Hello" that will then be processed in xml format in the client
     */

    private void onHello(){
        //System.out.println("Got hello from " + client.getInetAddress().getHostAddress());
        String ret = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Hello.toString() + "</id></cmd>";
        SendCommand(ret);
    }

    /**
     * Method that prepares the command that will then be processed in xml format in the client
     * It is used to process the xml received from the client containing the client's username with which it wants to be registered
     * and if it's a valid nickname (not empty) it triggers the method of the lobby that registers the gamer with this nickname, otherwise it
     * responds that the nickname's missing
     * It builds the command response (xml in string format) depending on the succession or failure fo the user's registering (if the
     * nickname is already used by another gamer)
     * @param data node containing the data of the command received by the client (nickname to register in this case)
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
     * It is used to deregister a gamer triggering a method of the lobby that does the action
     * @param data node containing the data of the command received by the client
     */

    private void onDeregister(Node data){

        System.out.println("Received bye from " + nickname);
        lobby.deregisterPlayer(this);
        String strRet = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Deregistered.toString() + "</id></cmd>";
        SendCommand(strRet);
    }


    /* **************** METHODS THAT PROCESS THE XML STRING RECEIVED FROM THE CLIENT IN THE FASE PF SETTING UP THE MATCH *************** */

    /**
     * Method that processes the command in xml format received from the client
     * It triggers a method of the lobby that searches an available match for the client that requested it with its requested number of players
     * @param data xml containing the number of players in the type of match (2 or 3) that the client requested to play
     */

    private void onSearchMatchOfGivenType(Node data) {

        System.out.println("Received onSearchMatchOfGivenType from " + nickname);

        int nPlayers = 1; //for default
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("playerscount")) {
                    nPlayers = Integer.parseInt(node.getTextContent());
                }
            }

            if ((nPlayers >= 2) && (nPlayers <= 3)) {
                lobby.searchMatch(this, nPlayers);
            }
        }
    }

    /**
     * Method that processes the chosen gods written in the command in xml format received from the client
     * It triggers a method of the lobby that saves this chosen gods as the gods in use in the match
     * @param data xml of the chosen gods received from the client
     */

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

    /**
     * Method that processes the chosen god written in the command in xml format received from the client
     * It triggers a method of the lobby that saves this chosen god as the player's god card
     * @param data xml containing the chosen god by the client
     */

    private void onChosenGod(Node data) {

        System.out.println("Received onChosenGod from " + nickname);
        Node node;

        String god = "";

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("player")) {
                    god = node.getTextContent();
                }
            }

            lobby.SetChosenGodOfPlayer(this, god);
        }
    }

    /**
     * Method that processes the chosen first player written in the command in xml format received from the client
     * It triggers a method of the lobby that saves the first player and starts its turn
     * @param data xml containing the chosen first player
     */

    private void onChosenFirstPlayer(Node data) {

        System.out.println("Received onChosenFirstPlayer from " + nickname);
        Node node;

        String player = "";

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("player")) {
                    player = node.getTextContent();
                }
            }

            lobby.SetFirstPlayerOnMatch(this, player);
        }
    }

    /**
     * Method that processes the chosen workers written in the command in xml format received from the client
     * It triggers a method of the lobby that saves the starting workers' position chosen
     * @param data xml of the chosen positions received from the client
     */

    private void onChosenWorkersFirstPositions(Node data) {

        System.out.println("Received onChosenWorkersFirstPositions from " + nickname);
        Node node;
        List<String> chosenpositions = new ArrayList<String>();

        if (data.hasChildNodes()) {
            NodeList positions = data.getChildNodes();

            for (int i = 0; i < positions.getLength(); i++) {
                node = positions.item(i);

                if (node.getNodeName().equals("positions")) {

                    NodeList position = node.getChildNodes();

                    for (int j = 0; j < position.getLength(); j++)
                    {
                        chosenpositions.add(position.item(j).getTextContent());
                    }
                }
            }

            lobby.SetWorkersFirstPosition(this, chosenpositions);
        }
    }

    /* *************** METHODS THAT PROCESS THE COMMAND RECEIVED FROM THE CLIENT WHEN THE TURNS HAVE STARTED ************** */

    /**
     * Method that process the chosen worker written in the command in xml format received from the client.
     * It triggers a method in the lobby that sets the chosen worker in the turn that is being played in the correspondent match
     * @param data xml of the chosen worker received from the client
     */

    private void onChosenWorker(Node data) {

        System.out.println("Received onChosenWorker from " + nickname);
        String chosenWorker = "";
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("worker")) {
                    chosenWorker = node.getTextContent();
                }
            }
            lobby.SetChosenWorker(this, chosenWorker);
        }
    }

    /**
     * Method that process the candidate cell on which to move the worker written in the command in xml format received from the client.
     * It triggers a method in the lobby that sets the chosen cell in the turn that is being played in the correspondent match
     * @param data xml of the chosen cell received from the client
     */
    private void onMove(Node data) {

        System.out.println("Received onMove from " + nickname);
        String chosenCell="";
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("cell")) {
                    chosenCell = node.getTextContent();
                }
            }
            lobby.MoveWorkerOnGivenCell(this, chosenCell);
        }
    }

    /**
     * Method that skips the move
     */
    private void onMovePassed() {

        System.out.println("Received onMovePassed from " + nickname);
        String chosenCell = "";
        Node node;

        lobby.passMove(this);
    }
    /**
     * Method that process the candidate cell on which to build written in the command in xml format received from the client.
     * It triggers a method in the lobby that sets the chosen cell in the turn that is being played in the correspondent match
     * @param data xml of the chosen cell received from the client
     */
    private void onBuild(Node data) {

        System.out.println("Received onBuild from " + nickname);
        String chosenCell="";
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("cell")) {
                    chosenCell = node.getTextContent();
                }
            }
            lobby.BuildOnGivenCell(this, chosenCell);
        }
    }

    /**
     * Method that process the candidate cell on which to build a level or a dome depending what the player whose god is Atlas chose,
     * written in the command in xml format received from the client.
     * It triggers a method in the lobby that sets the chosen cell in the turn that is being played in the correspondent match and
     * tells it what to build (block if B or dome if D)
     * @param data xml of the chosen cell received from the client
     */
    private void onBuildForAtlas(Node data) {

        System.out.println("Received onBuild from " + nickname);
        String chosenCell = "";
        String build_BorD = "";
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("cell")) {
                    chosenCell = node.getTextContent();
                }
                if (node.getNodeName().equals("blockordome")) {
                    build_BorD = node.getTextContent();
                }
            }
            lobby.BuildOnGivenCellForAtlas(this, chosenCell, build_BorD);
        }
    }

    /**
     * Method that skips the build
     */
    private void onBuildPassed() {

        System.out.println("Received onBuildPassed from " + nickname);
        String chosenCell = "";
        Node node;

        lobby.passBuild(this);
    }

    /**
     * Method that process the candidate cell on which to perform the end action written in the command in xml format received from the client.
     * It triggers a method in the lobby that sets the chosen cell in the turn that is being played in the correspondent match
     * @param data xml of the chosen cell received from the client
     */
    private void onEndAction(Node data) {

        System.out.println("Received onEnd from " + nickname);
        String chosenCell="";
        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("cell")) {
                    chosenCell = node.getTextContent();
                }
            }
            lobby.doEndActionOnGivenCell(this, chosenCell);
        }
    }

    /**
     * Method that skips the end
     */
    private void onEndPassed() {
        System.out.println("Received onEndPassed from " + nickname);
        lobby.passEnd(this);
    }
}