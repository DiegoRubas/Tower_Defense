package model;

import java.util.ArrayList;

/**
 * A subclass of tile marking the beginning of a path
 */
public abstract class TileSpawn extends Tile{

    private static final long serialVersionUID = 1L;

    private final ArrayList<ArrayList<Tile>> paths = new ArrayList<>();

    public TileSpawn(TileStats tileStats, int x, int y) {
        super(tileStats, x, y);
    }

    /**
     * Adds an array list of tiles--a path--to the object's array list of array lists of tiles--array list of paths
     */
    public void addPaths(ArrayList<ArrayList<Tile>> paths) {
        this.paths.addAll(paths);
    }

    /**
     * Returns all the possible paths starting from this tile ending at any valid end tile
     */
    public ArrayList<ArrayList<Tile>> getPaths() {
        return paths;
    }
}
