package hlavnyBalicek;

import fri.shapesge.Image;
import fri.shapesge.Manager;
import matematika.Vektor;
import veze.GoldMine;

import java.util.Random;

/**
 * Trieda minca, po kliknutí na mincu sa pridajú mince
 */
public class Minca implements Clickable {
    private final GoldMine goldMine;
    private Manager manager;
    private int x;
    private int y;
    private int imgOffsetX;
    private int imgOffsetY;
    private GameController gameController;
    private boolean staticka;
    private Image obrazok;
    private final String imageCesta = "pics/ostatne/minca.png";
    private Vektor vektor;

    /**
     * Inicializuje mincu
     * @param gameController odkaz na gamecontroller potrebný na volanie mince
     * @param staticka boolean hodnota či sa minca hýba alebo nie
     * @param goldMine odkaz na GoldMine ak mincu vygenerovala goldMine
     */
    public Minca(GameController gameController, boolean staticka, GoldMine goldMine) {
        this.goldMine = goldMine;
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.gameController = gameController;
        this.staticka = staticka;
        this.imgOffsetX = 25;
        this.imgOffsetY = 25;
        Random rn = new Random();
        if (staticka) {
            this.x = goldMine.getX() - 5;
            this.y = goldMine.getY() - 80;
            this.gameController.pridajObjektMincu(this);
        } else {
            this.y = rn.nextInt(740) + 30;
            this.vektor = new Vektor(rn.nextInt(5) + 1, rn.nextInt(4)  - 2);
        }
        this.obrazok = new Image(this.imageCesta, this.x, this.y);
        this.obrazok.makeVisible();
    }

    /**
     * pohyb mince
     */
    public void coinmove() {
        if (!this.staticka) {
            if (this.obrazok != null && this.vektor != null) {
                this.obrazok.changePosition(this.x, this.y);
                this.x += this.vektor.getSmerX();
                this.y += this.vektor.getSmerY();
            }
            if (this.y < -50 || this.y > 850 || this.x > 850) {
                this.obrazok.makeInvisible();
                this.gameController.odstranMincu(this);
            }
        }
    }

    /**
     * prída mince uživateľovi po kliknutí na mincu
     */
    @Override
    public void efekt() {
        if (this.staticka) {
            this.gameController.pridajMince(50);
            this.obrazok.makeInvisible();
            this.goldMine.odstranMincu();
        } else {
            this.gameController.pridajMince(150);
            this.obrazok.makeInvisible();
            this.gameController.odstranMincu(this);
        }
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     * @return Vráti true alebo false podľa toho či sa na mincu kliklo alebo nie
     */
    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return (this.x + this.imgOffsetX >= mouseX && this.x <= mouseX &&
                this.y <= mouseY && this.y + this.imgOffsetY >= mouseY);
    }

    /**
     * skryje mincu z plátna
     */
    public void skry() {
        this.obrazok.makeInvisible();
    }
}
