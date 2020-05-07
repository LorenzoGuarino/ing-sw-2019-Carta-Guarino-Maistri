package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseYourGodController {

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
    public ImageView Apollo;
    @FXML
    public ImageView Ares;
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
    public ImageView Hestia;
    @FXML
    public ImageView Medusa;
    @FXML
    public ImageView Minotaur;
    @FXML
    public ImageView Pan;
    @FXML
    public ImageView Poseidon;
    @FXML
    public ImageView Prometheus;
    @FXML
    public ImageView Zeus;
    @FXML
    public GridPane GodsToChoose;
    @FXML
    public ImageView ConfirmSelectionButton;

    Image ConfirmSelectionButtonPressed = new Image("images/Buttons/bnt_green_pressed.png");
    Image ConfirmSelectionButtonReleased = new Image("images/Buttons/btn_green.png");

    @FXML
    public ImageView GodSelectedZoom;

    @FXML
    public ImageView GodSelectedDescription;

    /* ****************************************************************************************************************** */


    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize()
    {

    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmSelectionButtonPressed() {
        ConfirmSelectionButton.setImage(ConfirmSelectionButtonPressed);
        //TODO controllare il dio selezionato, e inviarlo al server
    }

    public void confirmSelectionButtonReleased() {
        ConfirmSelectionButton.setImage(ConfirmSelectionButtonReleased);
    }

    public void godHasBeenClicked(){
        //TODO trovare l'immagine che è stata cliccata e mostrarla in GodSelectedZoom e mostrare la descrizione del rispettivo dio in GodSelectedDescription


    }





}
