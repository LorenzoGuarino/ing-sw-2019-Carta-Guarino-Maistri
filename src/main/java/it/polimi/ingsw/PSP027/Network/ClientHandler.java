package it.polimi.ingsw.PSP027.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Elisa Maistri
 */

public class ClientHandler implements Runnable
{
    private Socket client;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;


    ClientHandler(Socket client)
    {
        this.client = client;
    }


    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress().getHostAddress() + " connection dropped");
        }
    }

    public void SendConmand(String cmd) {
        try {
            if(output != null)
                output.writeObject(cmd);
        }
        catch (IOException e) {
        }
    }

    private void handleClientConnection() throws IOException {
        System.out.println("Connected to " + client.getInetAddress().getHostAddress());

        output = new ObjectOutputStream(client.getOutputStream());
        input = new ObjectInputStream(client.getInputStream());

        try {
            while (true) {
                /* read a String from the stream  */
                Object next = input.readObject();
                String strCmd = (String)next;
                String strResponseCmd = "";

                // convert strCmd into an XML object and evaluate received command

                Document doc = ParseStringToXMLDocument( strCmd );

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
                                    cmdID = node.getNodeValue();
                                }
                                else if(node.getNodeName().equals("data"))
                                {
                                    cmdData = node;
                                }
                            }

                            if(!cmdID.isEmpty()) {
                                if(cmdID.equals("clt_hello")) {
                                    strResponseCmd = "<cmd><id>srv_hello</id></cmd>";
                                }
                                else if(cmdID.equals("clt_xxxx"))  {
                                    //...
                                }
                                else if(cmdID.equals("clt_xxxx"))  {
                                    //...
                                }
                                else if(cmdID.equals("clt_xxxx")) {
                                    //...
                                }
                                else if(cmdID.equals("clt_xxxx")) {
                                    //...
                                }
                                else if(cmdID.equals("clt_xxxx")) {
                                    //...
                                }
                                else if(cmdID.equals("clt_xxxx")) {
                                    //...
                                }
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

        client.close();
    }

    private static Document ParseStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
