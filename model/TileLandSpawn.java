package model;

/**
 * A subclass of tileLand marking the beginning of a path
 */
public class TileLandSpawn extends TileSpawn implements TileTypeLand {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the land spawn tile object
     */
    public TileLandSpawn(int x, int y) {
        super(TileStats.LAND_SPAWN, x, y);
    }

}
