package org.jhproject.memorysequencegame;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains methods that will provide functionality to the GUI elements of the
 * difficulty selection screen of the game.
 *
 * @author Jose Hernandez
 */
public class SelectionController {
    @FXML
    private VBox rootVbox;
    @FXML
    private Label headerLabel;
    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;
    @FXML
    private HBox headerHbox;
    @FXML
    private VBox buttonVbox;
    @FXML
    private Button settingsButton;

    private final URL HEADER_FONT = getClass().getResource("fonts/Poppins-Bold.ttf");
    private final URL BUTTON_FONT = getClass().getResource("fonts/NunitoSans_7pt-SemiBold.ttf");
    private final URL DIFFICULTY_STYLE_SHEET = getClass().getResource("stylesheets/selection-screen.css");

    private final ChangeListener<Number> buttonSizeListener = (_, _, _) -> {
        if (BUTTON_FONT != null) {
            easyButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
            mediumButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
            hardButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
        }
    };

    private final ChangeListener<Number> headerHboxSizeListener = (_, _, newValue) -> {
        if (newValue.doubleValue() <= 480.0 && HEADER_FONT != null) {
            headerLabel.setFont(Font.loadFont(HEADER_FONT.toString(), headerHbox.getWidth() / 9.7));
        }
        else if (newValue.doubleValue() > 480.0 && HEADER_FONT != null) {
            headerLabel.setFont(Font.loadFont(HEADER_FONT.toString(), 50));
        }
    };

    /**
     * Applies font to labels and buttons, as well as changing the font size to fit the available space
     * without overflowing.
     */
    @FXML
    private void initialize() {
        buttonVbox.heightProperty().addListener(buttonSizeListener);
        buttonVbox.widthProperty().addListener(buttonSizeListener);
        headerHbox.widthProperty().addListener(headerHboxSizeListener);
        if (BUTTON_FONT != null) {
            settingsButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 16.5));
        }
    }

    /**
     * Switches scene to display information about the easy difficulty of the game.
     */
    @FXML
    private void displayEasyGame() {
        FXMLLoader easyInfoPage = new FXMLLoader(getClass().getResource("infoscreen-view.fxml"));

        try {
            Parent easyInfoParent = easyInfoPage.load();
            InfoController easyInfoController = easyInfoPage.getController();
            URL easyStyleSheet = easyInfoController.getEasyStyleSheet();

            Scene currentScene = rootVbox.getScene();
            if (easyStyleSheet != null) {
                easyInfoParent.getStylesheets().add(easyStyleSheet.toString());
                easyInfoController.initializeEasyInfo("en");
                currentScene.setRoot(easyInfoParent);
                buttonVbox.heightProperty().removeListener(buttonSizeListener);
                buttonVbox.widthProperty().removeListener(buttonSizeListener);
                headerHbox.widthProperty().removeListener(headerHboxSizeListener);
            }
        } catch (IOException e) {
            System.out.println("Error loading Easy Info page");
        }
    }

    /**
     * Switches scene to display information about the medium difficulty of the game.
     */
    @FXML
    private void displayMediumGame() {
        FXMLLoader mediumInfoPage = new FXMLLoader(getClass().getResource("infoscreen-view.fxml"));

        try {
            Parent mediumInfoParent = mediumInfoPage.load();
            InfoController mediumInfoController = mediumInfoPage.getController();
            URL mediumStyleSheet = mediumInfoController.getMediumStyleSheet();

            Scene currentScene = rootVbox.getScene();
            if (mediumStyleSheet != null) {
                mediumInfoParent.getStylesheets().add(mediumStyleSheet.toString());
                mediumInfoController.initializeMediumInfo("en");
                currentScene.setRoot(mediumInfoParent);
                buttonVbox.heightProperty().removeListener(buttonSizeListener);
                buttonVbox.widthProperty().removeListener(buttonSizeListener);
                headerHbox.widthProperty().removeListener(headerHboxSizeListener);
            }
        } catch (IOException e) {
            System.out.println("Error loading Easy Info page");
        }
    }

    /**
     * Switches scene to display information about the hard difficulty of the game.
     */
    @FXML
    private void displayHardGame() {
        FXMLLoader hardInfoPage = new FXMLLoader(getClass().getResource("infoscreen-view.fxml"));

        try {
            Parent hardInfoParent = hardInfoPage.load();
            InfoController hardInfoController = hardInfoPage.getController();
            URL hardStyleSheet = hardInfoController.getHardStyleSheet();

            Scene currentScene = rootVbox.getScene();
            if (hardStyleSheet != null) {
                hardInfoParent.getStylesheets().add(hardStyleSheet.toString());
                hardInfoController.initializeHardInfo("en");
                currentScene.setRoot(hardInfoParent);
                buttonVbox.heightProperty().removeListener(buttonSizeListener);
                buttonVbox.widthProperty().removeListener(buttonSizeListener);
                headerHbox.widthProperty().removeListener(headerHboxSizeListener);
            }
        } catch (IOException e) {
            System.out.println("Error loading Easy Info page");
        }
    }

    /**
     * Calculates the new font size for the buttons based on the width and height of the buttonVbox.
     *
     * @return The new font size for the text in the difficulty buttons.
     */
    private double adjustButtonFontSize() {
        double newFontSizeHeight = buttonVbox.getHeight() / 12.5;
        double newFontSizeWidth = buttonVbox.getWidth() / 9.5;
        return Math.min(newFontSizeHeight, newFontSizeWidth);
    }

    /**
     * Gets the URL of the CSS stylesheet for the difficulty selection screen.
     *
     * @return The URL of the CSS stylesheet for the difficulty selection screen.
     */
    protected URL getSelectionStylesheet() {
        return DIFFICULTY_STYLE_SHEET;
    }
}
