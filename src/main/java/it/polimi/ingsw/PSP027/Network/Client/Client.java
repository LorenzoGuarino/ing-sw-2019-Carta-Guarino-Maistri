package it.polimi.ingsw.PSP027.Network.Client;

import it.polimi.ingsw.PSP027.Network.ProtocolTypes;
import it.polimi.ingsw.PSP027.Network.Server.Server;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Elisa Maistri
 * @author Lorenzo Guarino
 * @author danielecarta
 */

public class Client implements Runnable, ServerObserver
{
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Socket server = null;
    private ServerHandler serverHandler = null;
    private String nickname = "";
    private String ip = "";
    private int port = Server.SOCKET_PORT;
    private Date lastHelloTime = null;

    private List<ClientObserver> observers = new ArrayList<>();


    private enum ConnectionStatus {
        Disconnected,
        KeepDisconnected,
        WaitForConnection,
        Connecting,
        Connected,
        KeepConnected,
        Disconnecting,
        WaitForDisconnection,
    }

    private enum RegistrationStatus {
        Unregistered,
        Registering,
        WaitForRegistration,
        Registered,
        Deregistering,
        WaitForDeregistration,
    }

    private RegistrationStatus regStatus = RegistrationStatus.Unregistered;
    private ConnectionStatus connStatus = ConnectionStatus.Disconnected;

    /**
     * Method to get this client's nickname
     * @return the client's nickname
     */

    public String getNickname() { return nickname; }

    /**
     * Method that adds an observer to the list of observers
     * @param observer observer to add
     */

