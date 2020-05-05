package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;
import it.polimi.ingsw.PSP027.Network.Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GUI extends Application {

    public static GUIController guiController;
    public String address;
    @FXML
    public ImageView ConnectButton;
    public ImageView ExitButton;
    Image ConnectButtonPressed = new Image("images/Buttons/btn_Connect_pressed.png");
    Image ExitButtonPressed = new Image("images/Buttons/btn_Exit_pressed.png");
    Image ConnectButtonReleased = new Image("images/Buttons/btn_Connect.png");
    Image ExitButtonReleased = new Image("images/Buttons/btn_Exit.png");
    public TextField ServerIp;
    public Pane entryPane;
    public Pane SecondPane;
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        this.guiController=new GUIController(this);
        System.out.println(this);
        window.setTitle("Santorini"); //name of the game window that is shown
        Parent entryPage = FXMLLoader.load(getClass().getResource("/EntryPage.fxml"));
        Scene entryScene = new Scene(entryPage, 1800, 850);
        window.setMaximized(true);
        window.setScene(entryScene);
        window.show();
    }

    public void changePane(){
        try {
            Parent SecondPage = FXMLLoader.load(getClass().getResource("/SecondPage.fxml"));
            Scene SecondScene = new Scene(SecondPage, 1800, 850);
            window.setMaximized(true);
            window.setScene(SecondScene);
            window.show();
        }catch (IOException exception){
            System.out.println("Prova");
        }
    }

    public void connectButtonPressed() {
        ConnectButton.setImage(ConnectButtonPressed);
        this.address=(this.ServerIp.getText());
        System.out.println("press guic"+this.guiController);
        this.guiController.client.Connect(this.address);
    }

    public void connectButtonReleased() {
        ConnectButton.setImage(ConnectButtonReleased);
        System.out.println("release guic"+this.guiController);
        System.out.println(this.address);

    }

    public void exitButtonPressed() throws Exception {
        ExitButton.setImage(ExitButtonPressed);
        guiController.client.Disconnect();
        Platform.exit();
        System.exit(0);
    }

    public void exitButtonReleased() {
        ExitButton.setImage(ExitButtonReleased);
    }
}
