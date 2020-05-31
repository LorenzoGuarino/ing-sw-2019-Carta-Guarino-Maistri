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
    private String godSelected;
    private final List<String> godsToSend = new ArrayList<>();
    private int numberOfPlayers;
    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView ChooseGodsTitle;
    public GridPane GodsToChooseGrid;

    public ImageView Apollo;
    public ImageView Artemis;
    public ImageView Athena;
    public ImageView Atlas;
    public ImageView Demeter;
    public ImageView Hephaestus;
    public ImageView Minotaur;
    public ImageView Pan;
    public ImageView Prometheus;
    public ImageView Ares;
    public ImageView Hestia;
    public ImageView Medusa;
    public ImageView Poseidon;
    public ImageView Zeus;

    public ImageView GodSelectedDisplayed;
    public ImageView GodDescription;
    public ImageView ConfirmButton;

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
        switch (numberOfPlayers) {
            case 2:
                ChooseGodsTitle.setImage(new Image("images/Choose2Gods.png"));
                break;

            case 3:
                ChooseGodsTitle.setImage(new Image("images/Choose3Gods.png"));
                break;

        }
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        gui.doSendGods(godsToSend);
    }

    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
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
                    GodDescription.setImage(new Image("images/Gods/ApolloDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Apollo_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Artemis":
                    GodDescription.setImage(new Image("images/Gods/ArtemisDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Artemis_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Ares":
                    GodDescription.setImage(new Image("images/Gods/AresDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Ares_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Athena":
                    GodDescription.setImage(new Image("images/Gods/AthenaDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Athena_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Atlas":
                    GodDescription.setImage(new Image("images/Gods/AtlasDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Atlas_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Demeter":
                    GodDescription.setImage(new Image("images/Gods/DemeterDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Demeter_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Hephaestus":
                    GodDescription.setImage(new Image("images/Gods/HephaestusDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Hephaestus_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Hestia":
                    GodDescription.setImage(new Image("images/Gods/HestiaDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Hestia_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Medusa":
                    GodDescription.setImage(new Image("images/Gods/MedusaDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Medusa_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Minotaur":
                    GodDescription.setImage(new Image("images/Gods/MinotaurDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Minotaur_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Pan":
                    GodDescription.setImage(new Image("images/Gods/PanDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Pan_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Poseidon":
                    GodDescription.setImage(new Image("images/Gods/PoseidonDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Poseidon_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Prometheus":
                    GodDescription.setImage(new Image("images/Gods/PrometheusDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Prometheus_big.png"));
                    godSelected = selectedGod.getId();
                    setGodToList();
                    break;
                case "Zeus":
                    GodDescription.setImage(new Image("images/Gods/ZeusDescription.png"));
                    GodSelectedDisplayed.setImage(new Image("images/Gods/Zeus_big.png"));
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
                        Apollo.setImage(new Image("images/Gods/Apollo_small_selected.png"));
                        break;
                    case "Artemis":
                        Artemis.setImage(new Image("images/Gods/Artemis_small_selected.png"));
                        break;
                    case "Ares":
                        Ares.setImage(new Image("images/Gods/Ares_small_selected.png"));
                        break;
                    case "Athena":
                        Athena.setImage(new Image("images/Gods/Athena_small_selected.png"));
                        break;
                    case "Atlas":
                        Atlas.setImage(new Image("images/Gods/Atlas_small_selected.png"));
                        break;
                    case "Demeter":
                        Demeter.setImage(new Image("images/Gods/Demeter_small_selected.png"));
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(new Image("images/Gods/Hephaestus_small_selected.png"));
                        break;
                    case "Hestia":
                        Hestia.setImage(new Image("images/Gods/Hestia_small_selected.png"));
                        break;
                    case "Medusa":
                        Medusa.setImage(new Image("images/Gods/Medusa_small_selected.png"));
                        break;
                    case "Minotaur":
                        Minotaur.setImage(new Image("images/Gods/Minotaur_small_selected.png"));
                        break;
                    case "Pan":
                        Pan.setImage(new Image("images/Gods/Pan_small_selected.png"));
                        break;
                    case "Poseidon":
                        Poseidon.setImage(new Image("images/Gods/Poseidon_small_selected.png"));
                        break;
                    case "Prometheus":
                        Prometheus.setImage(new Image("images/Gods/Prometheus_small_selected.png"));
                        break;
                    case "Zeus":
                        Zeus.setImage(new Image("images/Gods/Zeus_small_selected.png"));
                        break;
                }
            }
            if(godsToSend.size() == numberOfPlayers)
                ConfirmButton.setVisible(true);
        } else if (godsToSend.size() == numberOfPlayers) {
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
                        Apollo.setImage(new Image("images/Gods/Apollo_small_selected.png"));
                        break;
                    case "Artemis":
                        Artemis.setImage(new Image("images/Gods/Artemis_small_selected.png"));
                        break;
                    case "Ares":
                        Ares.setImage(new Image("images/Gods/Ares_small_selected.png"));
                        break;
                    case "Athena":
                        Athena.setImage(new Image("images/Gods/Athena_small_selected.png"));
                        break;
                    case "Atlas":
                        Atlas.setImage(new Image("images/Gods/Atlas_small_selected.png"));
                        break;
                    case "Demeter":
                        Demeter.setImage(new Image("images/Gods/Demeter_small_selected.png"));
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(new Image("images/Gods/Hephaestus_small_selected.png"));
                        break;
                    case "Hestia":
                        Hestia.setImage(new Image("images/Gods/Hestia_small_selected.png"));
                        break;
                    case "Medusa":
                        Medusa.setImage(new Image("images/Gods/Medusa_small_selected.png"));
                        break;
                    case "Minotaur":
                        Minotaur.setImage(new Image("images/Gods/Minotaur_small_selected.png"));
                        break;
                    case "Pan":
                        Pan.setImage(new Image("images/Gods/Pan_small_selected.png"));
                        break;
                    case "Poseidon":
                        Poseidon.setImage(new Image("images/Gods/Poseidon_small_selected.png"));
                        break;
                    case "Prometheus":
                        Prometheus.setImage(new Image("images/Gods/Prometheus_small_selected.png"));
                        break;
                    case "Zeus":
                        Zeus.setImage(new Image("images/Gods/Zeus_small_selected.png"));
                        break;
                }

                switch (godsToSend.get(0)) {
                    case "Apollo":
                        Apollo.setImage(new Image("images/Gods/Apollo_small.png"));
                        break;
                    case "Artemis":
                        Artemis.setImage(new Image("images/Gods/Artemis_small.png"));
                        break;
                    case "Ares":
                        Ares.setImage(new Image("images/Gods/Ares_small.png"));
                        break;
                    case "Athena":
                        Athena.setImage(new Image("images/Gods/Athena_small.png"));
                        break;
                    case "Atlas":
                        Atlas.setImage(new Image("images/Gods/Atlas_small.png"));
                        break;
                    case "Demeter":
                        Demeter.setImage(new Image("images/Gods/Demeter_small.png"));
                        break;
                    case "Hephaestus":
                        Hephaestus.setImage(new Image("images/Gods/Hephaestus_small.png"));
                        break;
                    case "Hestia":
                        Hestia.setImage(new Image("images/Gods/Hestia_small.png"));
                        break;
                    case "Medusa":
                        Medusa.setImage(new Image("images/Gods/Medusa_small.png"));
                        break;
                    case "Minotaur":
                        Minotaur.setImage(new Image("images/Gods/Minotaur_small.png"));
                        break;
                    case "Pan":
                        Pan.setImage(new Image("images/Gods/Pan_small.png"));
                        break;
                    case "Poseidon":
                        Poseidon.setImage(new Image("images/Gods/Poseidon_small.png"));
                        break;
                    case "Prometheus":
                        Prometheus.setImage(new Image("images/Gods/Prometheus_small.png"));
                        break;
                    case "Zeus":
                        Zeus.setImage(new Image("images/Gods/Zeus_small.png"));
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