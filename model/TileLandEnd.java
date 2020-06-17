package model;

/**
 * A subclass of tile accessible to land and flying enemies marking the end of a path
 */
public class TileLandEnd extends Tile implements TileTypeLand, TileTypeEnd {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the land end tile object
     */
    public TileLandEnd(int x, int y) {
        super(TileStats.LAND_END, x, y);
    }
}
