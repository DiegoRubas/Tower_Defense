package model;

/**
 * A subclass of tile accessible to water and flying enemies marking the end of a path
 */
public class TileWaterEnd extends Tile implements TileTypeWater, TileTypeEnd {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the water end tile object
     */
    public TileWaterEnd(int x, int y) {
        super(TileStats.WATER_END, x, y);
    }

}