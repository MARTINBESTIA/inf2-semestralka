package enemaci;

import cestaPackage.Cesta;
import efekty.Shield;
import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.TextBlock;
import hlavnyBalicek.HittableObject;
import matematika.PoziciaXY;
import static java.lang.Math.abs;

/**
 * Trieda, ktorá spája všetky spoločné vlastnosti všetkých nepriateľov
 * @author Martin Šimko
 * @version 1.1
 */
public class Enemy extends HittableObject {
    private int vzdialenostMedziBodmiCesty;
    private Cesta cestaPohybu;
    private int indexPolickaCesty;
    private int indexPoslednehoPolicka;
    private Image obrazok;
    private int rychlostPohybu;
    private TextBlock hpLeftText;
    private TypEnemy typEnemy;
    private Shield shield;
    private boolean isImobilized;

    /**
     * Inicializácia parametrov Nepriateľa na základe typu nepriateľa
     * @param cestaPohybu cesta, po ktorej sa nepriateľ pohybuje
     * @param typEnemy Enum typ nepriateľa, ktorý determinuje parametre nepriateľa
     */
    public Enemy(Cesta cestaPohybu, TypEnemy typEnemy) {
        super();
        this.typEnemy = typEnemy;
        this.cestaPohybu = cestaPohybu;
        super.setHitPoints(typEnemy.getTotalHp());
        super.setxImageWidth(typEnemy.getImageOffsetX());
        super.setyImageWidth(typEnemy.getImageOffsetY());
        this.indexPolickaCesty = 0;
        this.vzdialenostMedziBodmiCesty = 0;
        this.indexPoslednehoPolicka = this.cestaPohybu.getBodyCestyLength();
        super.setPoziciaXY(new PoziciaXY(this.cestaPohybu.getElement(this.indexPolickaCesty).getX(), this.cestaPohybu.getElement(this.indexPolickaCesty).getY()));
        this.rychlostPohybu = typEnemy.getRychlostPohybu();
        this.obrazok = new Image(typEnemy.getCestaObr(), this.getX() - this.getImgOffsetX(), this.getY() - this.getImgOffsetY());
        this.obrazok.makeVisible();
        this.hpLeftText = new TextBlock(super.getHitPoints() / this.getTotalHitpoints() + "%", this.getX(), this.getY() + 60);
        this.hpLeftText.changeFont("Arial", FontStyle.BOLD, 15);
        this.hpLeftText.changeColor("green");
        this.hpLeftText.makeVisible();
        this.isImobilized = false;
    }

    /**
     * Aktualizovanie pozície a nadpisu s počtom životov nad nepriateľom
     */
    public void tik() {
        this.pohybujSa(0);
        this.updateHpText();
    }

    /**
     * Metóda, ktorá hýbe s nepriateľom po ceste, pozerá sa na bod pred sebou, a na základe vzialenosti dvoch po sebe idúcich boodv sa snaží
     * minimalizovať rozdiel vo vzdialenostiach medzi dvoma bodmi, aby dochádzalo ku rovnomernému pohybovaniu
     * @param vertikalnyOffset dodatočná hodnota ktorá sa pripočítava ku celkovej pozícií Y. Podkopávač ju má pri podkopávaní na 1000, aby
     *                         som vytvoril efekt, že je podkopaný, pričom sa stále pohybuje po ceste
     */
    protected void pohybujSa(int vertikalnyOffset) {
        if (!this.isImobilized) {
            if (this.vzdialenostMedziBodmiCesty <= 1) {
                this.setX(this.getCestaPohybu().getElement(this.getIndexPolickaCesty() + 1).getX());
                this.setY(this.getCestaPohybu().getElement(this.getIndexPolickaCesty() + 1).getY() + vertikalnyOffset);
                this.posunObrazok();
                this.setIndexPolickaCesty(this.getIndexPolickaCesty() + 1);
                int vzdialenostY = abs(this.getCestaPohybu().getElement(this.getIndexPolickaCesty() + 1).getY() - this.getCestaPohybu().getElement(this.getIndexPolickaCesty()).getY());
                int vzdialenostX = abs(this.getCestaPohybu().getElement(this.getIndexPolickaCesty() + 1).getX() - this.getCestaPohybu().getElement(this.getIndexPolickaCesty()).getX());
                this.vzdialenostMedziBodmiCesty = (vzdialenostY + vzdialenostX) * this.getRychlostPohybu();
            } else {
                this.vzdialenostMedziBodmiCesty--;
            }
        }
    }

    /**
     * vráti x
     * @return x
     */
    public int getX() {
        return super.getPoziciaXY().getX();
    }

    /**
     * vráti y
     * @return y
     */
    public int getY() {
        return super.getPoziciaXY().getY();
    }

