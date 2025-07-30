package org.jhproject.memorysequencegame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

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
    private Button backButton;
    @FXML
    private Button startButton;
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
    @FXML
    private ProgressBar scriptProgressbar;

    private final URL HEADER_FONT = getClass().getResource("fonts/Poppins-Bold.ttf");
    private final URL INFO_LABEL_FONT = getClass().getResource("fonts/Poppins-SemiBold.ttf");
    private final URL BUTTON_FONT = getClass().getResource("fonts/NunitoSans_7pt-SemiBold.ttf");

    private final URL EASY_STYLE_SHEET = getClass().getResource("stylesheets/easy-info.css");
    private final URL MEDIUM_STYLE_SHEET = getClass().getResource("stylesheets/medium-info.css");
    private final URL HARD_STYLE_SHEET = getClass().getResource("stylesheets/hard-info.css");

    private Text[] instructionScript;
    private Timeline infoDisplay;
    private String difficulty;
    private static final GameInstructions GAME_INSTRUCTIONS = new GameInstructions();

    private final ChangeListener<Number> headerHboxSizeListener = (_, _, newValue) -> {
        if (newValue.doubleValue() <= 480.0 && HEADER_FONT != null) {
            difficultyLabel.setFont(Font.loadFont(HEADER_FONT.toString(), headerHbox.getWidth() / 9.7));
        }
        else if (newValue.doubleValue() > 480.0 && HEADER_FONT != null) {
            difficultyLabel.setFont(Font.loadFont(HEADER_FONT.toString(), 50));
        }
    };

    private final ChangeListener<Number> textVboxListener = (_, _, _) -> {
        for (Text line : instructionScript) {
            line.setFont(Font.font(line.getFont().getFamily(), adjustInfoFontSize()));
        }
    };

    /**
     * Applies font to labels and buttons, as well as changing the font size to fit the available space
     * without overflowing.
     */
    @FXML
    private void initialize() {
        if (INFO_LABEL_FONT != null) {
            infoLabel.setFont(Font.loadFont(INFO_LABEL_FONT.toString(), 24));
        }

        if (BUTTON_FONT != null) {
            backButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 18));
            startButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 18));
        }
        headerHbox.widthProperty().addListener(headerHboxSizeListener);
        textVbox.widthProperty().addListener(textVboxListener);
        textVbox.heightProperty().addListener(textVboxListener);

        Platform.runLater(() -> {
           infoDisplay = getDisplayTimeline(instructionScript);
           infoDisplay.play();
        });
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
            URL difficultyStyleSheet = difficultyController.getSelectionStylesheet();

            //Changes scene to difficulty selection screen.
            Scene currentScene = rootVbox.getScene();
            if (difficultyStyleSheet != null) {
                currentScene.getStylesheets().add(difficultyStyleSheet.toString());
                currentScene.setRoot(difficultyParent);

                if (infoDisplay.getStatus() == Animation.Status.RUNNING) {
                    infoDisplay.stop();
                }

                headerHbox.widthProperty().removeListener(headerHboxSizeListener);
                textVbox.widthProperty().removeListener(textVboxListener);
                textVbox.heightProperty().removeListener(textVboxListener);
            }
        } catch (IOException e) {
            System.out.println("Error loading Difficulty Page");
        }
    }

    /**
     * Switches the current scene of the info screen to the game screen.
     */
    @FXML
    private void startGame() {
        FXMLLoader gameplayPage = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));

        try {
            Parent gameplayParent = gameplayPage.load();
            Scene currentScene = rootVbox.getScene();
            currentScene.setRoot(gameplayParent);

            if (infoDisplay.getStatus() == Animation.Status.RUNNING) {
                infoDisplay.stop();
            }

            headerHbox.widthProperty().removeListener(headerHboxSizeListener);
            textVbox.widthProperty().removeListener(textVboxListener);
            textVbox.heightProperty().removeListener(textVboxListener);
        }
        catch (IOException e) {
            System.out.println("Error loading Gameplay Page");
        }
    }

    /**
     * Changes the difficulty information screen to the easy difficulty version.
     */
    protected void initializeEasyInfo(String language) {
        difficultyLabel.setText("Easy");
        difficulty = "Easy";
        instructionScript = GAME_INSTRUCTIONS.getEasyInstructions(language);
    }

    /**
     * Changes the difficulty information screen to the medium difficulty version.
     */
    protected void initializeMediumInfo(String language) {
        difficultyLabel.setText("Medium");
        difficulty = "Medium";
        instructionScript = GAME_INSTRUCTIONS.getMediumInstructions(language);
    }

    /**
     * Changes the difficulty information screen to the hard difficulty version.
     */
    protected void initializeHardInfo(String language) {
        difficultyLabel.setText("Hard");
        difficulty = "Hard";
        instructionScript = GAME_INSTRUCTIONS.getHardInstructions(language);
    }

    /**
     * Calculates the font size of the text in the info text Vbox to ensure it is readable.
     *
     * @return The new font size of the text displayed.
     */
    private double adjustInfoFontSize() {
        double textVboxWidth = textVbox.getWidth() - 20;
        double textVboxHeight = textVbox.getHeight();
        double textVboxArea = textVboxWidth * textVboxHeight;
        double initialFontSize = textVboxArea / 148;
        return Math.sqrt(initialFontSize);
    }

    /**
     * Creates a timeline that displays text about the  read from a JSON file.
     *
     * @param instructionScript An array that contains text read from a JSON file.
     * @return A timeline that would display a line from the difficulty info script.
     */
    private Timeline getDisplayTimeline(Text[] instructionScript) {
        Timeline timeline = new Timeline();
        for (int i = 0, j = 0; i < instructionScript.length && j < instructionScript.length * 4; i++, j += 4) {
            Text currentLine = instructionScript[i];
            int finalJ = j;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(j), _ -> {
                if (!infoTextFlow.getChildren().isEmpty()) {
                    infoTextFlow.getChildren().clear();
                }
                infoTextFlow.getChildren().add(currentLine);
                scriptProgressbar.setProgress(finalJ /24.0);
            }));
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(24.0), _ -> scriptProgressbar.setProgress(1.0)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(24.5), _ -> startGame()));
        timeline.setCycleCount(1);
        return timeline;
    }

    /**
     * Gets the URL of the CSS stylesheet for the easy info screen.
     *
     * @return The URL of the CSS stylesheet for the easy info screen.
     */
    protected URL getEasyStyleSheet() {
        return EASY_STYLE_SHEET;
    }

    /**
     * Gets the URL of the CSS stylesheet for the medium info screen.
     *
     * @return The URL of the CSS stylesheet for the medium info screen.
     */
    protected URL getMediumStyleSheet() {
        return MEDIUM_STYLE_SHEET;
    }

    /**
     * Gets the URL of the CSS stylesheet for the hard info screen.
     *
     * @return The URL of the CSS stylesheet for the hard info screen.
     */
    protected URL getHardStyleSheet() {
        return HARD_STYLE_SHEET;
    }
}
