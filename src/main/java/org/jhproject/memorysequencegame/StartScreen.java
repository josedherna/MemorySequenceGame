package org.jhproject.memorysequencegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Jose Hernandez
 */
public class StartScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false");
        FXMLLoader fxmlLoader = new FXMLLoader(StartScreen.class.getResource("title-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setMinWidth(320);
        stage.setMinHeight(490);
        stage.setTitle("Remember the Tiles");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}