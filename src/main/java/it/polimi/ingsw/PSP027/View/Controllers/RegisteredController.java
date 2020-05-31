package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.w3c.dom.Node;

import java.util.*;

public class RegisteredController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label welcomeMessage;
    public Label NumberOfPlayersMessage;
    public Label SearchingForMatchMessage;
    public ImageView PlayButton;
    public ImageView DeregisterButton;
    public ImageView Button1Player;
    public ImageView Button2Player;

    public Pane registeredPane;
    public GridPane playPane;
    public GridPane numberOfPlayersPane;
    public GridPane searchingMatchPane;


    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public RegisteredController() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize() {
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }

    public void setNickname (String nickname) {
        welcomeMessage.setText("Welcome " + nickname + "!");
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void playButtonPressed() {
        PlayButton.setImage(new Image("images/Buttons/button-play-down.png"));
    }

    public void playButtonReleased() {
        PlayButton.setImage(new Image("images/Buttons/button-play-normal.png"));
        numberOfPlayersPane.setVisible(true);
        playPane.setVisible(false);
    }

    public void deregisterButtonPressed() {
        DeregisterButton.setImage(new Image("images/Buttons/btn_goBack_pressed.png"));
        gui.doDeregister();
    }

    public void deregisterButtonReleased() {
        DeregisterButton.setImage(new Image("images/Buttons/btn_goBack.png"));
    }

    public void number1Pressed() {
        Button1Player.setImage(new Image("images/Buttons/btn_1_pressed.png"));
        int onePlayer = 2;
        gui.doSearchMatch(onePlayer);
    }

    public void number1Released() {
        Button1Player.setImage(new Image("images/Buttons/btn_1.png"));
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }

    public void number2Pressed() {
        Button2Player.setImage(new Image("images/Buttons/btn_2_pressed.png"));
        int twoPlayers = 3;
        gui.doSearchMatch(twoPlayers);
    }

    public void number2Released() {
        Button2Player.setImage(new Image("images/Buttons/btn_2.png"));
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }
}