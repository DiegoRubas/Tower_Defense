package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView {

    private final GridPane mapPane;
    private BorderPane gamePane;
    private TilePane towerPanel;
    private Button waveButton;
    private final ImageView crosshair;
    private final ImageView currentTurret;
    private TextFlow infoPanel, scorePanel;
    private VBox wavePanel;
    private Text infoText;
    private Text scoreInfo;
    private Text waveText;
    private ArrayList<Button> buttons;
    private VBox uiPanels;

    private Stage gameStage;

    /**
     * Initializes the game view object, the main display
     */
    public GameView() {
        mapPane = new GridPane();
        crosshair = new ImageView(new Image("assets/interface/crosshair.png", 64, 64, false, false));
        currentTurret = new ImageView();
        setupStage();
    }

    /**
     * Sets up the panes, scene and stage of the main display
     */
    private void setupStage() {
        createPanels();
        createLayout();
        createStage();
    }

    private void createStage() {
        gameStage = new Stage();
        gameStage.setTitle("Game");
        gameStage.setScene(new Scene(gamePane, (18 + 4) * 64, 12 * 64));
    }

    private void createLayout() {
        gamePane = new BorderPane();
        gamePane.setCenter(mapPane);
        gamePane.setRight(uiPanels);
        gamePane.setBackground(new Background(new BackgroundImage(new Image("assets/interface/grey_grass_tile.png",
                64, 64, false, false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, null)));
    }

    private void createPanels() {

        createTowerPanel();
        createInfoPanel();
        createScorePanel();
        createWavePanel();

        uiPanels = new VBox();
        uiPanels.setPrefWidth(4 * 64);
        uiPanels.getChildren().addAll(towerPanel, infoPanel, scorePanel, wavePanel);
    }

    private void createWavePanel() {
        wavePanel = new VBox();
        wavePanel.setPrefHeight(3 * 64);
        wavePanel.setPrefWidth(4 * 64);
        wavePanel.setAlignment(Pos.TOP_CENTER);
        waveText = new Text("Lorem Ipsum");
        waveText.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        waveText.setTextAlignment(TextAlignment.CENTER);
        waveButton = new Button("Send Wave");
        waveButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        wavePanel.getChildren().addAll(waveText, waveButton);
    }

    private void createScorePanel() {
        scorePanel = new TextFlow();
        scorePanel.setPrefHeight(2 * 64);
        scoreInfo = new Text("Lorem Ipsum");
        scoreInfo.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        scorePanel.getChildren().add(scoreInfo);
        scorePanel.setTextAlignment(TextAlignment.CENTER);
    }

    private void createInfoPanel() {
        infoPanel = new TextFlow();
        infoPanel.setPrefHeight(4 * 64);
        infoText = new Text("Lorem Ipsum");
        infoText.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        infoPanel.getChildren().add(infoText);
        infoPanel.setTextAlignment(TextAlignment.CENTER);
    }


    /**
     * Creates the map grid pane and fills it with its corresponding tile images from the content of a text file
     */
    public void createMap(Grid grid) {
        Tile[][] gridMap = grid.getMap();
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 18; i++) {
                mapPane.add(gridMap[j][i].getImageView(), i, j);
            }
        }
        gameStage.show();
    }

    /**
     * Creates the tower select button panel
     */
    private void createTowerPanel() {
        towerPanel = new TilePane();
        towerPanel.setPrefWidth(4 * 64);
        towerPanel.setPrefHeight(3 * 64);
        towerPanel.setAlignment(Pos.CENTER);
        towerPanel.setPadding(new Insets(8, 8, 8, 8));
        buttons = new ArrayList<>();
        for (TurretStats t : TurretStats.values()) {
            new TurretButton(t);
        }
        new ActionButton("upgrade");
        new ActionButton("sell");
    }

    public void setupListeners(EventHandler<MouseEvent> moveListener,
                               EventHandler<MouseEvent> clickListener,
                               EventHandler<WindowEvent> closeListener,
                               EventHandler<ActionEvent> waveButtonClickListener,
                               EventHandler<ActionEvent> actionButtonClickListener,
                               EventHandler<ActionEvent> towerButtonClickListener,
                               EventHandler<MouseEvent> buttonHoverListener) {
        for (Button b : buttons) {
            if (b instanceof ActionButton) {
                b.setOnAction(actionButtonClickListener);
                b.setOnMouseEntered(buttonHoverListener);
            } else if (b instanceof TurretButton) {
                b.setOnAction(towerButtonClickListener);
                b.setOnMouseEntered(buttonHoverListener);
            }
        }
        waveButton.setOnAction(waveButtonClickListener);
        mapPane.setOnMouseClicked(clickListener);
        gamePane.setOnMouseMoved(moveListener);
        gameStage.setOnCloseRequest(closeListener);
    }

    public void updateCrosshair(MouseEvent event) {
        if (event.getX() < 18 * 64 && event.getY() < 12 * 64) {
            if (!mapPane.getChildren().contains(crosshair)) {
                mapPane.add(crosshair, (int)event.getX()/64, (int)event.getY()/64);
                mapPane.add(currentTurret, (int)event.getX()/64, (int)event.getY()/64);
            }
            GridPane.setColumnIndex(crosshair, (int)event.getX()/64);
            GridPane.setRowIndex(crosshair, (int)event.getY()/64);
            GridPane.setColumnIndex(currentTurret, (int)event.getX()/64);
            GridPane.setRowIndex(currentTurret, (int)event.getY()/64);
        }
    }

    public void updateEnemies(CopyOnWriteArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            if (e.isAlive() && !e.isAtEnd()) {
                if (!e.isDisplayed()) {
                    gamePane.getChildren().addAll(e.getImageView(), e.getHealthBackground(), e.getHealthForeground());
                    e.setDisplayed(true);
                }
                e.getImageView().setLayoutX(e.getX() * 64);
                e.getImageView().setLayoutY(e.getY() * 64);

                e.getHealthForeground().setLayoutX(e.getX() * 64 + e.getOffset());
                e.getHealthForeground().setLayoutY(e.getY() * 64);
                e.getHealthForeground().setFitWidth(e.getHealthForegroundLength());
                e.getHealthBackground().setLayoutX(e.getX() * 64 + e.getOffset());
                e.getHealthBackground().setLayoutY(e.getY() * 64);

                e.getImageView().setRotate(e.getAngle());
            } else {
                if (e.isDisplayed()) {
                    gamePane.getChildren().removeAll(e.getImageView(), e.getHealthBackground(), e.getHealthForeground());
                    e.setDisplayed(false);
                    enemies.remove(e);
                }
            }
        }
    }

    public void updateTurretsAndShots(CopyOnWriteArrayList<Turret> turrets) {
        for (Turret t : turrets) {
            t.getImageView().setRotate(t.getAngle());
            for (Projectile s : t.getProjectiles()) {
                if (!s.hasCollided()) {
                    if (!s.isDisplayed()) {
                        gamePane.getChildren().add(s.getImageView());
                        s.setDisplayed(true);
                    }
                    s.getImageView().setLayoutX(s.getX() * 64);
                    s.getImageView().setLayoutY(s.getY() * 64);

                    s.getImageView().setRotate(s.getAngle());
                } else {
                    if (s.isDisplayed()) {
                        gamePane.getChildren().remove(s.getImageView());
                        s.setDisplayed(false);
                    }
                }
            }
        }
    }

    public void updateUI(Player player, Spawner spawner) {
        scoreInfo.setText(String.format("HP: %s\nGold: %s\nScore: %s",
                player.getHp(), player.getGold(), player.getScore()));
        waveText.setText(String.format("Wave no. %s\nTime Remaining\n%s",
                spawner.getWaveNumber(), (int)Math.ceil(spawner.getTimeUntilNextWave())));
    }

    public void setInfoText(String text) { infoText.setText(text); }

    public void changeCrosshair(String action) {
        String imageURL = "assets/interface/crosshair.png";
        if (action != null) {
            switch (action) {
                case "upgrade":
                    imageURL = "assets/interface/upgrade_crosshair.png";
                    break;
                case "sell":
                    imageURL = "assets/interface/sell_crosshair.png";
                    break;
            }
        }
        crosshair.setImage(new Image(imageURL, 64, 64, false, false));
    }

    public void changeTurretDisplay(TurretStats type) {
        if (type != null) {
            currentTurret.setImage(new Image(type.getImageURL1(), 64, 64, false, false));
        } else {
            currentTurret.setImage(null);
        }
    }

    public void displayLoss(Player player) {
        Text lossText = new Text(0, 5 * 64, "You have lost\nFinal Score: " + player.getScore());
        lossText.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 64 * 1.5));
        lossText.setFill(Color.RED);
        lossText.setStroke(Color.WHITE);
        lossText.setTextAlignment(TextAlignment.CENTER);
        lossText.setWrappingWidth(18 * 64);

        gamePane.getChildren().add(lossText);
    }

    public void disableListeners() {
        for (Button b : buttons) {
            if (b instanceof ActionButton) {
                b.setOnAction(null);
                b.setOnMouseEntered(null);
            } else if (b instanceof TurretButton) {
                b.setOnAction(null);
                b.setOnMouseEntered(null);
            }
        }
        waveButton.setOnAction(null);
        mapPane.setOnMouseClicked(null);
        gamePane.setOnMouseMoved(null);
    }

    public void addTurret(Turret t, int x, int y) {
        mapPane.add(t.getBaseImageView(), x, y);
        mapPane.add(t.getImageView(), x, y);
    }

    public void removeTurret(Turret t) {
        mapPane.getChildren().removeAll(t.getImageView(), t.getBaseImageView());
    }

    public void removeShot(Projectile s) {
        gamePane.getChildren().remove(s.getImageView());
    }

    public void updateEntityInfo(MouseEvent event, CopyOnWriteArrayList<Enemy> enemies,
                                 CopyOnWriteArrayList<Turret> turrets) {
        for (Enemy e: enemies) {
            if (e.getX() < event.getX() / 64 && event.getX() / 64 < e.getX() + 1
                    && e.getY() < event.getY() / 64 && event.getY() / 64 < e.getY() + 1) {
                setInfoText(String.format("%s\nBase HP: %s\nBase speed: %s\nBounty: %s\nBuff: %s\n", e.getName(),
                        (int)e.getBaseHealth(), (int)e.getSpeed(), e.getBounty(), e.getDescription()));
                e.getImageView().setEffect(new DropShadow());
            }
            else {
                e.getImageView().setEffect(null);
            }
        }
        for (Turret t: turrets) {
            if (t.getX() == (int)(event.getX()/64) && t.getY() == (int)(event.getY()/64)) {
                setInfoText(String.format("%s\nLevel: %s\nDamage: %s\nCooldown: %s\nRange: %s",
                        t.getClass().getSimpleName(), t.getLevel(), t.getDamage(), t.getCooldown(), t.getRange()));
                t.getImageView().setEffect(new DropShadow());
            }
            else {
                t.getImageView().setEffect(null);
            }
        }
    }

    public class TurretButton extends Button {
        private final TurretStats turretStats;
        private TurretButton(TurretStats turretStats) {
            super("");
            this.turretStats = turretStats;
            setGraphic(new ImageView(new Image(turretStats.getImageURL1(), 64, 64, false ,false)));
            buttons.add(this);
            towerPanel.getChildren().add(this);
        }
        public TurretStats getType() { return turretStats; }
    }

    public class ActionButton extends Button {
        private final String name;
        private ActionButton(String name) {
            super("", new ImageView(new Image(name.equals("upgrade") ? "assets/interface/upgrade.png" :
                    "assets/interface/sell.png")));
            buttons.add(this);
            towerPanel.getChildren().add(this);
            this.name = name;
        }
        public String getName() { return name; }
    }
}
