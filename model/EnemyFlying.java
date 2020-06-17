package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EnemyFlying extends Enemy {

    /**
     * Initializes the flying enemy object
     */
    public EnemyFlying(EnemyStats enemyStats) {
        super(enemyStats);
        selectRandomPath();
    }

    /**
     * Picks a random tile among the valid start tiles
     */
    @Override
    public TileSpawn randomStartTile() {
        return getGrid().getSpawnTiles().get(new Random().nextInt(getGrid().getSpawnTiles().size()));
    }

    /**
     * Returns the end tile corresponding to its starting tile, i.e. if the enemy spawns on a water tile, it will head
     * towards a water end tile
     */
    private Tile randomEndTile() {
        return getGrid().getEndTiles().get(new Random().nextInt(getGrid().getEndTiles().size()));
    }

    /**
     * Picks a random start tile and selects a random path from that tile
     */
    private void selectRandomPath() {
        setPath(new ArrayList<>(Arrays.asList(randomStartTile(), randomEndTile())));
        setX(getStartTile().getX());
        setY(getStartTile().getY());
        setPathIndex(0);
        calculateDirection();
    }

    /**
     * Sets the enemy's direction to pointing from its start tile to its end tile
     */
    public void calculateDirection() {
        float xDistance = getEndTile().getX() - getStartTile().getX();
        float yDistance = getEndTile().getY() - getStartTile().getY();
        setAngle(Math.atan2(yDistance, xDistance));
        setDirection(new double[] {Math.cos(getAngle()), Math.sin(getAngle())});
        calculateAngle();
    }

    public Tile getStartTile() { return getPath().get(0); }
    public Tile getEndTile() { return getPath().get(1); }
}
