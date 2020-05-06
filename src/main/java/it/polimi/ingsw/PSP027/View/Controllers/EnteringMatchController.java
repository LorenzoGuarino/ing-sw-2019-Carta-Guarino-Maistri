package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Node;

import java.util.*;

public class EnteringMatchController {

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
    public Label player1;
    @FXML
    public Label player2;
    @FXML
    public Label player3;
    @FXML
    public Pane enteringMatchPane;
    @FXML
    public ImageView DeregisterButton;
    Image DeregisterButtonPressed = new Image("images/Buttons/btn_goBack_pressed.png");
    Image DeregisterButtonReleased = new Image("images/Buttons/btn_goBack.png");


    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public EnteringMatchController() {
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
        if (players.size() == 2)
            player2.setText(players.get(1));
        else if (players.size() == 3) {
            player2.setText(players.get(1));
            player3.setText(players.get(2));
        }
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void deregisterButtonPressed() {
        DeregisterButton.setImage(DeregisterButtonPressed);
        gui.doDeregister();
    }

    public void deregisterButtonReleased() {
        DeregisterButton.setImage(DeregisterButtonReleased);
    }
}