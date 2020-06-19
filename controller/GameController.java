package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


import model.*;
import view.GameView;
import view.GameView.*;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

    private final GameView gameView;
    private final Grid grid;
    private final Player player;
    private final Spawner spawner;
    private final CopyOnWriteArrayList<Turret> turrets;
    private final CopyOnWriteArrayList<Enemy> enemies;
    private final MainMenuController mainMenuController;
    private Timeline viewLoop;
    private String currentAction = "";
    private TurretStats heldTurretStats;

    /**
     * Initializes the game controller object and starts the main display loop
     * @param gameView the display of the game
     */
    public GameController(GameView gameView) {

        this.gameView = gameView;
        Game game = Game.getGameInstance();
        grid = game.getGrid();
        player = game.getPlayer();
        spawner = game.getSpawner();
        turrets = player.getTurrets();
        enemies = spawner.getEnemies();
        gameView.createMap(grid);
        this.mainMenuController = MainMenuController.getInstance();

        gameView.setupListeners(new MoveListener(), new ClickListener(), new CloseListener(),
                new WaveButtonClickListener(), new ActionButtonClickListener(), new TowerButtonClickListener(),
                new ButtonHoverListener());
        startViewRefreshLoop(gameView);

    }

    private void startViewRefreshLoop(GameView gameView) {
        viewLoop = new Timeline(new KeyFrame(Duration.millis(17), actionEvent -> {
            if(player.isAlive()) {
                gameView.updateEnemies(enemies);
                gameView.updateTurretsAndShots(turrets);
                gameView.updateUI(player, spawner);
            } else {
                endGame(gameView);
            }
        }));
        viewLoop.setCycleCount(Animation.INDEFINITE);
        viewLoop.play();
    }

    private void endGame(GameView gameView) {
        viewLoop.stop();
        gameView.updateUI(player, spawner);
        gameView.displayLoss(player);
        gameView.disableListeners();
    }

    private class ClickListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            int x = (int)event.getX()/64;
            int y = (int)event.getY()/64;
            if (heldTurretStats != null) {
                addTower(x, y);
                heldTurretStats = null;
            } else if (!currentAction.equals("")) {
                if (currentAction.equals("upgrade")) {
                    upgradeTower(x, y);
                } else if (currentAction.equals("sell")) {
                    sellTower(x, y);
                }
                currentAction = "";
            }
            gameView.changeCrosshair(currentAction);
            gameView.changeTurretDisplay(heldTurretStats);
        }
    }

    private class MoveListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            gameView.updateCrosshair(event);
            gameView.updateEntityInfo(event, enemies, turrets);
        }
    }

    private class CloseListener implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent windowEvent) {
            exitGame();
        }
    }

    private class WaveButtonClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (spawner.allWaveEnemiesSent()) {
                spawner.sendNextWave();
            }
        }
    }

    private class ActionButtonClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            currentAction = ((ActionButton)actionEvent.getSource()).getName();
            heldTurretStats = null;
            gameView.changeCrosshair(currentAction);
        }
    }

    private class TowerButtonClickListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (player.canPay(((TurretButton)event.getSource()).getType().getCost())) {
                heldTurretStats = ((TurretButton)event.getSource()).getType();
                currentAction = "";
                gameView.changeTurretDisplay(heldTurretStats);
            } else {
                gameView.setInfoText("You don't have sufficient funds");
            }
        }
    }

    private class ButtonHoverListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            updateInfoText(event);
        }
    }

    private void updateInfoText(MouseEvent event) {
        if (event.getSource() instanceof TurretButton) {
            TurretStats type = ((TurretButton)event.getSource()).getType();
            String name = ((TurretButton)event.getSource()).getType().getName();
            int cost = type.getCost();
            int damage = type.getDamage();
            float cooldown = type.getCooldown();
            int range = type.getRange();
            String description = type.getDescription();
            gameView.setInfoText(String.format("%s\nCost: %s\nDamage: %s\nCooldown: %s\nRange: %s\nEffect: %s",
                    name, cost, damage, cooldown, range, description));
        } else if (event.getSource() instanceof ActionButton) {
            if (((ActionButton)event.getSource()).getName().equals("upgrade")) {
                gameView.setInfoText("Upgrade\nCost: Tower cost\nLevel 1: Base stats\nLevel 2: 2x damage" +
                        "\nLevel 3: 2x firing rate and projectile speed");
            } else {
                gameView.setInfoText("Sell\nSell one of your towers for half its purchasing cost");
            }
        }
    }

    private void addTower(int x, int y) {
        Tile currentTile = grid.getTile(x, y);
        if (!currentTile.isOccupied()) {
            gameView.addTurret(player.buyAndPlaceTurret(heldTurretStats.getName(), currentTile), x, y);
        }
    }

    private void upgradeTower(int x, int y) {
        for (Turret t : turrets) {
            if (t.getX() == x && t.getY() == y) {
                if (t.getLevel() < 3 && player.canPay(t.getCost())) {
                    player.upgradeTurret(t);
                }
            }
        }
    }

    private void sellTower(int x, int y) {
        for (Turret t : turrets) {
            if (t.getX() == x && t.getY() == y) {
                player.sellTurret(t);
                gameView.removeTurret(t);
                for (Projectile s : t.getProjectiles()) {
                    gameView.removeShot(s);
                }
            }
        }
    }

    private void exitGame() {
        viewLoop.stop();
        Game.clearInstance();
        mainMenuController.getMainMenuView().getStage().show();
    }
}
