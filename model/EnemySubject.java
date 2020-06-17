package model;

/**
 * Implemented by all enemies whose state is important to another class's operation
 */
public interface EnemySubject {
    void attachObserver(EnemyObserver enemyObserver);
    void notifyObservers();
}
