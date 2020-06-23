package it.polimi.ingsw.PSP027.View.Controllers;

import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.View.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

/**
 * @author Lorenzo Guarino
 */

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
     * @param Gui the Gui controlled by this controller
     */

    public void setGui(GUI Gui) {
        this.gui = Gui;
    }
    /**
     * Method that saves the chosen gods
     * @param chosenGods god chosen by the first player
     */
    public void setChooseGodTitle(List<String> chosenGods) {
        godsToSave = chosenGods;
        setImagesOfGods(chosenGods);
    }
    /* ******************************* GUI CONTROLLER METHODS THAT TRIGGER GUI RENDERING AND CONNECTION WITH SERVER  ****************************** */
    /**
     * Method that update the button image and than proceed to send the next command to GUI
     */
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
    /**
     * Method that update the image of the button pressed
     */
    public void confirmButtonReleased() {
        ConfirmButton.setImage(new Image("images/Buttons/btn_Confirm.png"));
    }
    /**
     * Method that display the god selected and his description, so the player can choose between the 2 gods
     * @param chosenGods the 3 gods chosen by the first player
     */
    public void setImagesOfGods(List<String> chosenGods){
        String god1 = chosenGods.get(0);
        GodLeft.setImage(new Image("images/Gods/"+god1+"_big.png"));
        GodLeftDescription = new Image("images/Gods/"+god1+"Description.png");
        String god2 = chosenGods.get(1);
        GodCenter.setImage(new Image("images/Gods/"+god2+"_big.png"));
        GodCenterDescription = new Image("images/Gods/"+god2+"Description.png");
        String god3 = chosenGods.get(2);
        GodRight.setImage(new Image("images/Gods/"+god3+"_big.png"));
        GodRightDescription = new Image("images/Gods/"+god3+"Description.png");
    }
    /**
     * Method that set the description of the god selected and set the button to visible
     */
    public void selectGodLeft(){
        ConfirmButton.setVisible(true);
        godselected = 0;
        GodDescription.setImage(GodLeftDescription);
    }
    /**
     * Method that set the description of the god selected and set the button to visible
     */
    public void selectGodCenter(){
        ConfirmButton.setVisible(true);
        godselected = 1;
        GodDescription.setImage(GodCenterDescription);
    }
    /**
     * Method that set the description of the god selected and set the button to visible
     */
    public void selectGodRight(){
        ConfirmButton.setVisible(true);
        godselected = 2;
        GodDescription.setImage(GodRightDescription);
    }

}
