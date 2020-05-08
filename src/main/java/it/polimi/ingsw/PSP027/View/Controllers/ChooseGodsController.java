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
    private String godSelected;
    private final List<String> godsToSend = new ArrayList<>();
    private int numberOfPlayers;
    boolean enableButton = false;
    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label ChooseGodsTitle;
    @FXML
    public GridPane GodsToChooseGrid;
    @FXML
    public Pane ChooseGodsPane;
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
    @FXML
    public ImageView ConfirmButton;
    Image ConfirmButtonReleased = new Image("images/Buttons/btn_green.png");
    Image ConfirmButtonPressed = new Image("images/Buttons/btn_green_pressed.png");
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
        numberOfPlayers = requiredGods;
        ChooseGodsTitle.setText("CHOOSE " + requiredGods + " GODS TO PLAY THE MATCH WITH");
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmButtonPressed() {
        ConfirmButton.setImage(ConfirmButtonPressed);

    }

    public void confirmButtonReleased() {
        ConfirmButton.setImage(ConfirmButtonReleased);
    }

    /**
     * Method that update the desciption of the god selected in real time
     * @param e click of the mouse on the selected god
     */
    public void displayGodDescription(MouseEvent e){
        ImageView selectedGod = (ImageView) e.getTarget();
        if(selectedGod.getId()!=null){
            switch (selectedGod.getId()) {
                case "Apollo":
                    GodDescription.setImage(ApolloDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Artemis":
                    GodDescription.setImage(ArtemisDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Ares":
                    GodDescription.setImage(AresDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Athena":
                    GodDescription.setImage(AthenaDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Atlas":
                    GodDescription.setImage(AtlasDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Demeter":
                    GodDescription.setImage(DemeterDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Hephaestus":
                    GodDescription.setImage(HephaestusDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Hestia":
                    GodDescription.setImage(HestiaDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Medusa":
                    GodDescription.setImage(MedusaDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Minotaur":
                    GodDescription.setImage(MinotaurDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Pan":
                    GodDescription.setImage(PanDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Poseidon":
                    GodDescription.setImage(PoseidonDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Prometheus":
                    GodDescription.setImage(PrometheusDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Zeus":
                    GodDescription.setImage(ZeusDescription);
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
            }
        }

    }

    /**
     * Method that put the selected gods in a List
     */
    public void setGodToList(){
        if(godsToSend.size()<numberOfPlayers){
            godsToSend.add(godSelected);
        }else if(godsToSend.size()==numberOfPlayers){
            ConfirmButton.setVisible(true);
            List<String> tempList = new ArrayList<>();
            for(int i=0; i<godsToSend.size()-1; i++){
                tempList.add(godsToSend.get(i+1));
            }
            tempList.add(godSelected);
            godsToSend.clear();
            godsToSend.addAll(tempList);
        }
        System.out.println(godsToSend.get(0));
        if(godsToSend.size()==2){
            System.out.println(godsToSend.get(1));
        }else if(godsToSend.size()==3){
            System.out.println(godsToSend.get(1));
            System.out.println(godsToSend.get(2));
        }
    }

}