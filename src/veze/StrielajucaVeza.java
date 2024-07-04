package veze;

import enemaci.Enemy;
import matematika.PoziciaXY;
import naboje.Naboj;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Veža ktorá dokáže strielať, resp. vytvárať inštancie nábojov
 */
public abstract class StrielajucaVeza extends Veza {
    private int radius;
    private ArrayList<Naboj> vystreleneNaboje;
    private List<Enemy> enemiesNaPloche;
    private Enemy spottedEnemy;
    private int strelaTimer;
    private int xShotOrigin;
    private int yShotOrigin;
    private PoziciaXY shotOrigin;
    private int timeToReload;

    /**
     * Inicializovanie parametrov podľa typu veže
     * @param x
     * @param y
     * @param typVeze
     * @param enemiesNaPloche
     * @param xWidth
     * @param yHeight
     */

    public StrielajucaVeza(int x, int y, TypVeze typVeze, List<Enemy> enemiesNaPloche, int xWidth, int yHeight) {
        super(x, y, typVeze, xWidth, yHeight);
        this.radius = typVeze.getRadius();
        this.enemiesNaPloche = Collections.unmodifiableList(enemiesNaPloche);
        this.vystreleneNaboje = new ArrayList<>();
        this.strelaTimer = typVeze.getReloadTime() / 2;
        this.timeToReload = typVeze.getReloadTime();
    }

    /**
     * Monitorovanie, či sa nenachádzajú nejaký nepriatelia v rádiuse veže
     */
    public void tik() {
        if (this.getHitPoints() > 0) {
            this.enemyUnspotting();
            this.enemyMonitoring();
        }
    }

    protected Enemy getSpottedEnemy() {
        return this.spottedEnemy;
    }
    protected PoziciaXY getShotOrigin() {
        return this.shotOrigin;
    }
    protected void setShotOrigin(int x, int y) {
        this.shotOrigin = new PoziciaXY(x, y);
        this.xShotOrigin = x;
        this.yShotOrigin = y;
    }
    private void enemyUnspotting() {
        if (this.spottedEnemy != null && this.spottedEnemy.getHitPoints() <= 0) {
            this.spottedEnemy = null;
        }
        if (this.spottedEnemy != null) {
            int vzdialenostOdSpottedEnemy = (int)Math.sqrt(Math.pow(Math.abs(this.getX() - this.spottedEnemy.getX()), 2) + Math.pow(Math.abs(this.getY() - this.spottedEnemy.getY()), 2));
            if (vzdialenostOdSpottedEnemy > this.radius) {
                this.spottedEnemy = null;
            }
        }
    }

    /**
     * odstráni naboj zo zoznamu vystrelených náboj
     * @param naboj odkaz na naboj ktory treba odstrániť
     */
    public void znicNaboj(Naboj naboj) {
        if (naboj != null && this.vystreleneNaboje != null) {
            this.vystreleneNaboje.remove(naboj);
        }
    }
    private void enemyMonitoring() {
        if (this.strelaTimer <= 0) {
            int najblizsiEnemyVzdialenost = Integer.MAX_VALUE;
            if (this.spottedEnemy == null) {
                for (Enemy enemy : this.enemiesNaPloche) {
                    int vzdialenost = this.getVzdialenostEnemyOdVeze(enemy);
                    if (vzdialenost < najblizsiEnemyVzdialenost && vzdialenost < this.radius) {
                        this.spottedEnemy = enemy;
                        najblizsiEnemyVzdialenost = vzdialenost;
                    }
                }
            }
            if (this.spottedEnemy != null) {
                int vzdialenost = this.getVzdialenostEnemyOdVeze(this.getSpottedEnemy());
                if (vzdialenost <= this.radius) {
                    this.strielaj();
                    this.resetStrelaTimer();
                }
            }
        } else {
            this.strelaTimer--;
        }
    }

    /**
     * Konkrétna implementácia streľby
     */
    public abstract void strielaj();
    protected void resetStrelaTimer() {
        this.strelaTimer = this.timeToReload;
    }
    protected void spawnNaboj(Naboj naboj) {
        this.vystreleneNaboje.add(naboj);
    }
    protected void setStrelaTimer(int value) {
        this.timeToReload = value;
    }
    protected int getxShotOrigin() {
        return this.xShotOrigin;
    }
    protected int getyShotOrigin() {
        return this.yShotOrigin;
    }
    protected PoziciaXY getShotOriginPozicia() {
        return new PoziciaXY(this.xShotOrigin, this.yShotOrigin);
    }
    protected int getStrelaTimer() {
        return this.timeToReload;
    }
    protected List<Enemy> getEnemiesNaPloche() {
        return Collections.unmodifiableList(this.enemiesNaPloche);
    }
    protected int getVzdialenostEnemyOdVeze(Enemy enemy) {
        return (int)Math.sqrt(Math.pow(Math.abs(this.getX() - enemy.getX()), 2) + Math.pow(Math.abs(this.getY() - enemy.getY()), 2));
    }

    @Override
    protected void removeReferences() {
        super.removeReferences();
        this.vystreleneNaboje = null;
        this.enemiesNaPloche = null;
        this.spottedEnemy = null;
        this.shotOrigin = null;
    }
}
