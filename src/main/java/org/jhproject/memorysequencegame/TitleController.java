package org.jhproject.memorysequencegame;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;

/**
 * This class contains methods that will dynamically change the game's title
 * image size based on window size and switch to difficulty selection screen.
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

    String imageFile = "/images/RTTLogo.png";
    InputStream imageStream = TitleController.class.getResourceAsStream(imageFile);
    Image gameLogo;

    /**
     * Adds game logo to title screen.
     */
    public void initializeTitleScreen() {
        try {
            gameLogo = new Image(imageStream);
            logoImageView.setImage(gameLogo);
        }
        catch (NullPointerException e) {
            System.out.println("Image not found");
        }
    }

}
