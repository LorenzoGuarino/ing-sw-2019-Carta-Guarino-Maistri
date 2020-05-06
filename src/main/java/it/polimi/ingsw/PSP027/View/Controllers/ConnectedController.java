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
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView RegisterButton;
    @FXML
    public ImageView ExitButton;
    @FXML
    public ImageView DisconnectButton;
    Image RegisterButtonPressed = new Image("images/Buttons/btn_Register_pressed.png");
    Image ExitButtonPressed = new Image("images/Buttons/btn_Exit_pressed.png");
    Image DisconnectButtonPressed = new Image("images/Buttons/btn_goBack_pressed.png");
    Image ExitButtonReleased = new Image("images/Buttons/btn_Exit.png");
    Image RegisterButtonReleased = new Image("images/Buttons/btn_Register.png");
    Image DisconnectButtonReleased = new Image("images/Buttons/btn_goBack.png");
    @FXML
    public TextField nickname;
    @FXML
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

    public void registerButtonPressed() {
        RegisterButton.setImage(RegisterButtonPressed);
        if(nickname.getText() != null && !nickname.getText().isEmpty()) {
            gui.doRegister(nickname.getText());
        }
    }

    public void registerButtonReleased() {
        RegisterButton.setImage(RegisterButtonReleased);
    }

    public void exitButtonPressed() throws Exception {
        ExitButton.setImage(ExitButtonPressed);
        gui.doExit();
    }

    public void exitButtonReleased() {
        ExitButton.setImage(ExitButtonReleased);
    }

    public void disconnectButtonPressed() {
        DisconnectButton.setImage(DisconnectButtonPressed);
        gui.doDisconnect();
    }
    public void disconnectButtonReleased() {
        DisconnectButton.setImage(DisconnectButtonReleased);
    }

}