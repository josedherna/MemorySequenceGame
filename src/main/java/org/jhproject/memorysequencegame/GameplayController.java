package org.jhproject.memorysequencegame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
