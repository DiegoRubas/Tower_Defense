package model;

import java.util.Random;

/**
 * This enemy can only travel on water and is fire resistant
 */
public class EnemyWater extends Enemy {

    public EnemyWater(EnemyStats enemyStats) {
        super(enemyStats);
    }

    /**
     * Returns a random start tile among the water start tiles
     */
    @Override
    public TileSpawn randomStartTile() {
        int random = new Random().nextInt(getGrid().getWaterSpawnTiles().size());
        return getGrid().getWaterSpawnTiles().get(random);
    }

    /**
     * Takes 50% less damage from burn damage and only takes it for 2 seconds
     */
    @Override
    public void takeBurnDamage() {
        if (isBurning()) {
            if (getBurnTickTime() > 0.25f) {
                takeDamage(Math.max(1,(int)(getBaseHealth() * 0.025f)));
                setBurnTickTime(0);
            }
            if (getBurnTickTime() >= 2) {
                setNormalStatus();
            }
            setBurnTime(getBurnTime() + 0.017f);
            setBurnTickTime(getBurnTickTime() + 0.017f);
        }
    }
}
