package model;

/**
 * Allows dynamic instantiating of objects of the projectile class and its subclasses
 */
public class ProjectileFactory {

    /**
     * Instantiates a projectile object from the turret's information and returns said projectile object
     */
    public static Projectile getInstance(Turret turret) {
        Projectile finalProjectile;
        switch (turret.getName()) {
            case "Basic Turret":
                finalProjectile = new Projectile(ProjectileStats.BASIC, turret);
                break;
            case "Frost Turret":
                finalProjectile = new ProjectileFrost(turret);
                break;
            case "Flame Turret":
                finalProjectile = new ProjectileFlame(turret);
                break;
            case "Missile Turret":
                finalProjectile = new ProjectileMissile(turret);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + turret.getName());
        }
        return finalProjectile;
    }

}