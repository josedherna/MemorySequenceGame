package org.jhproject.memorysequencegame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

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

    @FXML
    private void initialize() {
        if (HEADER_FONT != null) {
            difficultyLabel.setFont(Font.loadFont(HEADER_FONT.toString(), 34));
        }

        if (INFO_LABEL_FONT != null) {
            infoLabel.setFont(Font.loadFont(INFO_LABEL_FONT.toString(), 24));
        }

        if (BUTTON_FONT != null) {
            backButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 16));
            skipButton.setFont(Font.loadFont(BUTTON_FONT.toString(), 16));
        }
    }

    protected void initializeEasyInfo() {
        difficultyLabel.setText("Easy");
    }
}
