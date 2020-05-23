package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;
import it.polimi.ingsw.PSP027.View.Controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> PlayerGodMap = new HashMap<String, String>();
    private Map<String, String> PlayerWorkerMap = new HashMap<String, String>();
    private Stage SantoriniStage;
    private String firstPlayersGod;
    private List<String> deadPlayers = new ArrayList<String>();
    private String playingPlayerNickname;

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
        SantoriniStage.setMinHeight(900);
        SantoriniStage.setMinWidth(1440);
        SantoriniStage.setMaximized(true);
        SantoriniStage.setFullScreen(true);
        SantoriniStage.setScene(entryScene);
        SantoriniStage.setResizable(false);
        SantoriniStage.show();

        SantoriniStage.setOnCloseRequest(e->{
            System.out.println("Closing Santorini Game");
            Platform.exit();
            System.exit(0);
        });


    }

    public String getWorkerUrlImageGivenTheNickname (String nickname) {
        return PlayerWorkerMap.get(nickname);
    }

    public String getGodGivenTheNickname (String nickname) {
        return PlayerGodMap.get(nickname);
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
    public void showRegistrationError(String error) {

        switch(error) {
            case "Nickname already present": {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please try again with another nickname");
                alert.setTitle("REGISTRATION ERROR");
                alert.setHeaderText("There was an error with your registration: your chosen nickname is already taken.");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(SantoriniStage);
                Optional<ButtonType> result = alert.showAndWait();
                break;
            }
            case "Missing nickname": {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please try again with a valid nickname");
                alert.setTitle("REGISTRATION ERROR");
                alert.setHeaderText("There was an error with your registration: please enter a valid nickname and try again.");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initOwner(SantoriniStage);
                Optional<ButtonType> result = alert.showAndWait();
                break;
            }
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

    public void showBoardPage_PlacingWorkers(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_PlacingWorkers.fxml"));
            Parent PlacingWorkersPage = (Parent) loader.load();

            BoardPage_PlacingWorkersController boardPage_PlacingWorkersController = loader.getController();
            boardPage_PlacingWorkersController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_PlacingWorkersController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_PlacingWorkersController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_PlacingWorkersController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_PlacingWorkersController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_PlacingWorkersController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_PlacingWorkersController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_PlacingWorkersController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_PlacingWorkersController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(PlacingWorkersPage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showBoardPage_ChooseWorker(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_ChooseWorker.fxml"));
            Parent ChooseWorkerPage = (Parent) loader.load();

            BoardPage_ChooseWorkerController boardPage_ChooseWorkerController = loader.getController();
            boardPage_ChooseWorkerController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_ChooseWorkerController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_ChooseWorkerController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_ChooseWorkerController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_ChooseWorkerController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_ChooseWorkerController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_ChooseWorkerController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_ChooseWorkerController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_ChooseWorkerController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(ChooseWorkerPage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showBoardPage_Move(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_Move.fxml"));
            Parent MovePage = (Parent) loader.load();

            BoardPage_MoveController boardPage_MoveController = loader.getController();
            boardPage_MoveController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_MoveController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_MoveController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_MoveController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_MoveController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_MoveController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_MoveController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_MoveController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_MoveController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(MovePage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }


    }

    public void showBoardPage_OptMove(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_OptMove.fxml"));
            Parent OptMovePage = (Parent) loader.load();

            BoardPage_OptMoveController boardPage_OptMoveController = loader.getController();
            boardPage_OptMoveController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_OptMoveController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_OptMoveController.setDome(id);
                        }


                        if (!nickname.isEmpty()) {
                            boardPage_OptMoveController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_OptMoveController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptMoveController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptMoveController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptMoveController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_OptMoveController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(OptMovePage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }


    }

    public void showBoardPage_Build(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_Build.fxml"));
            Parent BuildPage = (Parent) loader.load();

            BoardPage_BuildController boardPage_BuildController = loader.getController();
            boardPage_BuildController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_BuildController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_BuildController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_BuildController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_BuildController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_BuildController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_BuildController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_BuildController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_BuildController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(BuildPage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showBoardPage_OptBuild(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_OptBuild.fxml"));
            Parent OptBuildPage = (Parent) loader.load();

            BoardPage_OptBuildController boardPage_OptBuildController = loader.getController();
            boardPage_OptBuildController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_OptBuildController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_OptBuildController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_OptBuildController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_OptBuildController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptBuildController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptBuildController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptBuildController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_OptBuildController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(OptBuildPage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showBoardPage_Update(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_Update.fxml"));
            Parent updatePage = (Parent) loader.load();

            BoardPage_UpdateController boardPage_updateController = loader.getController();
            boardPage_updateController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_updateController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_updateController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_updateController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_updateController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(playingPlayerNickname)) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_updateController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_updateController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_updateController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_updateController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(updatePage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showBoardPage_OptEnd(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BoardPage_OptEnd.fxml"));
            Parent OptEndPage = (Parent) loader.load();

            BoardPage_OptEndController boardPage_OptEndController = loader.getController();
            boardPage_OptEndController.setGui(this);

            Node cell;

            if (nodeboard.hasChildNodes()) {
                NodeList cells = nodeboard.getChildNodes();

                for (int i = 0; i < cells.getLength(); i++) {
                    cell = cells.item(i);

                    if (cell.getNodeName().equals("cell")) {
                        int id = getIdOfCellNode(cell);
                        int level = getLevelOfCellNode(cell);
                        boolean dome = getDomeOfCellNode(cell);

                        String nickname = getNicknameOfCellNode(cell);

                        if(level!=0) {
                            boardPage_OptEndController.setLevel(id, level);
                        }

                        if (dome) {
                            boardPage_OptEndController.setDome(id);
                        }

                        if (!nickname.isEmpty()) {
                            boardPage_OptEndController.setWorker(id, PlayerWorkerMap.get(nickname));
                        }

                        if (!indexcandidatecells.isEmpty()) {
                            for (int j = 0; j < indexcandidatecells.size(); j++) {
                                if (indexcandidatecells.get(j) == id) {
                                    boardPage_OptEndController.setCandidate(id);
                                    break;
                                }
                            }
                        }
                    }
                }

                if (PlayerGodMap.size() > 0) {
                    Set<String> nicknames = PlayerGodMap.keySet();
                    String nickname;
                    Iterator<String> itr = nicknames.iterator();
                    int playerscount = 1;
                    boolean playingPlayer = false;

                    while (itr.hasNext()) {
                        nickname = itr.next();
                        if (PlayerWorkerMap.containsKey(nickname) && PlayerGodMap.containsKey(nickname)) {
                            if(nickname.equals(client.getNickname())) {
                                playingPlayer = true;
                            }

                            if(playerscount == 1) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptEndController.setPlayer1Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 2) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptEndController.setPlayer2Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            } else if(playerscount == 3) {
                                boolean deadPlayer = false;
                                for(int i=0; i < deadPlayers.size(); i++) {
                                    if(deadPlayers.get(i).equals(nickname)) {
                                        deadPlayer = true;
                                    }
                                }
                                boardPage_OptEndController.setPlayer3Panel(PlayerGodMap.get(nickname), nickname, playingPlayer, deadPlayer, PlayerWorkerMap.get(nickname));
                            }
                        }
                        playerscount++;
                        playingPlayer = false;
                    }
                }

                if(players.size() == 2) {
                    boardPage_OptEndController.setPanel3Visibility(false);
                }
            }

            SantoriniStage.getScene().setRoot(OptEndPage);
            SantoriniStage.show();

        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showYouHaveWonPage(){
        try {
            System.out.println("showYouHaveWonPage IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/YouHaveWonPage.fxml"));
            Parent youHaveWonPage = (Parent) loader.load();

            YouHaveWonController youHaveWonController = loader.getController();
            youHaveWonController.setGui(this);
            String godWinner = PlayerGodMap.get(client.getNickname());
            youHaveWonController.setWinnerPodium(godWinner);
            youHaveWonController.setWinnerCongrats(client.getNickname());

            SantoriniStage.getScene().setRoot(youHaveWonPage);
            SantoriniStage.show();

            System.out.println("showYouHaveWonPage OUT");

        }catch (IOException exception){
            System.out.println(exception.toString());
        }
    }

    public void showYouHaveLostPage(String winner){
        try {
            System.out.println("showYouHaveLostPage IN");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/YouHaveLostPage.fxml"));
            Parent youHaveLostPage = (Parent) loader.load();

            YouHaveLostController youHaveLostController = loader.getController();
            youHaveLostController.setGui(this);

            if(!winner.isEmpty()) {
                youHaveLostController.setBetterLuckNextTime(winner);
            }

            if(client.getNickname() != null && !gods.isEmpty() && !PlayerGodMap.isEmpty()) {
                String godLoser = PlayerGodMap.get(client.getNickname());
                youHaveLostController.setLoserPodium(godLoser);
            }

            SantoriniStage.getScene().setRoot(youHaveLostPage);
            SantoriniStage.show();

            System.out.println("showYouHaveLostPage OUT");

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
        Platform.runLater(() -> showRegistrationError(error));
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
        deadPlayers.add(nickname);
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
        for (int i = 0; i < players.size(); i++) {
            if (i == 0) {
                PlayerWorkerMap.put(players.get(i), "images/Board/redWorker_Board.png");
            } else if (i == 1) {
                PlayerWorkerMap.put(players.get(i), "images/Board/blueWorker_Board.png");
            } else if (i == 2) {
                PlayerWorkerMap.put(players.get(i), "images/Board/violetWorker_Board.png");
            }

        }
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

                            PlayerGodMap.put(playerNickname, playerGod);
                        }
                    }
                }
            }
        }
        Platform.runLater(() -> showBoardPage_PlacingWorkers());
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
        Platform.runLater(() -> showBoardPage_ChooseWorker());
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
        Platform.runLater(() -> showBoardPage_Move());
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

        Platform.runLater(() -> showBoardPage_OptMove());
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
        Platform.runLater(() -> showBoardPage_Build());
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
        Platform.runLater(() -> showBoardPage_OptBuild());
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

        Platform.runLater(() -> showBoardPage_OptEnd());
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when there's a winner
     *
     * @param nickname nickname of the winner
     */

    @Override
    public void OnWinner(String nickname) {
        System.out.println("OnWinner IN");
        if(nickname.equals(client.getNickname())) {
            //the winner is this client
            Platform.runLater(() -> showYouHaveWonPage());
            System.out.println("OnWinner OUT");
        }
        else {
            //the winner is not this client, who has lost instead
            Platform.runLater(() -> showYouHaveLostPage(nickname));
            System.out.println("OnWinner OUT");
        }
    }

    /**
     * Method of the ClientObserver interface that is fired by the client when a user loses
     */

    @Override
    public void OnLoser() {
        System.out.println("OnLoser IN");
        Platform.runLater(() -> showYouHaveLostPage(""));
        System.out.println("OnLoser OUT");
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
                this.playingPlayerNickname = node.getAttributes().getNamedItem("nickname").getTextContent();
            }
        }
        Platform.runLater(() -> showBoardPage_Update());
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
        if(gods != null) {
            gods.clear();
        }
        if(players != null) {
            players.clear();
        }
        indexcandidatecells.clear();
        PlayerGodMap.clear();
        PlayerWorkerMap.clear();
        firstPlayersGod = "";
        deadPlayers.clear();
        playingPlayerNickname = "";
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

    public void doSendSelectedCellsForWorkers(List<String> CellsSelected){
        String[] chosenPosition = new String[2];
        String firstPosition = CellsSelected.get(0);
        String secondPosition = CellsSelected.get(1);
        int indexFirstPosition = (firstPosition.charAt(0) - 'A') * 5 + (firstPosition.charAt(1) - '1');
        int indexSecondPosition = (secondPosition.charAt(0) - 'A') * 5 + (secondPosition.charAt(1) - '1');
        chosenPosition[0] = Integer.toString(indexFirstPosition);
        chosenPosition[1] = Integer.toString(indexSecondPosition);
        client.ChosenWorkersFirstPositions(chosenPosition[0], chosenPosition[1]);
        Platform.runLater(() -> showWaitingPage("Wait for your turn to begin"));
    }

    public void doSendSelectedWorker(String chosenWorker){
        client.ChosenWorker(chosenWorker);
    }

    public void doSendCandidateMove(String candidateCell){
        this.restoreCandidateCells();
        client.CandidateMove(candidateCell);
    }

    public void doSkipOptMove(){
        this.restoreCandidateCells();
        client.passMove();
    }

    public void doSkipOptBuild(){
        this.restoreCandidateCells();
        client.passBuild();
    }

    public void doSendCandidateBuild(String candidateCell){
        this.restoreCandidateCells();
        client.CandidateBuild(candidateCell);
    }

    public void doSendCandidateEnd(String candidateCell){
        this.restoreCandidateCells();
        client.CandidateEnd(candidateCell);
    }

    public void doSkipOptEnd(){
        this.restoreCandidateCells();
        client.passEnd();
    }

    public void doSendCandidateBuildForAtlas(String candidateCell, String LevelOrDome) {
        this.restoreCandidateCells();
        client.CandidateBuildForAtlas(candidateCell, LevelOrDome);
    }

    public void doPlayAgain() {
        if(gods != null) {
            gods.clear();
        }
        if(players != null) {
            players.clear();
        }
        indexcandidatecells.clear();
        PlayerGodMap.clear();
        PlayerWorkerMap.clear();
        firstPlayersGod = "";
        deadPlayers.clear();
        playingPlayerNickname = "";
        Platform.runLater(() -> showRegisteredPage());
    }


}


