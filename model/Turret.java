package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.CopyOnWriteArrayList;

public class Turret extends Entity implements Runnable, Rotating, EnemyObserver {

    private String name;
    private int range;
    private int level;
    private int damage;
    private int cost;
    private float cooldown, timeSinceLastShot;
    private double angle;
    private Enemy target;
    private Tile tile;
    private boolean displayed;
    private ImageView baseImageView;
    private Game game;
    private CopyOnWriteArrayList<Projectile> projectiles;
    private String imageURL2;
    private String imageURL3;

    public Turret(TurretStats turretStats, Tile tile) {
        super(tile.getX(), tile.getY(), turretStats.getImageURL1());

        assignStats(turretStats, tile);

        Thread thread = new Thread(this);
        thread.start();
    }


    /**
     * Initializes the turret's stats
     */
    private void assignStats(TurretStats turretStats, Tile tile) {
        this.name = turretStats.getName();
        this.tile = tile;

        this.level = 1;
        this.cost = turretStats.getCost();

        this.damage = turretStats.getDamage();
        this.range = turretStats.getRange();
        this.cooldown = turretStats.getCooldown();
        this.timeSinceLastShot = cooldown;

        this.displayed = false;

        this.projectiles = new CopyOnWriteArrayList<>();

        this.game = Game.getGameInstance();

        this.imageURL2 = turretStats.getImageURL2();
        this.imageURL3 = turretStats.getImageURL3();
    }

    /**
     * Thread method
     * While the tile is occupied (i.e. the turret has not been sold), the turret acquires a target if it has none,
     * shoots if the time elapsed is greater than its cooldown and changes target to null if the target is out of range
     */
    @Override
    public void run() {
        while (tile.isOccupied()) {
            synchronized (this) {
                if (target == null) {
                    acquireTarget();
                } else {
                    calculateAngle();
                    if (cooldown < timeSinceLastShot) {
                        shoot();
                    }
                }
                if (target == null || !isInRange(target)) {
                    target = null;
                }
            }
            timeSinceLastShot += 0.017;
            try {
                //noinspection BusyWait
                Thread.sleep(17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a projectile instance and resets time since last shot to 0
     */
    public void shoot() {
        projectiles.add(ProjectileFactory.getInstance(this));
        timeSinceLastShot = 0;
    }

    /**
     * Calculates the angle of the projectile
     */
    public void calculateAngle() {
        angle = Math.toDegrees(Math.atan2(target.getY() - getY(), target.getX() - getX())) + 90;
    }

    /**
     * Sets the closest enemy within range to its target
     */
    private void acquireTarget() {
        double closestRange = range;
        for (Enemy e : game.getSpawner().getEnemies()) {
            if (isInRange(e) && getDistance(e) < closestRange && e.isAlive()) {
                target = e;
                closestRange = getDistance(e);
            }
        }
        if (target != null) {
            target.attachObserver(this);
        }
    }

    /**
     * Levels the turret up, increasing its stats
     */
    public void levelUp() {
        level++;
        switch (level) {
            case 2:
                damage *= 2;
                getImageView().setImage(new Image(imageURL2, 64, 64, false, false));
                break;
            case 3:
                cooldown *= 0.5;
                getImageView().setImage(new Image(imageURL3, 64, 64, false, false));
                baseImageView.setImage(new Image("assets/turrets/turret_base_2.png",
                        64, 64, false, false));
                break;
        }
    }

    /**
     * Returns whether or not the enemy is within range
     */
    private boolean isInRange(Enemy e) {
        return getDistance(e) < range;
    }


    /**
     * Updates the turret, notifying it that the enemy has died and that it has to change targets
     */
    @Override
    public void update(Enemy enemy) {
        synchronized (this) {
            if (target == enemy) {
                target = null;
            }
        }
    }

    public ImageView getBaseImageView() {
        if (baseImageView == null) {
            String baseImageURL = "assets/turrets/turret_base.png";
            baseImageView = new ImageView(new Image(baseImageURL,64, 64, false, false));
        }
        return baseImageView;
    }

    public boolean isDisplayed() { return displayed; }
    public void setDisplayed(boolean b) { displayed = b; }
    public CopyOnWriteArrayList<Projectile> getProjectiles() { return projectiles; }
    public int getLevel() { return level; }
    public double getAngle() { return angle; }
    public int getRange() { return range; }
    public int getCost() { return cost; }
    public int getDamage() { return damage; }
    public float getCooldown() { return cooldown; }
    public String getName() { return name; }
    public Enemy getTarget() { return target; }
    public Tile getTile() { return tile; }
}
