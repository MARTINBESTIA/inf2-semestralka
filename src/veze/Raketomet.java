package veze;

import enemaci.Enemy;
import naboje.AimOnTargetExplosiveNaboj;

import java.util.Collections;
import java.util.List;

/**
 * Veža ktorá spawnuje AimOnTargetExplosiveNaboj a špecifikuje rádius výbuchu
 */
public class Raketomet extends StrielajucaVeza {

    private int explosionRadius;

    public Raketomet(int x, int y, List<Enemy> enemies) {
        super(x, y, TypVeze.RAKETOMET, enemies, 100, 100);
        super.setShotOrigin(this.getX(), this.getY() - 75);
        this.explosionRadius = 150;
    }

    @Override
    public void strielaj() {
        super.spawnNaboj(new AimOnTargetExplosiveNaboj(this.getxShotOrigin(), this.getyShotOrigin(), this.getTypVeze().getTypNaboja(), this.getSpottedEnemy(), this, Collections.unmodifiableList(this.getEnemiesNaPloche()), this.explosionRadius));
    }

    /**
     * zvačší rádius výbuchu
     */
    @Override
    public void boost() {
        this.explosionRadius = 250;
    }

    /**
     * Dá zlepšenie preč
     */
    @Override
    public void unBoost() {
        this.explosionRadius = 150;
    }
}
