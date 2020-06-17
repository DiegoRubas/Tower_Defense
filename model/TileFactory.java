package model;

/**
 * Allows dynamic instantiating of objects of the tile class and its subclasses
 */
public class TileFactory {

    /**
     * Instantiates a tile object from the a tileStats enum and returns said tile object
     */
    public static Tile getInstance(TileStats tileStats, int x, int y) {
        Tile finalTile;
        switch (tileStats.toString()) {
            case "GRASS": finalTile = new Tile(TileStats.GRASS, x, y); break;
            case "LAND_SPAWN": finalTile = new TileLandSpawn(x, y); break;
            case "LAND": finalTile = new TileLand(x, y); break;
            case "LAND_END": finalTile = new TileLandEnd(x, y); break;
            case "WATER_SPAWN": finalTile = new TileWaterSpawn(x, y); break;
            case "WATER": finalTile = new TileWater(x, y); break;
            case "WATER_END": finalTile = new TileWaterEnd(x, y); break;
            case "SHALLOW_WATER": finalTile = new TileShallowWater(x, y); break;
            case "DEEP_WATER": finalTile = new Tile(TileStats.DEEP_WATER, x, y); break;
            default:
                throw new IllegalStateException("Unexpected value: " + tileStats.toString());
        }
        return finalTile;
    }

}