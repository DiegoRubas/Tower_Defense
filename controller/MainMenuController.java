package controller;

import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import model.*;
import view.*;

public class MainMenuController {

    private static MainMenuController instance = null;
    private final MainMenuView mainMenuView;

    /**
     * Creates the main menu controller linking to the main menu display
     */
    private MainMenuController() {
        this.mainMenuView = new MainMenuView();
        this.mainMenuView.addListeners(new PlayListener(), new EditListener(), new QuitListener());
    }

    public static MainMenuController getInstance() {
        if (MainMenuController.instance == null) {
            MainMenuController.instance = new MainMenuController();
        }
        return MainMenuController.instance;
    }

    /**
     * Creates the listener and handler for the play button in the main menu display
     */
    public class PlayListener implements EventHandler<MouseEvent> {
        private ToggleGroup mapGroup, difficultyGroup;
        public void addMapAndDifficulty(ToggleGroup mapGroup, ToggleGroup difficultyGroup) {
            this.mapGroup = mapGroup;
            this.difficultyGroup = difficultyGroup;
        }
        @Override
        public void handle(MouseEvent event) {
            String fileURL;
            int difficulty;
            String selectedMap = ((RadioButton)mapGroup.getSelectedToggle()).getText();
            String selectedDifficulty = ((RadioButton)difficultyGroup.getSelectedToggle()).getText();
            switch (selectedMap) {
                case "Plains": fileURL = "src/maps/plains.tmp"; break;
                case "Water World": fileURL = "src/maps/water_world.tmp"; break;
                case "Savanna": fileURL = "src/maps/savanna.tmp"; break;
                case "Shore Line": fileURL = "src/maps/shore_line.tmp"; break;
                default: fileURL = "src/maps/custom_map.tmp"; break;
            }
            switch (selectedDifficulty) {
                case "Normal": difficulty = 1; break;
                case "Hard": difficulty = 2; break;
                default: difficulty = 3; break;
            }
            mainMenuView.getStage().hide();
            openNewGame(fileURL, difficulty);
        }
    }

    /**
     * Creates the listener and handler for the edit button in the main menu display
     */
    private class EditListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            mainMenuView.getStage().hide();
            openNewEdit();
        }
    }

    private class QuitListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            exitMainMenu();
        }
    }

    /**
     * Creates a new game by instantiating its display, its model and linking them with its own controller
     */
    private void openNewGame(String fileURL, int difficulty) {
        GameView gameView = new GameView();
        Game game = Game.getGameInstance();
        game.startGame(fileURL, difficulty);
        new GameController(gameView);
    }

    /**
     * Creates a new game by instantiating its display, its model and linking them with its own controller
     */
    private void openNewEdit() {
        EditView editView = new EditView();
        Editor editor = new Editor();
        new EditController(editView, editor);
    }

    private void exitMainMenu() {
        System.exit(0);
    }

    public MainMenuView getMainMenuView() { return mainMenuView; }
}
