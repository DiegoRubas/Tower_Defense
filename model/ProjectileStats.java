package model;

/**
 * This enumeration stores all the attributes of each individual projectile, allowing all the information to be stored
 * in one class
 */
public enum ProjectileStats {

    BASIC(5, "assets/shots/shot.png", "assets/shots/shot_2.png"),
    FROST(5, "assets/shots/frost_shot.png", "assets/shots/frost_shot_2.png"),
    FLAME(5, "assets/shots/fire_shot.png", "assets/shots/fire_shot_2.png"),
    MISSILE(7, "assets/shots/rocket.png", "assets/shots/rocket_2.png");

    private final float speed;
    private final String imageURL1;
    private final String imageURL2;

    ProjectileStats(float speed, String imageURL1, String imageURL2) {
        this.speed = speed;
        this.imageURL1 = imageURL1;
        this.imageURL2 = imageURL2;
    }
    public float getSpeed() { return speed; }
    public String getImageURL1() { return imageURL1; }
    public String getImageURL2() { return imageURL2; }
}