    public void addObserver(ClientObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Method that removes an observer from the list of observers
     * @param observer observer to remove
     */

    public void removeObserver(ClientObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Method used to trigger the connection to the server if it receives an ip address valid (from CLI or GUI) and if the
     * user is in the "wait for connection status"
     * @param ipaddress ip address to use for connection typed by the user in the format ipaddress:port
     * @return true if command has been executed otherwise false
     */

    public synchronized boolean Connect(String ipaddress)
    {
        if(connStatus == ConnectionStatus.KeepDisconnected) {
            boolean bWrongAddress = false;
            String[] address = ipaddress.split(":");
            ip = address[0];

            if(ip.length()==0) {
                bWrongAddress = true;
                ip = "127.0.0.1";
            }

            if (address.length == 2) {
                port = Integer.parseInt(address[1]);
                if((port<1024) || (port > 65535))
                {
                    bWrongAddress = true;
                    port = Server.SOCKET_PORT;
                }
            }
            else
                port = Server.SOCKET_PORT;

            connStatus = ConnectionStatus.Connecting;
            return true;
        }
        else
            return false;
    }

    /**
     * Method used to trigger the disconnection from the server if the user is in the "connected status"
     * or the "keep connected status"
     * @return true if command has been executed otherwise false
     */

    public synchronized boolean Disconnect()
    {
        if((connStatus != ConnectionStatus.Disconnecting) &&
                (connStatus != ConnectionStatus.KeepDisconnected) &&
                (connStatus != ConnectionStatus.Disconnected)
        ){
            connStatus = ConnectionStatus.Disconnecting;
            return true;
        }
        else
            return false;
    }

    /**
     * Method used to trigger the registration of the user if it's in the "keep connected status" and in the
     * "unregistered status", if the user has entered a string not empty to use as the nickname
     * @param nickname to register the user with
     * @return true if command has been executed otherwise false
     */

    public synchronized boolean Register(String nickname)
    {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Unregistered) {

                if (nickname.length() > 0) {
                    this.nickname = nickname;
                    regStatus = RegistrationStatus.Registering;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method to trigger the deregistration of the user if it's in the "keep connected status" and in the
     * "registered status". It sets the nickname to an empty string again
     * @return true if command has been executed otherwise false
     */

    public synchronized boolean Deregister() {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                nickname = "";
                regStatus = RegistrationStatus.Deregistering;
                return true;
            }
        }

        return false;
    }

    /**
     * Method to trigger the search of the Match for a player. It sends a command to the server with the data required
     * @param nPlayer number of players that will play the match that is being searched
     * @return true if operation successful, false otherwise
     */

    public synchronized boolean SearchMatch(int nPlayer) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String strCmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_SearchMatchOfGivenType.toString()  + "</id><data><playerscount>" + Integer.toString(nPlayer) + "</playerscount></data></cmd>";

                serverHandler.SendCommand(strCmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command with the gods chosen by the client to the server
     * @param godCardsList list of god names that the client has chosen
     * @return true if operation successful, false otherwise
     */

    public synchronized boolean ChosenGods(List<String> godCardsList) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_ChosenGods.toString() + "</id><data><gods>";

                for(int i = 0; i < godCardsList.size(); i++){

                    cmd += "<god>" + godCardsList.get(i) + "</god>";
                }

                cmd += "</gods></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosengod god chosen by the player
     * @return true if operation successful, false otherwise
     */

    public synchronized boolean ChosenGod(String chosengod) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_ChosenGod.toString() + "</id><data><player>" + chosengod + "</player></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenplayer first player chosen by the user
     * @return true if operation successful, false otherwise
     */

    public synchronized boolean ChosenFirstPlayer(String chosenplayer) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_ChosenFirstPlayer.toString() + "</id><data><player>" + chosenplayer + "</player></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenfirstplayer position of the first worker the user wants to place on the board
     * @param chosensecondplayer position of the second worker the user wants to place on the board
     * @return true if operation successful, false otherwise
     */

    public synchronized boolean ChosenWorkersFirstPositions(String chosenfirstplayer, String chosensecondplayer) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_ChosenWorkersFirstPositions.toString() + "</id><data><positions><position>" + chosenfirstplayer + "</position><position>" + chosensecondplayer + "</position></positions></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenWorker worker chosen by the player that is playing the turn
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean ChosenWorker(String chosenWorker) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_ChosenWorker.toString() + "</id><data><worker>" + chosenWorker + "</worker></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenCell Cell that is chosen to move the worker onto
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean CandidateMove(String chosenCell) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Move.toString() + "</id><data><cell>" + chosenCell + "</cell></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server that skips the move
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean passMove() {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_MovePassed.toString() + "</id><data></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }
    /**
     * Method that sends a command to the server with the data required
     * @param chosenCell Cell that is chosen to build on
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean CandidateBuild(String chosenCell) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Build.toString() + "</id><data><cell>" + chosenCell + "</cell></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenCell Cell that is chosen to build on
     * @param build_BorD string indicating if the client asks the server to build a block (B) or a dome (D)
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean CandidateBuildForAtlas(String chosenCell, String build_BorD) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_BuildForAtlas.toString() + "</id><data><cell>" + chosenCell + "</cell><blockordome>" + build_BorD + "</blockordome></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server that skips the build
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean passBuild() {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_BuildPassed.toString() + "</id><data></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server with the data required
     * @param chosenCell Cell that is chosen to perform the end action on
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean CandidateEnd(String chosenCell) {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_EndAction.toString() + "</id><data><cell>" + chosenCell + "</cell></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that sends a command to the server that skips the end
     * @return true if operation successful, false otherwise
     */
    public synchronized boolean passEnd() {
        if(connStatus == ConnectionStatus.KeepConnected) {

            if (regStatus == RegistrationStatus.Registered) {

                String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_EndPassed.toString() + "</id><data></data></cmd>";

                serverHandler.SendCommand(cmd);
                return true;
            }
        }

        return false;
    }

    /**
     * Method that creates a socket to connect with the server, and if successful
     * create the adapter with a separate thread that will communicate with the server
     */

    @Override
    public void run() {
        int nHelloCounter = 40;
        try {
            while (true) {

                // the code within this block can be executed by one thread at a time
                synchronized (this) {

                    /* reset the variable that contains the next string to be consumed
                     * from the server */
                    response = null;

                    if(connStatus == ConnectionStatus.Disconnected) {
                        FireOnDisconnected();
                        connStatus = ConnectionStatus.KeepDisconnected;
                    }
                    else if(connStatus == ConnectionStatus.KeepDisconnected) {
                        /* empty state used to keep the user disconnected until
                         * a new status will be triggered.
                         * This state is necessary as the disconnected status after launching OnDisconnected()
                         * once should not be reentered (otherwise it would keep on launching OnDisconnected() */
                    }
                    else if(connStatus == ConnectionStatus.Connecting) {
                        /* Change of status used for the same reason of KeepDisconnected, so to go in a status
                         * that doesn't do anything other than preventing the Connecting status to be entered again after
                         * being triggered and launching another connection */
                        connStatus = ConnectionStatus.WaitForConnection;

                        // try to connect with the ip and the port set by the Connect() method (that is launched by CLI or GUI
                        try {
                            server = new Socket(ip, port);

                        } catch (IOException e) {
                            FireOnConnectionError();
                        }

                        /* Create the adapter that will allow communication with the server
                         * in background, and start running its thread */
                        if (server != null) {
                            serverHandler = new ServerHandler(server);
                            serverHandler.addObserver(this);
                            // start thread to manage connection in background
                            Thread serverAdapterThread = new Thread(serverHandler);
                            serverAdapterThread.start();
                        }
                        else {
                            connStatus = ConnectionStatus.Disconnected;
                        }
                    }
                    else if(connStatus == ConnectionStatus.WaitForConnection) {
                        // empty state for the same reasons stated above
                    }
                    else if(connStatus == ConnectionStatus.Connected) {
                        FireOnConnected();
                        lastHelloTime = new Date();
                        connStatus = ConnectionStatus.KeepConnected;
                    }
                    else if(connStatus == ConnectionStatus.KeepConnected) {

                        // send an hello packet nearly every 2 sec
                        nHelloCounter--;
                        if(nHelloCounter == 0)
                        {
                            nHelloCounter = 40; // 40 cycles * 50ms/cycle = 2 sec
                            serverHandler.SendHello();

                            Date now = new Date();

                            long lastHelloReceivedDelta = now.getTime() - lastHelloTime.getTime();

                            // if after 6 seconds we do not receive at least an hello, we assume server has died
                            // so force disconnection. NOTE: each pkt received from server is considered like an
                            // hello since it means server is alive.
                            // The hello received is used only when no other packets are sent

                            if(lastHelloReceivedDelta > 6000)
                            {
                                Disconnect();
                            }
                        }

                        if(regStatus == RegistrationStatus.Unregistered) {
                            // empty because in this state nothing has to be done
                        }
                        else if(regStatus == RegistrationStatus.Registering) {

                            regStatus = RegistrationStatus.WaitForRegistration;
                            serverHandler.SendRegisterCommand(nickname);
                        }
                        else if(regStatus == RegistrationStatus.WaitForRegistration) {
                            // empty state for the same reasons stated above
                        }
                        else if(regStatus == RegistrationStatus.Registered) {
                            //HERE WILL BE MANAGED THE COMMANDS THAT ARE POSSIBLE AFTER REGISTRATION
                        }
                        else if(regStatus == RegistrationStatus.Deregistering)
                        {
                            regStatus = RegistrationStatus.WaitForDeregistration;
                            serverHandler.SendDeregisterCommand();
                        }
                        else if(regStatus == RegistrationStatus.WaitForDeregistration) {
                            // empty state for the same reasons stated above
                        }
                    }
                    else if(connStatus == ConnectionStatus.Disconnecting) {
                        serverHandler.Stop();
                        connStatus = ConnectionStatus.WaitForDisconnection;
                    }
                    else if(connStatus == ConnectionStatus.WaitForDisconnection) {
                        // empty state for the same reasons stated above
                    }
                }

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /* ******************************************************
     * Firing events to ClientObserver (will be CLI or GUI) *
     * ******************************************************/

     /**
     * Method that fires the OnConnected() method of the observer (client instance)
     */

    private void FireOnConnected() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnConnected();
        }
    }

    /**
     * Method that fires the OnDisconnected() method of the observer (client instance)
     */

    private void FireOnDisconnected() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnDisconnected();
        }
    }

    /**
     * Method that fires the OnConnectionError() method of the observer (client instance)
     */

    private void FireOnConnectionError() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnConnectionError();
        }
    }

    /**
     * Method that fires the OnRegistered() method of the observer (client instance)
     */

    private void FireOnRegistered() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnRegistered();
        }
    }


    /**
     * Method that fires the OnDeregistered() method of the observer (client instance)
     */

    private void FireOnDeregistered() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnDeregistered();
        }
    }

    /**
     * Method that fires the OnServerConnectionClosed() method of the observer (client instance)
     */

    private void FireOnServerConnectionClosed() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnServerConnectionClosed();
        }
    }

    /**
     * Method that fires the OnServerHasDied() method of the observer (client instance)
     */

    private void FireOnServerHasDied() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnServerHasDied();
        }
    }

    /**
     * Method that fires the OnRegistrationError() method of the observer (client instance)
     * @param error error occurred when registration attempt
     */

    private void FireOnRegistrationError(String error) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnRegistrationError(error);
        }
    }

    /**
     * Method that fires the OnChooseMatchType() method of the observer (client instance)
     */

    private void FireOnChooseMatchType() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseMatchType();
        }
    }

    /**
     * Method that fires the OnEnteringMatch() method of the observer (client instance)
     * @param players list of players of the entering match
     */

    private void FireOnEnteringMatch(List<String> players) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnEnteringMatch(players);
        }
    }

    /**
     * Method that fires the OnEnteredMatch() method of the observer (client instance)
     * @param players list of players of the entered match
     */

    private void FireOnEnteredMatch(List<String> players) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnEnteredMatch(players);
        }
    }

    /**
     * Method that fires the OnLeftMatch() method of the observer (client instance)
     * @param nickname nickname of the client that has left the match
     */

    private void FireOnLeftMatch(String nickname) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnLeftMatch(nickname);
        }
    }

    /**
     * Method that fires the OnChooseGods() method of the observer (client instance)
     * @param gods list of the gods among which the client has to choose 2 or 3 gods from
     * @param requiredgods number of gods the client has to choose
     */

    private void FireOnChooseGods(int requiredgods, List<String> gods) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseGods(requiredgods, gods);
        }
    }

    /**
     * Method that fires the OnChooseGod() method of the observer (client instance)
     * @param chosengods gods among which the client has to choose its god
     */

    private void FireOnChooseGod(List<String> chosengods) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseGod(chosengods);
        }
    }

    /**
     * Method that fires the OnChooseFirstPlayer() method of the observer (client instance)
     * @param players list of players among which the client has to choose who will play first
     */

    private void FireOnChooseFirstPlayer(List<String> players) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseFirstPlayer(players);
        }
    }

    /**
     * Method that fires the OnChooseWorkerStartPosition() method of the observer (client instance)
     * @param nodes board
     */

    private void FireOnChooseWorkerStartPosition(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseWorkerStartPosition(nodes);
        }
    }

    /**
     * Method that fires the OnChooseWorker() method of the observer (client instance)
     * @param board current board of the game
     */
    private void FireOnChooseWorker(Node board) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnChooseWorker(board);
        }
    }

    /**
     * Method that fires the OnCandidateCellsForMove() method of the observer (client instance)
     * @param nodes cells where the worker selected can move onto
     */
    private void FireOnCandidateCellsForMove(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnCandidateCellsForMove(nodes);
        }
    }

    /**
     * Method that fires the OnCandidateCellsForOptMove() method of the observer (client instance)
     * @param nodes cells where the worker selected can move onto
     */
    private void FireOnCandidateCellsForOptMove(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnCandidateCellsForOptMove(nodes);
        }
    }

    /**
     * Method that fires the OnCandidateCellsForBuild() method of the observer (client instance)
     * @param nodes cells where the worker selected can build onto
     */
    private void FireOnCandidateCellsForBuild(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnCandidateCellsForBuild(nodes);
        }
    }

    /**
     * Method that fires the OnCandidateCellsForOptBuild() method of the observer (client instance)
     * @param nodes cells where the worker selected can build onto
     */
    private void FireOnCandidateCellsForOptBuild(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnCandidateCellsForOptBuild(nodes);
        }
    }

    /**
     * Method that fires the OnCandidateCellsForOptEnd() method of the observer (client instance)
     * @param nodes cells where the worker selected can perform the end action onto
     */
    private void FireOnCandidateCellsForOptEnd(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnCandidateCellsForOptEnd(nodes);
        }
    }

    /**
     * Method that fires the OnWinner() method of the observer (client instance)
     * @param nickname nickname of the winner
     */

    private void FireOnWinner(String nickname) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnWinner(nickname);
        }
    }

    /**
     * Method that fires the OnLoser() method of the observer (client instance)
     */

    private void FireOnLoser() {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnLoser();
        }
    }

    /**
     * Method that fires the OnPrintUpdatedBoard() method of the observer (client instance)
     * @param nodes board updated
     */

    private void FireOnPrintUpdatedBoard(NodeList nodes) {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnPrintUpdatedBoard(nodes);
        }
    }




    /* *****************************************************************************************************************
     * Events that catch the messages fired by the ServerHandler through the ServerObserver interface                  *
     * IMPORTANT NOTE: "lastHelloTime = new Date();" must be located in each event related to a message sent by server *
     * as it is stating that server is alive, even though the packet received could not be the hello answer.           *
     *******************************************************************************************************************/

    /**
     * Method that acts as ping to check connection drop
     */

    @Override
    public synchronized void onHello() {
        /*
         * WARNING: this method executes IN THE CONTEXT OF `serverAdapterThread`
         * because it is called from inside the `run` method of ServerAdapter!
         */

        /* Save the string and notify the main thread */
        lastHelloTime = new Date();
        notifyAll();
    }

     /**
     * Method of the ServerObserver interface that is fired by the ClientHandler that sets the connection status to Connected
     */

    @Override
    public synchronized void onConnected() {
        lastHelloTime = new Date();
        connStatus = ConnectionStatus.Connected;
        regStatus = RegistrationStatus.Unregistered;
        notifyAll();
    }

    /**
     * Method that sets the connection status to Disconnected
     */

    @Override
    public synchronized void onDisconnected() {
        connStatus = ConnectionStatus.Disconnected;
        regStatus = RegistrationStatus.Unregistered;
        notifyAll();
    }

    /**
     * Method that alerts the client that the server connection has been closed
     */

    @Override
    public synchronized void onServerConnectionClosed() {
        FireOnServerConnectionClosed();
        notifyAll();
    }

    /**
     * Method that alerts the client that the server has died
     */

    @Override
    public synchronized void onServerHasDied() {
        FireOnServerHasDied();
        notifyAll();
    }

    /**
     * Method that sets the connection status to Registered and calls the method that
     * fires the OnRegistered method of the observer (client instance)
     */

    @Override
    public synchronized void onRegister() {
        regStatus = RegistrationStatus.Registered;
        lastHelloTime = new Date();
        FireOnRegistered();
        notifyAll();
    }

    /**
     * Method that sets the connection status to Unregistered and calls the method that
     * fires the OnDeregistered method of the observer (client instance)
     */

    @Override
    public synchronized void onDeregister() {
        regStatus = RegistrationStatus.Unregistered;
        lastHelloTime = new Date();
        FireOnDeregistered();
        notifyAll();
    }

    /**
     * Method that sets the connection status to Unregistered
     * and fires OnRegistrationError method of the observer (client instance)
     */

    @Override
    public synchronized void onRegistrationError(String error) {
        regStatus = RegistrationStatus.Unregistered;
        lastHelloTime = new Date();
        FireOnRegistrationError(error);
        notifyAll();
    }

    /**
     * Method that fires the OnChooseMatchType method of the observer (client instance)
     */

    @Override
    public synchronized void onChooseMatchType() {
        lastHelloTime = new Date();
        FireOnChooseMatchType();
        notifyAll();
    }

    /**
     * Method that fires the OnEnteringMatch method of the observer (client instance)
     * @param players players that are in the match
     */

    @Override
    public synchronized void onEnteringMatch(List<String> players) {
        lastHelloTime = new Date();
        FireOnEnteringMatch(players);
        notifyAll();
    }

    /**
     * Method that fires the OnEnteredMatch method of the observer (client instance)
     * @param players players of the match
     */

    @Override
    public synchronized void onEnteredMatch(List<String> players) {
        lastHelloTime = new Date();
        FireOnEnteredMatch(players);
        notifyAll();
    }

    /**
     * Method that fires the OnLeftMatch method of the observer (client instance)
     * @param nickname nickname of the gamer that has left the match
     */

    @Override
    public synchronized void onLeftMatch(String nickname) {
        lastHelloTime = new Date();
        FireOnLeftMatch(nickname);
        notifyAll();
    }

    /**
     * Method that fires the OnChooseGods method of the observer (client instance)
     * @param requiredgods number of gods that must be chosen by the client
     * @param gods gods from which the client can choose from
     */

    @Override
    public synchronized void onChooseGods(int requiredgods, List<String> gods) {
        lastHelloTime = new Date();
        FireOnChooseGods(requiredgods, gods);
        notifyAll();
    }

    /**
     * Method that fires the OnChooseGod method of the observer (client instance)
     * @param chosengods chosen gods by the client that must be passed to OnChooseGod method
     */

    @Override
    public synchronized void onChooseGod(List<String> chosengods) {
        lastHelloTime = new Date();
        FireOnChooseGod(chosengods);
        notifyAll();
    }

    /**
     * Method that fires the OnChooseFirstPlayer method of the observer (client instance)
     * @param players players form which the user must choose who will be the first player
     *                placing the workers and consequently to play the first turn
     */

    @Override
    public synchronized void onChooseFirstPlayer(List<String> players) {
        lastHelloTime = new Date();
        FireOnChooseFirstPlayer(players);
        notifyAll();
    }


    /**
     * Method that fires the OnChooseWorkerStartPosition method of the observer (client instance)
     * @param nodes board to print in the user interface when a player has to choose the starting
     *              position of its workers and the list of players with their gods that the CLI
     *              needs to save
     */

    @Override
    public synchronized void onChooseWorkerStartPosition(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnChooseWorkerStartPosition(nodes);
        notifyAll();
    }



    /* ************************************* METHODS REGARDING THE COMMUNICATION WHEN THE TURN HAS STARTED ************************+ */

    /**
     * Method that fires the OnChooseWorker method of the observer (client instance)
     * @param board board to print in the user interface when a player has to choose the worker to play the turn with
     */

    @Override
    public synchronized void onChooseWorker(Node board) {
        lastHelloTime = new Date();
        FireOnChooseWorker(board);
        notifyAll();
    }

    /**
     * Method that fires the OnCandidateCellsForMove method of the observer (client instance)
     * @param nodes xml not yet processed containing the board and the candidate cells for the move
     */

    @Override
    public synchronized void onCandidateCellsForMove(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnCandidateCellsForMove(nodes);
        notifyAll();
    }

    /**
     * Method that fires the OnCandidateCellsForOptMove method of the observer (client instance)
     * @param nodes xml not yet processed containing the board and the candidate cells for the optional move
     */

    @Override
    public synchronized void onCandidateCellsForOptMove(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnCandidateCellsForOptMove(nodes);
        notifyAll();
    }

    /**
     * Method that fires the OnCandidateCellsForBuild method of the observer (client instance)
     * @param nodes xml not yet processed containing the board and the candidate cells for the build
     */

    @Override
    public synchronized void onCandidateCellsForBuild(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnCandidateCellsForBuild(nodes);
        notifyAll();
    }

    /**
     * Method that fires the OnCandidateCellsForOptBuild method of the observer (client instance)
     * @param nodes xml not yet processed containing the board and the candidate cells for the optional build
     */

    @Override
    public synchronized void onCandidateCellsForOptBuild(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnCandidateCellsForOptBuild(nodes);
        notifyAll();
    }

    /**
     * Method that fires the OnCandidateCellsForOptEnd method of the observer (client instance)
     * @param nodes xml not yet processed containing the board and the candidate cells for the optional end
     */

    @Override
    public synchronized void onCandidateCellsForOptEnd(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnCandidateCellsForOptEnd(nodes);
        notifyAll();
    }

    /**
     * Method that fires the OnWinner method of the observer (client instance)
     * @param nickname nickname of the winner that must be passed to OnWinner method
     */

    @Override
    public synchronized void onWinner(String nickname) {
        lastHelloTime = new Date();
        FireOnWinner(nickname);
        notifyAll();
    }

    /**
     * Method that calls the method that fires the OnLoser method of the observer (client instance)
     */

    @Override
    public synchronized void onLoser() {
        lastHelloTime = new Date();
        FireOnLoser();
        notifyAll();
    }

    /**
     * Method that calls the method that fires the OnPrintUpdatedBoard method of the observer (client instance)
     * @param nodes board updated to show the user
     */

    @Override
    public synchronized void onPrintUpdatedBoard(NodeList nodes) {
        lastHelloTime = new Date();
        FireOnPrintUpdatedBoard(nodes);
        notifyAll();
    }
}