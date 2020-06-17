package model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This major class handles money, hp and tower placement, upgrading and selling.
 */
public class Player implements EnemyObserver {

    private int hp, gold, score;
    private boolean alive;
    private final CopyOnWriteArrayList<Turret> turrets;

    public Player(int hp, int gold) {
        this.hp = hp;
        this.gold = gold;
        alive = (hp > 0);
        this.turrets = new CopyOnWriteArrayList<>();
    }

    /**
     * Decreases the player's hp and kills them if hp reaches 0
     */
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }

    /**
     * Updates the player in one of two ways once an enemy's death notifies them:
     * - it either deals damage to the player if the enemy has gotten to the end of its path
     * - it yields the player gold and score if the enemy has died before getting to the end of its path
     */
    @Override
    public void update(Enemy enemy) {
        if (enemy.isAtEnd()) {
            takeDamage(enemy.getDamage());
        } else {
            earnGold(enemy.getBounty());
            earnScore(enemy.getBounty());
        }
    }

    /**
     * Creates a turret, spends gold equal to the cost of the turret, sets the desired placement tile to occupied, adds
     * the turret to the player's array list of turrets and returns the turret
     */
    public Turret buyAndPlaceTurret(String turretName, Tile tile) {
        Turret turretToAdd = TurretFactory.getInstance(turretName, tile);
        spendGold(turretToAdd.getCost());
        tile.setOccupied(true);
        addTurret(turretToAdd);
        return turretToAdd;
    }

    /**
     * Removes a turret from the player's array list of turrets, sets the turret's tile to unoccupied, yields the player
     * gold equivalent to half of the turret's cost and removes all existing projectiles shot from the turret
     */
    public void sellTurret(Turret turret) {
        removeTurret(turret);
        turret.getTile().setOccupied(false);
        earnGold((int)(turret.getCost()*0.5));
        for (Projectile p: turret.getProjectiles()) {
            turret.getProjectiles().remove(p);
        }
    }

    /**
     * Upgrades the turret, improving one or more of the following: damage, projectile speed, cooldown, burn damage,
     * flying target damage, frost slow
     */
    public void upgradeTurret(Turret turret) {
        spendGold(turret.getCost());
        turret.levelUp();
    }

    public int getGold() { return gold; }
    public boolean canPay(int cost) { return cost <= gold; }
    public void spendGold(int goldSpent) { gold -= goldSpent; }
    public void earnGold(int goldEarned) { gold += goldEarned; }

    public int getScore() { return score; }
    public void earnScore(int scoreChange) { score += scoreChange; }

    public int getHp() { return hp; }
    public boolean isAlive() { return alive; }

    public CopyOnWriteArrayList<Turret> getTurrets() { return turrets; }

    public void addTurret(Turret turret) { turrets.add(turret); }
    public void removeTurret(Turret turret) { turrets.remove(turret); }
}
