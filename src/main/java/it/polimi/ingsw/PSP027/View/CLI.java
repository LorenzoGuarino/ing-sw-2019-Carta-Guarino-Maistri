package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;

import javax.swing.border.MatteBorder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Elisa Maistri
 */

public class CLI implements Runnable, ClientObserver {

    private Client client = null;
    private boolean bRun = false;
    private String cmdLine = "";
    private int requiredgods = 0;
    private List<String> gods = null;
    private boolean abortUserInput = false;
    private List<String> players = null;

    private static String DISCONNECT_COMMAND =  "disconnect";
    private static String BYE_COMMAND =  "bye";
    private static String CONNECT_COMMAND = "connect";
    private static String REGISTER_COMMAND = "register";
    private static String DEREGISTER_COMMAND = "deregister";
    private static String SEARCHMATCH_COMMAND = "searchmatch";
    private static String CHOSENGODS_COMMAND = "chosengods";
    private static String CHOSENGOD_COMMAND = "chosengod";
    private static String CHOSENFIRSTPLAYER_COMMAND = "firstplayerchosen";
    private static String PLAY_COMMAND = "play";

    private static String DISCONNECT_COMMAND_LABEL =  "  " + DISCONNECT_COMMAND + " (to disconnect from server)";
    private static String BYE_COMMAND_LABEL =  "  " + BYE_COMMAND + " (to quit the game)";
    private static String CONNECT_COMMAND_LABEL = "  " + CONNECT_COMMAND + " ip:port (this will let you connect to Santorini server. Port is optional. If not specified the default value 2705 will be used)";
    private static String REGISTER_COMMAND_LABEL =  "  " + REGISTER_COMMAND + " nickname (to register yourself within Santorini game using given nickname)";
    private static String DEREGISTER_COMMAND_LABEL =  "  " + DEREGISTER_COMMAND + " (to deregister yourself from Santorini game)";
    private static String PLAY_COMMAND_LABEL = "  " + PLAY_COMMAND + " (to start a new match)";
    private static String AVAILABLE_COMMANDS_LABEL = "Available commands:";
    private static String CONNECTED_LABEL = "Successfully connected to server.";
    private static String DISCONNECTED_LABEL = "You are not actually connected to server.";
    private static String QUIT_GAME_LABEL = "Thank you for having played Santorini game.\n\nSee you soon!";
    private static String CHOOSE_MATCH_TYPE_LABEL = "Please, enter the number of opponent players you wanna play with (choose between 1 or 2)";
    private static String SEARCHING_MATCH_LABEL = "Searching match ... please wait ...";

    /* *********************************************************************************************************
     * ********************************* UTILITY STRINGS FOR CLI RENDERING *********************************** *
     * *********************************************************************************************************/

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public void ColoredPrintRGB(String txt, int fr, int fg, int fb, int br, int bg, int bb)
    {
        String forecolor = "\033[38;2;" + Integer.toString(fr) + ";" + Integer.toString(fg) + ";" + Integer.toString(fb);
        String background = "\033[48;2;" + Integer.toString(br) + ";" + Integer.toString(bg) + ";" + Integer.toString(bb);
        System.out.print(forecolor);
        System.out.print(background);
        System.out.print(txt);
        System.out.print(RESET);
    }

    public void ColoredPrintLnRGB(String txt, int fr, int fg, int fb, int br, int bg, int bb)
    {
        String forecolor = "\033[38;2;" + Integer.toString(fr) + ";" + Integer.toString(fg) + ";" + Integer.toString(fb);
        String background = "\033[48;2;" + Integer.toString(br) + ";" + Integer.toString(bg) + ";" + Integer.toString(bb);
        System.out.print(forecolor);
        System.out.print(background);
        System.out.println(txt);
        System.out.print(RESET);
    }

    public void ColoredPrint(String txt, String forecolor, String background)
    {
        System.out.print(forecolor);
        System.out.print(background);
        System.out.print(txt);
        System.out.print(RESET);
    }

