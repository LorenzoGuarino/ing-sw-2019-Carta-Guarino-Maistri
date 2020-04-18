package it.polimi.ingsw.PSP027.Network.Client;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import it.polimi.ingsw.PSP027.Utils.Utils;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;

/**
 * @author Elisa Maistri
 */

public class ServerHandler implements Runnable
{
    private String convertStringParam;
    private boolean bManualDisconnection = false;

    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean bRun = true;

    private List<ServerObserver> observers = new ArrayList<ServerObserver>();

    /**
     * Constructor: instantiate the ServerHandler setting the server
     * @param server socket that makes the client speak with the server (through the client handler) via the server handler
     */

    public ServerHandler(Socket server)
    {
        this.server = server;
    }


    /**
     * Method that adds an observer to the list of observers
     * @param observer observer to add to the list
     */

    public void addObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Method that removes an observer from the list of observers
     * @param observer observer to remove from the list
     */

    public void removeObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    /**
     * Method used by the others to actually send a command to server
     * @param cmd command to send
     */

    public synchronized void SendCommand(String cmd) {
        try {
            if(output != null)
                output.writeObject(cmd);
        }
        catch (IOException e) {
        }
    }

    /* *****************************************************************
     * Methods that set the command to send with SendCommand() method *
     *******************************************************************/

    /**
     * Method that sends the command Register
     * @param nickname nickname to register the user with
     */

    public void SendRegisterCommand(String nickname) {
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Register.toString() + "</id><data><nickname>" + nickname + "</nickname></data></cmd>";
        SendCommand(cmd);
    }

    /**
     * Method that sends the command Deregister
     */

    public void SendDeregisterCommand() {
        bManualDisconnection = true;
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Deregister.toString() + "</id></cmd>";
        SendCommand(cmd);
    }

    /**
     * Method that sends the command Hello
     */

    public void SendHello() {

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Hello.toString() + "</id></cmd>";
        SendCommand(cmd);
    }

    /**
     * Method that closes the connection to the server
     */

    public synchronized void Stop() {
        bManualDisconnection = true;
        bRun = false;
        try {
            server.close();
        } catch (IOException e) { }
    }

