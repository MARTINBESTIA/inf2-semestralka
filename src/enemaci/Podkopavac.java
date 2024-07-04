package enemaci;

import cestaPackage.Cesta;

/**
 * Konkrétny typ Nepriateľq, ktorý sa okrem iného podkopáva pod zem
 * @author Martin Šimko
 * @version 1.1
 */
public class Podkopavac extends Enemy {
    private int timerNaPodkopanie = 1200;
    private int timerNaVykopanie = 750;
    private int uholObrazku = 0;
    private PodkopavacStav stav;

    /**
     * Inicializuje nepriateľa, okrem iného mu nastavý počiatočný stav na chodenie po zemi
     * @param cestaPohybu cesta po ktorej sa hýbe nepriateľ
     */
    public Podkopavac(Cesta cestaPohybu) {
        super(cestaPohybu, TypEnemy.PODKOPAVAC);
        this.stav = PodkopavacStav.CHODENIE_PO_ZEMI;
    }

    /**
     * Vykonáva rôzne veci na základe stavu. Stav Chodenie po zemi normálne chodí po zemi, Podkopávanie meni rotáciu svojho obrázku,
     * Chodenie po zemou chodi pod zemou, Vykopávanie tiež mení rotáciu svojho obrázku len v opačnom poradí
     */
    public void tik() {
        if (this.getIndexPolickaCesty() < this.getIndexPoslednehoPolicka() - 3) {
            switch (this.stav) {
                case CHODENIE_PO_ZEMI -> {
                    super.pohybujSa(0);
                    this.timerNaPodkopanie--;
                    if (this.timerNaPodkopanie <= 0) {
                        this.stav = PodkopavacStav.PODKOPAVANIE;
                        this.timerNaVykopanie = 750;
                    }
                }
                case PODKOPAVANIE -> {
                    this.podkopavanie();
                }
                case CHODENIE_POD_ZEMOU -> {
                    super.pohybujSa(1000);
                    this.timerNaVykopanie--;
                    if (this.timerNaVykopanie <= 0) {
                        this.stav = PodkopavacStav.VYKOPAVANIE;
                        super.setY(this.getY() - 1000);
                        super.posunObrazok();
                        this.timerNaPodkopanie = 1200;
                    }
                }
                case VYKOPAVANIE -> {
                    this.vykopavanie();
                }
            }
        }
        super.updateHpText();

    }
    private void podkopavanie() {
        if (this.uholObrazku > -180) {
            this.uholObrazku--;
            this.rotateEnemy(this.uholObrazku);
        } else {
            this.stav = PodkopavacStav.CHODENIE_POD_ZEMOU;
            this.uholObrazku = -180;
        }
    }
    private void vykopavanie() {
        if (this.uholObrazku < 0) {
            this.uholObrazku++;
            this.rotateEnemy(this.uholObrazku);
        } else {
            this.stav = PodkopavacStav.CHODENIE_PO_ZEMI;
            this.uholObrazku = 0;
        }
    }

}
