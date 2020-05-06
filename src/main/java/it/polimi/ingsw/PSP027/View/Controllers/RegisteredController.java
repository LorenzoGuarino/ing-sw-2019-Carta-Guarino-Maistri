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
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label welcomeMessage;
    @FXML
    public Label NumberOfPlayersMessage;
    @FXML
    public Label SearchingForMatchMessage;
    @FXML
    public ImageView PlayButton;
    @FXML
    public ImageView DeregisterButton;
    @FXML
    public ImageView Button1Player;
    @FXML
    public ImageView Button2Player;
    Image PlayButtonPressed = new Image("images/Buttons/button-play-down.png");
    Image DeregisterButtonPressed = new Image("images/Buttons/btn_goBack_pressed.png");
    Image Button1PlayerPressed = new Image("images/Buttons/btn_1_pressed.png");
    Image Button2PlayerPressed = new Image("images/Buttons/btn_2_pressed.png");
    Image PlayButtonReleased = new Image("images/Buttons/button-play-normal.png");
    Image DeregisterButtonReleased = new Image("images/Buttons/btn_goBack.png");
    Image Button1PlayerReleased = new Image("images/Buttons/btn_1.png");
    Image Button2PlayerReleased = new Image("images/Buttons/btn_2.png");
    @FXML
    public Pane registeredPane;
    @FXML
    public GridPane playPane;
    @FXML
    public GridPane numberOfPlayersPane;
    @FXML
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
        PlayButton.setImage(PlayButtonPressed);
    }

    public void playButtonReleased() {
        PlayButton.setImage(PlayButtonReleased);
        numberOfPlayersPane.setVisible(true);
        playPane.setVisible(false);
    }

    public void deregisterButtonPressed() {
        DeregisterButton.setImage(DeregisterButtonPressed);
        gui.doDeregister();
    }

    public void deregisterButtonReleased() {
        DeregisterButton.setImage(DeregisterButtonReleased);
    }

    public void number1Pressed() {
        Button1Player.setImage(Button1PlayerPressed);
        int onePlayer = 2;
        gui.doSearchMatch(onePlayer);
    }

    public void number1Released() {
        Button1Player.setImage(Button1PlayerReleased);
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }

    public void number2Pressed() {
        Button2Player.setImage(Button2PlayerPressed);
        int twoPlayers = 3;
        gui.doSearchMatch(twoPlayers);
    }

    public void number2Released() {
        Button2Player.setImage(Button2PlayerReleased);
        numberOfPlayersPane.setVisible(false);
        searchingMatchPane.setVisible(true);
    }
}