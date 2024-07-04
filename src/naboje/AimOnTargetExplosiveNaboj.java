package naboje;

import enemaci.Enemy;
import veze.StrielajucaVeza;

import java.util.List;

/**
 * Náboj, ktorý letí na nepriateľa a po doletení vybuchne a ubere nejaké životy všetkým rádiuse výbuchu
 */
public class AimOnTargetExplosiveNaboj extends AimOnTargetNaboj {
    private List<Enemy> enemies;
    private final int explosionRadius;

    /**
     * Inicialzácia Náboja
     * @param x
     * @param y
     * @param typNaboja
     * @param target
     * @param strielajucaVeza
     * @param enemies
     * @param explosionRadius
     */
    public AimOnTargetExplosiveNaboj(int x, int y, TypNaboja typNaboja, Enemy target, StrielajucaVeza strielajucaVeza, List<Enemy> enemies, int explosionRadius) {
        super(x, y, typNaboja, target, strielajucaVeza);
        this.enemies = enemies;
        this.explosionRadius = explosionRadius;
    }

    /**
     * Spôsob lietania rovnaký ako v AimOnTargetNaboj
     */
    @Override
    public void nabojLeti() {
        super.nabojLeti();
    }

    private boolean hitByExplosion(Enemy enemy) {
        return (Math.sqrt(Math.pow(this.getX() - enemy.getX(), 2) + Math.pow(this.getY() - enemy.getY(), 2)) <= this.explosionRadius);
    }
    @Override
    protected void znicNaboj() {
        for (Enemy enemy : this.enemies) {
            if (this.hitByExplosion(enemy) && enemy != this.getTarget()) {
                enemy.recieveDamage(this.getTypNaboja().getDamage() / 2);
            }
        }
        super.znicNaboj();
    }
}
