package controller;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import model.*;
import view.*;

public class EditController {

    private final EditView editView;
    private final Editor editor;
    private final MainMenuController mainMenuController;
    private TileStats selectedTileStats;

    public EditController(EditView editView, Editor editor) {

        this.editView = editView;
        this.editor = editor;
        this.mainMenuController = MainMenuController.getInstance();
        this.editView.createMap(editor.getGrid());
        this.editView.addListeners(new TileButtonListener(), new SaveButtonListener(), new MapClickListener(),
                new MapDragListener(), new CloseListener());

    }

    private class TileButtonListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            selectedTileStats = ((EditView.TileButton)event.getSource()).getTileStats();
        }
    }

    private class SaveButtonListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            editor.saveMap();
        }
    }

    private class MapClickListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (selectedTileStats != null) {
                changeTile(event);
            }
        }
    }

    private class MapDragListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (selectedTileStats != null &&
                    0 <= event.getX() && event.getX() < 18 * 64 &&
                    0 <= event.getY() && event.getY() < 12 * 64) {
                changeTile(event);
            }
        }
    }

    private class CloseListener implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent windowEvent) {
            exitEdit();
        }
    }

    private void changeTile(MouseEvent event) {
        int x = (int)event.getX() / 64;
        int y = (int)event.getY() / 64;
        editView.getMapPane().getChildren().remove(editor.getGrid().getTile(x, y).getImageView());
        editor.getGrid().setTile(TileFactory.getInstance(selectedTileStats, x, y), x, y);
        editView.getMapPane().add(new ImageView(new Image(selectedTileStats.getImageURL(), 64, 64, false,
                false)), x, y);
    }

    private void exitEdit() {
        mainMenuController.getMainMenuView().getStage().show();
    }
}
