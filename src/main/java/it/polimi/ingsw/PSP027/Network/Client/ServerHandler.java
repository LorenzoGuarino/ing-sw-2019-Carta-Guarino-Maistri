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


    public ServerHandler(Socket server)
    {
        this.server = server;
    }


    public void addObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }


    public void removeObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void SendRegisterCommand(String nickname)
    {
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Register.toString() + "</id><data><nickname>" + nickname + "</nickname></data></cmd>";
        SendCommand(cmd);
    }

    public void SendDeregisterCommand()
    {
        bManualDisconnection = true;
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Deregister.toString() + "</id></cmd>";
        SendCommand(cmd);
    }

    public void SendHello() {

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.clt_Hello.toString() + "</id></cmd>";
        SendCommand(cmd);
    }

    public synchronized void SendCommand(String cmd) {
        try {
            if(output != null)
                output.writeObject(cmd);
        }
        catch (IOException e) {
        }
    }

    public synchronized void Stop()
    {
        bManualDisconnection = true;
        bRun = false;
        try {
            server.close();
        } catch (IOException e) { }
    }

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
            onDisconnected();
        } catch (IOException | ClassNotFoundException e) { }

    }

    private void handleServerConnection() throws IOException, ClassNotFoundException {
        System.out.println("Connected to " + server.getInetAddress().getHostAddress());

        onConnected();

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
                                    onHello();
                                    break;
                                case srv_Registered:
                                    onRegister(cmdData);
                                    break;
                                case srv_Deregistered:
                                    onDeregister();
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

    private synchronized void onRegister(Node data) throws IOException, ClassNotFoundException
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

    private synchronized void onDeregister() throws IOException, ClassNotFoundException
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

    private synchronized void onHello() throws IOException, ClassNotFoundException
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

    private synchronized void onConnected() throws IOException, ClassNotFoundException
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

    private synchronized void onDisconnected() throws IOException, ClassNotFoundException
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
