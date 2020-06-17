package model;

/**
 * This enumeration stores all the attributes of each individual turret, allowing all the information to be stored
 * in one class
 */
public enum TurretStats {

    BASIC("Basic Turret", 15, 10, 5, 2,
            "assets/turrets/green_turret.png", "assets/turrets/green_turret_2.png",
            "assets/turrets/green_turret_3.png", "None"),
    FROST("Frost Turret", 25, 10, 5, 2,
            "assets/turrets/blue_turret.png", "assets/turrets/blue_turret_2.png",
            "assets/turrets/blue_turret_3.png", "Movement freeze"),
    FLAME("Flame Turret", 25, 10, 5, 2,
            "assets/turrets/red_turret.png", "assets/turrets/red_turret_2.png",
            "assets/turrets/red_turret_3.png", "Burning damage per second"),
    MISSILE("Missile Turret", 30, 10, 5, 2,
            "assets/turrets/rocket_turret.png", "assets/turrets/rocket_turret_2.png",
            "assets/turrets/rocket_turret_3.png", "Anti-aircraft");

    private final String name;
    private final String imageURL1;
    private final String imageURL2;
    private final String imageURL3;
    private final String description;
    private final int cost;
    private final int damage;
    private final int range;
    private final float cooldown;

    TurretStats(String name, int cost, int damage, int range, float cooldown, String imageURl1, String imageURL2,
                String imageURL3, String description) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.imageURL1 = imageURl1;
        this.imageURL2 = imageURL2;
        this.imageURL3 = imageURL3;
        this.description = description;
    }

    public String getName() { return name; }
    public String getImageURL1() { return imageURL1; }
    public String getImageURL2() { return imageURL2; }
    public String getImageURL3() { return imageURL3; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public float getCooldown() { return cooldown; }
}
