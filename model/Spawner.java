package model;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This major class handles the wave and spawning enemies
 */
public class Spawner implements Runnable {

    private int waveNumber, waveEnemiesSpawned, enemiesPerWave;
    private float timeSinceLastSpawn;
    private float timeSinceLastWave;
    private float spawnTime;
    private float waveTime;
    private final CopyOnWriteArrayList<Enemy> enemies;
    private final Game game;

    /**
     * Initializes the spawner object
     */
    public Spawner() {
        game = Game.getGameInstance();
        this.enemies = new CopyOnWriteArrayList<>();
        this.waveTime = 60;

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Thread method
     * Spawns an enemy on cooldown
     */
    @Override
    public void run() {
        while (waveNumber < 30) {
            if (timeSinceLastWave < waveTime) {
                if (waveEnemiesSpawned < enemiesPerWave && spawnTime < timeSinceLastSpawn) {
                    spawnEnemy();
                }
            } else {
                triggerNextWave();
            }
            timeSinceLastWave += 0.017;
            timeSinceLastSpawn += 0.017;
            try {
                //noinspection BusyWait
                Thread.sleep(17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void triggerNextWave() {
        increaseNextWaveStats();
        waveEnemiesSpawned = 0;
        timeSinceLastWave = 0;
        timeSinceLastSpawn = 10;
        waveNumber++;
    }

    private void increaseNextWaveStats() {
        if (waveNumber == 0) {
            enemiesPerWave = 2;
            waveTime = 40;
        } else {
            enemiesPerWave += 2;
            if (waveTime > 20) {
                waveTime -= 2;
            } else {
                waveTime *= 0.9f;
            }
        }
        spawnTime = Math.min((waveTime / 4) / enemiesPerWave, 2);
    }

    /**
     * Creates an enemy object, adds it to the enemies array list and increments the enemies spawned integer
     */
    private void spawnEnemy() {
        int landSpawnNumber = game.getGrid().getLandSpawnTiles().size();
        int waterSpawnNumber = game.getGrid().getWaterSpawnTiles().size();
        int random = new Random().nextInt((int)((landSpawnNumber + waterSpawnNumber) * 1.5f));
        String enemyCode;
        if (random < landSpawnNumber) {
            enemyCode = "land";
        } else if (random < landSpawnNumber + waterSpawnNumber) {
            enemyCode = "water";
        } else {
            enemyCode = "flying";
        }
        Enemy enemy = EnemyFactory.getInstance(enemyCode);
        waveEnemiesSpawned++;
        enemies.add(enemy);
        timeSinceLastSpawn = 0;
    }

    public CopyOnWriteArrayList<Enemy> getEnemies() { return enemies; }
    public int getWaveNumber() { return waveNumber; }
    public void setWaveNumber(int waveNumber) { this.waveNumber = waveNumber; }
    public float getTimeUntilNextWave() { return waveTime - timeSinceLastWave; }
    public void sendNextWave() { timeSinceLastWave = waveTime; }
    public boolean allWaveEnemiesSent() { return waveEnemiesSpawned == enemiesPerWave; }
}
