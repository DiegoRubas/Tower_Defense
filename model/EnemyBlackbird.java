package model;

/**
 * This flying enemy cannot be slowed
 */
public class EnemyBlackbird extends EnemyFlying implements Hasted {

    public EnemyBlackbird() {
        super(EnemyStats.BLACKBIRD);
    }
}
