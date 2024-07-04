package efekty;
import enemaci.Enemy;
import fri.shapesge.Image;
import fri.shapesge.Manager;
import matematika.PoziciaXY;

/**
 * Trieda ktorá pridelí hráčovy štít, resp, keď ho nepriateľ má, uberajú sa životy štítu a nie nepriateľovy
 * Taktiež ho zobrazuje graficky
 * @author Martin Šimko
 * @version 1.1
 */
public class Shield {
    private final int shieldOffset = 60;
    private int shieldHitpoints = 500;
    private Manager manager;
    private Enemy shieldedEnemy;
    private Image imageShield;
    private PoziciaXY poziciaXY;

    /**
     * Inicializácia štítu, graficky ho zobrazí
     * @param target nepriateľ, ktorý má štít, ukladá sa do atribútu
     */
    public Shield(Enemy target) {
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.shieldedEnemy = target;
        this.poziciaXY = new PoziciaXY(target.getX(), target.getY());
        this.imageShield = new Image("pics/ostatne/shield.png", target.getX() - this.shieldOffset, target.getY() - this.shieldOffset);
        this.imageShield.makeVisible();
    }

    /**
     * Kopírovanie pozície štítu na pozíciu nepriateľa
     */
    public void tik() {
        if (this.shieldedEnemy != null) {
            this.imageShield.changePosition(this.shieldedEnemy.getX() - this.shieldOffset, this.shieldedEnemy.getY() - this.shieldOffset);
        }
    }

    /**
     * Uberá štítu hitpointy, metódu volá enemy, keď je zaštítovaný a trafí ho náboj
     * @param damage damage ktorý sa uberie štítu
     */
    public void recieveDamage(int damage) {
        this.shieldHitpoints -= damage;
        if (this.shieldHitpoints <= 0) {
            this.imageShield.makeInvisible();
            this.shieldedEnemy.removeShield();
            this.shieldedEnemy = null;
        }
    }

    /**
     * vráti x pozíciu štítu
     * @return
     */
    public int getX() {
        return this.poziciaXY.getX();
    }

    /**
     * vráti y pozíciu štítu
     * @return
     */
    public int getY() {
        return this.poziciaXY.getY();
    }

    /**
     * skryje štít
     */
    public void skry() {
        this.imageShield.makeInvisible();
    }

}
