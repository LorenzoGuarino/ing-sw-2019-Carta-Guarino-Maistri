package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.w3c.dom.Node;

import java.util.*;

public class ChooseFirstPlayerCase2Controller {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label player1;
    public Label player2;
    public ImageView god;
    public ImageView ExitGameButton;

    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseFirstPlayerCase2Controller() {
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

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }

    public void setNickname (List<String> players) {
        player1.setText(players.get(0));
        player2.setText(players.get(1));
    }

    public void setGod (String firstPlayersGod) {
        switch (firstPlayersGod) {
            case "Apollo":
                god.setImage(new Image("images/Gods/Apollo_icon.png"));
                break;
            case "Artemis":
                god.setImage(new Image("images/Gods/Artemis_icon.png"));
                break;
            case "Ares":
                god.setImage(new Image("images/Gods/Ares_icon.png"));
                break;
            case "Athena":
                god.setImage(new Image("images/Gods/Athena_icon.png"));
                break;
            case "Atlas":
                god.setImage(new Image("images/Gods/Atlas_icon.png"));
                break;
            case "Demeter":
                god.setImage(new Image("images/Gods/Demeter_icon.png"));
                break;
            case "Hephaestus":
                god.setImage(new Image("images/Gods/Hephaestus_icon.png"));
                break;
            case "Hestia":
                god.setImage(new Image("images/Gods/Hestia_icon.png"));
                break;
            case "Medusa":
                god.setImage(new Image("images/Gods/Medusa_icon.png"));
                break;
            case "Minotaur":
                god.setImage(new Image("images/Gods/Minotaur_icon.png"));
                break;
            case "Pan":
                god.setImage(new Image("images/Gods/Pan_icon.png"));
                break;
            case "Poseidon":
                god.setImage(new Image("images/Gods/Poseidon_icon.png"));
                break;
            case "Prometheus":
                god.setImage(new Image("images/Gods/Prometheus_icon.png"));
                break;
            case "Zeus":
                god.setImage(new Image("images/Gods/Zeus_icon.png"));
                break;
        }
    }


    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void clickedOnFirstPlayer(){
        gui.doSendFirstPlayer(player1.getText());
    }
    public void clickedOnSecondPlayer(){
        gui.doSendFirstPlayer(player2.getText());
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

}
