package it.polimi.ingsw.PSP027.Network.Client;

import it.polimi.ingsw.PSP027.Network.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisa Maistri
 */

public class Client implements Runnable, ServerObserver
{
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Socket server = null;
    private ServerHandler serverHandler = null;
    private String nickname = "";
    private String ip = "127.0.0.1";
    private int port = Server.SOCKET_PORT;

    private List<ClientObserver> observers = new ArrayList<>();


    private enum ConnectionStatus {
        Disconnected,
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

    public void addObserver(ClientObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ClientObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public synchronized boolean Connect(String ipaddress)
    {
        if(connStatus == ConnectionStatus.WaitForConnection) {
            String[] address = ipaddress.split(":");
            ip = address[0];
            if (address.length == 2)
                port = Integer.parseInt(address[1]);
            else
                port = Server.SOCKET_PORT;
            connStatus = ConnectionStatus.Connecting;
            return true;
        }
        else
            return false;
    }

    public synchronized boolean Disconnect()
    {
        if((connStatus == ConnectionStatus.Connected) || (connStatus == ConnectionStatus.KeepConnected)){
            connStatus = ConnectionStatus.Disconnecting;
            return true;
        }
        else
            return false;
    }

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

    public synchronized boolean Deregister()
    {
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
     * Method that creates a socket to connect with the server, and if successful
     * create the adapter with a separate thread that will communicate with the server
     */

    @Override
    public void run()
    {
        try {
            while (true) {

                // the code within this block can be executed by one thread at a time
                synchronized (this) {

                    /* reset the variable that contains the next string to be consumed
                     * from the server */
                    response = null;

                    if(connStatus == ConnectionStatus.Disconnected) {
                        OnDisconnected();
                        connStatus = ConnectionStatus.WaitForConnection;
                    }
                    if(connStatus == ConnectionStatus.WaitForConnection) {
                    }
                    else if(connStatus == ConnectionStatus.Connecting) {

                        connStatus = ConnectionStatus.WaitForConnection;

                        try {
                            server = new Socket(ip, port);

                        } catch (IOException e) {
                            OnConnectionError();
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
                    else if(connStatus == ConnectionStatus.Disconnecting) {
                        serverHandler.Stop();
                        connStatus = ConnectionStatus.WaitForDisconnection;
                    }
                    if(connStatus == ConnectionStatus.WaitForDisconnection) {
                    }
                    else if(connStatus == ConnectionStatus.Connected) {
                        OnConnected();
                        connStatus = ConnectionStatus.KeepConnected;
                    }
                    else if(connStatus == ConnectionStatus.KeepConnected) {

                        if(regStatus == RegistrationStatus.Unregistered) {
                        }
                        else if(regStatus == RegistrationStatus.Registering) {

                            regStatus = RegistrationStatus.WaitForRegistration;
                            serverHandler.SendRegisterCommand(nickname);
                        }
                        else if(regStatus == RegistrationStatus.WaitForRegistration) {
                        }
                        else if(regStatus == RegistrationStatus.Registered) {
                        }
                        else if(regStatus == RegistrationStatus.Deregistering)
                        {
                            regStatus = RegistrationStatus.WaitForDeregistration;
                            serverHandler.SendDeregisterCommand();
                        }
                        else if(regStatus == RegistrationStatus.WaitForDeregistration) {
                        }
                    }
                }

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void OnConnected()
    {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnConnected();
        }
    }

    private void OnDisconnected()
    {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnDisconnected();
        }
    }

    private void OnConnectionError()
    {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnConnectionError();
        }
    }

    private void OnRegistered()
    {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnRegistered();
        }
    }

    private void OnDeregistered()
    {
        List<ClientObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ClientObserver observer: observersCpy) {
            observer.OnDeregistered();
        }
    }

    @Override
    public synchronized void onConnected() {
        connStatus = ConnectionStatus.Connected;
        notifyAll();
    }

    @Override
    public synchronized void onDisconnected() {
        connStatus = ConnectionStatus.Disconnected;
        notifyAll();
    }

    @Override
    public synchronized void onHello()
    {
        /*
         * WARNING: this method executes IN THE CONTEXT OF `serverAdapterThread`
         * because it is called from inside the `run` method of ServerAdapter!
         */

        /* Save the string and notify the main thread */
        System.out.println("Got srv hello");
        notifyAll();
    }

    @Override
    public synchronized void onRegistrationError(String error)
    {
        System.out.println("Registration failed: " + error);
        regStatus = RegistrationStatus.Unregistered;
        notifyAll();
    }

    @Override
    public synchronized void onRegister() {
        regStatus = RegistrationStatus.Registered;
        OnRegistered();
        notifyAll();
    }

    @Override
    public synchronized void onDeregister()
    {
        regStatus = RegistrationStatus.Unregistered;
        OnDeregistered();
        notifyAll();
    }

    @Override
    public synchronized void onSearchMatch() {

    }

    @Override
    public synchronized void onLeaveMatch() {

    }

    @Override
    public synchronized void onChosenGods() {

    }

    @Override
    public synchronized void onChooseGod() {

    }

    @Override
    public synchronized void onWorkerStartPositionChosen() {

    }

    @Override
    public synchronized void onChooseWorker() {

    }

    @Override
    public synchronized void onMoveWorker() {

    }

    @Override
    public synchronized void onBuild() {

    }

    @Override
    public synchronized void onUseGodPower() {

    }
}