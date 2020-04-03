package it.polimi.ingsw.PSP027.Network.Client;

import it.polimi.ingsw.PSP027.Network.Server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Elisa Maistri
 */

public class Client implements Runnable, ServerObserver
{
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Socket server = null;
    private ServerHandler serverHandler = null;
    private enum ConnectionStatus {
        Disconnected,
        Connecting,
        Connected,
        Disconnecting,
    }

    private enum RegistrationStatus {
        Unregistered,
        Registering,
        Registered,
        Deregistering,
    }

    private RegistrationStatus regStatus = RegistrationStatus.Unregistered;
    private ConnectionStatus connStatus = ConnectionStatus.Disconnected;
    
    public static void main( String[] args )
    {
        /* Instantiate a new Client which will also receive events from
         * the server by implementing the ServerObserver interface */
        Client client = new Client();
        client.run();
    }

    /**
     * Method that creates a socket to connect with the server, and if successful
     * create the adapter with a separate thread that will communicate with the server
     */

    @Override
    public void run()
    {
        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */

        Scanner scanner = new Scanner(System.in);

        System.out.println("Not connected to server.\r\nAvailable commands:");
        System.out.println("  connect ip:port (port is optional)");
        System.out.println("  bye");

        do {

            String str = scanner.nextLine();
            String[] cmdline = str.split(" ");
            String cmd;

            if(cmdline.length > 0)
            {
                cmd = cmdline[0];

                synchronized (this) {
                    /* reset the variable that contains the next string to be consumed
                     * from the server */
                    response = null;

                    if(connStatus == ConnectionStatus.Disconnected)
                    {
                        if(cmd.equals("connect"))
                        {
                            /* open a connection to the server */
                            if (cmdline.length == 2) {
                                try {
                                    connStatus = ConnectionStatus.Connecting;
                                    server = new Socket(cmdline[1], Server.SOCKET_PORT);
                                } catch (IOException e) {
                                    System.out.println("server " + cmdline[1] + " unreachable. Try another address");
                                }

                                /* Create the adapter that will allow communication with the server
                                 * in background, and start running its thread */
                                if (server != null) {
                                    System.out.println("Connected");
                                    serverHandler = new ServerHandler(server);
                                    serverHandler.addObserver(this);
                                    // start thread to manage connection in background
                                    Thread serverAdapterThread = new Thread(serverHandler);
                                    serverAdapterThread.start();
                                    connStatus = ConnectionStatus.Connected;
                                }
                                else
                                    connStatus = ConnectionStatus.Disconnected;
                            }
                        }
                        else if(cmd.equals("bye"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Not connected to server. Available commands:");
                            System.out.println("  connect ip:port (port is optional)");
                            System.out.println("  bye");
                        }
                    }
                    else if(connStatus == ConnectionStatus.Connected)
                    {
                        boolean printhelp = false;

                        if(cmd.equals("hello"))
                            serverHandler.SendHello();
                        else if(cmd.equals("bye"))
                            break;
                        else if(regStatus == RegistrationStatus.Unregistered)
                        {
                            if(cmd.equals("register"))
                            {
                                if (cmdline.length == 2)
                                {
                                    regStatus = RegistrationStatus.Registering;
                                    serverHandler.SendRegisterCommand(cmdline[1]);
                                } else
                                    System.out.println("Missing nickname from register command.\r\nSyntax: register nickname");
                            }
                            else
                                printhelp = true;
                        }
                        else if(regStatus == RegistrationStatus.Registered)
                        {
                            if (cmd.equals("deregister"))
                            {
                                regStatus = RegistrationStatus.Deregistering;
                                connStatus = ConnectionStatus.Disconnecting;
                                serverHandler.SendDeregisterCommand();
                            }
                            else
                                printhelp = true;
                        }

                        if(printhelp)
                        {
                            System.out.println("Unrecognized command. Available commands:");
                            if(regStatus == RegistrationStatus.Unregistered)
                                System.out.println("  register nickname");
                            if(regStatus == RegistrationStatus.Registered)
                                System.out.println("  deregister");
                            System.out.println("  hello");
                            System.out.println("  bye");
                        }
                    }
                }
            }

        } while (true);

        if(serverHandler != null)
            serverHandler.stop();
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
    public synchronized  void onRegistered()
    {
        System.out.println("Registration succeded");
        regStatus = RegistrationStatus.Registered;
        notifyAll();
    }
    @Override
    public synchronized void onDeregistered()
    {
        connStatus = ConnectionStatus.Disconnected;
        regStatus = RegistrationStatus.Unregistered;
        notifyAll();
    }

    @Override
    public synchronized void onRegistrationError(String error)
    {
        System.out.println("Registration failed: " + error);
        regStatus = RegistrationStatus.Unregistered;
        notifyAll();
    }
}
