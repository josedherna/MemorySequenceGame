package org.jhproject.memorysequencegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains methods that will dynamically change the game's title
 * image size based on window size and switch to difficulty selection screen automatically.
 *
 * @author Jose Hernandez
 */
public class TitleController {
    @FXML
    private ImageView logoImageView;
    @FXML
    private VBox titleParentVbox;
    @FXML
    private VBox imageVbox;

    private final URL IMAGE_FILE = getClass().getResource("images/RTT_Logo.png");
    private boolean boundToWidth = true;

    /**
     * Responds to changes in the width of imageVbox.
     * Binds logoImageView fit height to imageVbox width when width is greater than or equal to 512
     * to make game logo grow and shrink to fit the available space in imageVbox.
     */
    private final ChangeListener<Number> imgVboxWidth = (_, _, newValue) -> {
        if (newValue.doubleValue() < 512 && boundToWidth) {
            logoImageView.fitWidthProperty().unbind();
            logoImageView.fitHeightProperty().unbind();
            logoImageView.setFitWidth(320.0);
            logoImageView.setFitHeight(384.62);
            boundToWidth = false;
        } else if (newValue.doubleValue() >= 512 && !boundToWidth) {
            //Dividing the current width by 1.6 ensures image fits in the scene, the smaller the number, the bigger the image relative to width.
            logoImageView.fitWidthProperty().bind(imageVbox.widthProperty().divide(1.6));
            //Dividing the current height by 1.3 ensures image fits in the scene, the smaller the number, the bigger the image relative to width.
            logoImageView.fitHeightProperty().bind(imageVbox.heightProperty().divide(1.3));
            boundToWidth = true;
        }
    };

    /**
     * Adds game logo to title screen and starts a timeline that switches title screen to difficulty selection screen
     * after one second.
     */
    @FXML
    private void initialize() {
        if (IMAGE_FILE != null) {
            logoImageView.setVisible(false);
            Image gameLogo = new Image(IMAGE_FILE.toExternalForm());
            logoImageView.setImage(gameLogo);
            Timeline switchToDifficultyScreen = getTimeline();
            switchToDifficultyScreen.play();
        }
    }

    /**
     * Creates a timeline that will be played in order to apply bindings to logo image and switch from the title screen to
     * the difficulty selection screen.
     *
     * @return Timeline that switches the current scene from title screen to difficulty selection screen.
     */
    private Timeline getTimeline() {
        Timeline initializeProcess = new Timeline();

        initializeProcess.getKeyFrames().add(new KeyFrame(Duration.millis(8), _ -> {
            if (imageVbox.getWidth() < 512) {
                logoImageView.setFitWidth(320.0);
                logoImageView.setFitHeight(384.62);
            }
            else {
                logoImageView.fitWidthProperty().bind(imageVbox.widthProperty().divide(1.6));
                logoImageView.fitHeightProperty().bind(imageVbox.heightProperty().divide(1.3));
            }
            logoImageView.setVisible(true);
            imageVbox.widthProperty().addListener(imgVboxWidth);
        }));
        initializeProcess.getKeyFrames().add(new KeyFrame(Duration.seconds(1.2), _ -> {
            FXMLLoader difficultyPage = new FXMLLoader(getClass().getResource("selection-view.fxml"));
            Parent difficultyParent = null;

            try {
                difficultyParent = difficultyPage.load();
            } catch (IOException e) {
                System.out.println("Error loading Difficulty Page");
            }

            //Changes scene to difficulty selection screen.
            Scene currentScene = titleParentVbox.getScene();
            currentScene.setRoot(difficultyParent);
            imageVbox.widthProperty().removeListener(imgVboxWidth);
        }));

        return initializeProcess;
    }
}
