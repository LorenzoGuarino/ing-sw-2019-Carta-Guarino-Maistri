package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.View.CLI;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardPage_OptBuildController {

    private GUI gui;
    private ArrayList<ImageView> cellLevelList = new ArrayList<>();
    private ArrayList<ImageView> cellDomeList = new ArrayList<>();
    private ArrayList<ImageView> cellWorkerList = new ArrayList<>();
    private ArrayList<ImageView> cellCandidateList = new ArrayList<>();
    private final List<String> cellsToSend = new ArrayList<>();
    private String cellSelected;
    private List<Integer> indexcandidatecells = new ArrayList<Integer>();

    @FXML
    public ImageView DescriptionOptional;

    Image OptionalBuild = new Image("images/OptionalPhases/OptionalBuild.png");
    Image OptionalBuildBeforeMove = new Image("images/OptionalPhases/OptionalBuildBeforeMove.png");

    @FXML
    public ImageView SkipButton;

    Image SkipButtonReleased = new Image("images/Buttons/btn_Skip.png");
    Image SkipButtonPressed = new Image("images/Buttons/btn_Skip_pressed.png");

    @FXML
    //cellLevelImageViews
    public ImageView A1L;
    public ImageView A2L;
    public ImageView A3L;
    public ImageView A4L;
    public ImageView A5L;
    public ImageView B1L;
    public ImageView B2L;
    public ImageView B3L;
    public ImageView B4L;
    public ImageView B5L;
    public ImageView C1L;
    public ImageView C2L;
    public ImageView C3L;
    public ImageView C4L;
    public ImageView C5L;
    public ImageView D1L;
    public ImageView D2L;
    public ImageView D3L;
    public ImageView D4L;
    public ImageView D5L;
    public ImageView E1L;
    public ImageView E2L;
    public ImageView E3L;
    public ImageView E4L;
    public ImageView E5L;

    //cellDomeImageViews
    public ImageView A1D;
    public ImageView A2D;
    public ImageView A3D;
    public ImageView A4D;
    public ImageView A5D;
    public ImageView B1D;
    public ImageView B2D;
    public ImageView B3D;
    public ImageView B4D;
    public ImageView B5D;
    public ImageView C1D;
    public ImageView C2D;
    public ImageView C3D;
    public ImageView C4D;
    public ImageView C5D;
    public ImageView D1D;
    public ImageView D2D;
    public ImageView D3D;
    public ImageView D4D;
    public ImageView D5D;
    public ImageView E1D;
    public ImageView E2D;
    public ImageView E3D;
    public ImageView E4D;
    public ImageView E5D;

    //cellWorkerImageViews
    public ImageView A1W;
    public ImageView A2W;
    public ImageView A3W;
    public ImageView A4W;
    public ImageView A5W;
    public ImageView B1W;
    public ImageView B2W;
    public ImageView B3W;
    public ImageView B4W;
    public ImageView B5W;
    public ImageView C1W;
    public ImageView C2W;
    public ImageView C3W;
    public ImageView C4W;
    public ImageView C5W;
    public ImageView D1W;
    public ImageView D2W;
    public ImageView D3W;
    public ImageView D4W;
    public ImageView D5W;
    public ImageView E1W;
    public ImageView E2W;
    public ImageView E3W;
    public ImageView E4W;
    public ImageView E5W;

    //cellCandidateImageViews
    public ImageView A1C;
    public ImageView A2C;
    public ImageView A3C;
    public ImageView A4C;
    public ImageView A5C;
    public ImageView B1C;
    public ImageView B2C;
    public ImageView B3C;
    public ImageView B4C;
    public ImageView B5C;
    public ImageView C1C;
    public ImageView C2C;
    public ImageView C3C;
    public ImageView C4C;
    public ImageView C5C;
    public ImageView D1C;
    public ImageView D2C;
    public ImageView D3C;
    public ImageView D4C;
    public ImageView D5C;
    public ImageView E1C;
    public ImageView E2C;
    public ImageView E3C;
    public ImageView E4C;
    public ImageView E5C;


    public Image level1Image = new Image("images/Board/Level1_Board.png");
    public Image level2Image = new Image("images/Board/Level2_Board.png");
    public Image level3Image = new Image("images/Board/Level3_Board.png");
    public Image domeImage = new Image("images/Board/Dome_Board.png");
    public Image candidate = new Image("/images/Board/CandidateCell_board.png");

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
    public BoardPage_OptBuildController(){
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
     * This method creates a dependency between the level of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellLevelList() {
        cellLevelList.add(this.A1L);
        cellLevelList.add(this.A2L);
        cellLevelList.add(this.A3L);
        cellLevelList.add(this.A4L);
        cellLevelList.add(this.A5L);
        cellLevelList.add(this.B1L);
        cellLevelList.add(this.B2L);
        cellLevelList.add(this.B3L);
        cellLevelList.add(this.B4L);
        cellLevelList.add(this.B5L);
        cellLevelList.add(this.C1L);
        cellLevelList.add(this.C2L);
        cellLevelList.add(this.C3L);
        cellLevelList.add(this.C4L);
        cellLevelList.add(this.C5L);
        cellLevelList.add(this.D1L);
        cellLevelList.add(this.D2L);
        cellLevelList.add(this.D3L);
        cellLevelList.add(this.D4L);
        cellLevelList.add(this.D5L);
        cellLevelList.add(this.E1L);
        cellLevelList.add(this.E2L);
        cellLevelList.add(this.E3L);
        cellLevelList.add(this.E4L);
        cellLevelList.add(this.E5L);
    }
    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellDomeList() {
        cellDomeList.add(this.A1D);
        cellDomeList.add(this.A2D);
        cellDomeList.add(this.A3D);
        cellDomeList.add(this.A4D);
        cellDomeList.add(this.A5D);
        cellDomeList.add(this.B1D);
        cellDomeList.add(this.B2D);
        cellDomeList.add(this.B3D);
        cellDomeList.add(this.B4D);
        cellDomeList.add(this.B5D);
        cellDomeList.add(this.C1D);
        cellDomeList.add(this.C2D);
        cellDomeList.add(this.C3D);
        cellDomeList.add(this.C4D);
        cellDomeList.add(this.C5D);
        cellDomeList.add(this.D1D);
        cellDomeList.add(this.D2D);
        cellDomeList.add(this.D3D);
        cellDomeList.add(this.D4D);
        cellDomeList.add(this.D5D);
        cellDomeList.add(this.E1D);
        cellDomeList.add(this.E2D);
        cellDomeList.add(this.E3D);
        cellDomeList.add(this.E4D);
        cellDomeList.add(this.E5D);
    }

    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellWorkerList() {
        cellWorkerList.add(this.A1W);
        cellWorkerList.add(this.A2W);
        cellWorkerList.add(this.A3W);
        cellWorkerList.add(this.A4W);
        cellWorkerList.add(this.A5W);
        cellWorkerList.add(this.B1W);
        cellWorkerList.add(this.B2W);
        cellWorkerList.add(this.B3W);
        cellWorkerList.add(this.B4W);
        cellWorkerList.add(this.B5W);
        cellWorkerList.add(this.C1W);
        cellWorkerList.add(this.C2W);
        cellWorkerList.add(this.C3W);
        cellWorkerList.add(this.C4W);
        cellWorkerList.add(this.C5W);
        cellWorkerList.add(this.D1W);
        cellWorkerList.add(this.D2W);
        cellWorkerList.add(this.D3W);
        cellWorkerList.add(this.D4W);
        cellWorkerList.add(this.D5W);
        cellWorkerList.add(this.E1W);
        cellWorkerList.add(this.E2W);
        cellWorkerList.add(this.E3W);
        cellWorkerList.add(this.E4W);
        cellWorkerList.add(this.E5W);
    }

    /**
     * This method creates a dependency between the dome of the cell index = the list index
     * and the corresponding ImageView in the gridPane displaying the board
     */
    public void initCellCandidateList() {
        cellCandidateList.add(this.A1C);
        cellCandidateList.add(this.A2C);
        cellCandidateList.add(this.A3C);
        cellCandidateList.add(this.A4C);
        cellCandidateList.add(this.A5C);
        cellCandidateList.add(this.B1C);
        cellCandidateList.add(this.B2C);
        cellCandidateList.add(this.B3C);
        cellCandidateList.add(this.B4C);
        cellCandidateList.add(this.B5C);
        cellCandidateList.add(this.C1C);
        cellCandidateList.add(this.C2C);
        cellCandidateList.add(this.C3C);
        cellCandidateList.add(this.C4C);
        cellCandidateList.add(this.C5C);
        cellCandidateList.add(this.D1C);
        cellCandidateList.add(this.D2C);
        cellCandidateList.add(this.D3C);
        cellCandidateList.add(this.D4C);
        cellCandidateList.add(this.D5C);
        cellCandidateList.add(this.E1C);
        cellCandidateList.add(this.E2C);
        cellCandidateList.add(this.E3C);
        cellCandidateList.add(this.E4C);
        cellCandidateList.add(this.E5C);
    }


    /**
     * Given a cell index and a level ,this method sets the corresponding level height on the corresponding gridPane cell
     * @param index the index of the cell to set the level of
     * @param level the level displayed on the given cell after the method call
     */
    public void setLevel(int index, int level){
        System.out.println("SETLEVEL CALLED ON" +index);
        if(this.cellLevelList.size()==0){
            initCellLevelList();
        }
        switch(level){
            case 1: {
                this.cellLevelList.get(index).setImage(this.level1Image);
                break;
            }
            case 2: {
                this.cellLevelList.get(index).setImage(this.level2Image);
                break;
            }
            case 3: {
                this.cellLevelList.get(index).setImage(this.level3Image);
                break;
            }
        }
    }

    public void setDome(int index){
        if(this.cellDomeList.size()==0){
            initCellDomeList();
        }
        this.cellDomeList.get(index).setImage(this.domeImage);
    }

    public void setWorker(int index, String url){
        if(this.cellWorkerList.size()==0){
            initCellWorkerList();
        }
        Image worker = new Image(url);
        this.cellWorkerList.get(index).setImage(worker);
    }

    public void setCandidate(int index){
        if(this.cellCandidateList.size()==0){
            initCellCandidateList();
        }
        this.cellCandidateList.get(index).setImage(this.candidate);
        indexcandidatecells.add(index);
    }

    public void setPlayer1Panel(String god, String nickname, boolean playingPlayer, boolean deadPlayer, String url_icon) {
        if(playingPlayer) {
            Player1Panel.setImage(PlayingPlayerPanel);
            if(god.equals("Prometheus")){
                DescriptionOptional.setImage(OptionalBuildBeforeMove);
            }else{
                DescriptionOptional.setImage(OptionalBuild);
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
            if(god.equals("Prometheus")){
                DescriptionOptional.setImage(OptionalBuildBeforeMove);
            }else{
                DescriptionOptional.setImage(OptionalBuild);
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
            if(god.equals("Prometheus")){
                DescriptionOptional.setImage(OptionalBuildBeforeMove);
            }else{
                DescriptionOptional.setImage(OptionalBuild);
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

    public void clickedOnCell(MouseEvent e){
        int chosenCellIndex;
        ImageView selectedCell = (ImageView) e.getTarget();
        System.out.println(selectedCell.getId());
        String selectedCellId = selectedCell.getId();
        switch(selectedCellId.charAt(0)){
            case 'A':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        cellSelected = "A1";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '2':
                        cellSelected = "A2";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '3':
                        A3C.setVisible(true);
                        cellSelected = "A3";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '4':
                        cellSelected = "A4";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '5':
                        cellSelected = "A5";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                }
                break;
            case 'B':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        cellSelected = "B1";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '2':
                        cellSelected = "B2";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '3':
                        cellSelected = "B3";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '4':
                        cellSelected = "B4";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '5':
                        cellSelected = "B5";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                }
                break;
            case 'C':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        cellSelected = "C1";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '2':
                        cellSelected = "C2";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '3':
                        cellSelected = "C3";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '4':
                        cellSelected = "C4";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '5':
                        cellSelected = "C5";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                }
                break;
            case 'D':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        cellSelected = "D1";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '2':
                        cellSelected = "D2";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '3':
                        cellSelected = "D3";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '4':
                        cellSelected = "D4";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '5':
                        cellSelected = "D5";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                }
                break;
            case 'E':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        cellSelected = "E1";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '2':
                        cellSelected = "E2";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '3':
                        cellSelected = "E3";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '4':
                        cellSelected = "E4";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                    case '5':
                        cellSelected = "E5";
                        chosenCellIndex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
                        for (int i = 0; i < indexcandidatecells.size(); i++) {
                            if (indexcandidatecells.get(i) == chosenCellIndex) {
                                cellSelected = Integer.toString(chosenCellIndex);
                                gui.doSendCandidateBuild(cellSelected);
                                break;
                            }
                        }
                        break;
                }
                break;

        }

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

}
