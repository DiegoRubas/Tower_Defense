package model;

/**
 * A subclass of tile accessible to water and flying enemies
 */
public class TileWater extends Tile implements TileTypeWater {

    private static final long serialVersionUID = 1L;

    public TileWater(int x, int y) {
        super(TileStats.WATER, x, y);
    }

}