    public void ColoredPrintLn(String txt, String forecolor, String background)
    {
        System.out.print(forecolor);
        System.out.print(background);
        System.out.println(txt);
        System.out.print(RESET);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /* ************************************************************************************************* */

    private enum CLIConnectionState
    {
        cli_undetermined,
        cli_disconnected,
        cli_connecting,
        cli_Connected,
        cli_disconnecting,
    }

    private enum CLIGameState
    {
        cli_Deregistered,
        cli_Registering,
        cli_Registered,
        cli_Deregistering,
        cli_ChoosingMatch,
        cli_ChoosingGods,
        cli_ChoosingGod,
        cli_ChoosingFirstPlayer,
        cli_WaitForSomethingToHappen
    }

    /**
     * class to manage the user input
     */

    private class Keyboardinput implements Runnable{

        private Scanner scanner = null;
        public boolean bRun = true;

        @Override
        public void run() {

            scanner = new Scanner(System.in);

            while(bRun)
            {
                cmdLine = scanner.nextLine();
            }
        }
    }

    private Keyboardinput kbinput = null;
    private CLIConnectionState connstate = CLIConnectionState.cli_undetermined;
    private CLIGameState gamestate = CLIGameState.cli_Deregistered;

    /**
     * Main method of the CLI which the user will run instantiating a new CLI
     * @param args main arguments
     */

    public static void main( String[] args ) {
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
     * Method used when waiting for a user input
     */

    private void WaitForUserInput() {
        try {
            abortUserInput = false;
            cmdLine = "";
            while (cmdLine.isEmpty() && !abortUserInput)
                TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that is launched when the CLI starts and handles the action to perform in regard to the command entered by
     * by the user in an infinite cycle
     */

    @Override
    public void run() {
        System.out.println("Welcome to Santorini Game !");

        client = new Client();
        client.addObserver(this);
        // start thread to manage connection in background
        bRun = true;
        Thread clientThread = new Thread(client);
        clientThread.start();

        kbinput = new Keyboardinput();
        Thread kbinputThread = new Thread(kbinput);
        kbinputThread.start();

        while(bRun)
        {
            switch(connstate) {

                case cli_disconnected:
                {
                    System.out.println(AVAILABLE_COMMANDS_LABEL);
                    System.out.println(CONNECT_COMMAND_LABEL);
                    System.out.println(BYE_COMMAND_LABEL);

                    WaitForUserInput();

                    String[] cmdlineParts = cmdLine.split(" ");
                    if(cmdlineParts[0].equals(CONNECT_COMMAND))
                    {
                        connstate = CLIConnectionState.cli_connecting;
                        if (cmdlineParts.length == 2)
                            client.Connect(cmdlineParts[1]);
                        else
                            OnInvalidCommandSyntax(CONNECT_COMMAND);
                    }
                    else if(cmdlineParts[0].equals(BYE_COMMAND))
                    {
                        client.Disconnect();
                        bRun = false;
                    }
                }
                break;

                case cli_connecting:
                case cli_disconnecting:
                {
                    System.out.print(".");
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;

                case cli_Connected:
                {
                    switch(gamestate)
                    {
                        case cli_Deregistered:
                        {
                            ColoredPrint(AVAILABLE_COMMANDS_LABEL, WHITE, BLUE_BACKGROUND);
                            System.out.print("");
                            //System.out.println(AVAILABLE_COMMANDS_LABEL);
                            System.out.println(REGISTER_COMMAND_LABEL);
                            System.out.println(DISCONNECT_COMMAND_LABEL);
                            System.out.println(BYE_COMMAND_LABEL);
                            WaitForUserInput();
                        }
                        break;

                        case cli_Registering:
                        case cli_Deregistering:
                        {
                            System.out.print(".");
                            try {
                                TimeUnit.MILLISECONDS.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                        case cli_Registered:
                        {
                            System.out.println(AVAILABLE_COMMANDS_LABEL);
                            System.out.println(PLAY_COMMAND_LABEL);
                            System.out.println(DEREGISTER_COMMAND_LABEL);
                            System.out.println(DISCONNECT_COMMAND_LABEL);
                            System.out.println(BYE_COMMAND_LABEL);
                            WaitForUserInput();
                        }
                        break;

                        case cli_ChoosingMatch:
                        {
                            System.out.println(CHOOSE_MATCH_TYPE_LABEL);
                            WaitForUserInput();
                            int players = Integer.parseInt(cmdLine);
                            if((players>=1) && (players<=2))
                            {
                                // add local player to count as server search need to count all players...
                                players++;
                                System.out.println(SEARCHING_MATCH_LABEL);
                                // create an emulated command with proper syntax for
                                // processing entered command section
                                cmdLine = SEARCHMATCH_COMMAND + " " + Integer.toString(players);
                            }
                        }
                        break;

                        case cli_ChoosingGods:
                        {
                            if(gods != null) {
                                clearScreen();
                                System.out.println("Please choose " + Integer.toString(requiredgods) + " gods among the listed ones:");

                                for (int i = 0; i < gods.size(); i++) {
                                    System.out.println(gods.get(i));
                                }

                                System.out.println("Enter a comma separated list of gods");
                                System.out.println("For a description of a god's power, enter \"help god's name\"");
                                WaitForUserInput();

                                String[] helpcmd = cmdLine.split(" ");
                                if(helpcmd[0].equals("help")) {
                                    if(helpcmd.length == 2) {
                                        switch (helpcmd[1]) {
                                            case GodCard.APOLLO:
                                                System.out.println(GodCard.APOLLO_D);
                                                break;
                                            case GodCard.ARTEMIS:
                                                System.out.println(GodCard.ARTEMIS_D);
                                                break;
                                            case GodCard.ATHENA:
                                                System.out.println(GodCard.ATHENA_D);
                                                break;
                                            case GodCard.ATLAS:
                                                System.out.println(GodCard.ATLAS_D);
                                                break;
                                            case GodCard.DEMETER:
                                                System.out.println(GodCard.DEMETER_D);
                                                break;
                                            case GodCard.HEPHAESTUS:
                                                System.out.println(GodCard.HEPHAESTUS_D);
                                                break;
                                            case GodCard.MINOTAUR:
                                                System.out.println(GodCard.MINOTAUR_D);
                                                break;
                                            case GodCard.PAN:
                                                System.out.println(GodCard.PAN_D);
                                                break;
                                            case GodCard.PROMETHEUS:
                                                System.out.println(GodCard.PROMETHEUS_D);
                                                break;
                                        }
                                        System.out.println("\n\n");
                                    }
                                }
                                else {
                                    String[] chosengods = cmdLine.split(",");

                                    if (chosengods.length == requiredgods) {

                                        boolean bFound = false;

                                        for (String chosengod : chosengods) {
                                            chosengod = chosengod.trim();
                                            bFound = false;
                                            for (int i = 0; i < gods.size(); i++) {
                                                if(gods.get(i).equals(chosengod)) {
                                                    bFound = true;
                                                    break;
                                                }
                                            }
                                            if(bFound == false) {
                                                break;
                                            }

                                        }

                                        if(bFound == true) {
                                            // create an emulated command with proper syntax for
                                            // processing entered command section
                                            cmdLine = CHOSENGODS_COMMAND + " ";
                                            for (String chosengod : chosengods) {
                                                cmdLine += chosengod.trim() + " ";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                        case cli_ChoosingGod:
                        {
                            if(gods != null) {
                                clearScreen();
                                if (gods.size() > 1) {
                                    System.out.println("Please choose your god from the listed ones:");
                                    for (int i = 0; i < gods.size(); i++) {
                                        System.out.println(gods.get(i));
                                    }

                                    System.out.println("For a description of a god's power, enter \"help god's name\"");
                                    WaitForUserInput();

                                    String[] helpcmd = cmdLine.split(" ");
                                    if(helpcmd[0].equals("help")) {
                                        if(helpcmd.length == 2) {
                                            switch (helpcmd[1]) {
                                                case GodCard.APOLLO:
                                                    System.out.println(GodCard.APOLLO_D);
                                                    break;
                                                case GodCard.ARTEMIS:
                                                    System.out.println(GodCard.ARTEMIS_D);
                                                    break;
                                                case GodCard.ATHENA:
                                                    System.out.println(GodCard.ATHENA_D);
                                                    break;
                                                case GodCard.ATLAS:
                                                    System.out.println(GodCard.ATLAS_D);
                                                    break;
                                                case GodCard.DEMETER:
                                                    System.out.println(GodCard.DEMETER_D);
                                                    break;
                                                case GodCard.HEPHAESTUS:
                                                    System.out.println(GodCard.HEPHAESTUS_D);
                                                    break;
                                                case GodCard.MINOTAUR:
                                                    System.out.println(GodCard.MINOTAUR_D);
                                                    break;
                                                case GodCard.PAN:
                                                    System.out.println(GodCard.PAN_D);
                                                    break;
                                                case GodCard.PROMETHEUS:
                                                    System.out.println(GodCard.PROMETHEUS_D);
                                                    break;
                                            }
                                            System.out.println("\n\n");
                                        }
                                    }
                                    else {
                                        String chosengod = cmdLine;

                                        chosengod = chosengod.trim();
                                        boolean bFound = false;
                                        for (int i = 0; i < gods.size(); i++) {
                                            if(gods.get(i).equals(chosengod)) {
                                                bFound = true;
                                                break;
                                            }
                                        }
                                        if(bFound == false) {
                                            break;
                                        }

                                        if(bFound == true) {
                                            // create an emulated command with proper syntax for
                                            // processing entered command section
                                            cmdLine = CHOSENGOD_COMMAND + " " + chosengod;
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Your god is: " + gods.get(0));
                                    cmdLine = CHOSENGOD_COMMAND + " " + gods.get(0);
                                }
                            }
                        }
                            break;
                        case cli_ChoosingFirstPlayer:
                        {
                            if(players != null)
                            {
                                clearScreen();
                                System.out.println("Please choose the player that will be the first one to place its workers on the board and then the first to play the game between the listed players:");
                                for (int i = 0; i < players.size(); i++) {
                                    System.out.println(players.get(i));
                                }

                                WaitForUserInput();

                                String chosenplayer = cmdLine;
                                chosenplayer = chosenplayer.trim();

                                boolean bFound = false;

                                for(String player : players) {
                                    if(player.equals(cmdLine)) {
                                        bFound = true;
                                        break;
                                    }
                                }

                                if(bFound == false) {
                                    break;
                                }
                                else {
                                    // create an emulated command with proper syntax for
                                    // processing entered command section
                                    cmdLine = CHOSENFIRSTPLAYER_COMMAND + " " + chosenplayer;
                                }

                            }
                        }
                            break;

                        case cli_WaitForSomethingToHappen:
                        {
                            // in this state user is allowed to enter commands on commandline
                            // which will be processed on switch exit
                            try {
                                TimeUnit.MILLISECONDS.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }


                    // PROCESSING ENTERED COMMAND
                    if(!cmdLine.isEmpty())
                    {
                        String[] cmdlineParts = cmdLine.split(" ");

                        /* ************************** NOT GAME RELATED COMMANDS ********************** */
                        if(cmdlineParts[0].equals(DISCONNECT_COMMAND))
                        {
                            connstate = CLIConnectionState.cli_disconnecting;
                            client.Disconnect();
                        }
                        else if(cmdlineParts[0].equals(BYE_COMMAND))
                        {
                            connstate = CLIConnectionState.cli_disconnecting;
                            client.Disconnect();
                            bRun = false;
                        }
                        else if(cmdlineParts[0].equals(REGISTER_COMMAND))
                        {
                            if (cmdlineParts.length == 2)
                            {
                                gamestate = CLIGameState.cli_Registering;
                                client.Register(cmdlineParts[1]);
                            }
                            else
                                OnInvalidCommandSyntax(REGISTER_COMMAND);
                        }
                        else if(cmdlineParts[0].equals(DEREGISTER_COMMAND))
                        {
                            gamestate = CLIGameState.cli_Deregistering;
                            client.Deregister();
                        }
                        else if(cmdlineParts[0].equals(PLAY_COMMAND))
                        {
                            gamestate = CLIGameState.cli_ChoosingMatch;
                        }

                        /* ******************* GAME RELATED COMMANDS ******************** */

                        else if(cmdlineParts[0].equals(SEARCHMATCH_COMMAND))
                        {
                            if (cmdlineParts.length == 2)
                            {
                                gamestate = CLIGameState.cli_WaitForSomethingToHappen;
                                client.SearchMatch(Integer.parseInt(cmdlineParts[1]));
                            }
                        }
                        else if(cmdlineParts[0].equals(CHOSENGODS_COMMAND))
                        {
                            if (cmdlineParts.length == (requiredgods+1))
                            {
                                gamestate = CLIGameState.cli_WaitForSomethingToHappen;
                                List<String> chosengods = new ArrayList<String>();
                                for (int i = 1; i < (requiredgods+1); i++) {
                                    chosengods.add(cmdlineParts[i]);
                                }
                                client.ChosenGods(chosengods);
                                System.out.println("Please wait ...");
                            }
                        }
                        else if(cmdlineParts[0].equals(CHOSENGOD_COMMAND))
                        {
                            if (cmdlineParts.length == 2)
                            {
                                gamestate = CLIGameState.cli_WaitForSomethingToHappen;
                                client.ChosenGod(cmdlineParts[1]);
                            }
                        }
                        else if (cmdlineParts[0].equals(CHOSENFIRSTPLAYER_COMMAND))
                        {
                            if(cmdlineParts.length == 2)
                            {
                                gamestate = CLIGameState.cli_WaitForSomethingToHappen;
                                client.ChosenFirstPlayer(cmdlineParts[1]);
                            }
                        }
                        cmdLine = "";
                    }
                }
                break;
            }
        }

        kbinput.bRun = false;

        System.out.println(QUIT_GAME_LABEL);
        System.exit(0);
    }

    /**
     * Method that tells the user why the command was invalid
     * @param cmd command that determines which error will be displayed to the user
     */

    public void OnInvalidCommandSyntax(String cmd)
    {
        if(cmd.equals("connect"))
            System.out.println("Missing address from connect command.Syntax:\n connect ip:port where port is optional");
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
    public void OnConnected() {
        abortUserInput = true;
        System.out.println(CONNECTED_LABEL);
        connstate = CLIConnectionState.cli_Connected;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to connect
     */

    @Override
    public void OnConnectionError() {
        abortUserInput = true;
        System.out.println(DISCONNECTED_LABEL);
        connstate = CLIConnectionState.cli_disconnected;
        gamestate = CLIGameState.cli_Deregistered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if it has been disconnected from the server
     */

    @Override
    public void OnDisconnected() {
        System.out.println(DISCONNECTED_LABEL);
        abortUserInput = true;
        gamestate = CLIGameState.cli_Deregistered;
        connstate = CLIConnectionState.cli_disconnected;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has registered
     */

    @Override
    public void OnRegistered() {
        abortUserInput = true;
        gamestate = CLIGameState.cli_Registered;
        System.out.println("Welcome " + client.getNickname());
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to register
     */

    @Override
    public void OnRegistrationError(String error) {
        gamestate = CLIGameState.cli_Deregistered;
        System.out.println("Registration failed: " + error);
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has been deregistered
     */

    @Override
    public void OnDeregistered() {
        abortUserInput = true;
        gamestate = CLIGameState.cli_Deregistered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when a user leaves the match
     * @param nickname nicknames of the user that left the match
     */

    @Override
    public void OnLeftMatch(String nickname) {
        System.out.println(nickname + " has left the game.");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after connection
     */
    @Override
    public void OnChooseMatchType() {
        abortUserInput = true;
        gamestate = CLIGameState.cli_ChoosingMatch;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when entering a match
     */

    @Override
    public void OnEnteringMatch(List<String> players) {
        System.out.println("Entering match. Current players:");

        for(int i=0; i<players.size(); i++)
        {
            System.out.println(players.get(i));
        }

        System.out.println("Waiting for other players to join match");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when it has entered a match
     */

    @Override
    public void OnEnteredMatch(List<String> players) {
        System.out.println("Entered match. Current players:");

        for(int i=0; i<players.size(); i++)
        {
            System.out.println(players.get(i));
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the list of gods to use in the match
     * @param requiredgods number of gods that the client must choose
     * @param gods gods' names chosen by the user
     */

    @Override
    public void OnChooseGods(int requiredgods, List<String> gods) {
        abortUserInput = true;
        gamestate = CLIGameState.cli_ChoosingGods;
        this.requiredgods = requiredgods;
        this.gods = gods;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the god to play with
     * @param chosengods chosen gods to set as the gods managed by the cli that will communicate them to the user
     */

    @Override
    public void OnChooseGod(List<String> chosengods) {
        abortUserInput = true;
        gamestate = CLIGameState.cli_ChoosingGod;
        this.gods = chosengods;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the first player that
     * will place its workers and start playing
     * @param players players in the match from which the selection must be done
     */

    @Override
    public void OnChooseFirstPlayer(List<String> players) {
        abortUserInput = true;
        gamestate = CLIGameState.cli_ChoosingFirstPlayer;
        this.players = players;
    }



    /**
     * Method of the ClientObserver interface that is fired by the client when there's a winner
     * @param nickname nickname of the winner
     */

    @Override
    public void OnWinner(String nickname) {
        if(client.getNickname().equals(nickname))
            System.out.println("You have won the game. Congrats !!");
        else
            System.out.println(nickname + " has won the game.");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when a user loses
     */

    @Override
    public void OnLoser() {
        System.out.println("You have lost! Better luck next time.");
    }

}
