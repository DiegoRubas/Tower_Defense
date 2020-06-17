package model;

/**
 * This flying enemy evades one attack out of two
 */
public class EnemyMirage extends EnemyFlying implements Phasing {

    private float mirageCounter;
    private boolean hidden;
    private int hitCounter = 0;

    public EnemyMirage() {
        super(EnemyStats.MIRAGE);
    }

    /**
     * Advances unless hidden, in which case it reappears after 0.75 seconds
     */
    @Override
    public void advance() {
        if (hidden) {
            mirageCounter += 0.017f;
            if (mirageCounter > 0.75f) {
                reappear();
            }
        } else {
            super.advance();
        }
    }

    /**
     * Takes regular damage every first attack, disappears and dodges the damage every second attack
     */
    public void takeDamage(int damage) {
        if (hitCounter > 0 && canDisappear()) {
            disappear();
            hitCounter = 0;
        } else {
            super.takeDamage(damage);
            hitCounter++;
        }
    }

    /**
     * Reappears on the map 2 tiles further down its path
     */
    public void reappear() {
        setX(-getX() + 2 * getSpeed() * (float)getDirection()[0]);
        setY(-getY() + 2 * getSpeed() * (float)getDirection()[1]);
        hidden = false;
    }

    /**
     * Disappears from the map
     */
    public void disappear() {
        hidden = true;
        setX(-getX());
        setY(-getY());
        mirageCounter = 0;
    }

    /**
     * Boolean indicating whether or not it has the space to teleport forward by 2 tiles
     */
    public boolean canDisappear() {
        return getX() <= getEndTile().getX() - Math.abs(2 * getSpeed() * (float)getDirection()[0])
                || getEndTile().getX() + Math.abs(2 * getSpeed() * (float)getDirection()[0]) <= getX()
                && getY() <= getEndTile().getY() - Math.abs(2 * getSpeed() * (float)getDirection()[1])
                || getEndTile().getY() + Math.abs(2 * getSpeed() * (float)getDirection()[1]) <= getY();
    }

}
