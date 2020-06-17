package model;

public class ProjectileMissile extends Projectile implements AntiAircraft{

    private float multiplier;

    public ProjectileMissile(Turret turret) {
        super(ProjectileStats.MISSILE, turret);
        switch (turret.getLevel()) {
            case 1:
            case 2: multiplier = 2; break;
            case 3: multiplier = 3; break;
        }
    }

    /**
     * Damages targets and deals extra damage versus flying targets
     */
    @Override
    public void affectTarget() {
        obliterateFlyingTarget();
        damageTarget();
    }

    /**
     * Multiplies the projectile's damage by its multiplier if the target is a flying enemy
     */
    public void obliterateFlyingTarget() {
        if (getTarget() instanceof EnemyFlying) {
            multiplyDamage(multiplier);
        }
    }

}
