package org.jhproject.memorysequencegame;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains method that will manage the gameplay and all the processes of
 * selecting tiles to display and validating the player's input.
 *
 * @author Jose Hernandez
 */
public abstract class Game implements Runnable {
    private volatile boolean isRunning;
    private double time;
    private double score;
    private boolean paused;
    private boolean selectionTurn;
    private boolean playerTurn;
    private int validationIndex = 0;
    private int level = 1;
    private int selectionAmountIndex;
    private boolean successfulPlayerSelection;

    private StringProperty currentScoreLabel = new SimpleStringProperty("");
    private BooleanProperty disabledTiles = new SimpleBooleanProperty();
    private DoubleProperty timeProgress = new SimpleDoubleProperty();
    private BooleanProperty readTileSelection = new SimpleBooleanProperty();

    protected abstract void incrementScore();
    protected abstract Tiles[][] getGameTiles();
    protected abstract ArrayList<int[]> getTileSelection();
    protected abstract void tileSelectionTurn();
    protected abstract void playerTurn();
    protected abstract void validatePlayerSelection(int col, int row);
    protected abstract void gameOver();
    protected abstract void testTileTurn();
    protected abstract Map<String, List<List<int[]>>> generatePatterns();

    /**
     * Returns a string property that will display the current score to the GUI.
     *
     * @return The updated value of the current score.
     */
    protected StringProperty displayScore() {
        if (score < 10000.0) {
            this.currentScoreLabel.set(Integer.toString((int)score));
        }
        else if (score >= 10000.0 && score < 1000000.0) {
            this.currentScoreLabel.set((score/10000) + "K");
        }
        else if (score >= 1000000.0 && score < 1000000000.0) {
            this.currentScoreLabel.set((score/100000.0) + "M");
        }

        return this.currentScoreLabel;
    }

    /**
     * Gets the status of the tiles being disabled.
     *
     * @return The status of the tiles being disabled.
     */
    protected BooleanProperty disabledTiles() {
        return disabledTiles;
    }

    /**
     * Gets the time in the progressbar.
     *
     * @return The time in the progressbar.
     */
    protected DoubleProperty getTimeProgress() {
        return this.timeProgress;
    }

    /**
     * Sets the time in the progressbar.
     *
     * @param value The new value of time in the progress bar.
     */
    protected void setTimeProgress(double value) {
        this.timeProgress.set(value);
    }

    /**
     * Gets the current state of the game.
     *
     * @return The current state of the game.
     */
    protected boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Sets the current state of the game.
     *
     * @param state The new state of the game.
     */
    protected void setRunning(boolean state) {
        isRunning = state;
    }

    /**
     *
     * @param state The new state of the tiles being disabled.
     */
    protected void setDisabledTiles(boolean state) {
        disabledTiles.set(state);
    }

    /**
     * Gets if the game is paused.
     *
     * @return The paused status of the game.
     */
    protected boolean isPaused() {
        return paused;
    }

    /**
     *
     * @param state The new paused status of the game.
     */
    protected void setPaused(boolean state) {
        paused = state;
    }

    /**
     * Gets if it is the tile selection's turn.
     *
     * @return If it is the tile selection's turn.
     */
    protected boolean isSelectionTurn() {
        return selectionTurn;
    }

    /**
     * Sets the tile selection's turn.
     *
     * @param state Sets if it is the tile selection's turn.
     */
    protected void setSelectionTurn(boolean state) {
        selectionTurn = state;
    }

    /**
     * Gets the new state of the tile selection being displayed.
     *
     * @return The new state of the tile selection being displayed.
     */
    protected BooleanProperty readTileSelection() {
        return readTileSelection;
    }

    /**
     * Sets the new state of the tile selection being displayed.
     *
     * @param state The new state of the tile selection being displayed.
     */
    protected void setReadTileSelection(boolean state) {
        readTileSelection.set(state);
    }

    /**
     * Gets if it is the player's turn.
     *
     * @return If it is the player's turn.
     */
    protected boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * Sets the player's turn.
     *
     * @param state Sets if it is the player's turn.
     */
    protected void setPlayerTurn(boolean state) {
        playerTurn = state;
    }

    /**
     * Gets whether the player is successful in selecting the correct sequence.
     *
     * @return Whether the player is successful in selecting the correct sequence.
     */
    protected boolean isSuccessfulPlayerSelection() {
        return successfulPlayerSelection;
    }

    /**
     * Sets whether the player is successful in selecting the correct sequence.
     *
     * @param state Whether the player was successful in recalling the sequence.
     */
    protected void setSuccessfulPlayerSelection(boolean state) {
        successfulPlayerSelection = state;
    }

    /**
     * Gets the current score of the game.
     *
     * @return The current score of the game.
     */
    protected double getScore() {
        return score;
    }

    /**
     * Sets the new score of the game.
     *
     * @param newScore The new score of the game.
     */
    protected void setScore(double newScore) {
        score = newScore;
        displayScore();
    }

    /**
     * Gets the game's current time.
     *
     * @return The current time the player has left to select the sequence.
     */
    protected double getTime() {
        return time;
    }

    /**
     * Sets the value of the game's time.
     *
     * @param newTime The new value of the game's time.
     */
    protected void setTime(double newTime) {
        time = newTime;
    }

    /**
     * Gets the current index in the tile selection array that is compared for validation.
     *
     * @return The current index in the tile selection array that is compared for validation.
     */
    protected int getValidationIndex() {
        return validationIndex;
    }

    /**
     * Sets the new index in the tile selection array that is compared for validation.
     *
     * @param newValidationIndex The new index in the tile selection array that is compared for validation.
     */
    protected void setValidationIndex(int newValidationIndex) {
        validationIndex = newValidationIndex;
    }

    /**
     * Gets the current level of the game.
     *
     * @return The current level of the game.
     */
    protected int getLevel() {
        return level;
    }

    /**
     * Sets the level of the game.
     *
     * @param level The new level of the game.
     */
    protected void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the index of where the tile selection stops when it is displayed.
     *
     * @return The index of where the tile selection stops when it is displayed.
     */
    public int getSelectionAmountIndex() {
        return selectionAmountIndex;
    }

    /**
     * Sets the index of where the tile selection stops when it is displayed.
     *
     * @param selectionAmountIndex The new index of where the tile selection stops when it is displayed.
     */
    protected void setSelectionAmountIndex(int selectionAmountIndex) {
        this.selectionAmountIndex = selectionAmountIndex;
    }
}
