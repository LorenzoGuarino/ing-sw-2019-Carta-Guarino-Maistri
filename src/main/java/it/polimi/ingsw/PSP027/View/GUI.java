package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;
import it.polimi.ingsw.PSP027.View.Controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;

public class GUI extends Application implements ClientObserver {


    public Client client = null;
    private boolean bRun = false;
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;
    private Stage SantoriniStage;
    private String firstPlayersGod;

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

    /* ******************************************************************************************************************* */

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        client = new Client();
        client.addObserver(this);
        Thread clientThread = new Thread(client);
        clientThread.start();

        SantoriniStage = stage;

        SantoriniStage.setTitle("Santorini"); //name of the game window that is shown

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EntryPage.fxml"));
        Parent entryPage = (Parent) loader.load();

        EntryPageController ctrl = loader.getController();
        ctrl.setGui(this);

        Scene entryScene = new Scene(entryPage, 1800, 850);
        SantoriniStage.setMaximized(true);
        SantoriniStage.setFullScreen(true);
        SantoriniStage.setScene(entryScene);
        SantoriniStage.show();
    }

    public Stage getSantoriniStage() {
        return SantoriniStage;
    }

    public void showConnectedPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ConnectedPage.fxml"));
            Parent connectedPage = (Parent) loader.load();

            ConnectedController connectedController = loader.getController();
            connectedController.setGui(this);

            SantoriniStage.getScene().setRoot(connectedPage);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showEntryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EntryPage.fxml"));
            Parent entryPage = (Parent) loader.load();

            EntryPageController entryPageController = loader.getController();
            entryPageController.setGui(this);

            SantoriniStage.getScene().setRoot(entryPage);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showRegisteredPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisteredPage.fxml"));
            Parent registeredPage = (Parent) loader.load();

            RegisteredController registeredController = loader.getController();
            registeredController.setGui(this);
            registeredController.setNickname(client.getNickname());

            SantoriniStage.getScene().setRoot(registeredPage);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showEnteringMatchPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnteringMatchPage.fxml"));
            Parent enteringMatchPage = (Parent) loader.load();

            EnteringMatchController enteringMatchController = loader.getController();
            enteringMatchController.setGui(this);
            enteringMatchController.setNickname(players);

            SantoriniStage.getScene().setRoot(enteringMatchPage);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showChooseGodsPage() {
        try {
            System.out.println("showChooseGodsPage IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChooseGodsPage.fxml"));
            Parent chooseGodsPage = (Parent) loader.load();

            ChooseGodsController chooseGodsController = loader.getController();
            chooseGodsController.setGui(this);
            chooseGodsController.setChooseGodsTitle(requiredgods);

            SantoriniStage.getScene().setRoot(chooseGodsPage);
            SantoriniStage.show();

            System.out.println("showChooseGodsPage OUT");

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showChooseYourGodPageCase3() {
        try {
            System.out.println("showChooseYourGodPageCase3 IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChooseYourGodPageCase3.fxml"));
            Parent chooseYourGodsPageCase3 = (Parent) loader.load();

            ChooseYourGodCase3Controller chooseYourGodCase3Controller = loader.getController();
            chooseYourGodCase3Controller.setGui(this);
            chooseYourGodCase3Controller.setChooseGodTitle(this.gods);

            SantoriniStage.getScene().setRoot(chooseYourGodsPageCase3);
            SantoriniStage.show();

            System.out.println("showChooseYourGodPageCase3 OUT");

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showChooseYourGodPageCase2(){
        try {
            System.out.println("showChooseYourGodPageCase2 IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChooseYourGodPageCase2.fxml"));
            Parent chooseYourGodsPageCase2 = (Parent) loader.load();

            ChooseYourGodCase2Controller chooseYourGodCase2Controller = loader.getController();
            chooseYourGodCase2Controller.setGui(this);
            chooseYourGodCase2Controller.setChooseGodTitle(this.gods);

            SantoriniStage.getScene().setRoot(chooseYourGodsPageCase2);
            SantoriniStage.show();

            System.out.println("showChooseYourGodPageCase2 OUT");

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showWaitingPage(String waitingMessage){
        try {
            System.out.println("showWaitingPage IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Waiting.fxml"));
            Parent waitingPage = (Parent) loader.load();

            WaitingController waitingController = loader.getController();
            waitingController.setGui(this);
            waitingController.setWaitingMessage(waitingMessage);

            SantoriniStage.getScene().setRoot(waitingPage);
            SantoriniStage.show();

            System.out.println("showWaitingPage OUT");

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showChooseFirstPlayerPageCase2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChooseFirstPlayerCase2.fxml"));
            Parent chooseFirstPlayerCase2Page = (Parent) loader.load();

            ChooseFirstPlayerCase2Controller chooseFirstPlayerCase2Controller = loader.getController();
            chooseFirstPlayerCase2Controller.setGui(this);
            chooseFirstPlayerCase2Controller.setNickname(players);
            chooseFirstPlayerCase2Controller.setGod(firstPlayersGod);

            SantoriniStage.getScene().setRoot(chooseFirstPlayerCase2Page);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showChooseFirstPlayerPageCase3() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChooseFirstPlayerCase3.fxml"));
            Parent chooseFirstPlayerCase3Page = (Parent) loader.load();

            ChooseFirstPlayerCase3Controller chooseFirstPlayerCase3Controller = loader.getController();
            chooseFirstPlayerCase3Controller.setGui(this);
            chooseFirstPlayerCase3Controller.setNickname(players);
            chooseFirstPlayerCase3Controller.setGod(firstPlayersGod);

            SantoriniStage.getScene().setRoot(chooseFirstPlayerCase3Page);
            SantoriniStage.show();

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }


    /* ***************************************************************************************************************
     *                    Methods fired by the client's methods that trigger the change of the view                  *
     *****************************************************************************************************************/

    /**
     * Method of the ClientObserver interface that is fired by the client after connection
     */

    @Override
    public void OnConnected(){
        System.out.println("OnConnected IN");
        Platform.runLater(() -> showConnectedPage());
        System.out.println("OnConnected OUT");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to connect
     */

    @Override
    public void OnConnectionError() {
    }

    /**
     * Method of the ClientObserver interface that is fired by the client if it has been disconnected from the server
     */

    @Override
    public void OnDisconnected() {
        System.out.println("OnDisconnected IN");
        Platform.runLater(() -> showEntryPage());
        System.out.println("OnDisconnected OUT");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has registered
     */

    @Override
    public void OnRegistered() {
        System.out.println("OnRegistered IN");
        Platform.runLater(() -> showRegisteredPage());
        System.out.println("OnRegistered OUT");

    }

    /**
     * Method of the ClientObserver interface that is fired by the client if there was an error when trying to register
     */

    @Override
    public void OnRegistrationError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "We are very sorry, please try again.");
        alert.setTitle("REGISTRATION ERROR");
        alert.setHeaderText("There was an error with your registration");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(SantoriniStage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Method of the ClientObserver interface that is fired by the client after the user has been deregistered
     */

    @Override
    public void OnDeregistered() {

        Platform.runLater(() -> showConnectedPage());
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
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when entering a match
     */

    @Override
    public void OnEnteringMatch(List<String> players) {
        System.out.println("OnEnteringMatch IN");
        this.players = players;
        Platform.runLater(() -> showEnteringMatchPage());
        System.out.println("OnEnteringMatch OUT");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when it has entered a match
     */

    @Override
    public void OnEnteredMatch(List<String> players) {
        System.out.println("OnWaiting IN");
        this.players = players;
        Platform.runLater(() -> showWaitingPage("Wait while the first player chooses the gods you will play with"));
        System.out.println("OnWaiting OUT");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the list of gods to use in the match
     *
     * @param requiredgods number of gods that the client must choose
     * @param gods         gods' names chosen by the user
     */

    @Override
    public void OnChooseGods(int requiredgods, List<String> gods) {
        System.out.println("OnChooseGods IN");
        this.requiredgods = requiredgods;
        this.gods = gods;
        Platform.runLater(() -> showChooseGodsPage());
        System.out.println("OnChooseGods OUT");
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the god to play with
     *
     * @param chosengods chosen gods to set as the gods managed by the cli that will communicate them to the user
     */

    @Override
    public void OnChooseGod(List<String> chosengods) {
        System.out.println("OnChooseGod IN");
        this.gods = chosengods;
        if(this.gods.size()==3){
            Platform.runLater(() -> showChooseYourGodPageCase3());
            System.out.println("OnChooseGod OUT");
        }else if(this.gods.size()==2){
            Platform.runLater(() -> showChooseYourGodPageCase2());
            System.out.println("OnChooseGod OUT");
        }
        else if(this.gods.size()==1) {
            firstPlayersGod = chosengods.get(0);
            doSendSelectedGod(chosengods.get(0));
        }

    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the first player that
     * will place its workers and start playing
     *
     * @param players players in the match from which the selection must be done
     */

    @Override
    public void OnChooseFirstPlayer(List<String> players) {
        System.out.println("OnChooseFirstPlayer IN");
        this.players = players;
        if(players.size() == 2) {
            Platform.runLater(() -> showChooseFirstPlayerPageCase2());
            System.out.println("OnChooseFirstPlayer OUT");
        }
        else if (players.size() == 3) {
            Platform.runLater(() -> showChooseFirstPlayerPageCase3());
            System.out.println("OnChooseFirstPlayer OUT");
        }
    }


    /**
     * Method of the ClientObserver interface that is fired by the client when placing the workers on the board for the first time
     *
     * @param nodes board in xml format that needs to be processed by the GUI when it prints the board
     *              it also contains the list of players with their gods that needs to be saved
     */

    @Override
    public void OnChooseWorkerStartPosition(NodeList nodes) {

        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("players")) {

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
     *
     * @param board board in xml format that needs to be processed by the GUI when it prints the board
     */

    @Override
    public void OnChooseWorker(Node board) {
        this.nodeboard = board;
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when choosing the cell to move the worker onto
     *
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForMove(NodeList nodes) {

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("candidates")) {

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
     *
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptMove(NodeList nodes) {

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("candidates")) {

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
     *
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForBuild(NodeList nodes) {

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("candidates")) {

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
     *
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptBuild(NodeList nodes) {

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("candidates")) {

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
     *
     * @param nodes cells where is possible to move the worker in xml format that needs to be processed by the GUI
     */
    @Override
    public void OnCandidateCellsForOptEnd(NodeList nodes) {

        indexcandidatecells.clear();
        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("candidates")) {

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
     *
     * @param nodes board cells updated and the playing player's nickname
     */
    @Override
    public void OnPrintUpdatedBoard(NodeList nodes) {

        Node node;
        for (int i = 0; i < nodes.getLength(); i++) {
            node = nodes.item(i);

            if (node.getNodeName().equals("board")) {
                this.nodeboard = node;
            } else if (node.getNodeName().equals("playingPlayer")) {

                //this.playingPlayerNickname = node.getAttributes().getNamedItem("nickname").getTextContent();

            }
        }
    }



    /* ******************************************************************************************************************* *
     *                                      UTILITY METHODS FOR MANAGING THE BOARD TO SHOW                                 *
     * ******************************************************************************************************************* */

    /**
     * Method that returns the node of the cell that has a certain id from the board in xml format (nodeboard of the class)
     *
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
     *
     * @param cellnode node in xml format from which to get the id
     * @return the id of the cell
     */

    public int getIdOfCellNode(Node cellnode) {
        return Integer.parseInt(cellnode.getAttributes().getNamedItem("id").getTextContent());
    }

    /**
     * Method to get the nickname of a cell given its node
     *
     * @param cellnode node in xml format from which to get the nickname (it can be an empty string if the cell has no nikcname)
     * @return the nickname associated to the cell
     */

    public String getNicknameOfCellNode(Node cellnode) {

        return cellnode.getAttributes().getNamedItem("nickname").getTextContent();
    }

    /**
     * Method to get the level of a cell given its node
     *
     * @param cellnode node in xml format from which to get the level
     * @return the level of the cell
     */

    public int getLevelOfCellNode(Node cellnode) {
        return Integer.parseInt(cellnode.getAttributes().getNamedItem("level").getTextContent());
    }

    /**
     * Method to get the dome of a cell given its node
     *
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


    /* ******************************************************************************************************************* *
     *                                METHODS CALLED BY THE GUI CONTROLLER THAT TRIGGER ACTIONS                            *
     * ******************************************************************************************************************* */

    public void doConnect(String serverip) {
        client.Connect(serverip);
    }

    public void doExit() {
        client.Disconnect();
        Platform.exit();
        System.exit(0);
    }

    public void doDisconnect() {
        client.Disconnect();
    }

    public void doRegister(String nickname) {
        client.Register(nickname);
    }

    public void doDeregister() {
        client.Deregister();
    }

    public void doSearchMatch(int numberOfPlayers) {
        client.SearchMatch(numberOfPlayers);
    }

    public void doSendGods(List<String> GodsChosen){
        client.ChosenGods(GodsChosen);
        Platform.runLater(() -> showWaitingPage("Wait while the other players choose their gods"));
    }

    public void doSendSelectedGod(String GodSelected){
        client.ChosenGod(GodSelected);
        Platform.runLater(() -> showWaitingPage("Wait for your turn to begin"));
    }

    public void doSendFirstPlayer(String FirstPlayer){
        client.ChosenFirstPlayer(FirstPlayer);
        Platform.runLater(() -> showWaitingPage("Wait for your turn to begin"));
    }
}


