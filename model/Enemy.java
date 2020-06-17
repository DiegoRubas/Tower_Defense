package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Enemy extends Entity implements Runnable, Mobile, Rotating, EnemySubject {

    private int damage;
    private int bounty;
    private int offset;
    private ArrayList<Tile> path;
    private int pathIndex;
    private float health;
    private float baseHealth;
    private float speed;
    private float baseSpeed;
    private float healthBarLength;
    private double angle;
    private double[] direction = new double[2];
    private boolean alive, displayed;
    private ImageView healthBackground;
    private ImageView healthForeground;
    private String name;
    private String description;
    private boolean burning, frozen;

    private float burnTickTime = 100;
    private float healingTickTime = 100;
    private float burnTime;
    private float freezeTime;

    private final Game game;
    private final CopyOnWriteArrayList<EnemyObserver> observers = new CopyOnWriteArrayList<>();
    private float xDistance;
    private float yDistance;
    private boolean atEnd;
    private int burnFactor;
    private float freezeFactor;

    /**
     * Initializes the enemy object
     */
    public Enemy(EnemyStats enemyStats) {
        super(enemyStats.getImageURL());
        game = Game.getGameInstance();
        attachObserver(game.getPlayer());
        assignStats(enemyStats);
        increaseStats(enemyStats, game.getSpawner().getWaveNumber(), game.getDifficulty());
        selectRandomPath();
        calculateDirection();
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Picks a random start tile and selects a random path from that tile
     */
    private void selectRandomPath() {
        TileSpawn startTile = randomStartTile();
        setX(startTile.getX());
        setY(startTile.getY());
        this.path = startTile.getPaths().get(new Random().nextInt(startTile.getPaths().size()));
        this.pathIndex = 0;
    }

    /**
     * Initializes the enemy's stats and statuses
     */
    private void assignStats(EnemyStats enemyStats) {
        this.name = enemyStats.getName();
        this.description = enemyStats.getDescription();
        this.damage = enemyStats.getDamage();
        this.bounty = enemyStats.getBounty();
        this.alive = true;
        this.displayed = false;
        this.burning = false;
        this.frozen = false;
    }

    /**
     * Adjusts the enemy stats to the wave number
     * @param enemyStats enumeration containing the base stats
     * @param waveNumber integer indicating how many increments need to be made
     * @param difficulty integer indicating how significant an increment in strength is
     */
    private void increaseStats(EnemyStats enemyStats, int waveNumber, int difficulty) {
        int increase = waveNumber - 1;  //first wave has no increase
        speed = enemyStats.getSpeed() * (1 + increase * 0.03f * difficulty);
        baseSpeed = enemyStats.getSpeed() * (1 + increase * 0.03f * difficulty);
        health = enemyStats.getHealth() * (1 + increase * 0.01f * difficulty);
        baseHealth = enemyStats.getHealth() * (1 + increase * 0.01f * difficulty);
        healthBarLength = health * (1 + increase * 0.01f * difficulty) / 100;
    }

    /**
     * Sets the enemy's direction to pointing from its previous tile to its next tile
     */
    public void calculateDirection() {
        direction[0] = getTile(pathIndex + 1).getX() - getTile(pathIndex).getX();
        direction[1] = getTile(pathIndex + 1).getY() - getTile(pathIndex).getY();
        calculateAngle();
    }

    /**
     * Extracts the angle from direction
     */
    public void calculateAngle() {
        angle = Math.toDegrees(Math.atan2(direction[1], direction[0]));
    }

    /**
     * Picks a random tile among the valid start tiles
     */
    public abstract TileSpawn randomStartTile();

    /**
     * Thread method
     * While the enemy is alive and has not gotten to the end, the enemy follows its path and updates its coordinates,
     * gets affected by all status effects (namely frost and burn) and regenerates health whenever there is a healing
     * enemy in its vicinity
     */
    @Override
    public void run() {
        while (isAlive() && !isAtEnd()) {
            navigatePath();
            getFrostSlowed();
            takeBurnDamage();
            regenerateHealth();
            try {
                //noinspection BusyWait
                Thread.sleep(17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        notifyObservers();
    }

    /**
     * The enemy advances and if its coordinates are exactly the same as those of its next tile, the aforementioned next
     * tile becomes its current tile and the new next tile will be the one after the previous next tile. Its path index
     * is incremented and if the index reflects the last tile of the path, the enemy gets to the end. Otherwise, it
     * recalculates its direction.
     *
     * Because of the floating point number nature of the enemy's speed, its coordinates will never be exact integers
     * reflecting the tiles' coordinates. This method therefore uses an interval conditions rather than equalities.
     */
    public void navigatePath() {
        advance();
        if (getTile(pathIndex + 1).getX() - Math.abs(xDistance) <= getX()
                && getX() <= getTile(pathIndex + 1).getX() + Math.abs(xDistance)
                && getTile(pathIndex + 1).getY() - Math.abs(yDistance) <= getY()
                && getY() <= getTile(pathIndex + 1).getY() + Math.abs(yDistance)) {
            setX(Math.round(getX()));
            setY(Math.round(getY()));
            pathIndex++;
            if (pathIndex == path.size()-1) {
                getToEnd();
            } else {
                calculateDirection();
            }
        }
    }

    /**
     * Takes the tile in the path at position x
     */
    private Tile getTile(int x) {
        return path.get(x);
    }

    /**
     * Calculates the distance it travels within one frame on the x and y axes and adds those values to its coordinates
     */
    public void advance() {
        xDistance = (float)direction[0] * speed * 0.017f;
        yDistance = (float)direction[1] * speed * 0.017f;
        incrementX(xDistance);
        incrementY(yDistance);
    }

    /**
     * Slows the enemy for 4 seconds
     */
    public void getFrostSlowed() {
        if (frozen) {
            speed = freezeFactor * baseSpeed;
            if (freezeTime >= 4) {
                setNormalStatus();
            }
            freezeTime += 0.017f;
        }
    }

    /**
     * Heals the enemy for every healing enemy in its vicinity (radius of 3)
     */
    public void regenerateHealth() {
        if (healingTickTime > 0.25f) {
            for (Enemy e : game.getSpawner().getEnemies()) {
                if (e != this && e instanceof Healing && getDistance(e) < 3) {
                    heal(Math.max(1,(int)(baseHealth * 0.05f)));
                    healingTickTime = 0;
                }
            }
        }
        healingTickTime += 0.017f;
    }

    /**
     * Deals damage over time to the enemy for 4 seconds
     */
    public void takeBurnDamage() {
        if (burning) {
            if (burnTickTime > 0.25f) {
                health -= (Math.max(1,(int)(baseHealth * 0.025f * burnFactor)));
                if (health <= 0 && isAlive()) {
                    die();
                }
                burnTickTime = 0;
            }
            if (burnTime >= 4) { //after 4 seconds
                setNormalStatus();
            }
            burnTime += 0.017f;
            burnTickTime += 0.017f;
        }
    }

    /**
     * Enemy gets to the end of the path
     */
    public void getToEnd() {
        atEnd = true;
    }

    /**
     * Executes the enemy
     */
    public void die() {
        alive = false;
    }

    /**
     * Increases the enemy's health
     */
    private void heal(int heal) {
        health = Math.min(health + heal, baseHealth);
    }

    /**
     * Reduces the enemy's health
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0 && isAlive()) {
            die();
        }
    }

    /**
     * Sets the enemy's status to frozen
     */
    public void freeze(float freezeFactor) {
        frozen = true;
        freezeTime = 0;
        this.freezeFactor = freezeFactor;
        if (healthForeground != null) {
            healthForeground.setImage(new Image("assets/health/health_foreground_frost.png"));
        }
    }

    /**
     * Sets the enemy's status to burning
     */
    public void burn(int burnFactor) {
        burning = true;
        burnTime = 0;
        this.burnFactor = burnFactor;
        if (healthForeground != null) {
            healthForeground.setImage(new Image("assets/health/health_foreground_flame.png"));
        }
    }

    /**
     * Resets the enemy's status to normal
     */
    public void setNormalStatus() {
        burning = false;
        frozen = false;
        burnTickTime = 100;
        speed = baseSpeed;
        if (healthForeground != null) {
            healthForeground.setImage(new Image("assets/health/health_foreground.png"));
        }
    }

    /**
     * Notifies the observers when the enemy dies or gets to the end
     */
    @Override
    public void notifyObservers() {
        for (EnemyObserver enemyObserver: observers) {
            enemyObserver.update(this);
        }
    }

    /**
     * Attaches an object as an observer of this enemy's state
     */
    @Override
    public void attachObserver(EnemyObserver enemyObserver) { observers.add(enemyObserver); }

    /**
     * Changes the enemy's x coordinate
     */
    @Override
    public void incrementX(float x) { setX(getX() + x); }

    /**
     * Changes the enemy's y coordinate
     */
    @Override
    public void incrementY(float y) { setY(getY() + y); }

    public String getName() { return name; }
    public float getBaseHealth() { return baseHealth; }
    public float getSpeed() { return speed; }
    public int getBounty() { return bounty; }
    public int getDamage() { return damage; }

    public boolean isDisplayed() { return displayed; }
    public void setDisplayed(boolean b) { displayed = b; }

    public boolean isAlive() { return alive; }
    public Grid getGrid() { return game.getGrid(); }

    public double[] getDirection() { return direction; }
    public void setDirection(double[] direction) { this.direction = direction; }

    public ImageView getHealthBackground() {
        if (healthBackground == null) {
            healthBackground = new ImageView(new Image("assets/health/health_background.png",
                    64 * healthBarLength, 64 * 0.125, false, false));
        }
        return healthBackground;
    }
    public ImageView getHealthForeground() {
        offset = (int)((1 - healthBarLength) * 0.5 * 64);
        if (healthForeground == null) {
            healthForeground = new ImageView(new Image("assets/health/health_foreground.png",
                    64 * healthBarLength, 64 * 0.125, false, false));
        }
        return healthForeground;
    }
    public float getHealthForegroundLength() { return 64 * healthBarLength * health / baseHealth; }
    public int getOffset() { return offset; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    public boolean isBurning() { return burning; }
    public String getDescription() { return description; }

    public float getBurnTickTime() { return burnTickTime; }
    public void setBurnTickTime(float burnTickTime) { this.burnTickTime = burnTickTime; }
    public float getBurnTime() { return burnTime; }
    public void setBurnTime(float burnTime) { this.burnTime = burnTime; }

    public ArrayList<Tile> getPath() { return path; }
    public void setPath(ArrayList<Tile> path) { this.path = path; }

    public int getPathIndex() { return pathIndex; }
    public void setPathIndex(int pathIndex) { this.pathIndex = pathIndex; }

    public boolean isAtEnd() { return atEnd; }
}
