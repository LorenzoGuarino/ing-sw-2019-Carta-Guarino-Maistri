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

public class ServerAdapter implements Runnable
{
    private enum Commands {
        CONVERT_STRING,
        STOP
    }
    private Commands nextCommand;
    private String convertStringParam;

    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private List<ServerObserver> observers = new ArrayList<>();


    public ServerAdapter(Socket server)
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


    public synchronized void stop()
    {
        nextCommand = Commands.STOP;
        notifyAll();
    }

    public void SendHello() {

        String cmd = "<cmd><id>clt_hello</id></cmd>";
        SendConmand(cmd);
    }

    public void SendConmand(String cmd) {
        try {
            if(output != null)
                output.writeObject(cmd);
        }
        catch (IOException e) {
        }
    }

    public synchronized void requestConversion(String input)
    {
        nextCommand = Commands.CONVERT_STRING;
        convertStringParam = input;
        notifyAll();
    }

    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
            handleServerConnection();
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        }

        try {
            server.close();
        } catch (IOException e) { }
    }

    private void handleServerConnection() throws IOException {
        System.out.println("Connected to " + server.getInetAddress().getHostAddress());

        try {
            while (true) {
                /* read a String from the stream  */
                Object next = input.readObject();
                String strCmd = (String)next;
                String strResponseCmd = "";

                // convert strCmd into an XML object and evaluate received command

                Document doc = Utils.ParseStringToXMLDocument( strCmd );

                if(doc.hasChildNodes()) {
                    Node root = doc.getFirstChild();

                    if(root.getNodeName().equals("cmd")) {
                        if(root.hasChildNodes()) {
                            NodeList nodes = root.getChildNodes();
                            Node node;

                            String cmdID = "";
                            Node cmdData = null;

                            for(int i=0; i<nodes.getLength(); i++)
                            {
                                node = nodes.item(i);

                                if(node.getNodeName().equals("id"))
                                {
                                    cmdID = node.getTextContent();
                                }
                                else if(node.getNodeName().equals("data"))
                                {
                                    cmdData = node;
                                }
                            }

                            if(!cmdID.isEmpty()) {
                                if(cmdID.equals("srv_hello")) {
                                    onHello();
                                }
//                                else if(cmdID.equals("clt_xxxx"))  {
//                                    //...
//                                }
//                                else if(cmdID.equals("clt_xxxx"))  {
//                                    //...
//                                }
//                                else if(cmdID.equals("clt_xxxx")) {
//                                    //...
//                                }
//                                else if(cmdID.equals("clt_xxxx")) {
//                                    //...
//                                }
//                                else if(cmdID.equals("clt_xxxx")) {
//                                    //...
//                                }
//                                else if(cmdID.equals("clt_xxxx")) {
//                                    //...
//                                }
                            }
                        }
                    }
                }

                // prepare answer for client and return it to client

                if(!strResponseCmd.isEmpty())
                    SendConmand(strResponseCmd);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
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

}
