package it.polimi.ingsw.PSP027.View;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class GUI extends Application {

    Button connectButton;
    Button exitButton;
    Button registerButton;
    Button disconnectButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Santorini"); //name of the game window that is shown

        connectButton = new Button();
        connectButton.setText("Connect");

        exitButton = new Button();
        exitButton.setText("Exit");

        registerButton = new Button();
        registerButton.setText("Register");

        disconnectButton = new Button();
        disconnectButton.setText("Disconnect");

        StackPane entryPane = new StackPane();
        entryPane.setId("entryPane");
        entryPane.getChildren().add(connectButton);

        Scene entryScene = new Scene(entryPane, 1800, 850);
        entryScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setMaximized(true);
        stage.setScene(entryScene);
        stage.show();
    }
}
