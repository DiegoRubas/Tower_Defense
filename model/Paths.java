package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains all possible paths from one spawn tile to all corresponding tiles
 */
public class Paths implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<ArrayList<Tile>> paths = new ArrayList<>();
    private final Tile startTile;
    private final Tile endTile;
    private final Grid grid;

    public Paths(Tile startTile, Tile endTile, Grid grid) {
        this.startTile = startTile;
        this.endTile = endTile;
        this.grid = grid;
        buildPath(new ArrayList<>());
    }

    /**
     * Either builds upon an array list of tiles by adding valid adjacent tiles and calling itself again or recognizing
     * that the list tile is an end tile and adding the array list to the possible paths
     */
    private void buildPath(ArrayList<Tile> path) {
        if (path.size() == 0) {
            path.add(startTile);
        }
        if (path.get(path.size()-1) == endTile) {
            ArrayList<Tile> correctPath = new ArrayList<>(path);
            paths.add(correctPath);
        } else {
            Tile currentTile = path.get(path.size()-1);
            for (Tile adjacentTile: adjacentTiles(currentTile)) {
                if (path.get(0) instanceof TileTypeLand) {
                    if (adjacentTile instanceof TileTypeLand && !path.contains(adjacentTile)) {
                        extendPath(path, adjacentTile);
                    }
                } else if (path.get(0) instanceof TileTypeWater) {
                    if (adjacentTile instanceof TileTypeWater && !path.contains(adjacentTile)) {
                        extendPath(path, adjacentTile);
                    }
                }
            }
        }
    }

    /**
     * Calls upon the buildPath method again
     */
    private void extendPath(ArrayList<Tile> path, Tile adjacentTile) {
        path.add(adjacentTile);
        buildPath(path);
        path.remove(adjacentTile);
    }

    /**
     * Gets all possible adjacent tiles
     */
    private ArrayList<Tile> adjacentTiles(Tile currentTile) {
        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        int currentX = (int)currentTile.getX();
        int currentY = (int)currentTile.getY();
        if (currentX > 0) {
            adjacentTiles.add(grid.getTile(currentX - 1, currentY));
        }
        if (currentX < grid.getMap()[0].length - 1) {
            adjacentTiles.add(grid.getTile(currentX + 1, currentY));
        }
        if (currentY > 0) {
            adjacentTiles.add(grid.getTile(currentX, currentY - 1));
        }
        if (currentY < grid.getMap().length - 1) {
            adjacentTiles.add(grid.getTile(currentX, currentY + 1));
        }
        return adjacentTiles;
    }

    public ArrayList<ArrayList<Tile>> getPaths() { return paths; }

}
