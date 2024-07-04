package hlavnyBalicek;

import fri.shapesge.Manager;
import matematika.PoziciaXY;

/**
 * Trieda, ktorej sa dá uberať hp, resp má nejaké životy
 */
public abstract class HittableObject {
    private Manager manager;
    private PoziciaXY poziciaXY;
    private int hitPoints;
    private int xImageWidth;
    private int yImageWidth;

    /**
     * Spravovanie manžera daného objektu
     */
    public HittableObject() {
        this.manager = new Manager();
        this.manager.manageObject(this);
    }

    /**
     * @return Vráti pozíciu, kde sa nachádza objekt
     */
    public PoziciaXY getPoziciaXY() {
        return this.poziciaXY;
    }

    /**
     * @return Vráti počet zostatých životov
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Ubere počet životov o hodnotu parametra damage
     * @param damage počet ubraných hp
     */
    public void recieveDamage(int damage) {
        this.hitPoints -= damage;
    }

    /**
     * nastavý šírku hitboxu
     * @param yImageWidth
     */
    public void setyImageWidth(int yImageWidth) {
        this.yImageWidth = yImageWidth;
    }

    /**
     * Nastavý výšku hitboxu
     * @param xImageWidth
     */
    public void setxImageWidth(int xImageWidth) {
        this.xImageWidth = xImageWidth;
    }

    /**
     * @return Vráti šírku objektu
     */
    public int getxImageWidth() {
        return this.xImageWidth;
    }

    /**
     * @return Vráti výšku objektu
     */
    public int getyImageWidth() {
        return this.yImageWidth;
    }
    protected void setPoziciaXY(PoziciaXY poziciaXY) {
        this.poziciaXY = poziciaXY;
    }
    protected void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    /**
     * Manažér prestane spravovať objekt
     */
    public void freeze() {
        this.manager.stopManagingObject(this);
    }
    protected void removeReferences() {
        this.manager.stopManagingObject(this);
        this.poziciaXY = null;
    }
}
