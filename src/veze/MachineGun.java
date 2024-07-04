package veze;

import enemaci.Enemy;
import matematika.Vektor;
import naboje.MachineGunNaboj;
import java.util.List;

/**
 * Strielajuca veža, spawnujúca MachineGun náboje
 */
public class MachineGun extends StrielajucaVeza {
    private final int[] shotOriginPivotsX = new int[] {-5, -15, 15};
    private final int[] shotOriginPivotsY = new int[] {-40, -35, -35};
    private int indexOrigin = 0;
    private boolean boosted;

    /**
     * Inicializuje MachienGun
     * @param x
     * @param y
     * @param enemiesNaPloche
     */
    public MachineGun(int x, int y, List<Enemy> enemiesNaPloche) {
        super(x, y, TypVeze.MACHINEGUN, enemiesNaPloche, 100, 100);
        super.setShotOrigin(this.getX() + this.shotOriginPivotsX[this.indexOrigin], this.getY() + this.shotOriginPivotsY[this.indexOrigin]);
        this.boosted = false;
    }

    /**
     * Vystrelí náboj a zmení pozíciu z ktorej má vystreliť
     */
    @Override
    public void strielaj() {
        Vektor vektor = new Vektor(this.getShotOrigin(), this.getSpottedEnemy().getPoziciaXY());
        vektor.normalizujVektor(2, 30, 8);
        super.spawnNaboj(new MachineGunNaboj<Enemy>(this.getxShotOrigin(), this.getyShotOrigin(), vektor, this.getTypVeze().getTypNaboja(), this.getSpottedEnemy(), this, this.getEnemiesNaPloche()));
        this.changeOrigin();
    }
    private void changeOrigin() {
        this.indexOrigin++;
        if (this.indexOrigin >= 3) {
            this.indexOrigin = 0;
        }
        super.setShotOrigin(this.getX() + this.shotOriginPivotsX[this.indexOrigin], this.getY() + this.shotOriginPivotsY[this.indexOrigin]);
    }

    /**
     * Skráti reload time veže
     */
    @Override
    public void boost() {
        super.setStrelaTimer(this.getTypVeze().getReloadTime() - 10);
    }

    /**
     * Zruší boost
     */
    @Override
    public void unBoost() {
        super.setStrelaTimer(this.getTypVeze().getReloadTime());
    }
}
