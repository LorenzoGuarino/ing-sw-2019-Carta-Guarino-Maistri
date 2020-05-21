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

public class ChooseFirstPlayerCase3Controller {

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
    public Label player1;
    @FXML
    public Label player2;
    @FXML
    public Label player3;
    @FXML
    public ImageView god;

    @FXML
    public ImageView ExitGameButton;
    Image exitButtonHovered = new Image("images/Buttons/btn_exitGame_hovered.png");
    Image exitButtonReleased = new Image("images/Buttons/btn_exitGame.png");

    Image ApolloDisplayed = new Image("images/Gods/Apollo_icon.png");
    Image AresDisplayed = new Image("images/Gods/Ares_icon.png");
    Image ArtemisDisplayed = new Image("images/Gods/Artemis_icon.png");
    Image AthenaDisplayed = new Image("images/Gods/Athena_icon.png");
    Image AtlasDisplayed = new Image("images/Gods/Atlas_icon.png");
    Image DemeterDisplayed = new Image("images/Gods/Demeter_icon.png");
    Image HephaestusDisplayed = new Image("images/Gods/Hephaestus_icon.png");
    Image HestiaDisplayed = new Image("images/Gods/Hestia_icon.png");
    Image MedusaDisplayed = new Image("images/Gods/Medusa_icon.png");
    Image MinotaurDisplayed = new Image("images/Gods/Minotaur_icon.png");
    Image PanDisplayed = new Image("images/Gods/Pan_icon.png");
    Image PoseidonDisplayed = new Image("images/Gods/Poseidon_icon.png");
    Image PrometheusDisplayed = new Image("images/Gods/Prometheus_icon.png");
    Image ZeusDisplayed = new Image("images/Gods/Zeus_icon.png");

    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseFirstPlayerCase3Controller() {
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

    public void setNickname (List<String> players) {
        player1.setText(players.get(0));
        player2.setText(players.get(1));
        player3.setText(players.get(2));
    }

    public void setGod (String firstPlayersGod) {
        switch (firstPlayersGod) {
            case "Apollo":
                god.setImage(ApolloDisplayed);
                break;
            case "Artemis":
                god.setImage(ArtemisDisplayed);
                break;
            case "Ares":
                god.setImage(AresDisplayed);
                break;
            case "Athena":
                god.setImage(AthenaDisplayed);
                break;
            case "Atlas":
                god.setImage(AtlasDisplayed);
                break;
            case "Demeter":
                god.setImage(DemeterDisplayed);
                break;
            case "Hephaestus":
                god.setImage(HephaestusDisplayed);
                break;
            case "Hestia":
                god.setImage(HestiaDisplayed);
                break;
            case "Medusa":
                god.setImage(MedusaDisplayed);
                break;
            case "Minotaur":
                god.setImage(MinotaurDisplayed);
                break;
            case "Pan":
                god.setImage(PanDisplayed);
                break;
            case "Poseidon":
                god.setImage(PoseidonDisplayed);
                break;
            case "Prometheus":
                god.setImage(PrometheusDisplayed);
                break;
            case "Zeus":
                god.setImage(ZeusDisplayed);
                break;
        }
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void clickedOnFirstPlayer(){
        gui.doSendFirstPlayer(player1.getText());
    }
    public void clickedOnSecondPlayer(){
        gui.doSendFirstPlayer(player2.getText());
    }
    public void clickedOnThirdPlayer(){
        gui.doSendFirstPlayer(player3.getText());
    }

    public void exitButtonHovered() {
        ExitGameButton.setImage(exitButtonHovered);
    }

    public void exitButtonPressed() {

        ButtonType YES = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType NO = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the game? You will be lead to the registering page." ,YES, NO);
        alert.setTitle("Exit Game");
        alert.setHeaderText("You are going to exit the game!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(gui.getSantoriniStage());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == YES)
            gui.doPlayAgain();
    }

    public void exitButtonReleased() {
        ExitGameButton.setImage(exitButtonReleased);
    }

}
