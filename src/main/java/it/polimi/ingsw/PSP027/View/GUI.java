package it.polimi.ingsw.PSP027.View;

import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Network.Client.Client;
import it.polimi.ingsw.PSP027.Network.Client.ClientObserver;
import it.polimi.ingsw.PSP027.Network.Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GUI extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("Santorini"); //name of the game window that is shown
        Parent entryPage = FXMLLoader.load(getClass().getResource("/EntryPage.fxml"));
        Scene entryScene = new Scene(entryPage, 1800, 850);
        stage.setMaximized(true);
        stage.setScene(entryScene);
        stage.show();
    }    
}
