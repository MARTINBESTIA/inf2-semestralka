package naboje;

import fri.shapesge.Circle;
import fri.shapesge.Manager;
import hlavnyBalicek.HittableObject;
import matematika.PoziciaXY;
import veze.StrielajucaVeza;

/**
 * Tredia ktorá drží všetky spoločné vlastnosti nábojov
 */
public abstract class Naboj {
    private Manager manager;
    private PoziciaXY pociatocnaPoziciaXY;
    private PoziciaXY aktualnaPoziciaXY;
    private HittableObject target;
    private boolean aimOnTarget;
    private Circle imageNaboja;
    private TypNaboja typNaboja;
    private StrielajucaVeza strielajucaVeza;
    private boolean doletel = false;
    private int sirkaNaboja;

    /**
     * Inicializuje hodnoty podľa typu náboja
     * @param x pozícia spawnutia na osi x
     * @param y pozícia spawnutia na osi y
     * @param typNaboja typ náboja pomocou ktorého sa inicialzujú niektoré atribúty
     * @param target odkaz na objekt na ktorý náboj letí
     * @param strielajucaVeza veža ktorá daný objekt vystrelila
     */
    public Naboj(int x, int y, TypNaboja typNaboja, HittableObject target, StrielajucaVeza strielajucaVeza) {
        this.pociatocnaPoziciaXY = new PoziciaXY(x, y);
        this.aktualnaPoziciaXY = new PoziciaXY(x, y);
        this.typNaboja = typNaboja;
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.target = target;
        this.aimOnTarget = typNaboja.getAimOnTarget();
        this.strielajucaVeza = strielajucaVeza;
        this.imageNaboja = new Circle(x, y);
        this.imageNaboja.changeColor(typNaboja.getFarbaNaboja());
        this.sirkaNaboja = typNaboja.getSirkaNaboja();
        this.imageNaboja.changeSize(this.sirkaNaboja);
        this.imageNaboja.makeVisible();
    }

    /**
     * Hýbe náboj za objektom a zničí ho ak je náhodou mimo plochy
     */
    public void nabojTik() {
        if (!this.doletel) {
            this.destroyIfOutOfCanvas();
            this.nabojLeti();
        }
    }

    /**
     * jednotlivé spôsoby pohybu náboja za nepriateľom
     */
    public abstract void nabojLeti() ;

    protected HittableObject getTarget() {
        return this.target;
    }
    protected boolean vyletel() {
        return this.doletel;
    }
    protected void setVyletelTrue() {
        this.doletel = true;
    }

    protected int getX() {
        return this.aktualnaPoziciaXY.getX();
    }

    protected int getY() {
        return this.aktualnaPoziciaXY.getY();
    }
    protected PoziciaXY getPociatocnaPoziciaXY() {
        return this.pociatocnaPoziciaXY;
    }
    protected void znicNaboj() {
        //if (this.target != null) {
        this.hideNaboj();
        this.setTargetNull();
        this.setVyletelTrue();
        if (this.strielajucaVeza != null) {
            this.getStrielajucaVeza().znicNaboj(this);
        }
        this.removeReferences();
       // }
    }
    protected boolean zasiahnutyTarget(HittableObject target) {
        return (Math.abs(this.getX() - target.getPoziciaXY().getX()) <= Math.abs(target.getxImageWidth() / 2 + this.typNaboja.getSirkaNaboja() / 2) &&
                Math.abs(this.getY() - target.getPoziciaXY().getY()) <= Math.abs(target.getyImageWidth() / 2) + this.typNaboja.getSirkaNaboja() / 2);
    }

    /**
     * zničí náboj ak je mimo plochy
     */
    public void destroyIfOutOfCanvas() {
        if (this.target != null) {
            if (this.getAktualnaPoziciaXY() == null) {
                this.znicNaboj();
            }
            if (this.getX() > 1800 || this.getX() < -900 || this.getY() < -900 || this.getY() > 1800) { //||
                //.target.getPoziciaXY().getX() > 900 || this.target.getPoziciaXY().getX() < -100 || this.target.getPoziciaXY().getY() < -100 || this.target.getPoziciaXY().getY() > 900) {
                this.znicNaboj();
            }
        }
    }
    protected void pripocitajY(int y) {
        this.aktualnaPoziciaXY.setY(this.getAktualnaPoziciaXY().getY() + y);
    }
    protected void pripocitajX(int x) {
        this.aktualnaPoziciaXY.setX(this.getAktualnaPoziciaXY().getX() + x);
    }
    protected int getDamage() {
        return this.typNaboja.getDamage();
    }
    protected void setTargetNull() {
        this.target = null;
    }
    protected int getRychlostNaboja() {
        return this.typNaboja.getRychlostNaboja();
    }
    protected StrielajucaVeza getStrielajucaVeza() {
        return this.strielajucaVeza;
    }
    protected void changeImagePosition(int x, int y) {
        this.imageNaboja.changePosition(x, y);
    }
    protected void hideNaboj() {
        this.imageNaboja.makeInvisible();
    }
    protected TypNaboja getTypNaboja() {
        return this.typNaboja;
    }

    /**
     * @return Vráti aktuálnu pozíciu náboja
     */
    public PoziciaXY getAktualnaPoziciaXY() {
        return this.aktualnaPoziciaXY;
    }
    private void removeReferences() {
        this.manager.stopManagingObject(this);
        this.manager = null;
        this.pociatocnaPoziciaXY = null;
        this.target = null;
        this.imageNaboja = null;
        this.typNaboja = null;
        this.strielajucaVeza = null;
        System.gc();
    }

}
