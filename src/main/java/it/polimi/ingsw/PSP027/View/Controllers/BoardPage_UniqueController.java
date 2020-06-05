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
    public ImageView LevelButton;
    public ImageView DomeButton;
    public Pane LevelOrDomeQuestion;
    public Pane DisabledPane;
    public ImageView SkipButton;
    public GridPane BoardGrid;
    public GridPane OptGrid;
    public ImageView PhaseName;

    public Label nicknamePlayer1;
    public Label nicknamePlayer2;
    public Label nicknamePlayer3;

    public ImageView Player1Panel;
    public ImageView Player2Panel;
    public ImageView Player3Panel;

    public ImageView Player1God;
    public ImageView Player2God;
    public ImageView Player3God;

    public ImageView Player1Dead;
    public ImageView Player2Dead;
    public ImageView Player3Dead;

    public ImageView Player1Icon;
    public ImageView Player2Icon;
    public ImageView Player3Icon;

    @FXML
    public ImageView ExitGameButton;
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
        SkipButton.setImage(new Image("images/Buttons/btn_Skip_pressed.png"));
        switch(this.gui.getCurrentPhase()){
            case OptEnd:
                gui.doSkipOptEnd();
                break;
            case OptMove:
                gui.doSkipOptMove();
                break;
            case OptBuild:
                gui.doSkipOptBuild();
                break;
        }
    }

    public void skipButtonReleased() {
        SkipButton.setImage(new Image("images/Buttons/btn_Skip.png"));
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
            Player1Panel.setImage(new Image("images/Board/PlayingPlayerPanel.png"));
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Ares.png"));
                    OptGrid.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Poseidon.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuildBeforeMove.png"));
                    OptGrid.setVisible(true);
                }else{
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuild.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalMove.png"));
                OptGrid.setVisible(true);
            }
        }
        else {
            Player1Panel.setImage(new Image("images/Board/PlayerBoardPanel.png"));
        }
        Player1God.setImage(new Image("images/Gods/"+god+"_icon.png"));

        nicknamePlayer1.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player1Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player1Dead.setVisible(true);
        }
    }

    public void setPlayer2Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player2Panel.setImage(new Image("images/Board/PlayingPlayerPanel.png"));
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Ares.png"));
                    OptGrid.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Poseidon.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuildBeforeMove.png"));
                    OptGrid.setVisible(true);
                }else{
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuild.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalMove.png"));
                OptGrid.setVisible(true);
            }
        }
        else {
            Player2Panel.setImage(new Image("images/Board/PlayerBoardPanel.png"));
        }
        Player2God.setImage(new Image("images/Gods/"+god+"_icon.png"));
        nicknamePlayer2.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player2Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player2Dead.setVisible(true);
        }
    }

    public void setPlayer3Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player3Panel.setImage(new Image("images/Board/PlayingPlayerPanel.png"));
            if(this.gui.getCurrentPhase().equals(GUI.Phase.OptEnd)){
                if (god.equals("Ares")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Ares.png"));
                    OptGrid.setVisible(true);
                } else if(god.equals("Poseidon")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalEnd_Poseidon.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptBuild)){
                if(god.equals("Prometheus")){
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuildBeforeMove.png"));
                    OptGrid.setVisible(true);
                }else{
                    DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalBuild.png"));
                    OptGrid.setVisible(true);
                }
            }else if(this.gui.getCurrentPhase().equals(GUI.Phase.OptMove)){
                DescriptionOptional.setImage(new Image("images/OptionalPhases/OptionalMove.png"));
                OptGrid.setVisible(true);
            }
        }
        else {
            Player3Panel.setImage(new Image("images/Board/PlayerBoardPanel.png"));
        }
        Player3God.setImage(new Image("images/Gods/"+god+"_icon.png"));
        nicknamePlayer3.setText(nickname.toUpperCase());

        Image worker_icon = new Image(url_icon);
        Player3Icon.setImage(worker_icon);

        if(deadPlayer) {
            Player3Dead.setVisible(true);
        }
    }

    public void setPanel3Visibility(boolean visibility) {
        Player3Icon.setVisible(visibility);
        Player3Panel.setVisible(visibility);
        nicknamePlayer3.setVisible(visibility);
        Player3God.setVisible(visibility);
    }

    public void resetBoardGrid() {
        if(BoardGrid.getChildren().size()!=0){
            BoardGrid.getChildren().clear();
        }
        OptGrid.setVisible(false);
        DisabledPane.setVisible(false);
        LevelOrDomeQuestion.setVisible(false);
        indexcandidatecells.clear();
    }

    public void clickedOnGrid(MouseEvent e){
        Node source = e.getPickResult().getIntersectedNode();
        Integer gridColumn = GridPane.getColumnIndex(source);
        Integer gridRow = GridPane.getRowIndex(source);
        if(gridRow != null && gridColumn != null) {
            int chosenCellIndex = abs(gridRow - 4) * 5 + gridColumn;
            switch (this.gui.getCurrentPhase()) {
                case ChooseWorker:
                    if (this.gui.getNicknameOfCellNode(this.gui.getCellNodeGivenTheID(chosenCellIndex)).equals(this.gui.client.getNickname())) {
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
        LevelButton.setImage(new Image("images/Buttons/btn_Level_pressed.png"));
    }

    public void DomeChosen() {
        DomeButton.setImage(new Image("images/Buttons/btn_Dome_pressed.png"));
    }

    public void LevelReleased() {
        LevelButton.setImage(new Image("images/Buttons/btn_Level.png"));
        gui.doSendCandidateBuildForAtlas(cellSelected, "B");
    }

    public void DomeReleased() {
        DomeButton.setImage(new Image("images/Buttons/btn_Dome_pressed.png"));
        gui.doSendCandidateBuildForAtlas(cellSelected, "D");
    }

    public void exitButtonHovered() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame_hovered.png"));
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
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }

    public void setPhaseName(){
        switch (this.gui.getCurrentPhase()){
            case ChooseWorker:
                PhaseName.setImage(new Image("images/Board/ChooseWorker.png"));
                break;
            case Move:
            case OptMove:
                PhaseName.setImage(new Image("images/Board/Move.png"));
                break;
            case Build:
            case OptBuild:
                PhaseName.setImage(new Image("images/Board/Build.png"));
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