    /**
     * Vráti počet životov nepriateľa
     * @return pocetZivotov
     */
    public int getHitPoints() {
        return super.getHitPoints();
    }

    /**
     * Vráti cestu po ktorej sa Enemy pohybuje
     * @return cesta
     */
    protected Cesta getCestaPohybu() {
        return this.cestaPohybu;
    }

    /**
     * Vráti index cesty, na ktorej sa nachádza nepriateľ
     * @return
     */
    public int getIndexPolickaCesty() {
        return this.indexPolickaCesty;
    }

    /**
     * Vráti index poslednej pozicie XY v poli cesty
     * @return
     */
    public int getIndexPoslednehoPolicka() {
        return this.indexPoslednehoPolicka;
    }

    /**
     * Vráti šírku nepriateľa
     * @return šírka obrázku nepriateľa
     */
    public int getImgOffsetX() {
        return this.typEnemy.getImageOffsetX();
    }
    /**
     * Vráti výšku nepriateľa
     * @return výšku obrázku nepriateľa
     */
    public int getImgOffsetY() {
        return this.typEnemy.getImageOffsetY();
    }

    /**
     * vráti rýchlosť pohybu nepriateľa, ako je uvedené v enume
     * @return rýchlosť pohybu
     */
    protected int getRychlostPohybu() {
        return this.rychlostPohybu;
    }
    /**
     * vráti novú pohybu nepriateľa
     */
    protected void setRychlostPohybu(int novaRychlost) {
        this.rychlostPohybu = novaRychlost;
    }

    /**
     * zmení pozíciu x nepriateľa no hodnotu v parametre x
     * @param x
     */
    protected void setX(int x) {
        super.getPoziciaXY().setX(x);
        this.posunObrazok();
    }
    /**
     * zmení pozíciu x nepriateľa no hodnotu v parametre y
     * @param y
     */
    protected void setY(int y) {
        super.getPoziciaXY().setY(y);
        this.posunObrazok();
    }
    protected void posunObrazok() {
        this.obrazok.changePosition(super.getPoziciaXY().getX() - this.getImgOffsetX(), super.getPoziciaXY().getY() - this.getImgOffsetY());
    }
    protected void setIndexPolickaCesty(int x) {
        this.indexPolickaCesty = x;
    }
    protected void pridajIndexPolickaCesty(int x) {
        this.indexPolickaCesty += x;
    }
    protected int getXPositionAtCestaIndex(int index) {
        return this.cestaPohybu.getElement(index).getX();
    }
    protected int posYAttIndexPolickaCesty(int x) {
        return this.cestaPohybu.getElement(25 + x).getY();
    }
    /**
        Uberie nepriateľovy životy o hodnotu v damage parametry, alebo štítu ak má nepriateľ štít
     */
    public void recieveDamage(int damage) {
        if (this.shield == null) {
            super.recieveDamage(damage);
        } else {
            this.shield.recieveDamage(damage);
        }
    }
    protected void addHitpoints(int hp) {
        super.setHitPoints(super.getHitPoints() + hp);
    }

    /**
     * Metóda ktorá skryje nepriateľa z plátna a ho spravovať manažer
     */
    public void umri() {
        this.skry();
        this.freeze();
    }
    protected void updateHpText() {
        if (this.hpLeftText != null) {
            this.hpLeftText.changeText((int)((double)super.getHitPoints() / (double)this.getTotalHitpoints() * 100.00) + "%");
            this.hpLeftText.changePosition(super.getPoziciaXY().getX(), super.getPoziciaXY().getY() - 70);
        }
    }
    protected void rotateEnemy(int uhol) {
        this.obrazok.changeAngle(uhol);
    }

    /**
     * Vráti celkové životy nepriateľa. Pozor, nie aktuálne.
     * @return celkové životy nepriateľa
     */
    public int getTotalHitpoints() {
        return this.typEnemy.getTotalHp();
    }
    /**
     * Pridelí nepriateľovy nový štít
     */
    public void shieldEnemy() {
        if (this.shield != null) {
            this.removeShield();
        }
        this.shield = new Shield(this);
    }

    /**
     * Odstríni štít z nepriateľa
     */
    public void removeShield() {
        if (this.shield != null) {
            this.shield.skry();
        }
        this.shield = null;
    }
    protected void imobilize() {
        this.isImobilized = true;
    }
    protected void mobilize() {
        this.isImobilized = false;
    }

    /**
     * skryje celého nepriateľa, metódu volá GameController pri resetovaní plátna
     */
    public void skry() {
        this.obrazok.makeInvisible();
        this.hpLeftText.makeInvisible();
        this.removeShield();
    }
}
