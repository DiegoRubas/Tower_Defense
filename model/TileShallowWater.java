package model;

/**
 * A subclass of tile accessible to all enemy classes
 */
public class TileShallowWater extends Tile implements TileTypeLand, TileTypeWater {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the bridge tile object
     */
    public TileShallowWater(int x, int y) {
        super(TileStats.SHALLOW_WATER, x, y);
    }

}