package org.jhproject.memorysequencegame;

import java.util.*;

public class EasyGame extends Game {
    private final Tiles[][] GAME_TILES;
    private final ArrayList<int[]> SELECTION_PATTERN;
    private final ArrayList<String> SELECTION_CATEGORY;

    EasyGame(Tiles[][] currentBoard) {
        GAME_TILES = currentBoard;
        SELECTION_PATTERN = new ArrayList<>();
        SELECTION_CATEGORY = new ArrayList<>();
        setDisabledTiles(true);
        setSelectionAmountIndex(2);
    }

    /**
     * Runs the game.
     */
    @Override
    public void run() {
        this.setRunning(true);
        this.setSelectionTurn(true);
        this.setTime(10.0);
        while (this.isRunning()) {
            tileSelectionTurn();
            playerTurn();
            increaseLevel();
        }
    }

    @Override
    protected void testTileTurn() {
        tileSelectionTurn();
        printSelectionPattern();
        SELECTION_PATTERN.clear();
        SELECTION_CATEGORY.clear();
    }

    /**
     * Gets a 2-dimensional array
     *
     * @return A 2-dimensional array containing the game tiles.
     */
    @Override
    protected Tiles[][] getGameTiles() {
        return GAME_TILES;
    }

    /**
     * Gets the generated tile sequence array.
     *
     * @return The tile sequence that was created.
     */
    @Override
    protected ArrayList<int[]> getTileSelection() {
        return SELECTION_PATTERN;
    }

