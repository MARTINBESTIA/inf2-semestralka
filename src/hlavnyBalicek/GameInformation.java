package hlavnyBalicek;

import fri.shapesge.FontStyle;
import fri.shapesge.Manager;
import fri.shapesge.TextBlock;
import veze.TypVeze;

/**
 * Trieda zpbrazujúca informácie o hre
 */
public class GameInformation {
    private Manager manager;
    private TextBlock pocetMinciText;
    private TextBlock pocetVln;
    private TextBlock oznacenaVeza;
    private TextBlock casPrezitia;
    private String cas;
    private String pocetVlnStr;

    /**
     * inicializácia textových polí
     */
    public GameInformation() {
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.pocetMinciText = new TextBlock(String.format("Počet mincí: %d", 0), 10, 40);
        this.pocetVln = new TextBlock(String.format("Vlna: %d", 0), 10, 80);
        this.oznacenaVeza = new TextBlock("Žiadna označená veža", 580, 40);
        this.casPrezitia = new TextBlock("00:00", 600, 80);
        this.pocetMinciText.changeFont("Arial", FontStyle.BOLD, 30);
        this.pocetVln.changeFont("Arial", FontStyle.BOLD, 30);
        this.casPrezitia.changeFont("Arial", FontStyle.BOLD, 30);
        this.oznacenaVeza.changeFont("Arial", FontStyle.BOLD, 10);
        this.pocetMinciText.makeVisible();
        this.casPrezitia.makeVisible();
        this.pocetVln.makeVisible();
        this.oznacenaVeza.makeVisible();
    }

    /**
     * Aktualizuje text s poctom minci
     * @param pocetMinci
     */
    public void updatePocetMinci(int pocetMinci) {
        this.pocetMinciText.changeText(String.format("Počet mincí: %d", pocetMinci));
    }

    /**
     * Aktualizuje počet, koľko vln nepriateľov sa spawnlo
     * @param pocetVln
     */
    public void updatePoceVln(int pocetVln) {
        this.pocetVln.changeText(String.format("Vlna: %d", pocetVln));
        this.pocetVlnStr = pocetVln + "";
    }

    /**
     * Typ veže ktorú má uživateľ označenú
     * @param typVeze
     */
    public void updateOznacenaVeza(TypVeze typVeze) {
        if (typVeze != null) {
            this.oznacenaVeza.changeText(String.format("Označená veža: %s", typVeze));
        } else {
            this.oznacenaVeza.changeText(String.format("Žiadna označená veža"));
        }
    }

    /**
     * Aktualizuje čas, ktorý uživateľ prežil
     * @param minuty int minút
     * @param sekudny int sekúnd
     */
    public void updateCasPrezitia(int minuty, int sekudny) {
        if (sekudny < 10 && minuty < 10) {
            this.casPrezitia.changeText(String.format("0%d:0%d", minuty, sekudny));
            this.cas = String.format("0%d:0%d", minuty, sekudny);
        } else if (sekudny < 10) {
            this.casPrezitia.changeText(String.format("%d:0%d", minuty, sekudny));
            this.cas = String.format("%d:0%d", minuty, sekudny);
        } else if (minuty < 10) {
            this.casPrezitia.changeText(String.format("0%d:%d", minuty, sekudny));
            this.cas = String.format("0%d:%d", minuty, sekudny);
        } else {
            this.casPrezitia.changeText(String.format("%d:%d", minuty, sekudny));
            this.cas = String.format("%d:%d", minuty, sekudny);
        }
    }

    /**
     * @return Vráti Stringovú reprezentáciu času
     */
    public String getCas() {
        return this.cas;
    }

    /**
     * @return Vráti stringovú reprezentáciu počtu prežitých vĺn
     */
    public String getPocetVln() {
        return this.pocetVlnStr;
    }

    /**
     * Skryje všetky texty
     */
    public void skry() {
        this.oznacenaVeza.makeInvisible();
        this.pocetVln.makeInvisible();
        this.pocetMinciText.makeInvisible();
        this.casPrezitia.makeInvisible();

    }
}