    /**
     * Method that handles the connection with the server on client side (it is the counterpart of client handler)
     */

    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
            bRun = true;
            handleServerConnection();
        } catch (IOException | ClassNotFoundException | ClassCastException  e) {

            if(bManualDisconnection)
                System.out.println("\nserver connection closed");
            else
                System.out.println("\nserver has died");
        }

        try {
            server.close();
        } catch (IOException  e) { }

        try {
            FireOnDisconnected();
        } catch (IOException | ClassNotFoundException e) { }

    }

    /**
     * Method that handles the connection of the client to the server
     * It processes the command the it receives from the server, firing specific methods depending on the command
     * (read in the cmd field of the xml that has been processed)
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private void handleServerConnection() throws IOException, ClassNotFoundException {

        FireOnConnected();

        try {
            while (bRun) {
                /* read a String from the stream  */
                Object next = input.readObject();
                String strCmd = (String)next;

                // convert strCmd into an XML object and evaluate received command

                Document doc = Utils.ParseStringToXMLDocument( strCmd );

                if(doc.hasChildNodes()) {
                    Node root = doc.getFirstChild();

                    if(root.getNodeName().equals("cmd")) {
                        if(root.hasChildNodes()) {
                            NodeList nodes = root.getChildNodes();
                            Node node;

                            ProtocolTypes.protocolCommand cmdID = ProtocolTypes.protocolCommand.undefined;
                            Node cmdData = null;

                            for(int i=0; i<nodes.getLength(); i++)
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

                            //SWITCH BASED ON THE COMMAND RECEIVED FROM THE SERVER THAT FIRES THE RIGHT METHOD THAT WILL
                            // PASS THE DATA TO THE CLIENT IN ORDER TO REACH THE CLI

                            switch(cmdID)
                            {
                                case srv_Hello:
                                    FireOnHello();
                                    break;
                                case srv_Registered:
                                    FireOnRegister(cmdData);
                                    break;
                                case srv_Deregistered:
                                    FireOnDeregister();
                                    break;
                                case srv_ChooseMatchType:
                                    FireOnChooseMatchType();
                                    break;
                                case srv_EnteringMatch:
                                    FireOnEnteringMatch(cmdData);
                                    break;
                                case srv_EnteredMatch:
                                    FireOnEnteredMatch(cmdData);
                                    break;
                                case srv_LeftMatch:
                                    FireOnLeftMatch(cmdData);
                                    break;
                                case srv_Loser:
                                    FireOnLoser();
                                    break;
                                case srv_Winner:
                                    FireOnWinner(cmdData);
                                    break;
                                case srv_ChooseGods:
                                    FireOnChooseGods(cmdData);
                                    break;
                                case srv_ChooseGod:
                                    FireOnChooseGod(cmdData);
                                    break;
                                case srv_ChooseFirstPlayer:
                                    FireOnChooseFirstPlayer(cmdData);
                                    break;
                                case srv_ChooseWorkerStartPosition:
                                    FireOnChooseWorkerStartPosition(cmdData);
                                    break;
                                case srv_ChooseWorker:
                                    FireOnChooseWorker(cmdData);
                                    break;
                                case srv_CandidateCellsForMove:
                                    FireOnCandidateCellsForMove(cmdData);
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }
    }



    /**
     * Method that fires the OnHello() method in the client
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnHello() throws IOException, ClassNotFoundException
    {
        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer: observersCpy) {
            observer.onHello();
        }
    }

    /**
     * Method that fires the OnConnected() method in the client
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnConnected() throws IOException, ClassNotFoundException
    {

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer: observersCpy) {
            observer.onConnected();
        }
    }

    /**
     * Method that fires the OnDisconnected() method in the client
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnDisconnected() throws IOException, ClassNotFoundException
    {
        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer: observersCpy) {
            observer.onDisconnected();
        }
    }

    /**
     * Method that fires the OnRegister() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnRegister(Node data) throws IOException, ClassNotFoundException {

        /* Possible return data
         * "<data>
         *       <retcode>FAIL</retcode>
         *       <reason>Nickname already present</reason>
         * </data>";
         * "<data>
         *       <retcode>SUCCESS</retcode>
         * </data>";
         * "<data>
         *       <retcode>FAIL</retcode>
         *       <reason>Missing nickname</reason>
         * </data>";
         */

        Node node;
        String ret = "";
        String err = "";

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("retcode"))
                {
                    ret = node.getTextContent();
                }
                else if(node.getNodeName().equals("reason"))
                {
                    err = node.getTextContent();
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                if(ret.equalsIgnoreCase("FAIL"))
                {
                    observer.onRegistrationError(err);
                }
                else
                {
                    observer.onRegister();
                }
            }
        }
    }

    /**
     * Method that fires the OnDeregister() method in the client
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnDeregister() throws IOException, ClassNotFoundException {
        System.out.println("FireOnDeregister");

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.onDeregister();
        }
    }

    /**
     * Method that fires the OnEnteringMatch() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnEnteringMatch(Node data) throws IOException, ClassNotFoundException {
        /* data possible value
         * <data>
         *      <players>
         *          <player>nickname1</player>
         *          <player>nickname2</player>
         *      </players>
         * </data>
         */

        Node node;
        List<String> players = new ArrayList<String>();

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("players"))
                {
                    if(node.hasChildNodes())
                    {
                        NodeList nodeplayers = node.getChildNodes();

                        for (int j = 0; j < nodeplayers.getLength(); j++)
                        {
                            players.add(nodeplayers.item(j).getTextContent());
                        }
                    }
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                observer.onEnteringMatch(players);
            }
        }
    }

    /**
     * Method that fires the OnEnteredMatch() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnEnteredMatch(Node data) throws IOException, ClassNotFoundException {
        /** data possible value
         * <data><players><player>nickname1</player><player>nickname2</player></players></data>
         */

        Node node;
        List<String> players = new ArrayList<String>();

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("players"))
                {
                    if(node.hasChildNodes())
                    {
                        NodeList nodeplayers = node.getChildNodes();

                        for (int j = 0; j < nodeplayers.getLength(); j++)
                        {
                            players.add(nodeplayers.item(j).getTextContent());
                        }
                    }
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                observer.onEnteredMatch(players);
            }
        }
    }

    /**
     * Method that processes the xml command in order to get its data and fired the OnLeftMatch method in the client.
     * In this case it gets the nickname of the player that has left the match, in order to trigger a method of
     * @param data
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnLeftMatch(Node data) throws IOException, ClassNotFoundException {
        /* data value
         * <data>
         *      <<player>nickname</player>
         * </data>
         */

        Node node;
        String nickname = "";

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("player"))
                {
                    nickname = node.getTextContent();
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                observer.onLeftMatch(nickname);
            }
        }
    }


    /**
     * Method that fires the onChooseMatchType() method in the client
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnChooseMatchType() throws IOException, ClassNotFoundException
    {
        System.out.println("FireOnChooseMatchType");

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.onChooseMatchType();
        }
    }


    /**
     * Method that fires the OnChooseGods() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnChooseGods(Node data) throws IOException, ClassNotFoundException {
        /* data value
         * <data>
         *     <requiredgods>2</requiredgods>
         *     <gods>
         *         <god>Apollo</god>
         *         ...
         *         <god>Prometheus</god>
         *     </gods>
         * </data>
         */

        Node node;
        int requiredgods = 2;
        List<String> gods = new ArrayList<String>();

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("gods"))
                {
                    NodeList nodegods = node.getChildNodes();

                    for (int j = 0; j < nodegods.getLength(); j++)
                    {
                        gods.add(nodegods.item(j).getTextContent());
                    }
                }
                else if(node.getNodeName().equals("requiredgods")) {

                    requiredgods = Integer.parseInt(node.getTextContent());
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                observer.onChooseGods(requiredgods, gods);
            }
        }
    }

    /**
     * Method that fires the OnChooseGod() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnChooseGod(Node data) throws IOException, ClassNotFoundException {
        /* data value
         * <data>
         *     <gods>
         *         <god></god>
         *         ...
         *         <god></god>
         *     </gods>
         * </data>
         */

        Node node;
        List<String> chosengods = new ArrayList<String>();

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("gods")) {
                    NodeList nodegods = node.getChildNodes();

                    for (int j = 0; j < nodegods.getLength(); j++) {
                        chosengods.add(nodegods.item(j).getTextContent());
                    }
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy) {
                observer.onChooseGod(chosengods);
            }
        }
    }

    /**
     * Method that fires the OnChooseFirstPlayer() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnChooseFirstPlayer(Node data) throws IOException, ClassNotFoundException {
        /* data value
         * <data>
         *     <players>
         *         <player></player>
         *         ...
         *         <player></player>
         *     </players>
         * </data>
         */

        Node node;
        List<String> players = new ArrayList<String>();

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("players")) {
                    NodeList nodegods = node.getChildNodes();

                    for (int j = 0; j < nodegods.getLength(); j++) {
                        players.add(nodegods.item(j).getTextContent());
                    }
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy) {
                observer.onChooseFirstPlayer(players);
            }
        }
    }

    /**
     * Method that fires the OnChooseWorkerStartPosition() method in the client, processing the command received from the server
     * @param data xml to process and then pass on to OnChooseWorkerStartPosition()
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnChooseWorkerStartPosition(Node data) throws IOException, ClassNotFoundException {
        /* data value (example)
         * <data>
         *     <board>
         *         <cell id="0" level="2" dome="false" nickname="Elisa" />
         *         ...
         *         <cell id="24" level="0" dome ="false" nickname="" />
         *     </board>
         * </data>
         */

        Node node;

        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("board")) {

                    //Here we don't process the command received because it's only graphic, the cli will process it instead

                    /* copy the list of observers in case some observers changes it from inside
                     * the notification method */
                    List<ServerObserver> observersCpy;
                    synchronized (observers) {
                        observersCpy = new ArrayList<ServerObserver>(observers);
                    }

                    /* notify the observers that we got the string */
                    for (ServerObserver observer : observersCpy) {
                        observer.onChooseWorkerStartPosition(node);
                    }

                }
            }
        }
    }

    /* ************************************* METHODS REGARDING THE COMMUNICATION WHEN THE TURN HAS STARTED ************************+ */

    /**
     * Method that fires the OnChooseWorker method in the client, processing the command received from the server
     * @param data xml to process and then pass on to onChooseWorker
     */
    private void FireOnChooseWorker(Node data) {
        /* data value (example)
         * <data>
         *     <board>
         *         <cell id="0" level="2" dome="false" nickname="Elisa" />
         *         ...
         *         <cell id="24" level="0" dome ="false" nickname="" />
         *     </board>
         * </data>
         */

        Node node;
        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("board")) {
                    List<ServerObserver> observersCpy;
                    synchronized (observers) {
                        observersCpy = new ArrayList<ServerObserver>(observers);
                    }
                    for (ServerObserver observer : observersCpy) {
                        observer.onChooseWorker(node);
                    }
                }
            }
        }
    }

    /**
     * Method that fires the OnCandidateCellsForMove method in the client, processing the command received from the server
     * @param data xml to process and then pass on to onCandidateCellsForMove
     */
    private void FireOnCandidateCellsForMove(Node data){
        /* data value (example)
         * <data>
         *     <candidates>
         *         <cell id="0"/>
         *         ...
         *         <cell id="8"/>
         *     </candidates>
         * </data>
         */
        Node node;
        if (data.hasChildNodes()) {
            NodeList nodes = data.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);

                if (node.getNodeName().equals("candidates")) {
                    List<ServerObserver> observersCpy;
                    synchronized (observers) {
                        observersCpy = new ArrayList<ServerObserver>(observers);
                    }
                    for (ServerObserver observer : observersCpy) {
                        observer.onCandidateCellsForMove(node);
                    }
                }
            }
        }

    }



    /**
     * Method that fires the OnLoser() method in the client, processing the command received from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnLoser() throws IOException, ClassNotFoundException {
        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<ServerObserver>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy)
        {
            observer.onLoser();
        }
    }

    /**
     * Method that fires the OnWinner() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnWinner(Node data) throws IOException, ClassNotFoundException {
        /** data value
         * <data><<player>winnernick</player></data>
         */

        Node node;
        String nickname = "";

        if(data.hasChildNodes())
        {
            NodeList nodes = data.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++)
            {
                node = nodes.item(i);

                if(node.getNodeName().equals("player"))
                {
                    nickname = node.getTextContent();
                }
            }

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<ServerObserver>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy)
            {
                observer.onWinner(nickname);
            }
        }
    }


}
