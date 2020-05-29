package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import static java.lang.Math.abs;

public class BoardPage_UniqueController {
    private GUI gui;
    private List<Integer> indexcandidatecells = new ArrayList<Integer>();
    private String cellSelected;

    @FXML
    public ImageView DescriptionOptional;

    Image OptionalEnd_Poseidon = new Image("images/OptionalPhases/OptionalEnd_Poseidon.png");
    Image OptionalEnd_Ares = new Image("images/OptionalPhases/OptionalEnd_Ares.png");
    Image OptionalBuild = new Image("images/OptionalPhases/OptionalBuild.png");
    Image OptionalMove = new Image("images/OptionalPhases/OptionalMove.png");
    Image OptionalBuildBeforeMove = new Image("images/OptionalPhases/OptionalBuildBeforeMove.png");
    @FXML
    public ImageView LevelButton;
    public ImageView DomeButton;

    Image LevelButtonPressed = new Image("images/Buttons/btn_Level_pressed.png");
    Image DomeButtonPressed = new Image("images/Buttons/btn_Dome_pressed.png");
    Image LevelButtonRestored = new Image("images/Buttons/btn_Level.png");
    Image DomeButtonRestored = new Image("images/Buttons/btn_Dome_pressed.png");

    @FXML
    public Pane LevelOrDomeQuestion;
    @FXML
    public Pane DisabledPane;
    @FXML
    public ImageView SkipButton;

    Image SkipButtonReleased = new Image("images/Buttons/btn_Skip.png");
    Image SkipButtonPressed = new Image("images/Buttons/btn_Skip_pressed.png");
    @FXML
    public GridPane BoardGrid;
    @FXML
    public GridPane OptGrid;
    public ImageView PhaseName;
    public Label nicknamePlayer1;
    public Label nicknamePlayer2;
    public Label nicknamePlayer3;

    public ImageView Player1Panel;
    public ImageView Player2Panel;
    public ImageView Player3Panel;

    Image PlayingPlayerPanel = new Image("images/Board/PlayingPlayerPanel.png");
    Image NormalPlayerPanel = new Image("images/Board/PlayerBoardPanel.png");

    public ImageView Player1God;
    public ImageView Player2God;
    public ImageView Player3God;

    public ImageView Player1Dead;
    public ImageView Player2Dead;
    public ImageView Player3Dead;

    public ImageView Player1Icon;
    public ImageView Player2Icon;
    public ImageView Player3Icon;

    Image redWorkerSelected = new Image("images/Board/redWorker_Board_selected.png");
    Image blueWorkerSelected = new Image("images/Board/blueWorker_Board_selected.png");
    Image violetWorkerSelected = new Image("images/Board/violetWorker_Board_selected.png");

    Image Apollo = new Image("images/Gods/Apollo_icon.png");
    Image Ares = new Image("images/Gods/Ares_icon.png");
    Image Artemis = new Image("images/Gods/Artemis_icon.png");
    Image Athena = new Image("images/Gods/Athena_icon.png");
    Image Atlas = new Image("images/Gods/Atlas_icon.png");
    Image Demeter = new Image("images/Gods/Demeter_icon.png");
    Image Hephaestus = new Image("images/Gods/Hephaestus_icon.png");
    Image Hestia = new Image("images/Gods/Hestia_icon.png");
    Image Medusa = new Image("images/Gods/Medusa_icon.png");
    Image Minotaur = new Image("images/Gods/Minotaur_icon.png");
    Image Pan = new Image("images/Gods/Pan_icon.png");
    Image Poseidon = new Image("images/Gods/Poseidon_icon.png");
    Image Prometheus = new Image("images/Gods/Prometheus_icon.png");
    Image Zeus = new Image("images/Gods/Zeus_icon.png");

    @FXML
    public ImageView ExitGameButton;
    Image exitButtonHovered = new Image("images/Buttons/btn_exitGame_hovered.png");
    Image exitButtonReleased = new Image("images/Buttons/btn_exitGame.png");

