package model;

/**
 * This enemy tank heals surrounding enemies
 */
public class EnemyChallenger extends EnemyTank implements Healing {

    public EnemyChallenger() {
        super(EnemyStats.CHALLENGER);
    }

}

