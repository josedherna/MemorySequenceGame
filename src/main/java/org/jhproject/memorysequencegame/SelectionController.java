package org.jhproject.memorysequencegame;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    private VBox buttonVbox;

    private final URL HEADER_FONT = getClass().getResource("fonts/Poppins-Bold.ttf");
    private final URL BUTTON_FONT = getClass().getResource("fonts/NunitoSans_7pt-SemiBold.ttf");
    private final URL STYLE_SHEET = getClass().getResource("stylesheets/selection-screen.css");

    /**
     * Responds to the changes in the width and height of buttonVbox.
     * It calls a method that calculates the appropriate font size of the buttons and changes the buttons' font size to fit in the buttons' new size.
     */
    private final ChangeListener<Number> buttonSizeListener = (_, _, _) -> {
        easyButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
        mediumButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
        hardButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
    };

    /**
     * Applies font to labels and buttons, as well as changing the font size to fit the available space
     * without overflowing.
     */
    @FXML
    private void initialize() {
        if (STYLE_SHEET != null) {
            rootVbox.getStylesheets().add(STYLE_SHEET.toString());
        }
        if (HEADER_FONT != null) {
            headerLabel.setFont(Font.loadFont(HEADER_FONT.toString(), 40));
        }
        Platform.runLater(() -> {
            if (BUTTON_FONT != null) {
                easyButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
                mediumButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
                hardButton.setFont(Font.loadFont(BUTTON_FONT.toString(), adjustButtonFontSize()));
            }
            buttonVbox.heightProperty().addListener(buttonSizeListener);
            buttonVbox.widthProperty().addListener(buttonSizeListener);
        });
    }

    /**
     * Calculates the new font size for the buttons based on the width and height of the buttonVbox.
     */
    private double adjustButtonFontSize() {
        double newFontSizeHeight = buttonVbox.getHeight() / 12.5;
        double newFontSizeWidth = buttonVbox.getWidth() / 9.5;
        return Math.min(newFontSizeHeight, newFontSizeWidth);
    }
}
