package it.polimi.ingsw.PSP027.Network.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.polimi.ingsw.PSP027.Model.Lobby;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import it.polimi.ingsw.PSP027.Utils.Utils;

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
     * the input is used to listen to any command that the client will send to server
     * the output is used by the server to send commands to the client
     */

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress().getHostAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method that handles the connection to the client parsing the commands received by the client in XML format
     * with this method the server keeps listening to what the client might send to it
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
                    Node root = doc.getFirstChild();

                    if(root.getNodeName().equals("cmd")) {
                        if(root.hasChildNodes()) {
                            NodeList nodes = root.getChildNodes();
                            Node node;

                            String cmdID = "";
                            Node cmdData = null;

                            // determine received command
                            for(int i = 0; i < nodes.getLength(); i++)
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

                            // process received command
                            if(!cmdID.isEmpty()) {
                                if(cmdID.equals("clt_hello")) {
                                    strResponseCmd = OnHello();
                                }
                                else if(cmdID.equals("clt_deregister")) {

                                    OnDeregister(cmdData);
                                    return;
                                }
                                else if(cmdID.equals("clt_register")) {

                                    strResponseCmd = OnRegister(cmdData);
                                }
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

    /**
     * Method used by the server to send commands to its clients, the command is sent is the stream output is not null
     * @param cmd command that the server wants to send to the client
     */

    public void SendConmand(String cmd) {
        try {
            if(output != null)
                output.writeObject(cmd);
        }
        catch (IOException e) {
        }
    }

    private String OnHello(){
        System.out.println("Got hello from ");
        String ret = "<cmd><id>srv_hello</id></cmd>";
        return ret;
    }

    private String OnRegister(Node data){

        String strRet = "<cmd><id>srv_registered</id><data><retcode>FAIL</retcode><reason>Invalid request</reason></data></cmd>";
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
                    strRet = "<cmd><id>srv_registered</id><data><retcode>FAIL</retcode><reason>Nickname already present</reason></data></cmd>";
                else
                    strRet = "<cmd><id>srv_registered</id><data><retcode>SUCCESS</retcode></data></cmd>";
            }
            else
                strRet = "<cmd><id>srv_registered</id><data><retcode>FAIL</retcode><reason>Missing nickname</reason></data></cmd>";
        }

        return strRet;
    }

    private void OnDeregister(Node data){

        System.out.println("Received bye from " + nickname);
        lobby.deregisterPlayer();
    }

}
