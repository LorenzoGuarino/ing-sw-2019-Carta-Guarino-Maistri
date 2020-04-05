package it.polimi.ingsw.PSP027.View;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;

import java.util.Scanner;

public class CLI implements Runnable, ClientObserver {

    private Scanner scanner = null;
    private Client client = null;
    private boolean bRun = false;

    public static void main( String[] args )
    {
        /* Instantiate a new CLI which will also receive events from
         * the server by implementing the ServerObserver interface */
        CLI cli = new CLI();
        cli.run();
    }

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

                if(cmd.equals("connect"))
                {
                    if(cmdline.length==2)
                        client.Connect(cmdline[1]);
                    else
                        OnInvalidCommandSyntax("connect");
                }
                else if(cmd.equals("disconnect"))
                {
                    client.Disconnect();
                }
                else if(cmd.equals("register"))
                {
                    if(cmdline.length==2)
                        client.Register(cmdline[1]);
                    else
                        OnInvalidCommandSyntax("register");
                }
                else if(cmd.equals("deregister"))
                {
                    client.Deregister();
                }
                else if(cmd.equals("bye"))
                {
                    client.Disconnect();
                    bRun = false;
                }
            }
            else
                System.out.println("Unrecognized command");

        } while (bRun);

        System.out.println("Thank you for having played Santorini game.\n\nSee u soon!");
        System.exit(0);
    }

    public void OnInvalidCommandSyntax(String cmd)
    {
        if(cmd.equals("connect"))
            System.out.println("Missing address from connect command.Syntax:\n connect ip:port\nwhere port is optional");
        else if(cmd.equals("register"))
            System.out.println("Missing nickname from register command. Syntax:\n register nickname");
    }

    @Override
    public void OnConnected()
    {
        System.out.println("Connected: Available commands:");
        System.out.println("  register nickname");
        System.out.println("  disconnect");
        System.out.println("  bye");

    }

    @Override
    public void OnDisconnected()
    {
        System.out.println("You are not actually connected to server.");
        System.out.println("Available commands:");
        System.out.println("  connect ip:port (this will let you connect to Santorini server.\r\n                   Port is optional. If not specified the default value 2705 will be used)");
        System.out.println("  bye (to quit the game)");
    }

    @Override
    public void OnRegistered()
    {
        System.out.println("Registration succeded");
        System.out.println("Available commands:");
        System.out.println("  deregister");
        System.out.println("  disconnect");
        System.out.println("  bye");
    }

    @Override
    public void OnDeregistered()
    {
        System.out.println("Available commands:");
        System.out.println("  register nickname");
        System.out.println("  disconnect");
        System.out.println("  bye");
    }

    @Override
    public void OnConnectionError()
    {

    }
}
