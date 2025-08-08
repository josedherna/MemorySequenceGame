package org.jhproject.memorysequencegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;

/**
 * This class contains methods that will provide functionality to the GUI elements of the gameplay.
 *
 * @author Jose Hernandez
 */
public class GameplayController {
    @FXML
    private Label scoreLabel;
    @FXML
    private ImageView pauseImageView;
    @FXML
    private ProgressBar timerBar;
    @FXML
    private Label bestScoreLabel;
    @FXML
    private Label currentScoreLabel;
    @FXML
    private VBox tileVbox;
    @FXML
    private Label bestScoreDisplayLabel;

    private final URL PAUSE_IMAGE = getClass().getResource("images/pause_logo.png");

    private final URL SCORE_FONT = getClass().getResource("fonts/Poppins-Bold.ttf");
    private final URL LABEL_FONT = getClass().getResource("fonts/Poppins-SemiBold.ttf");

    private final URL EASY_STYLE_SHEET = getClass().getResource("stylesheets/easy-gameplay.css");
    private final URL MEDIUM_STYLE_SHEET = getClass().getResource("stylesheets/medium-gameplay.css");
    private final URL HARD_STYLE_SHEET = getClass().getResource("stylesheets/hard-gameplay.css");

    private String difficulty;

    private GridPane easyGridpane;
    private GridPane mediumGridpane;
    private GridPane hardGridpane;

    /**
     * Sets the fonts of the labels and sets the image for the pause button.
     */
    @FXML
    private void initialize() {
        if (SCORE_FONT != null) {
            currentScoreLabel.setFont(Font.loadFont(SCORE_FONT.toString(), 50));
            bestScoreDisplayLabel.setFont(Font.loadFont(SCORE_FONT.toString(), 30));
        }

        if (LABEL_FONT != null) {
            scoreLabel.setFont(Font.loadFont(LABEL_FONT.toString(), 20));
            bestScoreLabel.setFont(Font.loadFont(LABEL_FONT.toString(), 20));
        }

        if (PAUSE_IMAGE != null) {
            pauseImageView.setImage(new Image(PAUSE_IMAGE.toString()));
        }

        startCountdown();
    }

    /**
     * Starts the countdown that will start the game.
     */
    protected void startCountdown() {
        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdown");

        if (LABEL_FONT != null) {
            countdownLabel.setFont(Font.loadFont(LABEL_FONT.toString(), 95));
        }

        tileVbox.getChildren().add(countdownLabel);

        Timeline countdownTimeline = getCountdownTimeline(countdownLabel);

        if(countdownTimeline.getStatus() == Timeline.Status.RUNNING) {
            countdownTimeline.stop();
            countdownTimeline.playFromStart();
        }
        else {
            countdownTimeline.play();
        }
    }

    /**
     * Creates a timeline that counts down from 3 seconds and starts the game.
     *
     * @param countdownLabel The label that will display the current time.
     * @return A timeline that counts down from 3 seconds and starts the game.
     */
    protected Timeline getCountdownTimeline(Label countdownLabel) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.0), _ -> {
            countdownLabel.setText("3");
            timerBar.setProgress(1.0);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), _ -> {
            countdownLabel.setText("2");
            timerBar.setProgress(2.0/3.0);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2.0), _ -> {
            countdownLabel.setText("1");
            timerBar.setProgress(1.0/3.0);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3.0), _ -> {
            timerBar.setProgress(0.0/3.0);
            countdownLabel.setText("Go!");
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4.0), _ -> {
            tileVbox.getChildren().remove(countdownLabel);

            switch(difficulty) {
                case "Easy" -> tileVbox.getChildren().add(easyGridpane);
                case "Medium" -> tileVbox.getChildren().add(mediumGridpane);
                case "Hard" -> tileVbox.getChildren().add(hardGridpane);
            }
        }));
        timeline.setCycleCount(1);

        return timeline;
    }

    /**
     * Creates a 3x3 grid pane that will be added to the tileVbox for the easy game difficulty.
     */
    protected void initializeEasyGame() {
        difficulty = "Easy";
        easyGridpane = new GridPane();

        easyGridpane.setHgap(5.0);
        easyGridpane.setVgap(5.0);

        VBox.setVgrow(easyGridpane, Priority.ALWAYS);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100 / 3.0);
            easyGridpane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100 / 3.0);
            easyGridpane.getRowConstraints().add(rowConstraints);
        }

        easyGridpane.setGridLinesVisible(true);
    }

    /**
     * Creates a 4x4 grid pane that will be added to the tileVbox for the medium game difficulty.
     */
    protected void initializeMediumGame() {
        difficulty = "Medium";
        mediumGridpane = new GridPane();

        mediumGridpane.setHgap(5.0);
        mediumGridpane.setVgap(5.0);

        VBox.setVgrow(mediumGridpane, Priority.ALWAYS);

        for (int i = 0; i < 4; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / 4.0);
            mediumGridpane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 4.0);
            mediumGridpane.getRowConstraints().add(rowConstraints);
        }

        mediumGridpane.setGridLinesVisible(true);
    }

    /**
     * Creates a 5x5 grid pane that will be added to the tileVbox for the hard game difficulty.
     */
    protected void initializeHardGame() {
        difficulty = "Hard";
        hardGridpane = new GridPane();

        hardGridpane.setHgap(5.0);
        hardGridpane.setVgap(5.0);

        VBox.setVgrow(hardGridpane, Priority.ALWAYS);

        for (int i = 0; i < 5; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / 5.0);
            hardGridpane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < 5; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 5.0);
            hardGridpane.getRowConstraints().add(rowConstraints);
        }

        hardGridpane.setGridLinesVisible(true);
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
