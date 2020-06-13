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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import java.util.*;

/**
 * @author Elisa Maistri
 * @author danielecarta
 */

public class EnteringMatchController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public Label player1;
    public ImageView player1Icon;

    public Label player2;
    public ImageView player2Icon;

    public Label player3;
    public ImageView player3Icon;

    public Pane enteringMatchPane;

    public ImageView ExitGameButton;


    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public EnteringMatchController() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize() {
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     * @param Gui the Gui controlled by this controller
     */

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }
    /**
     * Method that set the NickName of the Players
     * @param players players that are playing the game
     */
    public void setNickname (List<String> players) {
        player1.setText(players.get(0));
        player1Icon.setVisible(true);
        if (players.size() == 2){
            player2.setText(players.get(1));
            player2Icon.setVisible(true);
        }
        else if (players.size() == 3) {
            player2.setText(players.get(1));//?
            player2Icon.setVisible(true);
            player3.setText(players.get(2));
            player3Icon.setVisible(true);
        }
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */
    /**
     * Method that update the image of the button when the mouse is on the button
     */
    public void exitButtonHovered() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame_hovered.png"));
    }
    /**
     * Method that update the button image and than proceed to deregister the player that want to exit from the game
     */
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
            gui.doDeregister();
    }
    /**
     * Method that update the image of the button pressed
     */
    public void exitButtonReleased() {
        ExitGameButton.setImage(new Image("images/Buttons/btn_exitGame.png"));
    }
}