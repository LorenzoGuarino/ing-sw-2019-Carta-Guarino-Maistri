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

public class WaitingController {

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
    public Label WaitingMessage;
    @FXML
    public Pane WaitingPane;


    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public WaitingController() {
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

    public void setWaitingMessage (String waitingMessage) {
        WaitingMessage.setText(waitingMessage);
    }

}