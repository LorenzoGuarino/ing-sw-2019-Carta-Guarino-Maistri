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
    /**
     * Method that set the NickName of the Players
     * @param nickname player that is playing the game
     */
    public void setNickname (String nickname) {
        welcomeMessage.setText("Welcome " + nickname + "!");
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */
    /**
     * Method that update the image of the button pressed
     */
    public void playButtonPressed() {
        PlayButton.setImage(new Image("images/Buttons/button-play-down.png"));
    }
    /**
     * Method that update the image of the button pressed and set to visible the playPane
     */
    public void playButtonReleased() {
        PlayButton.setImage(new Image("images/Buttons/button-play-normal.png"));
        numberOfPlayersPane.setVisible(true);
        playPane.setVisible(false);
    }
    /**
     * Method called when the Deregister Button is pressed, update the image of the button and
     * it send the Deregister Command
     */
    public void deregisterButtonPressed() {
        DeregisterButton.setImage(new Image("images/Buttons/btn_goBack_pressed.png"));
        gui.doDeregister();
    }
    /**
     * Method that update the image of the button pressed
     */
    public void deregisterButtonReleased() {
        DeregisterButton.setImage(new Image("images/Buttons/btn_goBack.png"));
    }

    /**
     * Method that update the image of the button pressed and send the next command to GUI
     */
    public void number1Pressed() {
        Button1Player.setImage(new Image("images/Buttons/btn_1_pressed.png"));
        int onePlayer = 2;
        gui.doSearchMatch(onePlayer);
    }
    /**
     * Method that update the image of the button pressed and set to visible the searching Pane
     */
    public void number1Released() {
        Button1Player.setImage(new Image("images/Buttons/btn_1.png"));
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }
    /**
     * Method that update the image of the button pressed and send the next command to GUI
     */
    public void number2Pressed() {
        Button2Player.setImage(new Image("images/Buttons/btn_2_pressed.png"));
        int twoPlayers = 3;
        gui.doSearchMatch(twoPlayers);
    }
    /**
     * Method that update the image of the button pressed and set to visible the searching Pane
     */
    public void number2Released() {
        Button2Player.setImage(new Image("images/Buttons/btn_2.png"));
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }
}