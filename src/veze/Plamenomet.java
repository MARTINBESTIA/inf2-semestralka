package veze;

import enemaci.Enemy;
import matematika.Vektor;
import naboje.PenetratingNaboj;

import java.util.List;

/**
 * Trieda ktorá spawnuje veľa PenetratingNabojov
 */
public class Plamenomet extends StrielajucaVeza {
    private boolean boosted;
    private int towerHealingTimer = 200;

    /**
     * Inicializácia Veze
     * @param x
     * @param y
     * @param enemiesNaPloche
     */
    public Plamenomet(int x, int y, List<Enemy> enemiesNaPloche) {
        super(x, y, TypVeze.PLAMENOMET, enemiesNaPloche, 100, 100);
        super.setShotOrigin(this.getX() - 10, this.getY() - 50);
        this.boosted = false;
    }

    /**
     * Pridávanie trošky hp ak je veža boostnutá
     */
    public void boostTik() {
        if (this.boosted) {
            if (this.towerHealingTimer <= 0) {
                super.addHitPoints(10);
                this.towerHealingTimer = 200;
            } else {
                this.towerHealingTimer--;
            }
        }
    }

    /**
     * Vystrelí náboj resp. spawnuje Penetrating náboje podľa TypuNáboja
     */
    @Override
    public void strielaj() {
        Vektor vektor = new Vektor(this.getShotOrigin(), this.getSpottedEnemy().getPoziciaXY());
        vektor.normalizujVektor(2, 20, 10);
        super.spawnNaboj(new PenetratingNaboj(this.getxShotOrigin(), this.getyShotOrigin(), vektor, this.getTypVeze().getTypNaboja(), 180, this.getSpottedEnemy(), this, this.getEnemiesNaPloche()));
    }

    @Override
    public void boost() {
        this.boosted = true;
    }

    @Override
    public void unBoost() {
        this.boosted = false;
    }
}