    /**
     * Constructor
     */
    public BoardPage_UniqueController(){
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */

    @FXML
    public void initialize() {
        Player1Dead.setVisible(false);
        Player2Dead.setVisible(false);
        Player3Dead.setVisible(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void skipButtonPressed() {
        SkipButton.setImage(SkipButtonPressed);
        gui.doSkipOptBuild();
    }

    public void skipButtonReleased() {
        SkipButton.setImage(SkipButtonReleased);
    }

    /**
     * Given a cell index and a level ,this method sets the corresponding level height on the corresponding gridPane cell
     * @param index the index of the cell to set the level of
     * @param level the level displayed on the given cell after the method call
     */
    public void setLevel(int index, int level){
        int column = index%5;
        int row = abs(index/5-4);
        switch(level){
            case 1:
                ImageView level1 = new ImageView("images/Board/Level1_Board.png");
                level1.setPickOnBounds(true);
                level1.setFitHeight(100);
                level1.setFitWidth(100);
                BoardGrid.add(level1,column,row);
                break;
            case 2:
                ImageView level2 = new ImageView("images/Board/Level2_Board.png");
                level2.setPickOnBounds(true);
                level2.setFitWidth(100);
                level2.setFitHeight(100);
                BoardGrid.add(level2, column, row);
                break;
            case 3:
                ImageView level3 = new ImageView("images/Board/Level3_Board.png");
                level3.setPickOnBounds(true);
                level3.setFitHeight(100);
                level3.setFitWidth(100);
                BoardGrid.add(level3, column, row);
                break;
        }
    }

    public void setDome(int index){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView dome = new ImageView("images/Board/Dome_Board.png");
        dome.setPickOnBounds(true);
        dome.setFitHeight(100);
        dome.setFitWidth(100);
        BoardGrid.add(dome, column, row);
    }

    public void setWorker(int index, String url){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView worker = new ImageView(url);
        worker.setPickOnBounds(true);
        worker.setFitHeight(100);
        worker.setFitWidth(100);
        BoardGrid.add(worker, column, row);
    }

    public void setCandidate(int index){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView candidate = new ImageView("images/Board/CandidateCell_board.png");
        candidate.setPickOnBounds(true);
        candidate.setFitHeight(100);
        candidate.setFitWidth(100);
        BoardGrid.add(candidate, column, row);
        indexcandidatecells.add(index);
    }

    public void setPlayer1Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player1Panel.setImage(PlayingPlayerPanel);
            System.out.println("giocatore 1");
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                System.out.println("get phase");
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(OptionalEnd_Ares);
                    SkipButton.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(OptionalEnd_Poseidon);
                    System.out.println("skip?");
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(OptionalBuildBeforeMove);
                    SkipButton.setVisible(true);
                }else{
                    DescriptionOptional.setImage(OptionalBuild);
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(OptionalMove);
                SkipButton.setVisible(true);
            }
        }
        else {
            Player1Panel.setImage(NormalPlayerPanel);
        }

        switch (god) {
            case "Apollo":
                Player1God.setImage(Apollo);
                break;
            case "Artemis":
                Player1God.setImage(Artemis);
                break;
            case "Ares":
                Player1God.setImage(Ares);
                break;
            case "Athena":
                Player1God.setImage(Athena);
                break;
            case "Atlas":
                Player1God.setImage(Atlas);
                break;
            case "Demeter":
                Player1God.setImage(Demeter);
                break;
            case "Hephaestus":
                Player1God.setImage(Hephaestus);
                break;
            case "Hestia":
                Player1God.setImage(Hestia);
                break;
            case "Medusa":
                Player1God.setImage(Medusa);
                break;
            case "Minotaur":
                Player1God.setImage(Minotaur);
                break;
            case "Pan":
                Player1God.setImage(Pan);
                break;
            case "Poseidon":
                Player1God.setImage(Poseidon);
                break;
            case "Prometheus":
                Player1God.setImage(Prometheus);
                break;
            case "Zeus":
                Player1God.setImage(Zeus);
                break;
        }

        nicknamePlayer1.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player1Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player1Dead.setVisible(true);
        }
    }

    public void setPlayer2Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player2Panel.setImage(PlayingPlayerPanel);
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(OptionalEnd_Ares);
                    SkipButton.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(OptionalEnd_Poseidon);
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(OptionalBuildBeforeMove);
                    SkipButton.setVisible(true);
                }else{
                    DescriptionOptional.setImage(OptionalBuild);
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(OptionalMove);
                SkipButton.setVisible(true);
            }
        }
        else {
            Player2Panel.setImage(NormalPlayerPanel);
        }

        switch (god) {
            case "Apollo":
                Player2God.setImage(Apollo);
                break;
            case "Artemis":
                Player2God.setImage(Artemis);
                break;
            case "Ares":
                Player2God.setImage(Ares);
                break;
            case "Athena":
                Player2God.setImage(Athena);
                break;
            case "Atlas":
                Player2God.setImage(Atlas);
                break;
            case "Demeter":
                Player2God.setImage(Demeter);
                break;
            case "Hephaestus":
                Player2God.setImage(Hephaestus);
                break;
            case "Hestia":
                Player2God.setImage(Hestia);
                break;
            case "Medusa":
                Player2God.setImage(Medusa);
                break;
            case "Minotaur":
                Player2God.setImage(Minotaur);
                break;
            case "Pan":
                Player2God.setImage(Pan);
                break;
            case "Poseidon":
                Player2God.setImage(Poseidon);
                break;
            case "Prometheus":
                Player2God.setImage(Prometheus);
                break;
            case "Zeus":
                Player2God.setImage(Zeus);
                break;
        }

        nicknamePlayer2.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player2Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player2Dead.setVisible(true);
        }
    }

    public void setPlayer3Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player3Panel.setImage(PlayingPlayerPanel);
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(OptionalEnd_Ares);
                    SkipButton.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(OptionalEnd_Poseidon);
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(OptionalBuildBeforeMove);
                    SkipButton.setVisible(true);
                }else{
                    DescriptionOptional.setImage(OptionalBuild);
                    SkipButton.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(OptionalMove);
                SkipButton.setVisible(true);
            }
        }
        else {
            Player3Panel.setImage(NormalPlayerPanel);
        }

        switch (god) {
            case "Apollo":
                Player3God.setImage(Apollo);
                break;
            case "Artemis":
                Player3God.setImage(Artemis);
                break;
            case "Ares":
                Player3God.setImage(Ares);
                break;
            case "Athena":
                Player3God.setImage(Athena);
                break;
            case "Atlas":
                Player3God.setImage(Atlas);
                break;
            case "Demeter":
                Player3God.setImage(Demeter);
                break;
            case "Hephaestus":
                Player3God.setImage(Hephaestus);
                break;
            case "Hestia":
                Player3God.setImage(Hestia);
                break;
            case "Medusa":
                Player3God.setImage(Medusa);
                break;
            case "Minotaur":
                Player3God.setImage(Minotaur);
                break;
            case "Pan":
                Player3God.setImage(Pan);
                break;
            case "Poseidon":
                Player3God.setImage(Poseidon);
                break;
            case "Prometheus":
                Player3God.setImage(Prometheus);
                break;
            case "Zeus":
                Player3God.setImage(Zeus);
                break;
        }

        nicknamePlayer3.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player3Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player3Dead.setVisible(true);
        }
    }

    public void setPanel3Visibility(boolean visibility) {
        Player3Panel.setVisible(visibility);
        nicknamePlayer3.setVisible(visibility);
        Player3God.setVisible(visibility);
    }

    public void resetBoardGrid() {
        if(BoardGrid.getChildren().size()!=0){
            BoardGrid.getChildren().clear();
        }
        if(OptGrid.getChildren().size()!=0){
            System.out.println("ero io");
        }
        DisabledPane.setVisible(false);
        LevelOrDomeQuestion.setVisible(false);
    }

    public void clickedOnGrid(MouseEvent e){
        Node source = e.getPickResult().getIntersectedNode();
        Integer gridColumn = GridPane.getColumnIndex(source);
        Integer gridRow = GridPane.getRowIndex(source);
        int chosenCellIndex = abs(gridRow-4)*5 + gridColumn;
        switch(this.gui.getCurrentPhase()){
            case ChooseWorker:
                if(this.gui.getNicknameOfCellNode(this.gui.getCellNodeGivenTheID(chosenCellIndex)).equals(this.gui.client.getNickname())){
                    cellSelected = Integer.toString(chosenCellIndex);
                    gui.doSendSelectedWorker(cellSelected);
                }
                break;
            case Move:
            case OptMove:
                for (int i = 0; i < indexcandidatecells.size(); i++) {
                    if (indexcandidatecells.get(i) == chosenCellIndex) {
                        cellSelected = Integer.toString(chosenCellIndex);
                        gui.doSendCandidateMove(cellSelected);
                        break;
                    }
                }
                break;
            case Build:
                for (int i = 0; i < indexcandidatecells.size(); i++) {
                    if (indexcandidatecells.get(i) == chosenCellIndex) {
                        cellSelected = Integer.toString(chosenCellIndex);
                        sendCellSelected(cellSelected);
                        break;
                    }
                }
                break;
            case OptBuild:
                for (int i = 0; i < indexcandidatecells.size(); i++) {
                    if (indexcandidatecells.get(i) == chosenCellIndex) {
                        cellSelected = Integer.toString(chosenCellIndex);
                        gui.doSendCandidateBuild(cellSelected);
                        break;
                    }
                }
                break;
            case OptEnd:
                for (int i = 0; i < indexcandidatecells.size(); i++) {
                    if (indexcandidatecells.get(i) == chosenCellIndex) {
                        cellSelected = Integer.toString(chosenCellIndex);
                        gui.doSendCandidateEnd(cellSelected);
                        break;
                    }
                }
                break;
            case Update:
                break;
        }
    }


    public void sendCellSelected(String cellSelected) {
        if(!gui.getGodGivenTheNickname(gui.client.getNickname()).equals("Atlas")) {
            gui.doSendCandidateBuild(cellSelected);
        }
        else {
            DisabledPane.setVisible(true);
            LevelOrDomeQuestion.setVisible(true);
        }
    }

    public void LevelChosen() {
        LevelButton.setImage(LevelButtonPressed);
    }

    public void DomeChosen() {
        DomeButton.setImage(DomeButtonPressed);
    }

    public void LevelReleased() {
        LevelButton.setImage(LevelButtonRestored);
        gui.doSendCandidateBuildForAtlas(cellSelected, "B");
    }

    public void DomeReleased() {
        DomeButton.setImage(DomeButtonRestored);
        gui.doSendCandidateBuildForAtlas(cellSelected, "D");
    }

    public void exitButtonHovered() {
        ExitGameButton.setImage(exitButtonHovered);
    }

    public void exitButtonPressed() {

        ButtonType YES = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the game? You will be lead to the registering page." ,YES, NO);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You are going to exit the game!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(gui.getSantoriniStage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == YES)
            gui.doDeregister();
    }

    public void exitButtonReleased() {
        ExitGameButton.setImage(exitButtonReleased);
    }

    public void setPhaseName(){
        switch (this.gui.getCurrentPhase()){
            case ChooseWorker:
                PhaseName.setImage(new Image("images/Board/ChooseWorker.png"));
                break;
            case Move:
                PhaseName.setImage(new Image("images/Board/Move.png"));
                break;
            case Build:
                PhaseName.setImage(new Image("images/Board/Build.png"));
                break;
            case OptBuild:
                PhaseName.setImage(new Image("images/Board/Build.png"));
                break;
            case OptMove:
                PhaseName.setImage(new Image("images/Board/Move.png"));
                break;
            case OptEnd:
                PhaseName.setImage(new Image("images/Board/End.png"));
                break;
            case Update:
                PhaseName.setImage(new Image("images/Board/WaitForYourTurn.png"));
                break;
        }
    }

}
