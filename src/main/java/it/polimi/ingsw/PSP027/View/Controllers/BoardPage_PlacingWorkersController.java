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
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardPage_PlacingWorkersController {
    private GUI gui;
    private ArrayList<ImageView> cellWorkerList = new ArrayList<>();
    private ArrayList<ImageView> cellCandidateList = new ArrayList<>();
    private final List<String> cellsToSend = new ArrayList<>();
    private String cellSelected;

    @FXML
    public ImageView ConfirmButton;

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

    public Image candidate = new Image("/images/Board/CandidateCell_board.png");

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
    public ImageView ExitGameButton;
    /**
     * Constructor, called before the initialize method
     */
    public BoardPage_PlacingWorkersController(){
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
     * This method creates a List of Image to display the worker position
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
     * This method creates a List of Image to display the Cell that can be clicked
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
     * Method that update the image of the button pressed and call the method that sends the position,
     * chosen for the workers by the player that is playing to the GUI
     */
    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        gui.doSendSelectedCellsForWorkers(cellsToSend);
    }

    /**
     * Method that update the image of the button pressed
     */
    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }

    /**
     * Method that set an image to display the worker already on the board
     * @param index cell occupied by the worker
     * @param url Id that distinguishes each workers by his color (different by each player)
     */
    public void setWorker(int index, String url){
        if(this.cellWorkerList.size()==0){
            initCellWorkerList();
        }
        Image worker = new Image(url);
        this.cellWorkerList.get(index).setImage(worker);
    }

    /**
     * Method that set an image to display the Candidate Cell selectable
     * @param index cell highlighted by the Candidate ring
     */
    public void setCandidate(int index){
        if(this.cellCandidateList.size()==0) {
            initCellCandidateList();
        }
        this.cellCandidateList.get(index).setImage(this.candidate);
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
     * Method that set invisible the Player 3 Panel if this is a 2 PLayer match
     * @param visibility identify if the match is 2 or 3 Players
     */
    public void setPanel3Visibility(boolean visibility) {
        Player3Icon.setVisible(visibility);
        Player3Panel.setVisible(visibility);
        nicknamePlayer3.setVisible(visibility);
        Player3God.setVisible(visibility);
    }

    /**
     * Method that save the cell clicked
     * @param e Mouse Event that triggers the Method
     */
    public void clickedOnCell(MouseEvent e){
        ImageView selectedCell = (ImageView) e.getTarget();
        System.out.println(selectedCell.getId());
        String selectedCellId = selectedCell.getId();
        switch(selectedCellId.charAt(0)){
            case 'A':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        A1C.setVisible(true);
                        cellSelected = "A1";
                        setCellsToList();
                        break;
                    case '2':
                        A2C.setVisible(true);
                        cellSelected = "A2";
                        setCellsToList();
                        break;
                    case '3':
                        A3C.setVisible(true);
                        cellSelected = "A3";
                        setCellsToList();
                        break;
                    case '4':
                        A4C.setVisible(true);
                        cellSelected = "A4";
                        setCellsToList();
                        break;
                    case '5':
                        A5C.setVisible(true);
                        cellSelected = "A5";
                        setCellsToList();
                        break;
                }
                break;
            case 'B':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        B1C.setVisible(true);
                        cellSelected = "B1";
                        setCellsToList();
                        break;
                    case '2':
                        B2C.setVisible(true);
                        cellSelected = "B2";
                        setCellsToList();
                        break;
                    case '3':
                        B3C.setVisible(true);
                        cellSelected = "B3";
                        setCellsToList();
                        break;
                    case '4':
                        B4C.setVisible(true);
                        cellSelected = "B4";
                        setCellsToList();
                        break;
                    case '5':
                        B5C.setVisible(true);
                        cellSelected = "B5";
                        setCellsToList();
                        break;
                }
                break;
            case 'C':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        C1C.setVisible(true);
                        cellSelected = "C1";
                        setCellsToList();
                        break;
                    case '2':
                        C2C.setVisible(true);
                        cellSelected = "C2";
                        setCellsToList();
                        break;
                    case '3':
                        C3C.setVisible(true);
                        cellSelected = "C3";
                        setCellsToList();
                        break;
                    case '4':
                        C4C.setVisible(true);
                        cellSelected = "C4";
                        setCellsToList();
                        break;
                    case '5':
                        C5C.setVisible(true);
                        cellSelected = "C5";
                        setCellsToList();
                        break;
                }
                break;
            case 'D':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        D1C.setVisible(true);
                        cellSelected = "D1";
                        setCellsToList();
                        break;
                    case '2':
                        D2C.setVisible(true);
                        cellSelected = "D2";
                        setCellsToList();
                        break;
                    case '3':
                        D3C.setVisible(true);
                        cellSelected = "D3";
                        setCellsToList();
                        break;
                    case '4':
                        D4C.setVisible(true);
                        cellSelected = "D4";
                        setCellsToList();
                        break;
                    case '5':
                        D5C.setVisible(true);
                        cellSelected = "D5";
                        setCellsToList();
                        break;
                }
                break;
            case 'E':
                switch (selectedCellId.charAt(1)){
                    case '1':
                        E1C.setVisible(true);
                        cellSelected = "E1";
                        setCellsToList();
                        break;
                    case '2':
                        E2C.setVisible(true);
                        cellSelected = "E2";
                        setCellsToList();
                        break;
                    case '3':
                        E3C.setVisible(true);
                        cellSelected = "E3";
                        setCellsToList();
                        break;
                    case '4':
                        E4C.setVisible(true);
                        cellSelected = "E4";
                        setCellsToList();
                        break;
                    case '5':
                        E5C.setVisible(true);
                        cellSelected = "E5";
                        setCellsToList();
                        break;
                }
                break;

        }

    }

    /**
     * Method that check if the already selected cells are more than 2 and then removes the first clicked to save the last one clicked
     */
    public void setCellsToList(){
        boolean bAlreadySelected;
        boolean bAlreadyTaken;
        if(cellsToSend.size()<2){
            bAlreadySelected = false;
            for(int i=0; i<cellsToSend.size(); i++){
                if(cellSelected.equals(cellsToSend.get(i))){
                    bAlreadySelected = true;
                    break;
                }
            }

            bAlreadyTaken = false;
            int chosencellindex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
            if(!gui.getNicknameOfCellNode(gui.getCellNodeGivenTheID(chosencellindex)).isEmpty()) {
                bAlreadyTaken = true;
            }

            if(!bAlreadySelected && !bAlreadyTaken){
                cellsToSend.add(cellSelected);
                switch(cellSelected){
                    case "A1":
                        A1C.setImage(candidate);
                        break;
                    case "A2":
                        A2C.setImage(candidate);
                        break;
                    case "A3":
                        A3C.setImage(candidate);
                        break;
                    case "A4":
                        A4C.setImage(candidate);
                        break;
                    case "A5":
                        A5C.setImage(candidate);
                        break;
                    case "B1":
                        B1C.setImage(candidate);
                        break;
                    case "B2":
                        B2C.setImage(candidate);
                        break;
                    case "B3":
                        B3C.setImage(candidate);
                        break;
                    case "B4":
                        B4C.setImage(candidate);
                        break;
                    case "B5":
                        B5C.setImage(candidate);
                        break;
                    case "C1":
                        C1C.setImage(candidate);
                        break;
                    case "C2":
                        C2C.setImage(candidate);
                        break;
                    case "C3":
                        C3C.setImage(candidate);
                        break;
                    case "C4":
                        C4C.setImage(candidate);
                        break;
                    case "C5":
                        C5C.setImage(candidate);
                        break;
                    case "D1":
                        D1C.setImage(candidate);
                        break;
                    case "D2":
                        D2C.setImage(candidate);
                        break;
                    case "D3":
                        D3C.setImage(candidate);
                        break;
                    case "D4":
                        D4C.setImage(candidate);
                        break;
                    case "D5":
                        D5C.setImage(candidate);
                        break;
                    case "E1":
                        E1C.setImage(candidate);
                        break;
                    case "E2":
                        E2C.setImage(candidate);
                        break;
                    case "E3":
                        E3C.setImage(candidate);
                        break;
                    case "E4":
                        E4C.setImage(candidate);
                        break;
                    case "E5":
                        E5C.setImage(candidate);
                        break;
                }
            }
            if(cellsToSend.size() == 2){
                ConfirmButton.setVisible(true);
            }
        } else if(cellsToSend.size()==2){
            bAlreadySelected = false;
            for(int i=0; i<cellsToSend.size(); i++){
                if(cellSelected.equals(cellsToSend.get(i))){
                    bAlreadySelected = true;
                    break;
                }
            }
            bAlreadyTaken = false;
            int chosencellindex = (cellSelected.charAt(0) - 'A') * 5 + (cellSelected.charAt(1) - '1');
            if(!gui.getNicknameOfCellNode(gui.getCellNodeGivenTheID(chosencellindex)).isEmpty()) {
                bAlreadyTaken = true;
            }
            if(!bAlreadySelected && !bAlreadyTaken){
                switch(cellSelected){
                    case "A1":
                        A1C.setImage(candidate);
                        break;
                    case "A2":
                        A2C.setImage(candidate);
                        break;
                    case "A3":
                        A3C.setImage(candidate);
                        break;
                    case "A4":
                        A4C.setImage(candidate);
                        break;
                    case "A5":
                        A5C.setImage(candidate);
                        break;
                    case "B1":
                        B1C.setImage(candidate);
                        break;
                    case "B2":
                        B2C.setImage(candidate);
                        break;
                    case "B3":
                        B3C.setImage(candidate);
                        break;
                    case "B4":
                        B4C.setImage(candidate);
                        break;
                    case "B5":
                        B5C.setImage(candidate);
                        break;
                    case "C1":
                        C1C.setImage(candidate);
                        break;
                    case "C2":
                        C2C.setImage(candidate);
                        break;
                    case "C3":
                        C3C.setImage(candidate);
                        break;
                    case "C4":
                        C4C.setImage(candidate);
                        break;
                    case "C5":
                        C5C.setImage(candidate);
                        break;
                    case "D1":
                        D1C.setImage(candidate);
                        break;
                    case "D2":
                        D2C.setImage(candidate);
                        break;
                    case "D3":
                        D3C.setImage(candidate);
                        break;
                    case "D4":
                        D4C.setImage(candidate);
                        break;
                    case "D5":
                        D5C.setImage(candidate);
                        break;
                    case "E1":
                        E1C.setImage(candidate);
                        break;
                    case "E2":
                        E2C.setImage(candidate);
                        break;
                    case "E3":
                        E3C.setImage(candidate);
                        break;
                    case "E4":
                        E4C.setImage(candidate);
                        break;
                    case "E5":
                        E5C.setImage(candidate);
                        break;
                }

                switch(cellsToSend.get(0)){
                    case "A1":
                        A1C.setImage(null);
                        break;
                    case "A2":
                        A2C.setImage(null);
                        break;
                    case "A3":
                        A3C.setImage(null);
                        break;
                    case "A4":
                        A4C.setImage(null);
                        break;
                    case "A5":
                        A5C.setImage(null);
                        break;
                    case "B1":
                        B1C.setImage(null);
                        break;
                    case "B2":
                        B2C.setImage(null);
                        break;
                    case "B3":
                        B3C.setImage(null);
                        break;
                    case "B4":
                        B4C.setImage(null);
                        break;
                    case "B5":
                        B5C.setImage(null);
                        break;
                    case "C1":
                        C1C.setImage(null);
                        break;
                    case "C2":
                        C2C.setImage(null);
                        break;
                    case "C3":
                        C3C.setImage(null);
                        break;
                    case "C4":
                        C4C.setImage(null);
                        break;
                    case "C5":
                        C5C.setImage(null);
                        break;
                    case "D1":
                        D1C.setImage(null);
                        break;
                    case "D2":
                        D2C.setImage(null);
                        break;
                    case "D3":
                        D3C.setImage(null);
                        break;
                    case "D4":
                        D4C.setImage(null);
                        break;
                    case "D5":
                        D5C.setImage(null);
                        break;
                    case "E1":
                        E1C.setImage(null);
                        break;
                    case "E2":
                        E2C.setImage(null);
                        break;
                    case "E3":
                        E3C.setImage(null);
                        break;
                    case "E4":
                        E4C.setImage(null);
                        break;
                    case "E5":
                        E5C.setImage(null);
                        break;
                }

                cellsToSend.remove(0);
                cellsToSend.add(cellSelected);
            }
        }
    }

    /**
     * Method that update the image of the button when the mouse is on the button
     */
    public void exitButtonHovered() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame_hovered.png"));
    }

    /**
     * Method that update the button image and than proceed to deregister the player that want to exit from the game
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
     * Method that update the image of the button pressed
     */
    public void exitButtonReleased() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }
}
