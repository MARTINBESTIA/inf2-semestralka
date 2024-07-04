package matematika;

/**
 * Reprezentácia bodu XY na plátne
 */
public class PoziciaXY {
    private int x;
    private int y;

    /**
     * inicializácia bodu
     * @param x hodnota x
     * @param y hodnota y
     */
    public PoziciaXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return vráti x bodu
     */
    public int getX() {
        return this.x;
    }

    /**
     *
     * @return vráti y bodu
     */
    public int getY() {
        return this.y;
    }

    /**
     * nastavý hodnotu x na hodnotu v parametri
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *  nastavý hodnotu y na hodnotu v parametri
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
}
