package model;

import java.util.Random;

/**
 * This enemy can only travel on land
 */
public class EnemyLand extends Enemy {

    public EnemyLand(EnemyStats enemyStats) {
        super(enemyStats);
    }

    /**
     * Returns a random start tile among the land start tiles
     */
    @Override
    public TileSpawn randomStartTile() {
        int random = new Random().nextInt(getGrid().getLandSpawnTiles().size());
        return getGrid().getLandSpawnTiles().get(random);
    }
}
