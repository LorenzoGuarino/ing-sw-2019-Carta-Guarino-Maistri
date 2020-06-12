
package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import java.util.*;

/**
 * @author Elisa Maistri
 */

public class YouHaveWonController {

    // Reference to the main gui application
    private GUI gui;
    public Client client = null;

    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView WinnerGod;
    public Label Congrats;
    public ImageView ExitGameButton;
    public ImageView PlayButton;
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public YouHaveWonController() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize() {

    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui(GUI Gui) {
        this.gui = Gui;
    }
    /**
     * Method that set the god Winner on the screen
     * @param godWinner god of the player that has won
     */
    public void setWinnerPodium(String godWinner) {
        WinnerGod.setImage(new Image("images/Gods/"+godWinner+"_icon.png"));
    }
    /**
     * Method that tells the player that he has won the game
     * @param winner nickname of the player that has won
     */
    public void setWinnerCongrats(String winner) {
        Congrats.setText("Congratulations, " + winner + "!");
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game? The game will be closed." ,YES, NO);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You are going to exit the game!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(gui.getSantoriniStage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == YES)
            gui.doExit();
    }
    /**
     * Method that update the image of the button pressed
     */
    public void exitButtonReleased() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }
    /**
     * Method that update the image of the button pressed
     */
    public void playButtonPressed() {
        PlayButton.setImage(new Image("images/Buttons/button-play-down.png"));
    }
    /**
     * Method that update the image of the button pressed and send the command to start a new game
     */
    public void playButtonReleased() {
        PlayButton.setImage(new Image("images/Buttons/button-play-normal.png"));
        gui.doPlayAgain();
    }
}