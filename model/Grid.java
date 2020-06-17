package model;

import java.io.*;
import java.util.ArrayList;

public class Grid implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Tile[][] map = new Tile[12][18];
    private final ArrayList<Tile> endTiles = new ArrayList<>();
    private final ArrayList<Tile> landEndTiles = new ArrayList<>();
    private final ArrayList<Tile> waterEndTiles = new ArrayList<>();
    private final ArrayList<TileSpawn> spawnTiles = new ArrayList<>();
    private final ArrayList<TileSpawn> landSpawnTiles = new ArrayList<>();
    private final ArrayList<TileSpawn> waterSpawnTiles = new ArrayList<>();

    /**
     * Creates an 18x12 grid by loading data from a text file
     */
    public Grid(String fileURL) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileURL));
            String data = bufferedReader.readLine();
            for (int j = 0; j < 12; j++) {
                for (int i = 0; i < 18; i++) {
                    Tile correctTile = integerToTile(data.substring(i + j * 18, i + j * 18 + 1), i, j);
                    map[j][i] = correctTile;
                }
            }
            bufferedReader.close();
            fillTileArrayLists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        generatePaths();
    }

    /**
     * Creates an 18x12 grid by loading data from a nested list of strings
     */
    public Grid(String[][] intMap) {
        for (int j = 0; j < intMap.length; j++) {
            for (int i = 0; i < intMap[j].length; i++) {
                map[j][i] = integerToTile(intMap[j][i], i, j);
            }
        }
        fillTileArrayLists();
    }

    public Grid() {
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 18; i++) {
                map[j][i] = new Tile(TileStats.GRASS, i, j);
            }
        }
    }

    public void generatePaths() {
        for (TileSpawn landSpawnTile: landSpawnTiles) {
            for (Tile landEndTile: landEndTiles) {
                Paths paths = new Paths(landSpawnTile, landEndTile, this);
                landSpawnTile.addPaths(paths.getPaths());
            }
        }
        for (TileSpawn waterSpawnTile: waterSpawnTiles) {
            for (Tile waterEndTile: waterEndTiles) {
                Paths paths = new Paths(waterSpawnTile, waterEndTile, this);
                waterSpawnTile.addPaths(paths.getPaths());
            }
        }
    }

    /**
     * Converts the string integers to their corresponding tiles
     * @param data the selected character from the text file
     * @param i,j x and y coordinates of the tile
     * @return the corresponding tile
     */
    private Tile integerToTile(String data, int i, int j) {
        Tile tile;
        int intData = Integer.parseInt(data);
        switch (intData) {
            case 0: tile = new Tile(TileStats.GRASS, i, j); break;
            case 1: tile = new TileLandSpawn(i, j); break;
            case 2: tile = new TileLand(i, j); break;
            case 3: tile = new TileLandEnd(i, j); break;
            case 4: tile = new TileWaterSpawn(i, j); break;
            case 5: tile = new TileWater(i, j); break;
            case 6: tile = new TileWaterEnd(i, j); break;
            case 7: tile = new TileShallowWater(i, j); break;
            case 8: tile = new Tile(TileStats.DEEP_WATER, i, j); break;
            default:
                throw new IllegalStateException("Unexpected value: " + intData);
        }
        return tile;
    }

    public ArrayList<TileSpawn> getSpawnTiles() { return spawnTiles; }

    public ArrayList<TileSpawn> getLandSpawnTiles() { return landSpawnTiles; }

    public ArrayList<TileSpawn> getWaterSpawnTiles() { return waterSpawnTiles; }

    public ArrayList<Tile> getEndTiles() { return endTiles; }

    public Tile getTile(int x, int y) { return map[y][x]; }

    public void setTile(Tile tile, int x, int y){ map[y][x] = tile; }

    public Tile[][] getMap() { return map; }

    public boolean isValid() {
        fillTileArrayLists();
        if (spawnTiles.size() == 0 && endTiles.size() == 0) {
            throw new RuntimeException("no spawn nor end tiles");
        } else if (landSpawnTiles.size() > 0 && landEndTiles.size() == 0) {
            throw new RuntimeException(landSpawnTiles.size() + " land spawn tiles but no land end tiles");
        } else if (landSpawnTiles.size() == 0 && landEndTiles.size() > 0) {
            throw new RuntimeException(landEndTiles.size() + " land end tiles but no land spawn tiles");
        } else if (waterSpawnTiles.size() > 0 && waterEndTiles.size() == 0) {
            throw new RuntimeException(waterSpawnTiles.size() + " water spawn tiles but no water end tiles");
        } else if (waterSpawnTiles.size() == 0 && waterEndTiles.size() > 0) {
            throw new RuntimeException(waterEndTiles.size() + " water end tiles but no water spawn tiles");
        } else {
            generatePaths();
            return true;
        }
    }

    private void fillTileArrayLists() {
        for (Tile[] rowTiles : map) {
            for (Tile tile : rowTiles) {
                if (tile instanceof TileSpawn) {
                    spawnTiles.add((TileSpawn)tile);
                    if (tile instanceof TileTypeLand) {
                        landSpawnTiles.add((TileSpawn)tile);
                    } else {
                        waterSpawnTiles.add((TileSpawn)tile);
                    }
                } else if (tile instanceof TileTypeEnd) {
                    endTiles.add(tile);
                    if (tile instanceof TileTypeLand) {
                        landEndTiles.add(tile);
                    } else {
                        waterEndTiles.add(tile);
                    }
                }
            }
        }
    }
}
