package model;

/**
 * This land enemy takes 50% less damage from projectiles and spawns a land enemy when it is destroyed
 */
public class EnemyTank extends EnemyLand implements Armored, Manned {

    private final Game game;

    public EnemyTank(EnemyStats enemyStats) {
        super(enemyStats);
        game = Game.getGameInstance();
    }

    /**
     * Dies and ejects its operator
     */
    @Override
    public void die() {
        super.die();
        ejectOperator();
    }

    /**
     * Spawns a land enemy, transfers its path information to it and adds it to the spawner's enemy array list
     */
    public void ejectOperator() {
        Enemy tankOperator = EnemyFactory.getInstance("man");
        transferPathInfo(tankOperator);
        game.getSpawner().getEnemies().add(tankOperator);
    }

    /**
     * Transfers the tank's position, direction, path, index in that path and angle to the enemy it spawns such that the
     * spawned enemy can continue in its stead
     */
    private void transferPathInfo(Enemy tankOperator) {
        tankOperator.setX(getX());
        tankOperator.setY(getY());
        tankOperator.setDirection(getDirection());
        tankOperator.setPath(getPath());
        tankOperator.setPathIndex(getPathIndex());
        tankOperator.calculateAngle();
    }
}