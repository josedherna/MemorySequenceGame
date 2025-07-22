package org.jhproject.memorysequencegame;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class contains methods that will manage the display of the game's instruction.
 *
 * @author Jose Hernandez
 */
public class GameInstructions {
    private final URL EASY_INSTRUCTIONS_PATH = getClass().getResource("infoscript/easy_info.json");
    private final URL MEDIUM_INSTRUCTIONS_PATH = getClass().getResource("infoscript/medium_info.json");
    private final URL HARD_INSTRUCTIONS_PATH = getClass().getResource("infoscript/hard_info.json");
    private final URL INSTRUCTIONS_FONT = getClass().getResource("fonts/Roboto-Regular.ttf");

    /**
     * Reads the JSON file containing the easy difficulty information script.
     *
     * @param language A player's selected language for the game.
     * @return An array containing Text nodes to be displayed in the easy difficulty information screen.
     */
    public Text[] getEasyInstructions(String language) {
        if (EASY_INSTRUCTIONS_PATH != null) {
            try {
                String easyJSONContent = Files.readString(Paths.get(EASY_INSTRUCTIONS_PATH.toURI()));
                JSONObject easyJSONObject = new JSONObject(easyJSONContent);
                JSONObject languageObject = easyJSONObject.getJSONObject(language);
                JSONArray easyInstructionsArray = languageObject.getJSONArray("lines");

                Text[] easyInstructions = new Text[easyInstructionsArray.length()];
                for (int i = 0; i < easyInstructionsArray.length(); i++) {
                    Text easyInstructionLine = new Text(easyInstructionsArray.getString(i));
                    if (INSTRUCTIONS_FONT != null) {
                        easyInstructionLine.setFont(Font.loadFont(INSTRUCTIONS_FONT.toString(), 16));
                    }
                    easyInstructions[i] = easyInstructionLine;
                }

                return easyInstructions;
            } catch (IOException e) {
                System.out.println("Error finding easy instructions file");
            } catch (URISyntaxException e) {
                System.out.println("Error reading easy instructions file");
            }
        }
        return new Text[0];
    }

    /**
     * Reads the JSON file containing the medium difficulty information script.
     *
     * @param language A player's selected language for the game.
     * @return An array containing Text nodes to be displayed in the medium difficulty information screen.
     */
    public Text[] getMediumInstructions(String language) {
        if (MEDIUM_INSTRUCTIONS_PATH != null) {
            try {
                String mediumJSONContent = Files.readString(Paths.get(MEDIUM_INSTRUCTIONS_PATH.toURI()));
                JSONObject mediumJSONObject = new JSONObject(mediumJSONContent);
                JSONObject languageObject = mediumJSONObject.getJSONObject(language);
                JSONArray mediumInstructionsArray = languageObject.getJSONArray("lines");

                Text[] mediumInstructions = new Text[mediumInstructionsArray.length()];
                for (int i = 0; i < mediumInstructionsArray.length(); i++) {
                    Text mediumInstructionLine = new Text(mediumInstructionsArray.getString(i));
                    if (INSTRUCTIONS_FONT != null) {
                        mediumInstructionLine.setFont(Font.loadFont(INSTRUCTIONS_FONT.toString(), 16));
                    }
                   mediumInstructions[i] = mediumInstructionLine;
                }
                return mediumInstructions;
            } catch (IOException e) {
                System.out.println("Error finding easy instructions file");
            } catch (URISyntaxException e) {
                System.out.println("Error reading easy instructions file");
            }
        }
        return new Text[0];
    }

    /**
     * Reads the JSON file containing the hard difficulty information script.
     *
     * @param language A player's selected language for the game.
     * @return An array containing Text nodes to be displayed in the hard difficulty information screen.
     */
    public Text[] getHardInstructions(String language) {
        if (HARD_INSTRUCTIONS_PATH != null) {
            try {
                String hardJSONContent = Files.readString(Paths.get(HARD_INSTRUCTIONS_PATH.toURI()));
                JSONObject hardJSONObject = new JSONObject(hardJSONContent);
                JSONObject languageObject = hardJSONObject.getJSONObject(language);
                JSONArray hardInstructionsArray = languageObject.getJSONArray("lines");

                Text[] hardInstructions = new Text[hardInstructionsArray.length()];
                for (int i = 0; i < hardInstructionsArray.length(); i++) {
                    Text hardInstructionLine = new Text(hardInstructionsArray.getString(i));
                    if (INSTRUCTIONS_FONT != null) {
                        hardInstructionLine.setFont(Font.loadFont(INSTRUCTIONS_FONT.toString(), 16));
                    }
                    hardInstructions[i] = hardInstructionLine;
                }
                return hardInstructions;
            } catch (IOException e) {
            System.out.println("Error finding easy instructions file");
            } catch (URISyntaxException e) {
            System.out.println("Error reading easy instructions file");
            }
        }
        return new Text[0];
    }
}
