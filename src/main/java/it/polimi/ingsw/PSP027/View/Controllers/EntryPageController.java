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
    private boolean bRun = false;
    private int requiredgods = 0;
    private List<String> gods = null;
    private List<String> players = null;
    private Node nodeboard; //it's overwritten every time a new board needs to be printed
    private List<Integer> indexcandidatecells = new ArrayList<Integer>(); //used for move and build and is overwritten every time
    private Map<String, String> NicknameGodMap = new HashMap<String, String>();
    private String[] chosen_cmd;


    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */

    @FXML
    public ImageView ConnectButton;
    @FXML
    public ImageView ExitButton;
    Image ConnectButtonPressed = new Image("images/Buttons/btn_Connect_pressed.png");
    Image ExitButtonPressed = new Image("images/Buttons/btn_Exit_pressed.png");
    Image RegisterButtonPressed = new Image("images/Buttons/btn_Register_pressed.png");
    Image ConnectButtonReleased = new Image("images/Buttons/btn_Connect.png");
    Image ExitButtonReleased = new Image("images/Buttons/btn_Exit.png");
    Image RegisterButtonReleased = new Image("images/Buttons/btn_Register.png");
    @FXML
    public TextField ServerIp;
    @FXML
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

    public void connectButtonPressed() {
        ConnectButton.setImage(ConnectButtonPressed);
        if(ServerIp.getText() != null && !ServerIp.getText().isEmpty()) {
            gui.doConnect(ServerIp.getText());
        }
    }

    public void connectButtonReleased() {
        ConnectButton.setImage(ConnectButtonReleased);
    }

    public void exitButtonPressed() throws Exception {
        ExitButton.setImage(ExitButtonPressed);
        gui.doExit();
    }

    public void exitButtonReleased() {
        ExitButton.setImage(ExitButtonReleased);
    }

}