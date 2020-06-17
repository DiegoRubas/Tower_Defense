package model;

/**
 * A subclass of tile only accessible to land and flying enemies
 */
public class TileLand extends Tile implements TileTypeLand {

    private static final long serialVersionUID = 1L;

    /**
     * Initializes the land tile object
     */
    public TileLand(int x, int y) {
        super(TileStats.LAND, x, y);
    }

}
