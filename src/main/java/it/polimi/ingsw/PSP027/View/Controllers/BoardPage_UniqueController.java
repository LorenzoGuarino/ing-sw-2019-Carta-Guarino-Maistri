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

/**
 * @author Elisa Maistri
 * @author danielecarta
 * @author Lorenzo Guarino
 */

public class BoardPage_UniqueController {
    private GUI gui;
    private List<Integer> indexcandidatecells = new ArrayList<Integer>();
    private String cellSelected;
    private final List<String> cellsToSend = new ArrayList<>();

    @FXML
    public ImageView ConfirmButton;

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
     * Constructor, called before the initialize method
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
        ConfirmButton.setVisible(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method that updates the image of the button pressed, and call the next GUI command
     * based on the phase of the turn played
     */
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

    /**
     * Method that updates the image of the button pressed
     */
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

    /**
     * Method that sets the dome on the cell index given
     * @param index cell where the dome needs to be add
     */
    public void setDome(int index){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView dome = new ImageView("images/Board/Dome_Board.png");
        dome.setPickOnBounds(true);
        dome.setFitHeight(100);
        dome.setFitWidth(100);
        BoardGrid.add(dome, column, row);
    }
    /**
     * Method that sets the worker on the cell index given
     * @param index cell where the worker needs to be add
     * @param url indicate the color of the worker (different color for each player)
     */
    public void setWorker(int index, String url){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView worker = new ImageView(url);
        worker.setPickOnBounds(true);
        worker.setFitHeight(100);
        worker.setFitWidth(100);
        BoardGrid.add(worker, column, row);
    }

    /**
     * Method that sets the given cell index in a candidate cell highlighted
     * @param index cell where the candidate ring needs to be add
     */
    public void setCandidate(int index){
        int column = index%5;
        int row = abs(index/5-4);
        ImageView candidate = new ImageView("images/Board/CandidateCell_Board.png");
        candidate.setPickOnBounds(true);
        candidate.setFitHeight(100);
        candidate.setFitWidth(100);
        BoardGrid.add(candidate, column, row);
        indexcandidatecells.add(index);
    }

    /**
     *
     */
    public void setPlacingWorkersBoard() {
        for(int i=0; i<25; i++) {
            int column = i%5;
            int row = abs(i/5-4);
            ImageView invisiblecandidate = new ImageView("images/Board/InvisibleCell.png");
            invisiblecandidate.setPickOnBounds(true);
            invisiblecandidate.setFitHeight(100);
            invisiblecandidate.setFitWidth(100);
            BoardGrid.add(invisiblecandidate, column, row);
        }
    }

    /**
     * Method that set the Player 1 Pane with every detail about it
     * @param god god card owned by the player 1
     * @param nickname nickname of the player 1
     * @param playingPlayer indicate if the player 1 is the one that is playing the turn
     * @param deadPlayer indicate if the player 1 is dead or is not playing anymore
     * @param url_icon Id that distinguishes each workers by his color (different for each player)
     */
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
    /**
     * Method that set the Player 2 Pane with every detail about it
     * @param god god card owned by the player 2
     * @param nickname nickname of the player 2
     * @param playingPlayer indicate if the player 2 is the one that is playing the turn
     * @param deadPlayer indicate if the player 2 is dead or is not playing anymore
     * @param url_icon Id that distinguishes each workers by his color (different for each player)
     */
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
    /**
     * Method that set the Player 3 Pane with every detail about it
     * @param god god card owned by the player 3
     * @param nickname nickname of the player 3
     * @param playingPlayer indicate if the player 3 is the one that is playing the turn
     * @param deadPlayer indicate if the player 3 is dead or is not playing anymore
     * @param url_icon Id that distinguishes each workers by his color (different for each player)
     */
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
    /**
     * Method that sets invisible the Player 3 Panel if this is a 2 PLayer match
     * @param visibility identify if the match is 2 or 3 Players
     */
    public void setPanel3Visibility(boolean visibility) {
        Player3Icon.setVisible(visibility);
        Player3Panel.setVisible(visibility);
        nicknamePlayer3.setVisible(visibility);
        Player3God.setVisible(visibility);
    }

    /**
     * Method that clears the Board (GridPane) and reset the Player Panel every turn
     */
    public void resetBoardGrid() {
        if(BoardGrid.getChildren().size()!=0){
            BoardGrid.getChildren().clear();
        }
        OptGrid.setVisible(false);
        DisabledPane.setVisible(false);
        LevelOrDomeQuestion.setVisible(false);
        indexcandidatecells.clear();
        Player1Dead.setVisible(false);
        Player1Icon.setImage(null);
        Player2Dead.setVisible(false);
        Player2Icon.setImage(null);
        Player3Dead.setVisible(false);
        Player3Icon.setImage(null);
        ConfirmButton.setVisible(false);
    }

    /**
     * Method that allows you to launch the next command, based on the phase of the turn played
     * @param e Mouse Event that triggers the Method
     */
    public void clickedOnGrid(MouseEvent e){
        Node source = e.getPickResult().getIntersectedNode();
        ImageView selectedCell = (ImageView) e.getTarget();
        Integer gridColumn = GridPane.getColumnIndex(source);
        Integer gridRow = GridPane.getRowIndex(source);
        if(gridRow != null && gridColumn != null) {

            int chosenCellIndex = (4 - gridRow) * 5 + gridColumn;

            switch (this.gui.getCurrentPhase()) {

                case PlaceWorkers:

                    cellSelected = Integer.toString(chosenCellIndex);
                    boolean bAlreadySelected;
                    boolean bAlreadyTaken;

                    if(cellsToSend.size() <2) {
                        bAlreadySelected = false;
                        for(int i=0; i<cellsToSend.size(); i++){
                            if(cellSelected.equals(cellsToSend.get(i))){
                                bAlreadySelected = true;
                                break;
                            }
                        }

                        bAlreadyTaken = false;
                        if (!gui.getNicknameOfCellNode(gui.getCellNodeGivenTheID(chosenCellIndex)).isEmpty()) {
                            bAlreadyTaken = true;
                        }

                        if (!bAlreadySelected && !bAlreadyTaken) {
                            selectedCell.setImage(new Image("images/Board/CandidateCell_Board.png"));
                            cellsToSend.add(cellSelected);
                        }

                        /*if (!bAlreadySelected && !bAlreadyTaken) {
                            for(Node child : BoardGrid.getChildren()) {
                                Integer r = GridPane.getRowIndex(child);
                                Integer c = GridPane.getColumnIndex(child);
                                int row = r == null ? 0 : r;
                                int column = c == null ? 0 : c;
                                if (row == gridRow && column == gridColumn) {
                                    //ImageView selectedCell = (ImageView) child;
                                    //selectedCell.setVisible(true);
                                    selectedCell.setImage(new Image("images/Board/CandidateCell_Board.png"));
                                    cellsToSend.add(cellSelected);
                                    break;
                                }
                            }
                        }*/

                        if (cellsToSend.size() == 2) {
                            ConfirmButton.setVisible(true);
                        }

                    } else if(cellsToSend.size() == 2) {
                        bAlreadySelected = false;
                        for(int i=0; i<cellsToSend.size(); i++){
                            if(cellSelected.equals(cellsToSend.get(i))){
                                bAlreadySelected = true;
                                break;
                            }
                        }

                        bAlreadyTaken = false;
                        if (!gui.getNicknameOfCellNode(gui.getCellNodeGivenTheID(chosenCellIndex)).isEmpty()) {
                            bAlreadyTaken = true;
                        }

                        if (!bAlreadySelected && !bAlreadyTaken) {
                            int cellToRemoveIndex = Integer.parseInt(cellsToSend.get(0));
                            int cellToRemoveRow = 4 - (cellToRemoveIndex / 5);
                            int cellToRemoveColumn = cellToRemoveIndex % 5;

                            boolean candidateSet = false;
                            boolean candidateDeleted = false;

                            for(Node child : BoardGrid.getChildren()) {
                                Integer r = GridPane.getRowIndex(child);
                                Integer c = GridPane.getColumnIndex(child);
                                int row = r == null ? 0 : r;
                                int column = c == null ? 0 : c;
                                if(row == cellToRemoveRow && column == cellToRemoveColumn) {

                                    System.out.println("Removing cell " + row + " " + column);
                                    ImageView selectedCellToRemove = (ImageView) child;
                                    cellsToSend.remove(0);
                                    selectedCellToRemove.setImage(new Image("images/Board/InvisibleCell.png"));
                                    selectedCell.setImage(new Image("images/Board/CandidateCell_Board.png"));
                                    cellsToSend.add(cellSelected);
                                    break;
                                }
                            }

                            /*for(Node child : BoardGrid.getChildren()) {
                                Integer r = GridPane.getRowIndex(child);
                                Integer c = GridPane.getColumnIndex(child);
                                int row = r == null ? 0 : r;
                                int column = c == null ? 0 : c;
                                if (row == gridRow && column == gridColumn) {
                                    //ImageView selectedCell = (ImageView) child;
                                    //selectedCell.setVisible(true);
                                    selectedCell.setImage(new Image("images/Board/CandidateCell_Board.png"));
                                    cellsToSend.add(cellSelected);
                                    candidateSet = true;
                                } else if(row == cellToRemoveRow && column == cellToRemoveColumn) {
                                    ImageView selectedCellToRemove = (ImageView) child;
                                    cellsToSend.remove(0);
                                    selectedCellToRemove.setImage(new Image("images/Board/InvisibleCell.png"));
                                    candidateDeleted = true;
                                }

                                if(candidateDeleted && candidateSet)
                                    break;
                            }*/
                        }
                    }
                    break;
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

    /**
     * Method that checks if the current player has Atlas as god, so we can display the correct Pane
     * @param cellSelected cell selected by the player for the phase
     */
    public void sendCellSelected(String cellSelected) {
        if(!gui.getGodGivenTheNickname(gui.client.getNickname()).equals("Atlas")) {
            gui.doSendCandidateBuild(cellSelected);
        }
        else {
            DisabledPane.setVisible(true);
            LevelOrDomeQuestion.setVisible(true);
        }
    }

    /**
     * Method used only if the current player has Atlas as god, change the image of the button
     */
    public void LevelChosen() {
        LevelButton.setImage(new Image("images/Buttons/btn_Level_pressed.png"));
    }
    /**
     * Method used only if the current player has Atlas as god, change the image of the button
     */
    public void DomeChosen() {
        DomeButton.setImage(new Image("images/Buttons/btn_Dome_pressed.png"));
    }
    /**
     * Method used only if the current player has Atlas as god, change the image of the button
     * and send the next command
     */
    public void LevelReleased() {
        LevelButton.setImage(new Image("images/Buttons/btn_Level.png"));
        gui.doSendCandidateBuildForAtlas(cellSelected, "B");
    }
    /**
     * Method used only if the current player has Atlas as god, change the image of the button
     * and send the next command
     */
    public void DomeReleased() {
        DomeButton.setImage(new Image("images/Buttons/btn_Dome_pressed.png"));
        gui.doSendCandidateBuildForAtlas(cellSelected, "D");
    }

    /**
     * Method that updates the image of the button pressed and call the method that sends the position,
     * chosen for the workers by the player that is playing to the GUI
     */
    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        gui.doSendSelectedCellsForWorkers(cellsToSend);
    }

    /**
     * Method that updates the image of the button pressed
     */
    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }

    /**
     * Method that updates the image of the button when the mouse is on the button
     */
    public void exitButtonHovered() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame_hovered.png"));
    }

    /**
     * Method that updates the button image and than proceed to deregister the player that want to exit from the game
     */
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
    /**
     * Method that updates the image of the button pressed
     */
    public void exitButtonReleased() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }

    /**
     * Method that updates the image of the PhaseName, so the player has clear which phase
     * is currently playing
     */
    public void setPhaseName(){
        switch (this.gui.getCurrentPhase()){
            case PlaceWorkers:
                PhaseName.setImage(new Image("images/Board/PlaceWorkers.png"));
                break;
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
