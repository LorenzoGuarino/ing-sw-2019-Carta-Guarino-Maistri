
package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.w3c.dom.Node;

import java.util.*;

public class YouHaveLostController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */
    @FXML
    Label BetterLuckNextTime;
    @FXML
    ImageView LoserGod;

    Image Apollo = new Image("images/Gods/Apollo_icon.png");
    Image Ares = new Image("images/Gods/Ares_icon.png");
    Image Artemis = new Image("images/Gods/Artemis_icon.png");
    Image Athena = new Image("images/Gods/Athena_icon.png");
    Image Atlas = new Image("images/Gods/Atlas_icon.png");
    Image Demeter = new Image("images/Gods/Demeter_icon.png");
    Image Hephaestus = new Image("images/Gods/Hephaestus_icon.png");
    Image Hestia = new Image("images/Gods/Hestia_icon.png");
    Image Medusa = new Image("images/Gods/Medusa_icon.png");
    Image Minotaur = new Image("images/Gods/Minotaur_icon.png");
    Image Pan = new Image("images/Gods/Pan_icon.png");
    Image Poseidon = new Image("images/Gods/Poseidon_icon.png");
    Image Prometheus = new Image("images/Gods/Prometheus_icon.png");
    Image Zeus = new Image("images/Gods/Zeus_icon.png");

    @FXML
    public ImageView ExitGameButton;
    Image exitButtonHovered = new Image("images/Buttons/btn_exitGame_hovered.png");
    Image exitButtonReleased = new Image("images/Buttons/btn_exitGame.png");

    @FXML
    public ImageView PlayButton;
    Image PlayButtonPressed = new Image("images/Buttons/button-play-down.png");
    Image PlayButtonReleased = new Image("images/Buttons/button-play-normal.png");
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public YouHaveLostController() {
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

    public void setBetterLuckNextTime(String winner) {
        BetterLuckNextTime.setText(winner + " has won the game, better luck next time!");
    }

    public void setLoserPodium(String godLoser) {
        switch (godLoser) {
            case "Apollo":
                LoserGod.setImage(Apollo);
                break;
            case "Artemis":
                LoserGod.setImage(Artemis);
                break;
            case "Ares":
                LoserGod.setImage(Ares);
                break;
            case "Athena":
                LoserGod.setImage(Athena);
                break;
            case "Atlas":
                LoserGod.setImage(Atlas);
                break;
            case "Demeter":
                LoserGod.setImage(Demeter);
                break;
            case "Hephaestus":
                LoserGod.setImage(Hephaestus);
                break;
            case "Hestia":
                LoserGod.setImage(Hestia);
                break;
            case "Medusa":
                LoserGod.setImage(Medusa);
                break;
            case "Minotaur":
                LoserGod.setImage(Minotaur);
                break;
            case "Pan":
                LoserGod.setImage(Pan);
                break;
            case "Poseidon":
                LoserGod.setImage(Poseidon);
                break;
            case "Prometheus":
                LoserGod.setImage(Prometheus);
                break;
            case "Zeus":
                LoserGod.setImage(Zeus);
                break;
        }
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void exitButtonHovered() {
        ExitGameButton.setImage(exitButtonHovered);
    }

    public void exitButtonPressed() {

        ButtonType YES = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game? The game will be closed." ,YES, NO);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You are going to exit the game!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(gui.getSantoriniStage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == YES)
            gui.doExit();
    }

    public void exitButtonReleased() {
        ExitGameButton.setImage(exitButtonReleased);
    }

    public void playButtonPressed() {
        PlayButton.setImage(PlayButtonPressed);
    }

    public void playButtonReleased() {
        PlayButton.setImage(PlayButtonReleased);
        gui.doPlayAgain();
    }
}