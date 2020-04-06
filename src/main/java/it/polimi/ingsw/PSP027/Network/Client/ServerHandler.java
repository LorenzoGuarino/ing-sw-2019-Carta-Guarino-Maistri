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

    private List<ServerObserver> observers = new ArrayList<>();

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
     * Method used by the others to actually send to command
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
     * Methods that sets the command to send with SendCommand() method *
     *******************************************************************/

    /**
     * Method that sends the command Register
     * @param nickname nickname to register the user with
     */

    public void SendRegisterCommand(String nickname)
    {
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Register.toString() + "</id><data><nickname>" + nickname + "</nickname></data></cmd>";
        SendCommand(cmd);
    }

    /**
     * Method that sends the command Deregister
     */

    public void SendDeregisterCommand()
    {
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

    public synchronized void Stop()
    {
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
                System.out.println("server connection closed");
            else
                System.out.println("server has died");
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
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private void handleServerConnection() throws IOException, ClassNotFoundException {
        System.out.println("Connected to " + server.getInetAddress().getHostAddress());

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
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        System.out.println("Disconnected from " + server.getInetAddress().getHostAddress());
    }

    /**
     * Method that fires the OnRegister() method in the client, processing the command received from the server
     * @param data xml to process
     * @throws IOException
     * @throws ClassNotFoundException
     */

    private synchronized void FireOnRegister(Node data) throws IOException, ClassNotFoundException
    {
        /** Possible return data
        * "<data><retcode>FAIL</retcode><reason>Nickname already present</reason></data>";
        * "<data><retcode>SUCCESS</retcode></data>";
        * "<data><retcode>FAIL</retcode><reason>Missing nickname</reason></data>";
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
                observersCpy = new ArrayList<>(observers);
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

    private synchronized void FireOnDeregister() throws IOException, ClassNotFoundException
    {
        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.onDeregister();
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
            observersCpy = new ArrayList<>(observers);
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
            observersCpy = new ArrayList<>(observers);
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
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer: observersCpy) {
            observer.onDisconnected();
        }
    }
}
