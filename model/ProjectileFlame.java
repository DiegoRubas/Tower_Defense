package model;

public class ProjectileFlame extends Projectile {

    private int burnFactor;

    public ProjectileFlame(Turret turret) {
        super(ProjectileStats.FLAME, turret);
        switch (turret.getLevel()) {
            case 1: burnFactor = 1; break;
            case 2:
            case 3: burnFactor = 2; break;
        }
    }

    /**
     * Damages and burns the target
     */
    @Override
    public void affectTarget() {
        burnTarget();
        damageTarget();
    }

    /**
     * Sets the target's status to burning and communicates the projectile's burn factor
     */
    private void burnTarget() {
        getTarget().burn(burnFactor);
    }

}
