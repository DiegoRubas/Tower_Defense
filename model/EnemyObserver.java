package model;

/**
 * Implemented by all classes whose behavior changes when an enemy's state changes
 */
public interface EnemyObserver {

    /**
     * Updates enemy observers on an enemy's state
     */
    void update(Enemy e);
}
