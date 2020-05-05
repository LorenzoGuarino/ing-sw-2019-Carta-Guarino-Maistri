package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIController implements ClientObserver {
    public GUI Gui;
    public Client client = null;
    private boolean bRun = false;
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;

    /* ****************************************************** COMMANDS ************************************************** */

    private static String DISCONNECT_COMMAND = "disconnect";
    private static String BYE_COMMAND = "bye";
    private static String CONNECT_COMMAND = "connect";
    private static String REGISTER_COMMAND = "register";
    private static String DEREGISTER_COMMAND = "deregister";
    private static String SEARCHMATCH_COMMAND = "searchmatch";
    private static String CHOSENGODS_COMMAND = "chosengods";
    private static String CHOSENGOD_COMMAND = "chosengod";
    private static String CHOSENFIRSTPLAYER_COMMAND = "firstplayerchosen";
    private static String PLAY_COMMAND = "play";
    private static String WORKERSPOSITION_COMMAND = "workerspositionchosen";
    private static String CHOSENWORKER_COMMAND = "workerchosen";
    private static String CANDIDATECELLFORMOVE_COMMAND = "candidatecellchosen";
    private static String CANDIDATECELLFORBUILD_COMMAND = "candidatebuildcell";
    private static String CANDIDATECELLFOREND_COMMAND = "candidateendcell";
    private static String PASSMOVE_COMMAND = "movepassed";
    private static String PASSBUILD_COMMAND = "buildpassed";
    private static String PASSEND_COMMAND = "endpassed";

    /* ****************************************************************************************************************** */

    private enum GUIConnectionState {
        gui_undetermined,
        gui_disconnected,
        gui_connecting,
        gui_Connected,
        gui_disconnecting,
    }

    private enum GUIGameState {
        gui_Deregistered,
        gui_Registering,
        gui_Registered,
        gui_Deregistering,
        gui_ChoosingMatch,
        gui_ChoosingGods,
        gui_ChoosingGod,
        gui_ChoosingFirstPlayer,
        gui_ChoosingWorkersStartPosition,
        gui_ManagePlacingFirstWorker,
        gui_ManagePlacingSecondWorker,
        gui_ChoosingWorker,
        gui_CandidateCellsForMove,
        gui_CandidateCellsForOptMove,
        gui_CandidateCellsForBuild,
        gui_ChooseBuildOrDome,
        gui_CandidateCellsForOptBuild,
        gui_CandidateCellsForOptEnd,
        gui_PrintingUpdatedBoard,
        gui_WaitForSomethingToHappen
    }

    private GUIController.GUIConnectionState connstate = GUIController.GUIConnectionState.gui_undetermined;
    private GUIController.GUIGameState gamestate = GUIController.GUIGameState.gui_Deregistered;

    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */


    /* ****************************************************************************************************************** */

    public GUIController(GUI Gui) {
        this.Gui = Gui;
        this.initialize();
    }


    public void initialize()
    {
        client = new Client();
        client.addObserver(this);

        // start thread to manage connection in background
        bRun = true;
        Thread clientThread = new Thread(client);
        clientThread.start();
    }


//        String[] chosenposition = new String[2];
//
//       while (bRun) {
//
//            // SWITCH WHEN THE CONNECTION STATE CHANGES
//
//            switch (connstate) {
//
//                case gui_disconnected: {
//
//                }
//                break;
//
//                case gui_connecting:
//                case gui_disconnecting: {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//
//                case gui_Connected: {
//
//                    // SWITCH WHEN THE GAME STATE CHANGES, ONLY POSSIBLE IN THE CONNECTED STATE
//
//                    switch (gamestate) {
//                        case gui_Deregistered: {
//
//                        }
//                        break;
//
//                        case gui_Registering:
//                        case gui_Deregistering: {
//
//                        }
//                        break;
//
//                        case gui_Registered: {
//
//                        }
//                        break;
//
//                        case gui_ChoosingMatch: {
//
//                        }
//                        break;
//
//                        case gui_ChoosingGods: {
//
//                        }
//                        break;
//
//                        case gui_ChoosingGod: {
//
//                        }
//                        break;
//                        case gui_ChoosingFirstPlayer: {
//
//                        }
//                        break;
//
//                        case gui_ChoosingWorkersStartPosition: {
//
//                        }
//                        break;
//
//                        case gui_ManagePlacingFirstWorker: {
//
//                        }
//                        break;
//
//                        case gui_ManagePlacingSecondWorker: {
//
//                        }
//                        break;
//
//                        case gui_ChoosingWorker:  {
//
//                        }
//                        break;
//
//                        case gui_CandidateCellsForMove: {
//
//                        }
//                        break;
//                        case gui_CandidateCellsForOptMove: {
//
//                        }
//                        break;
//                        case gui_CandidateCellsForBuild: {
//
//                        }
//                        break;
//                        case gui_ChooseBuildOrDome: {
//
//                        }
//                        break;
//                        case gui_CandidateCellsForOptBuild: {
//
//                        }
//                        break;
//
//                        case gui_CandidateCellsForOptEnd: {
//
//                        }
//                        break;
//
//                        case gui_PrintingUpdatedBoard: {
//
//                        }
//                        break;
//
//                        case gui_WaitForSomethingToHappen: {
//
//                        }
//                        break;
//                    }
//
//
//                    // PROCESSING CHOSEN COMMAND
//
//                    if (chosen_cmd.length != 0) {
//
//                        /* ************************** CONNECTION RELATED COMMANDS ********************** */
//
//                        if (chosen_cmd[0].equals(DISCONNECT_COMMAND)) {
//                            connstate = GUIController.GUIConnectionState.gui_disconnecting;
//                            client.Disconnect();
//                        } else if (chosen_cmd[0].equals(BYE_COMMAND)) {
//                            connstate = GUIController.GUIConnectionState.gui_disconnecting;
//                            client.Disconnect();
//                            bRun = false;
//                        } else if (chosen_cmd[0].equals(REGISTER_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_Registering;
//                                client.Register(chosen_cmd[1]);
//                            } //else
//                                //OnInvalidCommandSyntax(REGISTER_COMMAND);
//                        } else if (chosen_cmd[0].equals(DEREGISTER_COMMAND)) {
//                            gamestate = GUIController.GUIGameState.gui_Deregistering;
//                            client.Deregister();
//                        } else if (chosen_cmd[0].equals(PLAY_COMMAND)) {
//                            gamestate = GUIController.GUIGameState.gui_ChoosingMatch;
//                        }
//
//                        /* *************************** GAME RELATED COMMANDS *************************** */
//
//                        else if (chosen_cmd[0].equals(SEARCHMATCH_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.SearchMatch(Integer.parseInt(chosen_cmd[1]));
//                            }
//                        } else if (chosen_cmd[0].equals(CHOSENGODS_COMMAND)) {
//                            if (chosen_cmd.length == (requiredgods + 1)) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                List<String> chosengods = new ArrayList<String>();
//                                for (int i = 1; i < (requiredgods + 1); i++) {
//                                    chosengods.add(chosen_cmd[i]);
//                                }
//                                client.ChosenGods(chosengods);
//                                //System.out.println(DEFAULT_BOLD + "\nPlease wait while the other players choose their gods among the ones you picked..." + RESET);
//                            }
//                        } else if (chosen_cmd[0].equals(CHOSENGOD_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.ChosenGod(chosen_cmd[1]);
//                            }
//                        } else if (chosen_cmd[0].equals(CHOSENFIRSTPLAYER_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.ChosenFirstPlayer(chosen_cmd[1]);
//                            }
//                        } else if (chosen_cmd[0].equals(WORKERSPOSITION_COMMAND)) {
//                            if (chosen_cmd.length == 3) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.ChosenWorkersFirstPositions(chosen_cmd[1], chosen_cmd[2]);
//                            }
//                        } else if (chosen_cmd[0].equals(CHOSENWORKER_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.ChosenWorker(chosen_cmd[1]);
//                            }
//                        } else if(chosen_cmd[0].equals(CANDIDATECELLFORMOVE_COMMAND)){
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.CandidateMove(chosen_cmd[1]);
//                            }
//                        } else if(chosen_cmd[0].equals(PASSMOVE_COMMAND)) {
//                            if (chosen_cmd.length == 1) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.passMove();
//                            }
//                        } else if(chosen_cmd[0].equals(CANDIDATECELLFORBUILD_COMMAND)) {
//                            if (chosen_cmd.length == 2 || (chosen_cmd.length == 3 && NicknameGodMap.get(client.getNickname()).equals("Atlas"))) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                if(NicknameGodMap.get(client.getNickname()).equals("Atlas")) {
//                                    client.CandidateBuildForAtlas(chosen_cmd[1], chosen_cmd[2]);
//                                }
//                                else {
//                                    client.CandidateBuild(chosen_cmd[1]);
//                                }
//                            }
//                        } else if(chosen_cmd[0].equals(PASSBUILD_COMMAND)) {
//                            if (chosen_cmd.length == 1) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.passBuild();
//                            }
//                        } else if(chosen_cmd[0].equals(CANDIDATECELLFOREND_COMMAND)) {
//                            if (chosen_cmd.length == 2) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.CandidateEnd(chosen_cmd[1]);
//                            }
//                        } else if(chosen_cmd[0].equals(PASSEND_COMMAND)) {
//                            if (chosen_cmd.length == 1) {
//                                gamestate = GUIController.GUIGameState.gui_WaitForSomethingToHappen;
//                                client.passEnd();
//                            }
//                        }
//                        //cmdLine = "";
//                    }
//                }
//                break;
//            }
//        }
//
//        System.exit(0);


    /* *********************************************************************************************************
     *       Methods fired by the client's methods that trigger a change of connection state or game state     *
     ***********************************************************************************************************/

    /**
     * Method of the ClientObserver interface that is fired by the client after connection
     */

    @Override
    public void OnConnected() {
        System.out.println("provaprovaprova");
        this.Gui.changePane();
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to connect
     */

    @Override
    public void OnConnectionError() {
        connstate = GUIController.GUIConnectionState.gui_disconnected;
        gamestate = GUIController.GUIGameState.gui_Deregistered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if it has been disconnected from the server
     */

    @Override
    public void OnDisconnected() {
        gamestate = GUIController.GUIGameState.gui_Deregistered;
        connstate = GUIController.GUIConnectionState.gui_disconnected;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has registered
     */

    @Override
    public void OnRegistered() {
        gamestate = GUIController.GUIGameState.gui_Registered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to register
     */

    @Override
    public void OnRegistrationError(String error) {
        gamestate = GUIController.GUIGameState.gui_Deregistered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has been deregistered
     */

    @Override
    public void OnDeregistered() {
        gamestate = GUIController.GUIGameState.gui_Deregistered;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when a user leaves the match
     *
     * @param nickname nicknames of the user that left the match
     */

    @Override
    public void OnLeftMatch(String nickname) {

    }

    /**
     * Method of the ClientObserver interface that is fired by the client after connection
     */
    @Override
    public void OnChooseMatchType() {
        gamestate = GUIController.GUIGameState.gui_ChoosingMatch;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when entering a match
     */

    @Override
    public void OnEnteringMatch(List<String> players) {

    }

    /**
     * Method of the ClientObserver interface that is fired by the client when it has entered a match
     */

    @Override
    public void OnEnteredMatch(List<String> players) {

    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the list of gods to use in the match
     *
     * @param requiredgods number of gods that the client must choose
     * @param gods         gods' names chosen by the user
     */

    @Override
    public void OnChooseGods(int requiredgods, List<String> gods) {
        gamestate = GUIController.GUIGameState.gui_ChoosingGods;
        this.requiredgods = requiredgods;
        this.gods = gods;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the god to play with
     *
     * @param chosengods chosen gods to set as the gods managed by the cli that will communicate them to the user
     */

    @Override
    public void OnChooseGod(List<String> chosengods) {
        gamestate = GUIController.GUIGameState.gui_ChoosingGod;
        this.gods = chosengods;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the first player that
     * will place its workers and start playing
     *
     * @param players players in the match from which the selection must be done
     */

    @Override
    public void OnChooseFirstPlayer(List<String> players) {
        gamestate = GUIController.GUIGameState.gui_ChoosingFirstPlayer;
        this.players = players;
    }


    /**
     * Method of the ClientObserver interface that is fired by the client when placing the workers on the board for the first time
     * @param nodes board in xml format that needs to be processed by the GUI when it prints the board
     * it also contains the list of players with their gods that needs to be saved
     */

    @Override
    public void OnChooseWorkerStartPosition(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_ChoosingWorkersStartPosition;

        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("players")) {

                Node player;
                if (node.hasChildNodes()) {
                    NodeList players = node.getChildNodes();

                    for (int j = 0; j < players.getLength(); j++) {
                        player = players.item(j);

                        if (player.getNodeName().equals("player")) {
                            String playerNickname = player.getAttributes().getNamedItem("nickname").getTextContent();
                            String playerGod = player.getAttributes().getNamedItem("god").getTextContent();

                            NicknameGodMap.put(playerNickname, playerGod);
                        }
                    }
                }
            }
        }
    }


    /* ************************************* METHODS REGARDING THE COMMUNICATION WHEN THE TURN HAS STARTED ************************+ */

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the worker to play the turn with
     * @param board board in xml format that needs to be processed by the GUI when it prints the board
     */

    @Override
    public void OnChooseWorker(Node board) {
        gamestate = GUIController.GUIGameState.gui_ChoosingWorker;
        this.nodeboard = board;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForMove(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_CandidateCellsForMove;

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("candidates")) {

                Node cell;
                if (node.hasChildNodes()) {
                    NodeList cells = node.getChildNodes();

                    for (int j = 0; j < cells.getLength(); j++) {
                        cell = cells.item(j);

                        if (cell.getNodeName().equals("cell")) {
                            int id = getIdOfCellNode(cell);
                            indexcandidatecells.add(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptMove(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_CandidateCellsForOptMove;

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("candidates")) {

                Node cell;
                if (node.hasChildNodes()) {
                    NodeList cells = node.getChildNodes();

                    for (int j = 0; j < cells.getLength(); j++) {
                        cell = cells.item(j);

                        if (cell.getNodeName().equals("cell")) {
                            int id = getIdOfCellNode(cell);
                            indexcandidatecells.add(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForBuild(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_CandidateCellsForBuild;

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("candidates")) {

                Node cell;
                if (node.hasChildNodes()) {
                    NodeList cells = node.getChildNodes();

                    for (int j = 0; j < cells.getLength(); j++) {
                        cell = cells.item(j);

                        if (cell.getNodeName().equals("cell")) {
                            int id = getIdOfCellNode(cell);
                            indexcandidatecells.add(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptBuild(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_CandidateCellsForOptBuild;

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("candidates")) {

                Node cell;
                if (node.hasChildNodes()) {
                    NodeList cells = node.getChildNodes();

                    for (int j = 0; j < cells.getLength(); j++) {
                        cell = cells.item(j);

                        if (cell.getNodeName().equals("cell")) {
                            int id = getIdOfCellNode(cell);
                            indexcandidatecells.add(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptEnd(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_CandidateCellsForOptEnd;

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("candidates")) {

                Node cell;
                if (node.hasChildNodes()) {
                    NodeList cells = node.getChildNodes();

                    for (int j = 0; j < cells.getLength(); j++) {
                        cell = cells.item(j);

                        if (cell.getNodeName().equals("cell")) {
                            int id = getIdOfCellNode(cell);
                            indexcandidatecells.add(id);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when there's a winner
     *
     * @param nickname nickname of the winner
     */

    @Override
    public void OnWinner(String nickname) {

    }

    /**
     * Method of the ClientObserver interface that is fired by the client when a user loses
     */

    @Override
    public void OnLoser() {

    }


    /**
     * Method of the ClientObserver interface that is fired by the client when receiving the updated board when it's not playing
     * @param nodes board cells updated and the playing player's nickname
     */
    @Override
    public void OnPrintUpdatedBoard(NodeList nodes) {
        gamestate = GUIController.GUIGameState.gui_PrintingUpdatedBoard;

        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            }
            else if (node.getNodeName().equals("playingPlayer")) {

                //this.playingPlayerNickname = node.getAttributes().getNamedItem("nickname").getTextContent();

            }
        }
    }



    /* *********************************************************************************************************** *
     *                                 UTILITY METHODS FOR MANAGING THE BOARD TO SHOW                              *
     * *********************************************************************************************************** */

    /**
     * Method that returns the node of the cell that has a certain id from the board in xml format (nodeboard of the class)
     * @param cellnodeid is of the cell node that is being found
     * @return the cell node found with the given id, null if there were no cells in the board given by the server
     */

    public Node getCellNodeGivenTheID(int cellnodeid) {
        Node cell;

        if (nodeboard.hasChildNodes()) {
            NodeList cells = nodeboard.getChildNodes();
            for (int i = 0; i < cells.getLength(); i++) {
                cell = cells.item(i);

                if (cell.getNodeName().equals("cell")) {
                    if (Integer.parseInt(cell.getAttributes().getNamedItem("id").getTextContent()) == cellnodeid) {
                        return cell;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Method to get the id of a cell given its node
     * @param cellnode node in xml format from which to get the id
     * @return the id of the cell
     */

    public int getIdOfCellNode(Node cellnode) {
        return Integer.parseInt(cellnode.getAttributes().getNamedItem("id").getTextContent());
    }

    /**
     * Method to get the nickname of a cell given its node
     * @param cellnode node in xml format from which to get the nickname (it can be an empty string if the cell has no nikcname)
     * @return the nickname associated to the cell
     */

    public String getNicknameOfCellNode(Node cellnode) {

        return cellnode.getAttributes().getNamedItem("nickname").getTextContent();
    }

    /**
     * Method to get the level of a cell given its node
     * @param cellnode node in xml format from which to get the level
     * @return the level of the cell
     */

    public int getLevelOfCellNode(Node cellnode) {
        return Integer.parseInt(cellnode.getAttributes().getNamedItem("level").getTextContent());
    }

    /**
     * Method to get the dome of a cell given its node
     * @param cellnode node in xml format from which to get the dome
     * @return true fi the call has a dome, otherwise false
     */

    public boolean getDomeOfCellNode(Node cellnode) {
        return Boolean.parseBoolean(cellnode.getAttributes().getNamedItem("dome").getTextContent());
    }



    /**
     * Method that resets the candidate cells saved in the GUI
     * IMPORTANT: use this each time before sending the answer to the server
     */

    public void restoreCandidateCells() {
        indexcandidatecells.clear();
    }

//
//    public void connectToServer(){
//
//        connstate = GUIController.GUIConnectionState.gui_connecting;
//
//        client.Connect(userInput.toString());
//
//    }
}