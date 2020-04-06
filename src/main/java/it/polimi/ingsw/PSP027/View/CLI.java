package it.polimi.ingsw.PSP027.View;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;

import java.util.Scanner;

/**
 * @author Elisa Maistri
 */

public class CLI implements Runnable, ClientObserver {

    private Scanner scanner = null;
    private Client client = null;
    private boolean bRun = false;

    /**
     * Main method of the CLI which the user will run instantiating a new CLI
     * @param args main arguments
     */

    public static void main( String[] args )
    {
        /* Instantiate a new CLI which will also receive events from
         * the server by implementing the ServerObserver interface */
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * Method that checks if a command entered by the user is valid
     * @param command command to check
     * @return true if it's a valid command, otherwise false
     */

    public boolean IsAValidCommand(String command){

        if(command.length() > 0) {
            String[] str = command.split(" ");

            if (str[0].equals("connect"))
                return true;
            if (str[0].equals("disconnect"))
                return true;
            if (str[0].equals("bye"))
                return true;
            if (str[0].equals("register"))
                return true;
            if (str[0].equals("deregister"))
                return true;
        }

        return false;
    }

    /**
     * Method that is launched when the CLI starts and handles the action to perform in regard to the command entered by
     * by the user in an infinite cycle
     */

    @Override
    public void run()
    {
        System.out.println("Welcome to Santorini Game !");

        client = new Client();
        client.addObserver(this);
        // start thread to manage connection in background
        bRun = true;
        Thread clientThread = new Thread(client);
        clientThread.start();

        scanner = new Scanner(System.in);

        do {

            String command = scanner.nextLine();

            if(IsAValidCommand(command))
            {
                String[] cmdline = command.split(" ");
                command = "";
                String cmd;

                cmd = cmdline[0];

                switch (cmd) {
                    case "connect":
                        if (cmdline.length == 2)
                            client.Connect(cmdline[1]);
                        else
                            OnInvalidCommandSyntax("connect");
                        break;
                    case "disconnect":
                        client.Disconnect();
                        break;
                    case "register":
                        if (cmdline.length == 2)
                            client.Register(cmdline[1]);
                        else
                            OnInvalidCommandSyntax("register");
                        break;
                    case "deregister":
                        client.Deregister();
                        break;
                    case "bye":
                        client.Disconnect();
                        bRun = false;
                        break;

                        //here goes every command that the user will enter in the CLI
                }
            }
            else
                System.out.println("Unrecognized command");

        } while (bRun);

        System.out.println("Thank you for having played Santorini game.\n\nSee u soon!");
        System.exit(0);
    }

    /**
     * Method that tells the user why the command was invalid
     * @param cmd command that determines which error will be displayed to the user
     */

    public void OnInvalidCommandSyntax(String cmd)
    {
        if(cmd.equals("connect"))
            System.out.println("Missing address from connect command.Syntax:\n connect ip:port\nwhere port is optional");
        else if(cmd.equals("register"))
            System.out.println("Missing nickname from register command. Syntax:\n register nickname");
    }

    /* ***********************************************************************************
     * Methods fired by the client's methods triggered in particular connection statuses *
     *************************************************************************************/

    /**
     * Method of the ClientObserver interface that is fired by the client after connection
     */
    @Override
    public void OnConnected()
    {
        System.out.println("Connected: Available commands:");
        System.out.println("  register nickname");
        System.out.println("  disconnect");
        System.out.println("  bye");

    }

    /**
     * Method of the ClientObserver interface that is fired by the client if it has been disconnected from the server
     */

    @Override
    public void OnDisconnected()
    {
        System.out.println("You are not actually connected to server.");
        System.out.println("Available commands:");
        System.out.println("  connect ip:port (this will let you connect to Santorini server.\r\nPort is optional. If not specified the default value 2705 will be used)");
        System.out.println("  bye (to quit the game)");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has registered
     */

    @Override
    public void OnRegistered()
    {
        System.out.println("Registration succeded");
        System.out.println("Available commands:");
        System.out.println("  deregister");
        System.out.println("  disconnect");
        System.out.println("  bye");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has been deregistered
     */

    @Override
    public void OnDeregistered()
    {
        System.out.println("Available commands:");
        System.out.println("  register nickname");
        System.out.println("  disconnect");
        System.out.println("  bye");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to connect
     */

    @Override
    public void OnConnectionError()
    {

    }
}
