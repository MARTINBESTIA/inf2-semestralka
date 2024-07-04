package naboje;

import enemaci.Enemy;
import matematika.Vektor;
import veze.StrielajucaVeza;

import java.util.ArrayList;
import java.util.List;

/**
 * Náboj, ktorý ktorý zoberie na začiatku pozíciu najbližšieho nepriateľa, podľa neho vystrelí náboj ktorý sa nezničí pri kontakte s
 * terčom ale dá damage všetkým nepriateľom, ktoré stoja v dráhe náboja
 */
public class PenetratingNaboj extends Naboj {
    private List<Enemy> enemiesNaPloche;
    private ArrayList<Enemy> damagedEnemies;
    private Vektor vektor;
    private final int travelDistance;

    /**
     * inicializácia náboja
     * @param x
     * @param y
     * @param vektor
     * @param typNaboja
     * @param travelDistance
     * @param spottedEnemy
     * @param strielajucaVeza
     * @param enemiesNaPloche
     */
    public PenetratingNaboj(int x, int y, Vektor vektor, TypNaboja typNaboja, int travelDistance, Enemy spottedEnemy, StrielajucaVeza strielajucaVeza, List<Enemy> enemiesNaPloche) {
        super(x, y, typNaboja, spottedEnemy, strielajucaVeza);
        this.travelDistance = travelDistance;
        this.damagedEnemies = new ArrayList<>();
        this.enemiesNaPloche = enemiesNaPloche;
        this.vektor = vektor;
    }

    /**
     * Špecifický pohyb náboja
     */
    @Override
    public void nabojLeti() {
        if (this.getTarget() != null) {
            for (int i = 0; i < getRychlostNaboja(); i++) {
                this.pripocitajX(this.vektor.getSmerX());
                this.pripocitajY(this.vektor.getSmerY());
                this.changeImagePosition(this.getX(), this.getY());
                for (Enemy enemy : this.enemiesNaPloche) {
                    if (enemy != null && super.zasiahnutyTarget(enemy)) {
                        if (!this.damagedEnemies.contains(enemy)) {
                            enemy.recieveDamage(this.getDamage());
                            this.damagedEnemies.add(enemy);
                        }
                    }
                }
                if (this.nabojDistanceTravelled()) {
                    this.znicNaboj();
                    break;
                }
            }
        }
    }

    private boolean nabojDistanceTravelled() {
        try {
            return Math.sqrt(Math.pow(this.getX() - this.getStrielajucaVeza().getX(), 2) + Math.pow(this.getY() - this.getStrielajucaVeza().getY(), 2)) > this.travelDistance;
        } catch (NullPointerException e) {
            return true;
        }
    }
}
