package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.util.*;

/**
 * @author Lorenzo Guarino
 */

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

    /**
     * Method that set the correct title on the Pane
     * @param requiredGods number of gods chosen that is equals to the number of players
     */
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
    /**
     * Method that update the button image and than proceed to send the next command to GUI
     */
    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        gui.doSendGods(godsToSend);
    }
    /**
     * Method that update the image of the button pressed
     */
    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }

    /**
     * Method that updates the description of the god selected in real time
     * @param e click of the mouse on the selected god
     */
    public void displayGodDescription(MouseEvent e) {
        ImageView selectedGod = (ImageView) e.getTarget();
        if (selectedGod.getId() != null) {
            godSelected = selectedGod.getId();
            GodDescription.setImage(new Image("images/Gods/"+godSelected+"Description.png"));
            GodSelectedDisplayed.setImage(new Image("images/Gods/"+godSelected+"_big.png"));
            setGodToList();

        }
    }

    /**
     * Method that put the selected gods in a List, that will be send to the GUI
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
        }
    }
}