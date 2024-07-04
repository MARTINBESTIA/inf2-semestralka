package hlavnyBalicek;

import enemaci.TypEnemy;
import fri.shapesge.Manager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda zodpovedná za vytváranie vĺn nepriateľov na základe celkového faktur sily vlny, posiela správy na vytvorenie inštancií
 * nových nepriateľov
 */
public class EnemySpawner {
    private final GameInformation gameInformation;
    private Manager manager;
    private int pocetVln = 0;
    private int strengthFactor = 0;
    private GameController gameController;
    private ArrayList<TypEnemy> waveOfEnemies;
    private final TypEnemy[] typyEnemies = TypEnemy.values();
    private final int timeToSpawnNewWave = 3500;
    private int timer = 500;
    private int enemySpawnTimer;
    private TypEnemy pickedEnemy;

    /**
     * @param gameController referencia na gamecontroller potrebná na vytváranie inštancií nepriateľov,
     * @param gameInformation panel s informáciami, ktoré potom táto trieda mení
     */
    public EnemySpawner(GameController gameController, GameInformation gameInformation) {
        this.gameInformation = gameInformation;
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.gameController = gameController;
        this.waveOfEnemies = new ArrayList<>();
        this.pickedEnemy = null;
    }

    /**
     * Postupne spawnuje nepriateľov a pridáva celkový faktor sily veže, a aktualizuje počet vypustených vĺn
     */
    public void tik() {
        if (!this.waveOfEnemies.isEmpty() && this.enemySpawnTimer <= 0) {
            this.chooseAndSpawnEnemy();
        } else {
            this.enemySpawnTimer--;
        }
        if (this.timer <= 0 && this.waveOfEnemies != null && this.waveOfEnemies.isEmpty()) {
            this.strengthFactor += 3;
            this.generateWave(this.strengthFactor);
            this.pocetVln++;
            this.gameInformation.updatePoceVln(this.pocetVln);
            this.timer = this.timeToSpawnNewWave;
        } else {
            this.timer--;
        }
    }
    private void generateWave(int totalWaveStrengthFactor) {
        this.waveOfEnemies = new ArrayList<>();
        int waveStrengthFactor = totalWaveStrengthFactor;
        while (waveStrengthFactor > 0) {
            ArrayList<TypEnemy> spawnableEnemies = this.getListOfSpawnableEnemies(waveStrengthFactor);
            int randomTypEnemyIndex = this.getRandomNumber(spawnableEnemies.size());
            TypEnemy randomSpawnableEnemy = spawnableEnemies.get(randomTypEnemyIndex);
            this.waveOfEnemies.add(randomSpawnableEnemy);
            waveStrengthFactor -= randomSpawnableEnemy.getStrengthFactor();
        }
    }
    private ArrayList<TypEnemy> getListOfSpawnableEnemies(int strengthFactorLeft) {
        ArrayList<TypEnemy> spawnableEnemies = new ArrayList<>();
        for (TypEnemy typEnemy : this.typyEnemies) {
            if (typEnemy.getStrengthFactor() <= strengthFactorLeft) {
                spawnableEnemies.add(typEnemy);
            }
        }
        return spawnableEnemies;
    }
    private int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
    private void chooseAndSpawnEnemy() {
        if (!this.waveOfEnemies.isEmpty()) {
            if (this.waveOfEnemies.contains(TypEnemy.HEAVY_RYTIER)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.HEAVY_RYTIER);
            } else if (this.waveOfEnemies.contains(TypEnemy.RYTIER)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.RYTIER);
            } else if (this.waveOfEnemies.contains(TypEnemy.NAJAZDNIK)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.NAJAZDNIK);
            } else if (this.waveOfEnemies.contains(TypEnemy.PODKOPAVAC)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.PODKOPAVAC);
            } else if (this.waveOfEnemies.contains(TypEnemy.MINI_RYTIER)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.MINI_RYTIER);
            } else if (this.waveOfEnemies.contains(TypEnemy.HEALER_SHIELDER)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.HEALER_SHIELDER);
            } else if (this.waveOfEnemies.contains(TypEnemy.RANGE_ATTACKER)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.RANGE_ATTACKER);
            } else if (this.waveOfEnemies.contains(TypEnemy.MAG)) {
                this.pickedEnemy = this.getTypEnemyFromWave(TypEnemy.MAG);
            }
            this.gameController.spawnEnemy(this.pickedEnemy);
            this.enemySpawnTimer = 75 * this.pickedEnemy.getStrengthFactor();
            this.pickedEnemy = null;
        }
    }
    private TypEnemy getTypEnemyFromWave(TypEnemy typEnemy) {
        TypEnemy typEnemy1 = null;
        for (TypEnemy enemyInWave : this.waveOfEnemies) {
            if (typEnemy == enemyInWave) {
                typEnemy1 = enemyInWave;
                break;
            }
        }
        this.waveOfEnemies.remove(typEnemy1);
        return typEnemy1;
    }
    public void freeze() {
        this.manager.stopManagingObject(this);
    }
}
