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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.scene.Node;

import java.util.*;

public class ChooseYourGodCase2Controller {
    // Reference to the main gui application
    private GUI gui;

    public Client client = null;
    private List<String> godsToSave = new ArrayList<>();
    private int godselected=0;

    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */
    @FXML
    public ImageView GodLeft;
    public ImageView GodRight;
    public ImageView GodDescription;
    public ImageView ConfirmButton;

    Image GodLeftDescription;
    Image GodRightDescription;
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseYourGodCase2Controller() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize() {
        ConfirmButton.setVisible(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui(GUI Gui) {
        this.gui = Gui;
    }

    public void setChooseGodTitle(List<String> chosenGods) {
        godsToSave = chosenGods;
        setImagesOfGods(chosenGods);
    }
    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        if (godselected == 0) {
            gui.doSendSelectedGod(godsToSave.get(0));
        } else if(godselected == 1) {
            gui.doSendSelectedGod(godsToSave.get(1));
        }
    }

    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }

    public void setImagesOfGods(List<String> chosenGods) {
        switch (chosenGods.get(0)) {
            case "Apollo":
                GodLeft.setImage(new Image("images/Gods/Apollo_big.png"));
                GodLeftDescription = new Image("images/Gods/ApolloDescription.png");
                break;
            case "Artemis":
                GodLeft.setImage(new Image("images/Gods/Artemis_big.png"));
                GodLeftDescription = new Image("images/Gods/ArtemisDescription.png");
                break;
            case "Ares":
                GodLeft.setImage(new Image("images/Gods/Ares_big.png"));
                GodLeftDescription = new Image("images/Gods/AresDescription.png");
                break;
            case "Athena":
                GodLeft.setImage(new Image("images/Gods/Athena_big.png"));
                GodLeftDescription = new Image("images/Gods/AthenaDescription.png");
                break;
            case "Atlas":
                GodLeft.setImage(new Image("images/Gods/Atlas_big.png"));
                GodLeftDescription = new Image("images/Gods/AtlasDescription.png");
                break;
            case "Demeter":
                GodLeft.setImage(new Image("images/Gods/Demeter_big.png"));
                GodLeftDescription = new Image("images/Gods/DemeterDescription.png");
                break;
            case "Hephaestus":
                GodLeft.setImage(new Image("images/Gods/Hephaestus_big.png"));
                GodLeftDescription = new Image("images/Gods/HephaestusDescription.png");
                break;
            case "Hestia":
                GodLeft.setImage(new Image("images/Gods/Hestia_big.png"));
                GodLeftDescription =new Image("images/Gods/HestiaDescription.png");
                break;
            case "Medusa":
                GodLeft.setImage(new Image("images/Gods/Medusa_big.png"));
                GodLeftDescription = new Image("images/Gods/MedusaDescription.png");
                break;
            case "Minotaur":
                GodLeft.setImage(new Image("images/Gods/Minotaur_big.png"));
                GodLeftDescription = new Image("images/Gods/MinotaurDescription.png");
                break;
            case "Pan":
                GodLeft.setImage(new Image("images/Gods/Pan_big.png"));
                GodLeftDescription = new Image("images/Gods/PanDescription.png");
                break;
            case "Poseidon":
                GodLeft.setImage(new Image("images/Gods/Poseidon_big.png"));
                GodLeftDescription = new Image("images/Gods/PoseidonDescription.png");
                break;
            case "Prometheus":
                GodLeft.setImage(new Image("images/Gods/Prometheus_big.png"));
                GodLeftDescription = new Image("images/Gods/PrometheusDescription.png");
                break;
            case "Zeus":
                GodLeft.setImage(new Image("images/Gods/Zeus_big.png"));
                GodLeftDescription = new Image("images/Gods/ZeusDescription.png");
                break;
        }
        switch (chosenGods.get(1)) {
            case "Apollo":
                GodRight.setImage(new Image("images/Gods/Apollo_big.png"));
                GodRightDescription = new Image("images/Gods/ApolloDescription.png");
                break;
            case "Artemis":
                GodRight.setImage(new Image("images/Gods/Artemis_big.png"));
                GodRightDescription = new Image("images/Gods/ArtemisDescription.png");
                break;
            case "Ares":
                GodRight.setImage(new Image("images/Gods/Ares_big.png"));
                GodRightDescription = new Image("images/Gods/AresDescription.png");
                break;
            case "Athena":
                GodRight.setImage(new Image("images/Gods/Athena_big.png"));
                GodRightDescription = new Image("images/Gods/AthenaDescription.png");
                break;
            case "Atlas":
                GodRight.setImage(new Image("images/Gods/Atlas_big.png"));
                GodRightDescription = new Image("images/Gods/AtlasDescription.png");
                break;
            case "Demeter":
                GodRight.setImage(new Image("images/Gods/Demeter_big.png"));
                GodRightDescription = new Image("images/Gods/DemeterDescription.png");
                break;
            case "Hephaestus":
                GodRight.setImage(new Image("images/Gods/Hephaestus_big.png"));
                GodRightDescription = new Image("images/Gods/HephaestusDescription.png");
                break;
            case "Hestia":
                GodRight.setImage(new Image("images/Gods/Hestia_big.png"));
                GodRightDescription = new Image("images/Gods/HestiaDescription.png");
                break;
            case "Medusa":
                GodRight.setImage(new Image("images/Gods/Medusa_big.png"));
                GodRightDescription = new Image("images/Gods/MedusaDescription.png");
                break;
            case "Minotaur":
                GodRight.setImage(new Image("images/Gods/Minotaur_big.png"));
                GodRightDescription = new Image("images/Gods/MinotaurDescription.png");
                break;
            case "Pan":
                GodRight.setImage(new Image("images/Gods/Pan_big.png"));
                GodRightDescription = new Image("images/Gods/PanDescription.png");
                break;
            case "Poseidon":
                GodRight.setImage(new Image("images/Gods/Poseidon_big.png"));
                GodRightDescription = new Image("images/Gods/PoseidonDescription.png");
                break;
            case "Prometheus":
                GodRight.setImage(new Image("images/Gods/Prometheus_big.png"));
                GodRightDescription = new Image("images/Gods/PrometheusDescription.png");
                break;
            case "Zeus":
                GodRight.setImage(new Image("images/Gods/Zeus_big.png"));
                GodRightDescription = new Image("images/Gods/ZeusDescription.png");
                break;
        }
    }

    public void selectGodLeft(){
        godselected = 0;
        GodDescription.setImage(GodLeftDescription);
        ConfirmButton.setVisible(true);
    }

    public void selectGodRight(){
        godselected = 1;
        GodDescription.setImage(GodRightDescription);
        ConfirmButton.setVisible(true);
    }

}
