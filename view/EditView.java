package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

import java.util.ArrayList;

public class EditView {

    private GridPane mapPane;
    private Stage editStage;
    private Button saveButton;
    private ArrayList<Button> buttons;

    public EditView() {
        setUpStage();
    }

    private void setUpStage() {
        mapPane = new GridPane();
        VBox tileSelect = createTileSelect();
        BorderPane editPane = new BorderPane();
        editPane.setBackground(new Background(new BackgroundImage(new Image("assets/interface/grey_grass_tile.png",
                64, 64, false, false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, null)));
        editPane.setCenter(mapPane);
        editPane.setRight(tileSelect);
        editStage = new Stage();
        editStage.setTitle("Game");
        editStage.setScene(new Scene(editPane, (18 + 4) * 64, 12 * 64));
        editStage.show();
    }

    private VBox createTileSelect() {
        VBox tileSelect = new VBox();
        tileSelect.setAlignment(Pos.CENTER);
        tileSelect.setPrefWidth(64 * 4);
        buttons = new ArrayList<>();
        TileButton grassButton = new TileButton(TileStats.GRASS);
        TileButton landSpawnButton = new TileButton(TileStats.LAND_SPAWN);
        TileButton landButton = new TileButton(TileStats.LAND);
        TileButton landEndButton = new TileButton(TileStats.LAND_END);
        TileButton waterSpawnButton = new TileButton(TileStats.WATER_SPAWN);
        TileButton waterButton = new TileButton(TileStats.WATER);
        TileButton waterEndButton = new TileButton(TileStats.WATER_END);
        TileButton shallowWaterButton = new TileButton(TileStats.SHALLOW_WATER);
        TileButton deepWaterButton = new TileButton(TileStats.DEEP_WATER);
        saveButton = new Button("SAVE");
        tileSelect.getChildren().addAll(grassButton, landSpawnButton, landButton, landEndButton, waterSpawnButton,
                waterButton, waterEndButton, shallowWaterButton, deepWaterButton, saveButton);
        return tileSelect;
    }

    /**
     * Adds a listener to the play button
     */
    public void addListeners(EventHandler<MouseEvent> tileButtonListener, EventHandler<MouseEvent> saveButtonListener,
                             EventHandler<MouseEvent> mapClickListener, EventHandler<MouseEvent> mapDragListener,
                             EventHandler<WindowEvent> closeListener) {
        for (Button b : buttons) {
            b.setOnMouseClicked(tileButtonListener);
        }
        saveButton.setOnMouseClicked(saveButtonListener);
        mapPane.setOnMouseClicked(mapClickListener);
        mapPane.setOnMouseDragged(mapDragListener);
        editStage.setOnCloseRequest(closeListener);
    }

    public void createMap(Grid grid) {
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 18; i++) {
                mapPane.add(grid.getMap()[j][i].getImageView(), i, j);
            }
        }
    }

    public GridPane getMapPane() { return mapPane; }

    public class TileButton extends Button {
        private final TileStats tileStats;
        public TileButton(TileStats tileStats) {
            super(tileStats.name());
            this.tileStats = tileStats;
            setGraphic(new ImageView(new Image(tileStats.getImageURL(), 64, 64, false, false)));
            buttons.add(this);
        }
        public TileStats getTileStats() { return tileStats; }
    }
}
