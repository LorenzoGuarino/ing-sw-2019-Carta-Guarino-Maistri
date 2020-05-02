package it.polimi.ingsw.PSP027.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

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
