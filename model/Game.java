package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This is the main class for the operation of the game. It instantiates the 3 overarching classes--the spawner, the
 * grid and the player--each responsible for one major aspect of the game--the enemies, the tiles and the turrets (and
 * projectiles).
 */
public class Game {

    private static Game gameInstance = null;

    private Grid grid;
    private Spawner spawner;
    private Player player;
    private int difficulty;

    private Game() {}

    /**
     * Returns the game instance, instantiating it first if it hasn't already been
     */
    public static Game getGameInstance() {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    /**
     * Creates the primary game elements--the grid, the player and the spawner
     */
    public void startGame(String fileURL, int difficulty) {
        this.grid = deserializeMap(fileURL);
        this.player = new Player(20, 45);
        this.spawner = new Spawner();
        this.difficulty = difficulty;
    }

    /**
     * Clears the game instance to allow for playing other levels
     */
    public static void clearInstance() {
        gameInstance.getSpawner().setWaveNumber(30);
        for (Enemy enemy: gameInstance.getSpawner().getEnemies()) {
            enemy.getToEnd();
        }
        for (Turret turret: gameInstance.getPlayer().getTurrets()) {
            gameInstance.getPlayer().sellTurret(turret);
        }
        gameInstance = null;
    }

    /**
     * Loads a grid by deserializing a tmp file
     */
    private Grid deserializeMap(String fileURL) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        Grid grid;
        try {
            fileInputStream = new FileInputStream(fileURL);
            objectInputStream = new ObjectInputStream(fileInputStream);
            grid = (Grid)objectInputStream.readObject();
            grid.generatePaths();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            grid = new Grid();
        }
        return grid;
    }

    public Spawner getSpawner() { return spawner; }
    public Grid getGrid() { return grid; }
    public Player getPlayer() { return player; }
    public int getDifficulty() { return difficulty; }
}