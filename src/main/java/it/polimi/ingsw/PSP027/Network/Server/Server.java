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

    public static void main(String[] args)
    {
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);

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

