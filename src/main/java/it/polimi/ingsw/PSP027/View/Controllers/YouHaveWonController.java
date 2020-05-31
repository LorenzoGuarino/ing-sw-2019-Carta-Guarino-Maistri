
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

    public void setWinnerPodium(String godWinner) {
        switch (godWinner) {
            case "Apollo":
                WinnerGod.setImage(new Image("images/Gods/Apollo_icon.png"));
                break;
            case "Artemis":
                WinnerGod.setImage(new Image("images/Gods/Artemis_icon.png"));
                break;
            case "Ares":
                WinnerGod.setImage(new Image("images/Gods/Ares_icon.png"));
                break;
            case "Athena":
                WinnerGod.setImage(new Image("images/Gods/Athena_icon.png"));
                break;
            case "Atlas":
                WinnerGod.setImage(new Image("images/Gods/Atlas_icon.png"));
                break;
            case "Demeter":
                WinnerGod.setImage(new Image("images/Gods/Demeter_icon.png"));
                break;
            case "Hephaestus":
                WinnerGod.setImage(new Image("images/Gods/Hephaestus_icon.png"));
                break;
            case "Hestia":
                WinnerGod.setImage(new Image("images/Gods/Hestia_icon.png"));
                break;
            case "Medusa":
                WinnerGod.setImage(new Image("images/Gods/Medusa_icon.png"));
                break;
            case "Minotaur":
                WinnerGod.setImage(new Image("images/Gods/Minotaur_icon.png"));
                break;
            case "Pan":
                WinnerGod.setImage(new Image("images/Gods/Pan_icon.png"));
                break;
            case "Poseidon":
                WinnerGod.setImage(new Image("images/Gods/Poseidon_icon.png"));
                break;
            case "Prometheus":
                WinnerGod.setImage(new Image("images/Gods/Prometheus_icon.png"));
                break;
            case "Zeus":
                WinnerGod.setImage(new Image("images/Gods/Zeus_icon.png"));
                break;
        }
    }

    public void setWinnerCongrats(String winner) {
        Congrats.setText("Congratulations, " + winner + "!");
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