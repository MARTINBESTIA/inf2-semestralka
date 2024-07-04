package veze;

import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.TextBlock;
import hlavnyBalicek.Clickable;
import hlavnyBalicek.HittableObject;
import matematika.PoziciaXY;

/**
 * Trieda ktorá drží všetky spoločné vlastnosti veži
 */
public abstract class Veza extends HittableObject implements Clickable {
    private TypVeze typVeze;
    private Image imageVeze;
    private final int xWidth;
    private final int yHeight;
    private TextBlock hpLeftText;

    /**
     * Inicializácia veže na základe Enumu typVeze
     * @param x pozícia spawnutia X
     * @param y pozícia spawnutia Y
     * @param typVeze typ spawnutej veže
     * @param xWidth širka veže
     * @param yHeight výška veže
     */
    public Veza(int x, int y, TypVeze typVeze, int xWidth, int yHeight) {
        super();
        this.typVeze = typVeze;
        super.setPoziciaXY(new PoziciaXY(x, y));
        super.setHitPoints(typVeze.getHitPoints());
        super.setxImageWidth(typVeze.getxImageWidth());
        super.setyImageWidth(typVeze.getyImageWidth());
        this.imageVeze = new Image(typVeze.getCesta(), super.getPoziciaXY().getX() - xWidth / 2, super.getPoziciaXY().getY() - yHeight / 2);
        this.xWidth = xWidth;
        this.yHeight = yHeight;
        this.imageVeze.makeVisible();
        this.hpLeftText = new TextBlock(super.getHitPoints() / this.typVeze.getHitPoints() + "%", super.getPoziciaXY().getX() - 10, super.getPoziciaXY().getY() - 60);
        this.hpLeftText.changeFont("Arial", FontStyle.BOLD, 15);
        this.hpLeftText.changeColor("green");
        this.hpLeftText.makeVisible();
    }

    /**
     * Postupne uberá životy veži
     */
    public void hptik() {
        if (this.getHitPoints() > 0) {
            this.uberajPostupneHP();
            this.updateHpText();
        }
    }

    private void uberajPostupneHP() {
        this.setHitPoints(super.getHitPoints() - 1);
    }

    /**
     * zničí vežu a skryje ju z plátna
     */
    public void znicVezu() {
        this.imageVeze.makeInvisible();
        this.hpLeftText.makeInvisible();
        this.removeReferences();
    }

    /**
     * @return vráti x pozíciu veže
     */
    public int getX() {
        return super.getPoziciaXY().getX();
    }

    /**
     * @return vráti y pozíciu veže
     */
    public int getY() {
        return super.getPoziciaXY().getY();
    }
    protected void addHitPoints(int hitPoints) {
        setHitPoints(super.getHitPoints() + hitPoints);
    }
    protected void updateHpText() {
        this.hpLeftText.changeText((int)((double)super.getHitPoints() / (double)this.typVeze.getHitPoints() * 100.00) + "%");
    }

    /**
     * @return Vráti enum typu veže
     */
    public TypVeze getTypVeze() {
        return this.typVeze;
    }

    /**
     * konkrétny efekt boostu jednotlivej veži
     */
    public abstract void boost();
    /**
     * vrátenie efektu po booste do normálu
     */
    public abstract void unBoost();
    @Override
    public void efekt() {

    }

    /**
     * Overí, či sa na vežu kliklo
     * @param mouseX x pozícia kliknutia
     * @param mouseY y pozícia kliknutia
     * @return vráti, či sa na danú vežu kliklo
     */
    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return (super.getPoziciaXY().getX() + this.xWidth / 2 >= mouseX && super.getPoziciaXY().getX() - this.xWidth / 2 <= mouseX &&
                super.getPoziciaXY().getY() + this.yHeight / 2 >= mouseY && super.getPoziciaXY().getY() - this.yHeight / 2 <= mouseY);
    }

    /**
     * skryje vežu z plátna
     */
    public void skry() {
        this.imageVeze.makeInvisible();
        this.hpLeftText.makeInvisible();
    }
    protected void removeReferences() {
        super.removeReferences();
        this.hpLeftText = null;
        this.imageVeze = null;
        this.typVeze = null;
        System.gc();
    }

}
