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
public class ChooseYourGodCase3Controller {

    // Reference to the main gui application
    private GUI gui;

    public Client client = null;
    private List<String> godsToSave = new ArrayList<>();
    private int godselected=0;

    /* ***************************************** GUI RENDERING RELATED VARIABLES ****************************************** */
    @FXML
    public ImageView GodLeft;
    public ImageView GodCenter;
    public ImageView GodRight;
    public ImageView GodDescription;
    public ImageView ConfirmButton;
    public Image GodLeftDescription;
    public Image GodCenterDescription;
    public Image GodRightDescription;
    /* ****************************************************************************************************************** */

    /**
     * Constructor, called before the initialize method
     */
    public ChooseYourGodCase3Controller() {
    }

    /**
     * Initializes the controller, method automatically called when the fxml is loaded
     */
    @FXML
    public void initialize() {
        ConfirmButton.setVisible(false);
    }

    /**
     * Method called by the main GUI in order to give the gui controller a reference of itself
     */

    public void setGui(GUI Gui) {
        this.gui = Gui;
    }

    public void setChooseGodTitle(List<String> chosenGods) {
        godsToSave = chosenGods;
        setImagesOfGods(chosenGods);
    }
    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */

    public void confirmButtonPressed() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm_pressed.png"));
        if (godselected == 0) {
            gui.doSendSelectedGod(godsToSave.get(0));
        } else if (godselected == 1) {
            gui.doSendSelectedGod(godsToSave.get(1));
        } else if (godselected == 2) {
            gui.doSendSelectedGod(godsToSave.get(2));
        }
    }

    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }

    public void setImagesOfGods(List<String> chosenGods){
        String god1 = chosenGods.get(0);
        GodLeft.setImage(new Image("images/Gods/"+god1+"_big.png"));
        GodLeftDescription = new Image("images/Gods/"+god1+"Description.png");
        String god2 = chosenGods.get(1);
        GodRight.setImage(new Image("images/Gods/"+god2+"_big.png"));
        GodRightDescription = new Image("images/Gods/"+god2+"Description.png");
        String god3 = chosenGods.get(3);
        GodCenter.setImage(new Image("images/Gods/"+god3+"_big.png"));
        GodCenterDescription = new Image("images/Gods/"+god3+"Description.png");
    }

    public void selectGodLeft(){
        ConfirmButton.setVisible(true);
        godselected = 0;
        GodDescription.setImage(GodLeftDescription);
    }
    public void selectGodCenter(){
        ConfirmButton.setVisible(true);
        godselected = 1;
        GodDescription.setImage(GodCenterDescription);
    }
    public void selectGodRight(){
        ConfirmButton.setVisible(true);
        godselected = 2;
        GodDescription.setImage(GodRightDescription);
    }

}
