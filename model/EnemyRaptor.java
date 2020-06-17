package model;

/**
 * This flying enemy takes 50% less damage from projectiles
 */
public class EnemyRaptor extends EnemyFlying implements Armored {

    public EnemyRaptor() {
        super(EnemyStats.RAPTOR);
    }
}
