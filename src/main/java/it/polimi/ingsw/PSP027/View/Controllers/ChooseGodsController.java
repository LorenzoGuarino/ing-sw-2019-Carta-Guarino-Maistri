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

    Image ApolloSelected = new Image("images/Gods/Apollo_small_selected.png");
    Image AresSelected = new Image("images/Gods/Ares_small_selected.png");
    Image ArtemisSelected = new Image("images/Gods/Artemis_small_selected.png");
    Image AthenaSelected = new Image("images/Gods/Athena_small_selected.png");
    Image AtlasSelected = new Image("images/Gods/Atlas_small_selected.png");
    Image DemeterSelected = new Image("images/Gods/Demeter_small_selected.png");
    Image HephaestusSelected = new Image("images/Gods/Hephaestus_small_selected.png");
    Image HestiaSelected = new Image("images/Gods/Hestia_small_selected.png");
    Image MedusaSelected = new Image("images/Gods/Medusa_small_selected.png");
    Image MinotaurSelected = new Image("images/Gods/Minotaur_small_selected.png");
    Image PanSelected = new Image("images/Gods/Pan_small_selected.png");
    Image PoseidonSelected = new Image("images/Gods/Poseidon_small_selected.png");
    Image PrometheusSelected = new Image("images/Gods/Prometheus_small_selected.png");
    Image ZeusSelected = new Image("images/Gods/Zeus_small_selected.png");

    Image ApolloDeselected = new Image("images/Gods/Apollo_small.png");
    Image AresDeselected = new Image("images/Gods/Ares_small.png");
    Image ArtemisDeselected = new Image("images/Gods/Artemis_small.png");
    Image AthenaDeselected = new Image("images/Gods/Athena_small.png");
    Image AtlasDeselected = new Image("images/Gods/Atlas_small.png");
    Image DemeterDeselected = new Image("images/Gods/Demeter_small.png");
    Image HephaestusDeselected = new Image("images/Gods/Hephaestus_small.png");
    Image HestiaDeselected = new Image("images/Gods/Hestia_small.png");
    Image MedusaDeselected = new Image("images/Gods/Medusa_small.png");
    Image MinotaurDeselected = new Image("images/Gods/Minotaur_small.png");
    Image PanDeselected = new Image("images/Gods/Pan_small.png");
    Image PoseidonDeselected = new Image("images/Gods/Poseidon_small.png");
    Image PrometheusDeselected = new Image("images/Gods/Prometheus_small.png");
    Image ZeusDeselected = new Image("images/Gods/Zeus_small.png");

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

    public void setGui(GUI Gui) {
        this.gui = Gui;
    }

    public void setChooseGodsTitle(int requiredGods) {
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
     * Method that updates the description of the god selected in real time
     *
     * @param e click of the mouse on the selected god
     */
    public void displayGodDescription(MouseEvent e) {
        ImageView selectedGod = (ImageView) e.getTarget();
        if (selectedGod.getId() != null) {
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
    public void setGodToList() {
        boolean bAlreadySelected;
        if (godsToSend.size() < numberOfPlayers) {
            bAlreadySelected = false;
            for (int i = 0; i < godsToSend.size(); i++) {
                if (godSelected.equals(godsToSend.get(i))) {
                    bAlreadySelected = true;
                    break;
                }
            }
            if (!bAlreadySelected) {
                godsToSend.add(godSelected);
                switch (godSelected) {
                    case "Apollo":
                        Apollo.setImage(ApolloSelected);
                        break;
                    case "Artemis":
                        Artemis.setImage(ArtemisSelected);
                        break;
                    case "Ares":
                        Ares.setImage(AresSelected);
                        break;
                    case "Athena":
                        Athena.setImage(AthenaSelected);
                        break;
                    case "Atlas":
                        Atlas.setImage(AtlasSelected);
                        break;
                    case "Demeter":
                        Demeter.setImage(DemeterSelected);
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(HephaestusSelected);
                        break;
                    case "Hestia":
                        Hestia.setImage(HestiaSelected);
                        break;
                    case "Medusa":
                        Medusa.setImage(MedusaSelected);
                        break;
                    case "Minotaur":
                        Minotaur.setImage(MinotaurSelected);
                        break;
                    case "Pan":
                        Pan.setImage(PanSelected);
                        break;
                    case "Poseidon":
                        Poseidon.setImage(PoseidonSelected);
                        break;
                    case "Prometheus":
                        Prometheus.setImage(PrometheusSelected);
                        break;
                    case "Zeus":
                        Zeus.setImage(ZeusSelected);
                        break;
                }
            }
        } else if (godsToSend.size() == numberOfPlayers) {
            ConfirmButton.setVisible(true);

            bAlreadySelected = false;
            for (int i = 0; i < godsToSend.size(); i++) {
                if (godSelected.equals(godsToSend.get(i))) {
                    bAlreadySelected = true;
                    break;
                }
            }
            if (!bAlreadySelected) {
                switch (godSelected) {
                    case "Apollo":
                        Apollo.setImage(ApolloSelected);
                        break;
                    case "Artemis":
                        Artemis.setImage(ArtemisSelected);
                        break;
                    case "Ares":
                        Ares.setImage(AresSelected);
                        break;
                    case "Athena":
                        Athena.setImage(AthenaSelected);
                        break;
                    case "Atlas":
                        Atlas.setImage(AtlasSelected);
                        break;
                    case "Demeter":
                        Demeter.setImage(DemeterSelected);
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(HephaestusSelected);
                        break;
                    case "Hestia":
                        Hestia.setImage(HestiaSelected);
                        break;
                    case "Medusa":
                        Medusa.setImage(MedusaSelected);
                        break;
                    case "Minotaur":
                        Minotaur.setImage(MinotaurSelected);
                        break;
                    case "Pan":
                        Pan.setImage(PanSelected);
                        break;
                    case "Poseidon":
                        Poseidon.setImage(PoseidonSelected);
                        break;
                    case "Prometheus":
                        Prometheus.setImage(PrometheusSelected);
                        break;
                    case "Zeus":
                        Zeus.setImage(ZeusSelected);
                        break;
                }

                switch (godsToSend.get(0)) {
                    case "Apollo":
                        Apollo.setImage(ApolloDeselected);
                        break;
                    case "Artemis":
                        Artemis.setImage(ArtemisDeselected);
                        break;
                    case "Ares":
                        Ares.setImage(AresDeselected);
                        break;
                    case "Athena":
                        Athena.setImage(AthenaDeselected);
                        break;
                    case "Atlas":
                        Atlas.setImage(AtlasDeselected);
                        break;
                    case "Demeter":
                        Demeter.setImage(DemeterDeselected);
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(HephaestusDeselected);
                        break;
                    case "Hestia":
                        Hestia.setImage(HestiaDeselected);
                        break;
                    case "Medusa":
                        Medusa.setImage(MedusaDeselected);
                        break;
                    case "Minotaur":
                        Minotaur.setImage(MinotaurDeselected);
                        break;
                    case "Pan":
                        Pan.setImage(PanDeselected);
                        break;
                    case "Poseidon":
                        Poseidon.setImage(PoseidonDeselected);
                        break;
                    case "Prometheus":
                        Prometheus.setImage(PrometheusDeselected);
                        break;
                    case "Zeus":
                        Zeus.setImage(ZeusDeselected);
                        break;
                }
                godsToSend.remove(0);
                godsToSend.add(godSelected);
            }

            System.out.println(godsToSend.get(0));
            if (godsToSend.size() == 2) {
                System.out.println(godsToSend.get(1));
            } else if (godsToSend.size() == 3) {
                System.out.println(godsToSend.get(1));
                System.out.println(godsToSend.get(2));
            }
        }
    }
}