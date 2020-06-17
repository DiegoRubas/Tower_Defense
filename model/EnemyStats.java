package model;

/**
 * This enumeration stores all the attributes of each individual enemy, allowing all the information to be stored in one
 * class
 */
public enum EnemyStats {

    MIRAGE("Dassault Mirage", 30, 1, 2, 11,
            "assets/enemies/dassault_mirage.png", "Mirage"),
    BLACKBIRD("Lockheed Blackbird", 20, 5, 2, 10,
            "assets/enemies/lockheed_blackbird.png", "Hasted"),
    RAPTOR("Lockheed Raptor", 35, 1.25f, 2, 9,
            "assets/enemies/lockheed_raptor.png", "Armored"),
    VICTORY("HMS Victory", 50, 1, 2, 8,
            "assets/enemies/hms_victory.png", "Fire-resistant"),
    VESUVIUS("USS Vesuvius", 50, 0.75f, 2, 7,
            "assets/enemies/uss_vesuvius.png", "Fire-resistant"),
    CHALLENGER("Challenger 2", 10, 0.65f, 0, 5,
            "assets/enemies/challenger_2.png", "Healing, Manned and Armored"),
    PANTHER("K2 Black Panther", 40, 0.85f, 2, 6,
            "assets/enemies/k2_black_panther.png", "Manned and Armored"),
    LEOPARD("Leopard 2A7", 50, 0.65f, 2, 5,
            "assets/enemies/leopard_2a7.png", "Manned and Armored"),
    RUNNER("Courier", 10, 3, 1, 4,
            "assets/enemies/runner.png", "Hasted"),
    INFANTRYMAN("Infantryman", 25, 1, 1, 3,
            "assets/enemies/infantryman.png", "None");

    private final String name;
    private final String imageURL;
    private final String description;
    private final int health;
    private final int damage;
    private final int bounty;
    private final float speed;

    EnemyStats(String name, int health, float speed, int damage, int bounty, String imageURL, String description) {
        this.name = name;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.bounty = bounty;
        this.imageURL = imageURL;
        this.description = description;
    }
    public String getName() { return name; }
    public String getImageURL() { return imageURL; }
    public String getDescription() { return description; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getBounty() { return bounty; }
    public float getSpeed() { return speed; }
}