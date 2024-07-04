package hlavnyBalicek;

import fri.shapesge.FontStyle;
import fri.shapesge.Image;
import fri.shapesge.Rectangle;
import fri.shapesge.TextBlock;
import hlavnyBalicek.Clickable;
import veze.TypVeze;

/**
 * Trieda zobrazujúca kartu a zodpovedná za vybranie karty po kliknutí na ňu
 */
public class Karta implements Clickable {
    private int stredX;
    private final TypVeze typVeze;
    private final int stredY = 700;
    private int cena;
    private final String cestaVeze;
    private Rectangle telo;
    private TextBlock textCena;
    private final int xOffset = 60;
    private final int yOffset = 100;
    private Image miniMinca;
    private GameController gameController;
    private Image imageVeze;

    /**
     * Inicializuje vzhšad karty na základe typu veže
     * @param stredX stredu objektu karty
     * @param typVeze typVeze ktorú karta znázorňuje
     * @param gameController referencia na gamecontroller
     */
    public Karta(int stredX, TypVeze typVeze, GameController gameController) {
        this.stredX = stredX;
        this.typVeze = typVeze;
        this.cena = typVeze.getCena();
        this.cestaVeze = typVeze.getCesta();
        this.gameController = gameController;
        this.zobrazKartu();
    }

    /**
     * Vykreslí kartu na plátno
     */
    public void zobrazKartu() {
        this.telo = new Rectangle(this.stredX, this.stredY);
        this.telo.changeSize(this.xOffset, this.yOffset);
        this.telo.changeColor("blue");
        this.textCena = new TextBlock(this.cena + "", this.stredX + 8, this.stredY + 15);
        this.textCena.changeFont("Arial", FontStyle.BOLD, 15);
        this.telo.makeVisible();
        this.imageVeze = new Image(this.cestaVeze, this.stredX - this.typVeze.getxImageOffset(), this.stredY + 10);
        this.imageVeze.makeVisible();
        this.textCena.makeVisible();
        this.miniMinca = new Image("pics/ostatne/miniminca.png", this.stredX + 40, this.stredY + 5);
        this.miniMinca.makeVisible();
    }

    /**
     * označí kartu v gamecontroleri
     */
    @Override
    public void efekt() {
        this.gameController.oznacVezu(this.typVeze);
    }

    /**
     * @param mouseX pozicia kliknutia myši X
     * @param mouseY pozicia kliknutia myši Y
     * @return Vráti hodnotu true alebo false, podľa toho či sa na kartu kliklo
     */
    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return (this.stredX  + this.xOffset >= mouseX && this.stredX <= mouseX &&
                this.stredY <= mouseY && this.stredY + this.yOffset >= mouseY);
    }

    /**
     * Skryje kartu
     */
    public void skry() {
        this.telo.makeInvisible();
        this.imageVeze.makeInvisible();
        this.miniMinca.makeInvisible();
        this.textCena.makeInvisible();
    }
}
