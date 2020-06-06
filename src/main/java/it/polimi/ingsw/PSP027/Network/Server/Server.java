package it.polimi.ingsw.PSP027.Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * @author Elisa Maistri
 * Class Server that opens a socket to accept connections each with a new thread and handled by ClientHandler
 */

public class Server
{
    public final static int SOCKET_PORT = 2705;
    public static Lobby lobby = new Lobby();

    /**
     * Main method of the server, it opens the socket for the clients who want to connect and handles the dropping of their connection in the thread launched
     * @param args if args[0] is stated, it contains the port on which to open the socket, otherwise the default port will be used (2705)
     */

    public static void main(String[] args)
    {
        ServerSocket socket;
        try {

            int port = SOCKET_PORT;

            if(args.length > 0) {

                try {
                    port = Integer.parseInt(args[0]);
                }
                catch (NumberFormatException e) {
                    System.err.println("Argument" + args[0] + " must be an integer. Default port will be used !");
                    port = SOCKET_PORT;
                }
            }

            socket = new ServerSocket(port);

            InetAddress ip;
            String hostname;

            try {
                ip = InetAddress.getLocalHost();
                System.out.println("Server running on: " + ip.getHostAddress() + ":" + socket.getLocalPort());

            } catch (UnknownHostException e) {

                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client, lobby);

                Thread thread = new Thread(clientHandler, "client_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}

