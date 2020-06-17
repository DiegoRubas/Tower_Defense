package model;

/**
 * This class contains all the tiles crucial to the operation of the grid and thus the game
 */
public class Tile extends Entity {

    private static final long serialVersionUID = 1L;
    private boolean occupied;
    private String name;

    public Tile(TileStats tileStats, int x, int y) {
        super(x, y, tileStats.getImageURL());
        this.name = tileStats.name();
        this.occupied = !tileStats.isBuildable();
    }

    public Tile(TileStats tileStats) {
        super(tileStats.getImageURL());
    }

    public boolean isOccupied() { return occupied; }

    public void setOccupied(boolean b) { occupied = b; }

    public String getName() { return name; }
}
