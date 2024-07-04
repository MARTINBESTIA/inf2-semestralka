package cestaPackage;
import java.util.ArrayList;
import fri.shapesge.Circle;
import matematika.PoziciaXY;

/**
 * Trieda obsahujúca sled pozícií XY, ktoré tvoria cestu, Nepriatelia tieto body používajú pri pohybe
 * @author Martin Šimko
 * @version 1.1
 */
public class Cesta {
    /**
     * Zoznam bodov XY
     */
    private ArrayList<PoziciaXY> bodyCesty;
    /**
     * Graficke kružky cesty
     */
    private ArrayList<Circle> castyCesty;
    /**
     * Inicializácia cesty
     */
    public Cesta() {
        this.bodyCesty = new ArrayList<>();
        this.castyCesty = new ArrayList<>();
        this.generujCestu();

    }
    /**
     * vráti celkovú dĺžku poľa cesty
     */
    public int getBodyCestyLength() {
        return this.bodyCesty.size();
    }
    /**
     * vráti PozíciuXY na indexe cesty
     * @param index index PozicieXY v kontajneri bodyCesty
     */
    public PoziciaXY getElement(int index) {
        return this.bodyCesty.get(index);
    }
    /**
     * vráti PozíciuXY ktorá má rovnaký hodnotu x ako parameter x, inak vráti 0
     * @param x hodnota x, ktorú musí mať rovnakú vrátená pozíciaXY
     */
    public PoziciaXY getElementAtPositionX(int x) {
        for (PoziciaXY poziciaXY : this.bodyCesty) {
            if (poziciaXY.getX() == x) {
                return poziciaXY;
            }
        }
        return new PoziciaXY(0, 0);
    }

    /**
     * Vygeneruje PozicieXY, pridá ich do kontajnera body cesty, a zobrazí na plátne
     */
    private void generujCestu() {
        for (int x = -50; x < 875; x++) {
            if (x <= 400) {
                this.bodyCesty.add(new PoziciaXY(x, (int)(200 * Math.sin(0.02 * x) + 400)));
            } else if (x < 500) {
                this.bodyCesty.add(new PoziciaXY(x, 600));
            } else if (x == 500) {
                for (int y = 600; y > 201; y--) {
                    this.bodyCesty.add(new PoziciaXY(x, y));
                }
            } else if (x < 600) {
                this.bodyCesty.add(new PoziciaXY(x, 200));
            } else if (x < 700) {
                this.bodyCesty.add(new PoziciaXY(x, (2 * x - 1200) + 200));
            } else {
                this.bodyCesty.add(new PoziciaXY(x, 400));
            }
        }
        for (PoziciaXY poziciaXY : this.bodyCesty) {
            var castCesty = new Circle(poziciaXY.getX(), poziciaXY.getY());
            castCesty.changeColor("black");
            castCesty.changeSize(40);
            castCesty.changePosition(poziciaXY.getX() - 20, poziciaXY.getY() - 20);
            castCesty.makeVisible();
            this.castyCesty.add(castCesty);
        }
    }

    /**
     * zobrazí celú cestu
     */
    public void skry() {
        for (Circle circle : this.castyCesty) {
            circle.makeInvisible();
        }
    }
}
