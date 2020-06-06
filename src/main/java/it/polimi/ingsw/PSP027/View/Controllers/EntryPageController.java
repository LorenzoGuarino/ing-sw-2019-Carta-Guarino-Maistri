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

public class EntryPageController {

    // Reference to the main gui application
    private GUI gui;
    public Client client = null;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView ConnectButton;
    public ImageView ExitButton;
    public TextField ServerIp;
    public Pane entryPane;

    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public EntryPageController() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize()
    {

        //makes the text field not focused at launch
        ServerIp.setFocusTraversable(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui (GUI Gui) {
        this.gui = Gui;
    }

    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    /**
     * Method called when the Connect Button is pressed,
     * it send the Connect Command
     */

    public void connectButtonPressed() {
        ConnectButton.setImage(new Image("images/Buttons/btn_Connect_pressed.png"));
        if(ServerIp.getText() != null && !ServerIp.getText().isEmpty()) {
            gui.doConnect(ServerIp.getText());
        }
    }

    /**
     * Method that update the image of the button pressed
     */
    public void connectButtonReleased() {
        ConnectButton.setImage(new Image("images/Buttons/btn_Connect.png"));
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

}