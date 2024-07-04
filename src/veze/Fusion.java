package veze;

import efekty.Paprsok;
import enemaci.Enemy;
import matematika.Vektor;
import naboje.AimOnTargetExplosiveNaboj;
import naboje.AimOnTargetNaboj;
import naboje.PenetratingNaboj;
import naboje.TypNaboja;
import java.util.ArrayList;
import java.util.List;

/**
 *Strielajúca veža, ktorá striela tri rôzne náboje, AimOnTarget, AimOnTargetExplosion, a penetrating
 */
public class Fusion extends StrielajucaVeza {
    private int chargeNumber;
    private int pocetZamieritelnychEnemies;

    /**
     * Inicializuje vežu
     * @param x
     * @param y
     * @param enemiesNaPloche
     */
    public Fusion(int x, int y, List<Enemy> enemiesNaPloche) {
        super(x, y, TypVeze.FUSION, enemiesNaPloche, 64, 100);
        super.setShotOrigin(this.getX() - 5, this.getY() - 50);
        this.pocetZamieritelnychEnemies = 3;
        this.chargeNumber = 1;
    }

    /**
     * Vystrelí konkrétny útok v porádí ak je v dosahu nejaký nepriateľ
     */
    @Override
    public void strielaj() {
        if (this.getSpottedEnemy() != null) {
            this.changeChargesAndShoot();
        }
    }
    private void chargeOne() {
        for (Enemy enemy : this.getThreeClosestEnemies()) {
            super.spawnNaboj(new AimOnTargetNaboj(this.getxShotOrigin(), this.getyShotOrigin(), TypNaboja.FUSIONKANON, enemy, this));
        }
    }
    private void chargeTwo() {
        for (Enemy enemy : this.getThreeClosestEnemies()) {
            Vektor vektor = new Vektor(this.getShotOrigin(), enemy.getPoziciaXY());
            vektor.normalizujVektor(2, 30, 20);
            Paprsok paprsok = new Paprsok(this.getShotOriginPozicia(), TypNaboja.FUSIONLASER);
            paprsok.showPaprsok(vektor);
            super.spawnNaboj(new PenetratingNaboj(this.getxShotOrigin(), this.getyShotOrigin(), vektor, TypNaboja.FUSIONLASER, 1600, enemy, this, this.getEnemiesNaPloche()));
        }
    }
    private void chargeThree() {
        for (Enemy enemy : this.getThreeClosestEnemies()) {
            super.spawnNaboj(new AimOnTargetExplosiveNaboj(this.getxShotOrigin(), this.getyShotOrigin(), TypNaboja.FUSIONEXPLOSIVE, enemy, this, this.getEnemiesNaPloche(), 150));
        }
    }

    private ArrayList<Enemy> getThreeClosestEnemies() {

        ArrayList<Enemy> enemiesOnTarget = new ArrayList<>();
        enemiesOnTarget.addAll(this.getEnemiesNaPloche());
        ArrayList<Enemy> threeClosestEnemies = new ArrayList<>();
        for (Enemy enemy : enemiesOnTarget) {
            if (threeClosestEnemies.size() < this.pocetZamieritelnychEnemies) {
                threeClosestEnemies.add(enemy);
            } else {
                Enemy mostDistantEnemy = null;
                int largestDistance = 0;
                for (Enemy enemy1 : threeClosestEnemies) {
                    if (largestDistance < this.getVzdialenostEnemyOdVeze(enemy1)) {
                        mostDistantEnemy = enemy1;
                        largestDistance = this.getVzdialenostEnemyOdVeze(enemy1);
                    }
                }
                if (largestDistance > this.getVzdialenostEnemyOdVeze(enemy)) {
                    threeClosestEnemies.remove(mostDistantEnemy);
                    threeClosestEnemies.add(enemy);
                }
            }
        }
        return threeClosestEnemies;
    }
    private void changeChargesAndShoot() {
        switch (this.chargeNumber) {
            case 1 -> {
                this.chargeOne();
            }
            case 2 -> {
                this.chargeTwo();
            }
            case 3 -> {
                this.chargeThree();
            }
        }
        if (this.chargeNumber >= 3) {
            this.chargeNumber = 1;
        } else {
            this.chargeNumber++;
        }
    }

    /**
     * Zvýši sa počet zamieriteľných nepriateľov
     */
    @Override
    public void boost() {
        this.pocetZamieritelnychEnemies += 1;
    }

    /**
     * Počet zamieriteľných nepriateľov sa dá do normálu
     */
    @Override
    public void unBoost() {
        this.pocetZamieritelnychEnemies -= 1;
    }
}
