package org.jhproject.memorysequencegame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains methods that will provide functionality to the GUI elements of the
 * difficulty information screen of the game.
 *
 * @author Jose Hernandez
 */
public class InfoController {

    @FXML
    private VBox middleInfoVbox;
    @FXML
    private Label infoLabel;
    @FXML
    private Label difficultyLabel;
    @FXML
    private HBox infoHbox;
    @FXML
    private Button skipButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox rootVbox;
    @FXML
    private HBox headerHbox;
    @FXML
    private TextFlow infoTextFlow;
    @FXML
    private VBox textVbox;
    @FXML
    private HBox buttonHbox;

    private final URL HEADER_FONT = getClass().getResource("fonts/Poppins-Bold.ttf");
    private final URL INFO_LABEL_FONT = getClass().getResource("fonts/Poppins-SemiBold.ttf");
    private final URL BUTTON_FONT = getClass().getResource("fonts/NunitoSans_7pt-SemiBold.ttf");
    private final URL DIFFICULTY_STYLE_SHEET = getClass().getResource("stylesheets/selection-screen.css");

    @FXML
    private void initialize() {
        if (HEADER_FONT != null) {
            difficultyLabel.setFont(Font.loadFont(HEADER_FONT.toString(), 34));
        }

        if (INFO_LABEL_FONT != null) {
            infoLabel.setFont(Font.loadFont(INFO_LABEL_FONT.toString(), 24));
        }

        if (BUTTON_FONT != null) {
            backButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 18));
            skipButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 18));
        }
    }

    /**
     * Returns the player back to the difficulty selection screen.
     */
    @FXML
    private void returnToSelection() {
        FXMLLoader difficultyPage = new FXMLLoader(getClass().getResource("selection-view.fxml"));

        try {
            Parent difficultyParent = difficultyPage.load();
            SelectionController difficultyController = difficultyPage.getController();
            //Changes scene to difficulty selection screen.
            Scene currentScene = rootVbox.getScene();
            if (DIFFICULTY_STYLE_SHEET != null) {
                currentScene.getStylesheets().add(DIFFICULTY_STYLE_SHEET.toString());
                difficultyController.adjustHeaderFontSize(rootVbox.getWidth());
                currentScene.setRoot(difficultyParent);
            }
        } catch (IOException e) {
            System.out.println("Error loading Difficulty Page");
        }
    }

    /**
     * Changes the difficulty information screen to the easy difficulty version.
     */
    protected void initializeEasyInfo() {
        difficultyLabel.setText("Easy");
    }

    /**
     * Changes the difficulty information screen to the medium difficulty version.
     */
    protected void initializeMediumInfo() {
        difficultyLabel.setText("Medium");
    }
}
