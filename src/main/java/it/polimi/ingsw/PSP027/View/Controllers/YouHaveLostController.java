
package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.w3c.dom.Node;

import java.util.*;

public class YouHaveLostController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */
    @FXML
    public Label BetterLuckNextTime;
    public ImageView LoserGod;
    public ImageView ExitGameButton;
    public ImageView PlayButton;
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public YouHaveLostController() {
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

    public void setBetterLuckNextTime(String winner) {
        BetterLuckNextTime.setText(winner + " has won the game, better luck next time!");
    }

    public void setLoserPodium(String godLoser) {
        LoserGod.setImage(new Image("images/Gods/"+godLoser+"_icon.png"));
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void exitButtonHovered() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame_hovered.png"));
    }

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

    public void exitButtonReleased() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }

    public void playButtonPressed() {
        PlayButton.setImage(new Image("images/Buttons/button-play-down.png"));
    }

    public void playButtonReleased() {
        PlayButton.setImage(new Image("images/Buttons/button-play-normal.png"));
        gui.doPlayAgain();
    }
}