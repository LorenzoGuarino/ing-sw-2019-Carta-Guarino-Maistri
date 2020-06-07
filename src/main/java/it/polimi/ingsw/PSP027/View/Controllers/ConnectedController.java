package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectedController {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView RegisterButton;
    public ImageView ExitButton;
    public ImageView DisconnectButton;
    public TextField nickname;
    public Pane connectedPane;

    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ConnectedController() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize()
    {

        //makes the text field not focused at launch
        nickname.setFocusTraversable(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */
    /**
     * Method called when the Register Button is pressed,
     * it send the Register Command
     */
    public void registerButtonPressed() {
        RegisterButton.setImage(new Image("images/Buttons/btn_Register_pressed.png"));
        if(nickname.getText() != null && !nickname.getText().isEmpty()) {
            gui.doRegister(nickname.getText());
        }
    }
    /**
     * Method that update the image of the button pressed
     */
    public void registerButtonReleased() {
        RegisterButton.setImage(new Image("images/Buttons/btn_Register.png"));
    }
    /**
     * Method that update the image of the button pressed and call the Exit method in GUI
     */
    public void exitButtonPressed() {
        ExitButton.setImage(new Image("images/Buttons/btn_Exit_pressed.png"));
        gui.doExit();
    }
    /**
     * Method that update the image of the button pressed
     */
    public void exitButtonReleased() {
        ExitButton.setImage(new Image("images/Buttons/btn_Exit.png"));
    }
    /**
     * Method called when the Disconnect Button is pressed,
     * it send the Disconnect Command
     */
    public void disconnectButtonPressed() {
        DisconnectButton.setImage(new Image("images/Buttons/btn_goBack_pressed.png"));
        gui.doDisconnect();
    }
    /**
     * Method that update the image of the button pressed
     */
    public void disconnectButtonReleased() {
        DisconnectButton.setImage(new Image("images/Buttons/btn_goBack.png"));
    }

}