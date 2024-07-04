package enemaci;

import cestaPackage.Cesta;
import naboje.ShieldingNaboj;
import naboje.TypNaboja;

import java.util.List;

/**
 * Nepriateľ, ktorý ešte okrem iného oživuje a štítuje nepriateľov
 * @author Martin Šimko
 * @version 1.1
 */
public class HealerShielder extends Enemy {
    private List<Enemy> enemiesNaPloche;
    private int healTimer;
    private int shieldTimer;

    /**
     * Inicializuje nepriateľa, okrem iného aj čas kým môže dať niekomu oživenie a čas kým môže niekomu prideliť štít
     * @param cestaPohybu cesta, po ktorej sa hýbe nepriateľ
     * @param enemiesNaPloche nemodifikovateľmí zoznam nepriateľov na ploche, ktorých môže oživovať a štítovať
     */
    public HealerShielder(Cesta cestaPohybu, List<Enemy> enemiesNaPloche) {
        super(cestaPohybu, TypEnemy.HEALER_SHIELDER);
        this.healTimer = 1300;
        this.shieldTimer = 3000;
        this.enemiesNaPloche = enemiesNaPloche;
    }

    /**
     * Hýbe sa ked uplynie čas na pridelenie štítu alebo oživenia, pridelí niekomu štít alebo niekoho oživí
     */
    @Override
    public void tik() {
        super.tik();
        if (this.getHitPoints() > 0) {
            this.shieldEnemies();
            this.healEnemies();
        }
    }

    private void shieldEnemies() {
        if (this.shieldTimer <= 0) {
            Enemy lowestHpEnemy = null;
            int lowestHpValue = Integer.MAX_VALUE;
            for (Enemy enemy : this.enemiesNaPloche) {
                int hpLeft = (int)((enemy.getHitPoints() / enemy.getTotalHitpoints()) * 100);
                if (hpLeft < lowestHpValue) {
                    lowestHpEnemy = enemy;
                    lowestHpValue = hpLeft;
                }
            }
            new ShieldingNaboj(this.getX() + 20, this.getY(), TypNaboja.SHIELDING, lowestHpEnemy);
            this.shieldTimer = 5000;
        } else {
            this.shieldTimer--;
        }
    }

    private void healEnemies() {
        if (this.healTimer <= 0) {
            for (Enemy enemy : this.enemiesNaPloche) {
                int additionalHp = (int)((enemy.getTotalHitpoints() - enemy.getHitPoints()) * 0.40);
                enemy.addHitpoints(additionalHp);
            }
            this.healTimer = 1600;
        } else {
            this.healTimer--;
        }
    }
}
