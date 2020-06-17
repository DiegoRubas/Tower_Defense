package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {

    /**
     * Player is alive when taking less damage than their hp
     */
    @Test
    public void testIsAlive1() {
        Player player = new Player(20, 50);
        player.takeDamage(10);
        assertTrue(player.isAlive());
    }

    /**
     * Player dies when taking as much damage as their hp
     */
    @Test
    public void testIsAlive2() {
        Player player = new Player(20, 50);
        player.takeDamage(20);
        assertFalse(player.isAlive());
    }

    /**
     * Player can pay 20 gold 2 times
     */
    @Test
    public void testCanPay3() {
        Player player = new Player(20, 50);
        assertTrue(player.canPay(20));
        player.spendGold(20);
        assertTrue(player.canPay(20));
    }

    /**
     * Player cannot pay 20 gold 3 times
     */
    @Test
    public void testCanPay4() {
        Player player = new Player(20, 50);
        assertTrue(player.canPay(20));
        player.spendGold(20);
        assertTrue(player.canPay(20));
        player.spendGold(20);
        assertFalse(player.canPay(20));
    }

    /**
     * Player earns gold when an enemy is killed
     */
    @Test
    public void testUpdate1() {
        Game game = Game.getGameInstance();
        game.startGame("src/maps/savanna.tmp", 1);
        int initialGold = game.getPlayer().getGold();
        Enemy enemy = EnemyFactory.getInstance("flying");
        enemy.die();
        int finalGold = game.getPlayer().getGold();
        assertTrue(finalGold > initialGold);
    }

    /**
     * Player loses HP when an enemy reaches the end
     */
    @Test
    public void testUpdate2() {
        Game game = Game.getGameInstance();
        game.startGame("src/maps/savanna.tmp", 1);
        int initialHp = game.getPlayer().getHp();
        Enemy enemy = EnemyFactory.getInstance("flying");
        enemy.getToEnd();
        int finalHp = game.getPlayer().getHp();
        assertTrue(finalHp < initialHp);
    }

}