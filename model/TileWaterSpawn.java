package model;

/**
 * A subclass of tileSpawn accessible to land and flying enemies
 */
public class TileWaterSpawn extends TileSpawn implements TileTypeWater {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the water spawn tile object
     */
    public TileWaterSpawn(int x, int y) {
        super(TileStats.WATER_SPAWN, x, y);
    }

}
