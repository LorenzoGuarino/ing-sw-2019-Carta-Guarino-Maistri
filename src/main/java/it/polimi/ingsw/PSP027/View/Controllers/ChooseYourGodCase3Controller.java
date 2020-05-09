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
public class ChooseYourGodCase3Controller {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;
    private String godSelected;
    private List<String> godsToSave = new ArrayList<>();
    private int godselected=0;

    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */
    @FXML
    public ImageView GodLeft;
    @FXML
    public ImageView GodCenter;
    @FXML
    public ImageView GodRight;
    @FXML
    public ImageView GodDescription;
    @FXML
    public ImageView ConfirmButton;

    Image ConfirmButtonReleased = new Image("images/Buttons/btn_Confirm.png");
    Image ConfirmButtonPressed = new Image("images/Buttons/btn_Confirm_pressed.png");
    Image ApolloDescription = new Image("images/Gods/ApolloDescription.png");
    Image AresDescription = new Image("images/Gods/AresDescription.png");
    Image ArtemisDescription = new Image("images/Gods/ArtemisDescription.png");
    Image AthenaDescription = new Image("images/Gods/AthenaDescription.png");
    Image AtlasDescription = new Image("images/Gods/AtlasDescription.png");
    Image DemeterDescription = new Image("images/Gods/DemeterDescription.png");
    Image HephaestusDescription = new Image("images/Gods/HephaestusDescription.png");
    Image HestiaDescription = new Image("images/Gods/HestiaDescription.png");
    Image MedusaDescription = new Image("images/Gods/MedusaDescription.png");
    Image MinotaurDescription = new Image("images/Gods/MinotaurDescription.png");
    Image PanDescription = new Image("images/Gods/PanDescription.png");
    Image PoseidonDescription = new Image("images/Gods/PoseidonDescription.png");
    Image PrometheusDescription = new Image("images/Gods/PrometheusDescription.png");
    Image ZeusDescription = new Image("images/Gods/ZeusDescription.png");
    Image GodLeftDescription;
    Image GodCenterDescription;
    Image GodRightDescription;
    Image ApolloDisplayed = new Image("images/Gods/Apollo_big.png");
    Image AresDisplayed = new Image("images/Gods/Ares_big.png");
    Image ArtemisDisplayed = new Image("images/Gods/Artemis_big.png");
    Image AthenaDisplayed = new Image("images/Gods/Athena_big.png");
    Image AtlasDisplayed = new Image("images/Gods/Atlas_big.png");
    Image DemeterDisplayed = new Image("images/Gods/Demeter_big.png");
    Image HephaestusDisplayed = new Image("images/Gods/Hephaestus_big.png");
    Image HestiaDisplayed = new Image("images/Gods/Hestia_big.png");
    Image MedusaDisplayed = new Image("images/Gods/Medusa_big.png");
    Image MinotaurDisplayed = new Image("images/Gods/Minotaur_big.png");
    Image PanDisplayed = new Image("images/Gods/Pan_big.png");
    Image PoseidonDisplayed = new Image("images/Gods/Poseidon_big.png");
    Image PrometheusDisplayed = new Image("images/Gods/Prometheus_full.png");
    Image ZeusDisplayed = new Image("images/Gods/Zeus_big.png");
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseYourGodCase3Controller() {
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

    public void setChooseGodTitle(List<String> chosenGods) {
        godsToSave = chosenGods;
        setImagesOfGods(chosenGods);
    }
    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmButtonPressed() {
        ConfirmButton.setImage(ConfirmButtonPressed);
        if(godselected==0){
            gui.doSendSelectedGod(godsToSave.get(0));
        }else if(godselected==1){
            gui.doSendSelectedGod(godsToSave.get(1));
        }else if(godselected==2){
            gui.doSendSelectedGod(godsToSave.get(2));
        }
    }

    public void confirmButtonReleased() {
        ConfirmButton.setImage(ConfirmButtonReleased);
    }

    public void setImagesOfGods(List<String> chosenGods){
        switch (chosenGods.get(0)){
            case "Apollo":
                GodLeft.setImage(ApolloDisplayed);
                GodLeftDescription = ApolloDescription;
                break;
            case "Artemis":
                GodLeft.setImage(ArtemisDisplayed);
                GodLeftDescription = ArtemisDescription;
                break;
            case "Ares":
                GodLeft.setImage(AresDisplayed);
                GodLeftDescription = AresDescription;
                break;
            case "Athena":
                GodLeft.setImage(AthenaDisplayed);
                GodLeftDescription = AthenaDescription;
                break;
            case "Atlas":
                GodLeft.setImage(AtlasDisplayed);
                GodLeftDescription = AtlasDescription;
                break;
            case "Demeter":
                GodLeft.setImage(DemeterDisplayed);
                GodLeftDescription = DemeterDescription;
                break;
            case "Hephaestus":
                GodLeft.setImage(HephaestusDisplayed);
                GodLeftDescription = HephaestusDescription;
                break;
            case "Hestia":
                GodLeft.setImage(HestiaDisplayed);
                GodLeftDescription =HestiaDescription;
                break;
            case "Medusa":
                GodLeft.setImage(MedusaDisplayed);
                GodLeftDescription = MedusaDescription;
                break;
            case "Minotaur":
                GodLeft.setImage(MinotaurDisplayed);
                GodLeftDescription = MinotaurDescription;
                break;
            case "Pan":
                GodLeft.setImage(PanDisplayed);
                GodLeftDescription = PanDescription;
                break;
            case "Poseidon":
                GodLeft.setImage(PoseidonDisplayed);
                GodLeftDescription = PoseidonDescription;
                break;
            case "Prometheus":
                GodLeft.setImage(PrometheusDisplayed);
                GodLeftDescription = PrometheusDescription;
                break;
            case "Zeus":
                GodLeft.setImage(ZeusDisplayed);
                GodLeftDescription = ZeusDescription;
                break;
        }
        switch (chosenGods.get(1)) {
            case "Apollo":
                GodCenter.setImage(ApolloDisplayed);
                GodCenterDescription = ApolloDescription;
                break;
            case "Artemis":
                GodCenter.setImage(ArtemisDisplayed);
                GodCenterDescription = ArtemisDescription;
                break;
            case "Ares":
                GodCenter.setImage(AresDisplayed);
                GodCenterDescription = AresDescription;
                break;
            case "Athena":
                GodRight.setImage(AthenaDisplayed);
                GodCenterDescription = AthenaDescription;
                break;
            case "Atlas":
                GodCenter.setImage(AtlasDisplayed);
                GodCenterDescription = AtlasDescription;
                break;
            case "Demeter":
                GodCenter.setImage(DemeterDisplayed);
                GodCenterDescription = DemeterDescription;
                break;
            case "Hephaestus":
                GodCenter.setImage(HephaestusDisplayed);
                GodCenterDescription = HephaestusDescription;
                break;
            case "Hestia":
                GodCenter.setImage(HestiaDisplayed);
                GodCenterDescription = HestiaDescription;
                break;
            case "Medusa":
                GodCenter.setImage(MedusaDisplayed);
                GodCenterDescription = MedusaDescription;
                break;
            case "Minotaur":
                GodCenter.setImage(MinotaurDisplayed);
                GodCenterDescription = MinotaurDescription;
                break;
            case "Pan":
                GodCenter.setImage(PanDisplayed);
                GodCenterDescription = PanDescription;
                break;
            case "Poseidon":
                GodCenter.setImage(PoseidonDisplayed);
                GodCenterDescription = PoseidonDescription;
                break;
            case "Prometheus":
                GodCenter.setImage(PrometheusDisplayed);
                GodCenterDescription = PrometheusDescription;
                break;
            case "Zeus":
                GodCenter.setImage(ZeusDisplayed);
                GodCenterDescription = ZeusDescription;
                break;
        }
        switch (chosenGods.get(2)) {
            case "Apollo":
                GodRight.setImage(ApolloDisplayed);
                GodRightDescription = ApolloDescription;
                break;
            case "Artemis":
                GodRight.setImage(ArtemisDisplayed);
                GodRightDescription = ArtemisDescription;
                break;
            case "Ares":
                GodRight.setImage(AresDisplayed);
                GodRightDescription = AresDescription;
                break;
            case "Athena":
                GodRight.setImage(AthenaDisplayed);
                GodRightDescription = AthenaDescription;
                break;
            case "Atlas":
                GodRight.setImage(AtlasDisplayed);
                GodRightDescription = AtlasDescription;
                break;
            case "Demeter":
                GodRight.setImage(DemeterDisplayed);
                GodRightDescription = DemeterDescription;
                break;
            case "Hephaestus":
                GodRight.setImage(HephaestusDisplayed);
                GodRightDescription = HephaestusDescription;
                break;
            case "Hestia":
                GodRight.setImage(HestiaDisplayed);
                GodRightDescription = HestiaDescription;
                break;
            case "Medusa":
                GodRight.setImage(MedusaDisplayed);
                GodRightDescription = MedusaDescription;
                break;
            case "Minotaur":
                GodRight.setImage(MinotaurDisplayed);
                GodRightDescription = MinotaurDescription;
                break;
            case "Pan":
                GodRight.setImage(PanDisplayed);
                GodRightDescription = PanDescription;
                break;
            case "Poseidon":
                GodRight.setImage(PoseidonDisplayed);
                GodRightDescription = PoseidonDescription;
                break;
            case "Prometheus":
                GodRight.setImage(PrometheusDisplayed);
                GodRightDescription = PrometheusDescription;
                break;
            case "Zeus":
                GodRight.setImage(ZeusDisplayed);
                GodRightDescription = ZeusDescription;
                break;
        }
    }

    public void selectGodLeft(){
        godselected = 0;
        GodDescription.setImage(GodLeftDescription);
    }
    public void selectGodCenter(){
        godselected = 1;
        GodDescription.setImage(GodCenterDescription);
    }
    public void selectGodRight(){
        godselected = 2;
        GodDescription.setImage(GodRightDescription);
    }

}
