package enemaci;

import cestaPackage.Cesta;

/**
 * Konkrétny typ Nepriateľq, ktorý postupne zvyšuje svoju rýchlosť
 * @author Martin Šimko
 * @version 1.1
 */
public class Najazdnik extends Enemy {
    private final int timerZrychlenia = 900;
    private int timer;

    /**
     * Inicializuje nepriateľa, okrem iného čas kedy sa trochu zrýchli
     * @param cestaPohybu cesta, po ktorej sa enemy pohybuje
     */
    public Najazdnik(Cesta cestaPohybu) {
        super(cestaPohybu, TypEnemy.NAJAZDNIK);
        this.timer = this.timerZrychlenia;
    }

    /**
     * Pohybuje sa a po uplynutí času zvýši rýchlosť
     */
    public void tik() {
        super.tik();
        this.addSpeedGradually();
    }
    private void addSpeedGradually() {
        if (this.timer <= 0 && this.getRychlostPohybu() > 1) {
            super.setRychlostPohybu(this.getRychlostPohybu() - 1);
            this.timer = this.timerZrychlenia;
        } else {
            this.timer--;
        }
    }
}
