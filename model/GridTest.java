package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class GridTest {

    /*
      0 grass tile
      1 land spawn tile
      2 land tile
      3 land end tile
      4 water spawn tile
      5 water tile
      6 water end tile
      7 shallow water tile
      8 deep water tile
     */

    /**
     * A path with no spawn or end tiles, throwing a run time exception
     */
    @Test(expected = RuntimeException.class)
    public void testIsValid1() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"2","2","2","2","2","2","2"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        assertTrue(grid.isValid());
        grid.generatePaths();

    }

    /**
     * A path from a spawn tile without an end tile, throwing a run time exception
     */
    @Test(expected = RuntimeException.class)
    public void testIsValid2() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"1","2","2","2","2","2","2"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        assertTrue(grid.isValid());
    }

    /**
     * A path to an end tile without a spawn tile, throwing a run time exception
     */
    @Test(expected = RuntimeException.class)
    public void testIsValid3() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"2","2","2","2","2","2","3"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        assertTrue(grid.isValid());
    }

    /**
     * A clear path from the spawn tile to the end tile, making the grid valid
     */
    @Test
    public void testIsValid4() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"1","2","2","2","2","2","3"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        assertTrue(grid.isValid());
    }

    /**
     * A clear path from each spawn tile to its corresponding end tile, making the grid valid
     */
    @Test
    public void testIsValid5() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","4","0","0","0"},
                {"0","0","0","5","0","0","0"},
                {"0","0","0","5","0","0","0"},
                {"1","2","2","7","2","2","3"},
                {"0","0","0","5","0","0","0"},
                {"0","0","0","5","0","0","0"},
                {"0","0","0","6","0","0","0"}
        });
        assertTrue(grid.isValid());
    }

    /**
     * A single path provides a total of 1 path
     */
    @Test
    public void testGeneratePaths1() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","2","2","2","0","0"},
                {"1","2","2","0","2","2","3"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        grid.generatePaths();
        for (TileSpawn spawnTile: grid.getSpawnTiles()) {
            assertEquals(spawnTile.getPaths().size(), 1);
        }
    }

    /**
     * 1 junction with 2 choices provide a total of 2 paths
     */
    @Test
    public void testGeneratePaths2() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","2","2","2","0","0"},
                {"1","2","2","0","2","2","3"},
                {"0","0","2","2","2","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        grid.generatePaths();
        for (TileSpawn spawnTile: grid.getSpawnTiles()) {
            assertEquals(spawnTile.getPaths().size(), 2);
        }
    }

    /**
     * 2 junctions with 2 choices each provide a total of 4 paths
     */
    @Test
    public void testGeneratePaths3() {
        Grid grid = new Grid(new String[][] {
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"},
                {"0","2","2","2","2","2","0"},
                {"1","2","0","2","0","2","3"},
                {"0","2","2","2","2","2","0"},
                {"0","0","0","0","0","0","0"},
                {"0","0","0","0","0","0","0"}
        });
        grid.generatePaths();
        for (TileSpawn spawnTile: grid.getSpawnTiles()) {
            assertEquals(spawnTile.getPaths().size(), 4);
        }
    }

}
