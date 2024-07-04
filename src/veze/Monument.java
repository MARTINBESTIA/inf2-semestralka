package veze;

import hlavnyBalicek.Clickable;


import java.util.List;

/**
 * Veža, ktorá okrem iného dokáže boostnuť ostatné veže, len nie iné monumenty
 */
public class Monument extends Veza {
    private Veza boostedVeza;
    private List<Clickable> klikatelneObjekty;

    /**
     * Inicializáci veže
     * @param x
     * @param y
     * @param klikatelneObjekty
     */
    public Monument(int x, int y, List<Clickable> klikatelneObjekty) {
        super(x, y, TypVeze.MONUMENT, 100, 100);
        this.klikatelneObjekty = klikatelneObjekty;
    }

    /**
     * Unboostne vežu ak už monument nemá životy, alebo ak je boostnutá veža zničená
     */
    public void tik() {
        if (this.boostedVeza != null && ((this.boostedVeza.getHitPoints() == 0) || this.getHitPoints() == 0)) {
            this.boostedVeza.unBoost();
            this.boostedVeza = null;
        }
    }

    /**
     * Vykoná boost na vežu ak sa na nejakú vežu kliklo
     * @param x x súradnica kliknutia
     * @param y y súradnica kliknutia
     */
    public void klik(int x, int y) {
        if (this.boostedVeza == null) {
            for (Clickable clickable : this.klikatelneObjekty) {
                if (clickable.isClicked(x, y) && clickable instanceof Veza && clickable != this) {
                    ((Veza)clickable).boost();
                    this.boostedVeza = ((Veza)clickable);
                }
            }
        }
    }

    /**
     * Žiadny boost sa nevykoná
     */
    @Override
    public void boost() {

    }

    @Override
    public void unBoost() {

    }
    private void unboostVeza() {
        if (this.boostedVeza != null && ((this.boostedVeza.getHitPoints() <= 0) || this.getHitPoints() <= 0)) {
            this.boostedVeza.unBoost();
            this.boostedVeza = null;
        }
    }
}
