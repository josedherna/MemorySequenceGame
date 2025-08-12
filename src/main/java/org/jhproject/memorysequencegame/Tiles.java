package org.jhproject.memorysequencegame;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * This class is a custom button that players will interact with in the game
 * to memorize and select the sequence of tiles shown by the game.
 *
 * @author Jose Hernandez
 */
public class Tiles extends Button {
    private final int[] COORDINATES;

    Tiles(int[] values, String styleClass) {
        COORDINATES = values;
        this.getStyleClass().add(styleClass);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
    }

    /**
     * Gets the coordinates of the tile in the grid pane.
     *
     * @return An array containing the coordinate of the tiles in the grid pane.
     */
    protected int[] getCoordinates() {
        return COORDINATES;
    }
}
