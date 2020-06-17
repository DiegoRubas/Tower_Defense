package model;

/**
 * Allows dynamic instantiating of objects of the turret class and its subclasses
 */
public class TurretFactory {

    /**
     * Instantiates a turret object from an information string and returns said turret object
     */
    public static Turret getInstance(String type, Tile tile) {
        Turret turret;
        switch (type) {
            case "Basic Turret": turret = new Turret(TurretStats.BASIC, tile); break;
            case "Frost Turret": turret = new Turret(TurretStats.FROST, tile); break;
            case "Flame Turret": turret = new Turret(TurretStats.FLAME, tile); break;
            case "Missile Turret": turret = new Turret(TurretStats.MISSILE, tile); break;
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
        return turret;
    }

}