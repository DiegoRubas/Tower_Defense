package model;

public class ProjectileFrost extends Projectile {

    private float freezeFactor;

    public ProjectileFrost(Turret turret) {
        super(ProjectileStats.FROST, turret);
        switch (turret.getLevel()) {
            case 1: freezeFactor = 0.7f; break;
            case 2: freezeFactor = 0.4f; break;
            case 3: freezeFactor = 0.1f; break;
        }
    }

    /**
     * Damages and freezes the target
     */
    @Override
    public void affectTarget() {
        freezeTarget();
        damageTarget();
    }

    /**
     * Sets the target's status to frozen and communicates the projectile's freeze factor
     */
    private void freezeTarget() {
        if (!(getTarget() instanceof Hasted)) {
            getTarget().freeze(freezeFactor);
        }
    }

}
