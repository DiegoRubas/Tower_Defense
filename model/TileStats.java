package model;

/**
 * This enumeration stores all the attributes of each individual tile, allowing all the information to be stored
 * in one class
 */
public enum TileStats {

    GRASS(true, "assets/tiles/grass_tile.png"),
    LAND_SPAWN(false, "assets/tiles/land_spawn_tile.png"),
    LAND(false, "assets/tiles/land_tile.png"),
    LAND_END(false, "assets/tiles/land_end_tile.png"),
    WATER_SPAWN(false, "assets/tiles/water_spawn_tile.png"),
    WATER(false, "assets/tiles/water_tile.png"),
    WATER_END(false, "assets/tiles/water_end_tile.png"),
    SHALLOW_WATER(false, "assets/tiles/shallow_water_tile.png"),
    DEEP_WATER(false, "assets/tiles/deep_water_tile.png");

    private final boolean buildable;
    private final String imageURL;

    TileStats(boolean buildable, String imageURL) {
        this.buildable = buildable;
        this.imageURL = imageURL;
    }

    public String getImageURL() { return imageURL; }
    public boolean isBuildable() { return buildable; }
}
