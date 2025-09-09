package org.jhproject.memorysequencegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class contains methods that will provide functionality to the GUI elements of the gameplay.
 *
 * @author Jose Hernandez
 */
public class GameplayController {
    @FXML
    private VBox rootVbox;
    @FXML
    private Label scoreLabel;
    @FXML
    private ImageView pauseImageView;
    @FXML
    private ProgressBar timerBar;
    @FXML
    private Button pauseButton;
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
    private Game currentGame;
    private int index = 0;

    private GridPane easyGridpane;
    private GridPane mediumGridpane;
    private GridPane hardGridpane;

    /**
     * Sets the fonts of the labels and sets the image for the pause button.
     */
    @FXML
    private void initialize() {
        if (SCORE_FONT != null) {
            currentScoreLabel.setFont(Font.loadFont(SCORE_FONT.toString(), 42));
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

    private ChangeListener<Boolean> readState = (_,_, newValue) -> {
        if (newValue) {
            displayTileSelection();
        }
    };

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
                case "Easy" -> {
                    tileVbox.getChildren().add(easyGridpane);
                    startGame();
                }
                case "Medium" -> tileVbox.getChildren().add(mediumGridpane);
                case "Hard" -> tileVbox.getChildren().add(hardGridpane);
            }
        }));
        timeline.setCycleCount(1);

        return timeline;
    }

    /**
     *
     */
    private void startGame() {
        if (Objects.equals(difficulty, "Easy") && !currentGame.isRunning()) {
            Scene currentScene = rootVbox.getScene();
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setOnCloseRequest((_) -> endGame());

            currentScoreLabel.textProperty().bind(currentGame.displayScore());
            currentGame.readTileSelection().addListener(readState);
            timerBar.progressProperty().bind(currentGame.getTimeProgress());
            Thread easyGame = new Thread(currentGame);
            easyGame.start();
        }
    }

    /**
     * Ends the game at any point in time.
     */
    private void endGame() {
        currentGame.setRunning(false);
    }

    /**
     * Displays the tiles that were selected by the game.
     */
    private void displayTileSelection() {
        ArrayList<int[]> tileSelection = currentGame.getTileSelection();
        Timeline tileSelectionTimeline = getTileDisplayTimeline(tileSelection);
        tileSelectionTimeline.play();
    }

    /**
     * Creates and gets a timeline that will display the tile selection sequence that the player
     * has to memorize.
     *
     * @param tileSelection A list of tile coordinates in a sequence that will be displayed.
     * @return A timeline that displays the tile selection.
     */
    private Timeline getTileDisplayTimeline(ArrayList<int[]> tileSelection) {
        index = 0;
        Timeline tileDisplayTimeline = new Timeline();
        switch(difficulty) {
            case "Easy" -> {
                tileDisplayTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), _ -> {
                    int[] tile = tileSelection.get(index);
                    Tiles currentTile = currentGame.getGameTiles()[tile[0]][tile[1]];
                    currentTile.getStyleClass().remove("easy_tile");
                    currentTile.getStyleClass().add("easy_tile_selected");
                }));
                tileDisplayTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), _ -> {
                    int[] tile = tileSelection.get(index);
                    Tiles currentTile = currentGame.getGameTiles()[tile[0]][tile[1]];
                    currentTile.getStyleClass().remove("easy_tile_selected");
                    currentTile.getStyleClass().add("easy_tile");
                    index += 1;
                    if (index == currentGame.getSelectionAmountIndex() + 1) {
                        currentGame.setReadTileSelection(false);
                        currentGame.setPlayerTurn(true);
                        currentGame.setDisabledTiles(false);
                    }
                }));
            }
            case "Medium" -> {
            }
        }
        tileDisplayTimeline.setCycleCount(currentGame.getSelectionAmountIndex() + 1);
        return tileDisplayTimeline;
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

        generateEasyTiles(easyGridpane);
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

        generateMediumTiles(mediumGridpane);
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

        generateHardTiles(hardGridpane);
    }

    /**
     * Generates the tiles displayed in the easy difficulty.
     *
     * @param currentGridPane The grid pane where the tiles are going to be added.
     */
    private void generateEasyTiles(GridPane currentGridPane) {
        Tiles[][] generatedEasyTiles = new Tiles[3][3];
        currentGame = new EasyGame(generatedEasyTiles);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tiles generated = new Tiles(new int[]{i, j}, "easy_tile");
                //For debugging purpose, the indices of the tiles are visible
                generated.setText(i + "," + j);
                generated.setOnAction(_ -> currentGame.validatePlayerSelection(generated.getCoordinates()[0], generated.getCoordinates()[1]));
                generated.disableProperty().bind(currentGame.disabledTiles());

                generatedEasyTiles[i][j] = generated;
                currentGridPane.add(generated, j, i);
            }
        }

        pauseButton.setOnAction(_ -> currentGame.testTileTurn());
    }

    /**
     * Generates the tiles displayed in the medium difficulty.
     *
     * @param currentGridPane The grid pane where the tiles are going to be added.
     */
    private void generateMediumTiles(GridPane currentGridPane) {
        Tiles[][] generatedMediumTiles = new Tiles[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tiles generated = new Tiles(new int[]{i, j}, "medium_tile");
                generated.setText(i + "," + j);
                generated.disableProperty().bind(currentGame.disabledTiles());

                generatedMediumTiles[i][j] = generated;
                currentGridPane.add(generated, j, i);
            }
        }

        //currentGame = new Game(generatedMediumTiles);
    }

    /**
     * Generates the tiles displayed in the hard difficulty.
     *
     * @param currentGridPane The grid pane where the tiles are going to be added.
     */
    private void generateHardTiles(GridPane currentGridPane) {
        Tiles[][] generatedHardTiles = new Tiles[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tiles generated = new Tiles(new int[]{i, j}, "hard_tile");
                generated.setText(i + "," + j);
                generated.disableProperty().bind(currentGame.disabledTiles());

                generatedHardTiles[i][j] = generated;
                currentGridPane.add(generated, j, i);
            }
        }

        //currentGame = new Game(generatedHardTiles);
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
