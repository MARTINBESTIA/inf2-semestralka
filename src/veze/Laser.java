package veze;

import efekty.Paprsok;
import enemaci.Enemy;
import matematika.Vektor;
import naboje.PenetratingNaboj;
import naboje.TypNaboja;
import java.util.List;

/**
 * Veža, ktorá strieľa penetrating náboje a vytvorí paprsok
 */
public class Laser extends StrielajucaVeza {

    private Vektor vektor;
    private Paprsok paprsok;
    private TypNaboja laserNaVystrelenie;

    /**
     * Incializácia veže
     * @param x
     * @param y
     * @param enemiesNaPloche
     */
    public Laser(int x, int y, List<Enemy> enemiesNaPloche) {
        super(x, y, TypVeze.LASER, enemiesNaPloche, 100, 100);
        super.setShotOrigin(this.getX() - 5, this.getY() - 20);
        this.laserNaVystrelenie = this.getTypVeze().getTypNaboja();
        this.paprsok = new Paprsok(this.getShotOriginPozicia(), this.laserNaVystrelenie);
    }

    /**
     * Vystrelí náboj a ukáže paprsok
     */
    @Override
    public void strielaj() {
        this.vektor = new Vektor(this.getShotOrigin(), this.getSpottedEnemy().getPoziciaXY());
        this.vektor.normalizujVektor(2, 15, 10);
        this.paprsok.showPaprsok(this.vektor);
        super.spawnNaboj(new PenetratingNaboj(this.getxShotOrigin(), this.getyShotOrigin(), this.vektor, this.laserNaVystrelenie, 1600, this.getSpottedEnemy(), this, this.getEnemiesNaPloche()));
    }

    /**
     * Zamení náboj za silnejší
     */
    @Override
    public void boost() {
        this.laserNaVystrelenie = TypNaboja.LASER_BOOSTED;
        this.paprsok = new Paprsok(this.getShotOriginPozicia(), this.laserNaVystrelenie);
    }

    /**
     * Zruší boost
     */
    @Override
    public void unBoost() {
        this.laserNaVystrelenie = TypNaboja.LASER;
        this.paprsok = new Paprsok(this.getShotOriginPozicia(), this.laserNaVystrelenie);
    }
}
