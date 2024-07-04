package veze;

import hlavnyBalicek.GameController;
import hlavnyBalicek.Minca;

/***
 * Veža, ktorá generuje mince
 */
public class GoldMine extends Veza {
    private GameController gameController;
    private int coinSpawnTimer = 4000;
    private int coinTimerDeduction;
    private Minca vytazenaMinca;

    public GoldMine(int x, int y, GameController gameController) {
        super(x, y, TypVeze.GOLDMINE, 100, 94);
        this.gameController = gameController;
        this.coinTimerDeduction = 1;
    }

    /**
     * Generuje mince kým nie je veža zničená
     */
    public void tik() {
        if (this.getHitPoints() > 0) {
            this.generateCoins();
        }
    }

    private void generateCoins() {
        if (this.coinSpawnTimer <= 0 && this.vytazenaMinca == null) {
            this.vytazenaMinca = new Minca(this.gameController, true, this);
        } else {
            this.coinSpawnTimer -= this.coinTimerDeduction;
        }
    }

    /**
     * Odstráni mincu zo zoznamu v gamecontroleri pri kliknutí na mincu
     */
    public void odstranMincu() {
        this.gameController.odstranObjektMincu(this.vytazenaMinca);
        this.vytazenaMinca = null;
        this.coinSpawnTimer = 4000;
    }

    /**
     * Zvýši si rýchlosť generovania mincí
     */
    @Override
    public void boost() {
        this.coinTimerDeduction *= 2;
    }

    /**
     * Čas generovania sa dá do pôvodu
     */
    @Override
    public void unBoost() {
        this.coinTimerDeduction /= 2;
    }
}