    /**
     * Generates the available patterns for the game.
     *
     * @return A map that contains patterns organized by category
     */
    @Override
    protected Map<String, List<List<int[]>>> generatePatterns() {
        Map<String, List<List<int[]>>> patternMap = new HashMap<>();

        patternMap.put("Horizontal", new ArrayList<>());
        patternMap.put("Vertical", new ArrayList<>());
        patternMap.put("Diagonal", new ArrayList<>());
        patternMap.put("LBottomTriangle" , new ArrayList<>());
        patternMap.put("LTopTriangle" , new ArrayList<>());
        patternMap.put("RBottomTriangle" , new ArrayList<>());
        patternMap.put("RTopTriangle" , new ArrayList<>());

        //Horizontal line patterns
        for (int row = 0; row < 3; row++) {
            patternMap.get("Horizontal").add(Arrays.asList(
                    new int[] {row, 0}, new int[] {row, 1}, new int[] {row, 2}
            ));
        }

        //Vertical line patterns
        for (int column = 0; column < 3; column++) {
            patternMap.get("Vertical").add(Arrays.asList(
                    new int[] {0, column}, new int[] {1, column}, new int[] {2, column}
            ));
        }

        //Diagonal patterns
        patternMap.get("Diagonal").add(Arrays.asList(
                new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 2}
        ));
        patternMap.get("Diagonal").add(Arrays.asList(
                new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 0}
        ));

        //Left bottom base triangle patterns
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++) {
                patternMap.get("LBottomTriangle").add(Arrays.asList(
                        new int[] {row, column}, new int[] {row + 1, column}, new int[] {row + 1, column + 1}
                ));
            }
        }

        //Left top base triangle patterns
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++) {
                patternMap.get("LBottomTriangle").add(Arrays.asList(
                        new int[] {row, column}, new int[] {row, column + 1}, new int[] {row + 1, column}
                ));
            }
        }

        //Right bottom base triangle pattern
        for (int row = 0; row < 2; row++) {
            for (int column = 2; column > 0; column--) {
                patternMap.get("RBottomTriangle").add(Arrays.asList(
                        new int[] {row, column}, new int[] {row + 1, column}, new int[] {row + 1, column - 1}
                ));
            }
        }

        //Right top base triangle pattern
        for (int row = 0; row < 2; row++) {
            for (int column = 2; column > 0; column--) {
                patternMap.get("RTopTriangle").add(Arrays.asList(
                        new int[] {row, column}, new int[] {row, column - 1}, new int[] {row + 1, column}
                ));
            }
        }

        return patternMap;
    }

    /**
     * Converts an array representation of coordinates into a String representation to be the key in a hashmap.
     *
     * @param row The row number of the tile.
     * @param col The column number of the tile.
     * @return A string representation of the coordinates of the tiles.
     */
    private String tileKeyConversion(int row, int col) {
        return row + "," + col;
    }

    /**
     *
     * @return A hashmap containing the remaining tiles that haven't been selected.
     */
    private Map<String, int[]> generateRemainingTiles() {
        Map<String, int[]> remainingTiles = new HashMap<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                remainingTiles.put(tileKeyConversion(row, col), new int[] {row, col});
            }
        }
        return remainingTiles;
    }

    /**
     * Checks if two patterns overlap each other. Patterns that overlap are not selected by the game.
     *
     * @param pattern A list of generated tile selection patterns.
     * @param usedTiles A set of tiles that have already been selected.
     * @return Whether the tiles selected have been used and overlap with other patterns.
     */
    private boolean overlapsUsed(List<int[]> pattern, Set<String> usedTiles) {
        for (int[] tile : pattern) {
            if (usedTiles.contains(tileKeyConversion(tile[0], tile[1]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Selects the tiles that the player has to memorize.
     */
    @Override
    protected void tileSelectionTurn() {
        if (this.isRunning() && !this.isPaused() && this.isSelectionTurn()) {
            this.setTimeProgress(1.0);

            Map<String, List<List<int[]>>> patternsByCategory = generatePatterns();
            Set<String> usedTiles = new HashSet<>();
            Map<String, int[]> remainingTiles = generateRemainingTiles();
            Random random = new Random();

            while (!patternsByCategory.isEmpty()) {
                List<String> categories = new ArrayList<>(patternsByCategory.keySet());
                String category = categories.get(random.nextInt(categories.size()));

                List<List<int[]>> availableTiles = new ArrayList<>();
                for (List<int[]> pattern : patternsByCategory.get(category)) {
                    if (!overlapsUsed(pattern, usedTiles)) {
                        availableTiles.add(pattern);
                    }
                }

                if (availableTiles.isEmpty()) {
                    //Remove category if no valid patterns are available, i.e. it overlaps.
                    patternsByCategory.remove(category);
                    continue;
                }

                List<int[]> chosen = availableTiles.get(random.nextInt(availableTiles.size()));

                for (int[] tile : chosen) {
                    usedTiles.add(tileKeyConversion(tile[0], tile[1]));
                    SELECTION_PATTERN.add(tile);
                    remainingTiles.remove(tileKeyConversion(tile[0], tile[1]));
                }

                SELECTION_CATEGORY.add(category);

                for (String cat : new ArrayList<>(patternsByCategory.keySet())) {
                    patternsByCategory.get(cat).removeIf(p -> overlapsUsed(p, usedTiles));
                    if (patternsByCategory.get(cat).isEmpty()) {
                        patternsByCategory.remove(cat);
                    }
                }
            }

            if (!remainingTiles.isEmpty()) {
                while(!remainingTiles.isEmpty()) {
                    Set<String> keySets = remainingTiles.keySet();
                    String[] keysArray = keySets.toArray(new String[0]);
                    int randomIndex = random.nextInt(remainingTiles.size());
                    String key = keysArray[randomIndex];
                    SELECTION_PATTERN.add(remainingTiles.get(key));
                    remainingTiles.remove(key);
                }
                SELECTION_CATEGORY.add("Random");
            }

            setReadTileSelection(true);
            setSelectionTurn(false);
        }
    }

    /**
     *
     */
    @Override
    protected void playerTurn() {
        if (this.isRunning() && !this.isPaused() && this.isPlayerTurn()) {
            try {
                if (this.getTime() == 0.0) {
                    this.setTimeProgress(this.getTime() / 10.0);
                    gameOver();
                }
                else {
                    this.setTimeProgress(this.getTime() / 10.0);
                    System.out.println(this.getTime());
                    setTime(this.getTime() - 1.0);
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e) {
                System.out.println("Error in player turn.");
            }
        }
    }

    /**
     * Checks if the player selected the correct tile in the sequence.
     */
    @Override
    protected void validatePlayerSelection(int col, int row) {
        int[] currentTile = SELECTION_PATTERN.get(this.getValidationIndex());
        if (currentTile[0] != col && currentTile[1] != row) {
            gameOver();
            return;
        }

        if (this.getValidationIndex() == getSelectionAmountIndex()) {
            setSuccessfulPlayerSelection(true);
            setPlayerTurn(false);
        }

        this.setValidationIndex(this.getValidationIndex() + 1);

        this.setScore(this.getScore() + 50);
    }

    /**
     * Sets up the next round for the game.
     */
    protected void increaseLevel() {
        if (this.isRunning() && !this.isPaused() && this.isSuccessfulPlayerSelection()) {
            setDisabledTiles(true);
            setTime(10.0);
            setTimeProgress(1.0);

            SELECTION_PATTERN.clear();
            SELECTION_CATEGORY.clear();

            setValidationIndex(0);
            setSuccessfulPlayerSelection(false);
            setSelectionTurn(true);

            setLevel(getLevel() + 1);
            if (getLevel() > 3 && getLevel() <= 6) {
                setSelectionAmountIndex(5);
            }
            else if (getLevel() > 6) {
                setSelectionAmountIndex(8);
            }
        }
    }

    /**
     * Handles the game ending when the player fails.
     */
    @Override
    protected void gameOver() {
        setPlayerTurn(false);
        setSelectionTurn(false);
        setDisabledTiles(true);
        System.out.println("Game Over");
    }

    /**
     * Increases the current game's score.
     */
    @Override
    protected void incrementScore() {
        this.setScore(this.getScore() + 50.0);
    }

    public void printSelectionPattern() {
        System.out.println(SELECTION_PATTERN.size());
        for  (int[] pattern : SELECTION_PATTERN) {
            System.out.print(Arrays.toString(pattern) + ", ");
        }
        System.out.println();
        for (String category : SELECTION_CATEGORY) {
            System.out.print(category + ", ");
        }
        System.out.println();
    }
}
