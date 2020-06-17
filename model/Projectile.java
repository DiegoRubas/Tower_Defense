package model;

public class Projectile extends Entity implements Runnable, Mobile, Rotating{

    private int damage;
    private float xVelocity;
    private float yVelocity;
    private float speed;
    private double angle;
    private boolean collided, displayed;

    private Enemy target;

    public Projectile(ProjectileStats projectileStats, Turret turret) {
        super(turret.getX(), turret.getY(), (turret.getLevel() > 1 ? projectileStats.getImageURL2() :
                projectileStats.getImageURL1()));
        assignTarget(turret);
        assignStats(projectileStats, turret);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Sets the projectile's target to the source turret's target
     */
    private void assignTarget(Turret turret) {
        this.target = turret.getTarget();
    }

    /**
     * Initializes the projectile's stats
     */
    private void assignStats(ProjectileStats projectileStats, Turret turret) {
        this.speed = projectileStats.getSpeed() + (turret.getLevel() == 3 ? 3 : 0);
        this.damage = turret.getDamage();
        this.displayed = false;
        this.collided = false;
    }

    /**
     * While the projectile hasn't collided, it is considered active and calls all these methods
     */
    @Override
    public void run() {
        while(!collided) {
            advance();
            if (checkCollision()){
                affectTarget();
            }
            if ((target.getX() < 0 && target.getY() < 0) || !target.isAlive()) {
                collided = true;
            }
            try {
                //noinspection BusyWait
                Thread.sleep(17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void incrementX(float x) { setX(getX() + x); }

    @Override
    public void incrementY(float y) { setY(getY() + y); }

    public void advance() {
        calculateAngle();
        calculateVelocities();
        incrementX(0.017f * xVelocity * speed);
        incrementY(0.017f * yVelocity * speed);
    }

    /**
     * Calculates the angle between the projectile's current position and the enemy's current position
     */
    public void calculateAngle() {
        float xDistance = target.getX() - getX();
        float yDistance = target.getY() - getY();
        double tempAngle = Math.atan2(yDistance, xDistance);
        angle = Math.toDegrees(tempAngle);
    }

    /**
     * Transforms the angle and absolute speed into x and y velocities
     */
    private void calculateVelocities() {
        xVelocity = (float)Math.cos(Math.toRadians(angle));
        yVelocity = (float)Math.sin(Math.toRadians(angle));
    }

    /**
     * Checks if the projectile has hit the enemy
     */
    private boolean checkCollision() {
        return (target.getX() < getX() + 0.5 && getX() < target.getX() + 0.5 &&
                target.getY()  < getY() + 0.5 && getY() < target.getY() + 0.5);
    }

    /**
     * Applies all desired effects on the target
     */
    public void affectTarget() {
        damageTarget();
    }

    /**
     * Deals damage to the target, dealing 50% damage to armored enemies
     */
    public void damageTarget(){
        if (target instanceof Armored) {
            target.takeDamage(damage/2);
        } else {
            target.takeDamage(damage);
        }
        collided = true;
    }

    public boolean isDisplayed() { return displayed; }
    public void setDisplayed(boolean b) { displayed = b; }
    public double getAngle() { return angle; }
    public boolean hasCollided() { return collided; }
    public Enemy getTarget() { return target; }
    public void multiplyDamage(float factor) { damage *= factor; }
}
