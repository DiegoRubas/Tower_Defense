package model;

/**
 * This land enemy cannot be slowed and spawns when an enemy tank is destroyed
 */
public class EnemyRunner extends EnemyLand implements Hasted {

    public EnemyRunner() {
        super(EnemyStats.RUNNER);
    }

}
