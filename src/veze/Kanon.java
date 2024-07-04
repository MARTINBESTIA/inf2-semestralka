package veze;

import enemaci.Enemy;
import naboje.AimOnTargetNaboj;

import java.util.List;

/**
 * Strielajúca veža, ktorá strieľa AimOnTargetNaboj
 */
public class Kanon extends StrielajucaVeza {
    private boolean boosted;
    private boolean enabledSecondNaboj;
    private int secondNabojTimer = 7;

    public Kanon(int x, int y, List<Enemy> enemiesNaPloche) {
        super(x, y, TypVeze.KANON, enemiesNaPloche, 100, 100);
        super.setShotOrigin(this.getX() - 10, this.getY() - 50);
        this.boosted = false;
        this.enabledSecondNaboj = false;
    }

    /**
     * Ak je veža boostnuta tak táto metóda vystrelí dodatočný druhý náboj
     */
    public void boostTik() {
        if (this.enabledSecondNaboj) {
            if (this.secondNabojTimer <= 0) {
                if (this.getSpottedEnemy() != null) {
                    super.spawnNaboj(new AimOnTargetNaboj(this.getxShotOrigin(), this.getyShotOrigin(), this.getTypVeze().getTypNaboja(), this.getSpottedEnemy(), this));
                    this.enabledSecondNaboj = false;
                    this.secondNabojTimer = 7;
                }
            } else {
                this.secondNabojTimer--;
            }
        }
    }

    /**
     * Výstrelí náboj
     */
    @Override
    public void strielaj() {
        super.spawnNaboj(new AimOnTargetNaboj(this.getxShotOrigin(), this.getyShotOrigin(), this.getTypVeze().getTypNaboja(), this.getSpottedEnemy(), this));
        if (this.boosted) {
            this.enabledSecondNaboj = true;
        }
    }

    /**
     * Boostne vežu
     */
    @Override
    public void boost() {
        this.boosted = true;
    }

    /**
     * Zruší boost
     */
    @Override
    public void unBoost() {
        this.boosted = false;
    }
}
