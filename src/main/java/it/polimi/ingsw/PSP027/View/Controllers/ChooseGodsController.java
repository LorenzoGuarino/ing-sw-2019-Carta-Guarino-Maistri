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

public class ChooseGodsController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label ChooseGodsTitle;
    @FXML
    public Pane chooseGodsPane;
    @FXML
    public ImageView Apollo;
    @FXML
    public ImageView Artemis;
    @FXML
    public ImageView Athena;
    @FXML
    public ImageView Atlas;
    @FXML
    public ImageView Demeter;
    @FXML
    public ImageView Hephaestus;
    @FXML
    public ImageView Minotaur;
    @FXML
    public ImageView Pan;
    @FXML
    public ImageView Prometheus;
    @FXML
    public ImageView Ares;
    @FXML
    public ImageView Hestia;
    @FXML
    public ImageView Medusa;
    @FXML
    public ImageView Poseidon;
    @FXML
    public ImageView Zeus;

    @FXML
    public ImageView GodDescription;


    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseGodsController() {
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

    public void setChooseGodsTitle (int requiredGods) {
        ChooseGodsTitle.setText("CHOOSE " + requiredGods + " GODS TO PLAY THE MATCH WITH");
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

}