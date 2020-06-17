package model;

import java.util.Random;

/**
 * Allows dynamic instantiating of objects of the enemy class and its subclasses
 */
public class EnemyFactory {

    /**
     * Instantiates an enemy object from an information string and returns said enemy object
     */
    public static Enemy getInstance(String type) {
        Enemy enemy = null;
        int random;
        switch (type) {
            case "flying":
                random = new Random().nextInt(3);
                switch (random) {
                    case 0: enemy = new EnemyBlackbird();break;
                    case 1: enemy = new EnemyRaptor();break;
                    case 2: enemy = new EnemyMirage(); break;
                }
                break;
            case "water":
                random = new Random().nextInt(2);
                switch (random) {
                    case 0: enemy = new EnemyWater(EnemyStats.VESUVIUS);break;
                    case 1: enemy = new EnemyWater(EnemyStats.VICTORY);break;
                }
                break;
            case "land":
                random = new Random().nextInt(3);
                switch (random) {
                    case 0: enemy = new EnemyTank(EnemyStats.PANTHER);break;
                    case 1: enemy = new EnemyTank(EnemyStats.LEOPARD);break;
                    case 2: enemy = new EnemyChallenger();break;
                }
                break;
            case "man":
                random = new Random().nextInt(2);
                switch (random) {
                    case 0: enemy = new EnemyRunner();break;
                    case 1: enemy = new EnemyLand(EnemyStats.INFANTRYMAN);break;
                }
        }
        return enemy;
    }

}