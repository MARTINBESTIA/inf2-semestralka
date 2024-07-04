package efekty;

import fri.shapesge.Circle;
import fri.shapesge.Manager;
import matematika.PoziciaXY;
import matematika.Vektor;
import naboje.TypNaboja;
import java.util.ArrayList;

/**
 * Trieda, ktorá predstavuje paprsok. Je to sled Kruhov, ktoré sa vytvárajú pri strele v triede Laser a Fusion, veľkosť
 * sa prispôsobuje na základe typu náboja
 * @author Martin Šimko
 * @version 1.1
 */
public class Paprsok {
    private final TypNaboja typNaboja;
    private ArrayList<Circle> ray;
    private final int rayDuration;
    private int timer = 71;
    private Manager manager;
    private PoziciaXY origin;

    /**
     * Inicializácia paprsku
     * @param origin poziciaXY, od ktorej vedie paprsok, vkladá sa tam origin strieľajúcej veže
     * @param typNaboja Enum typNaboja podľa ktorého sa vykresľuje rôzny paprsok
     */
    public Paprsok(PoziciaXY origin, TypNaboja typNaboja) {
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.typNaboja = typNaboja;
        this.ray = new ArrayList<>();
        this.origin = origin;
        this.rayDuration = 70;
    }
    /**
     * Odpočítava čas do skyria paprsku, keď čas vyprší tak paprsok skryje
     */
    public void tik() {
        if (this.timer > this.rayDuration) {
            this.hidePaprsok();
        } else {
            this.timer++;
        }
    }
    /**
     * Generuje paprsok, v smere normalizovaneho vektoru
     * @param normalizovanyVektor vektor, v smere ktorom sa generuje paprsok
     */
    public void showPaprsok(Vektor normalizovanyVektor) {
        this.timer = 0;
        int rayX = this.origin.getX();
        int rayY = this.origin.getY();
        while (rayX > -20 && rayX < 820 && rayY < 820 && rayY > -20) {
            Circle circle = new Circle(rayX, rayY);
            circle.changeSize(this.typNaboja.getSirkaNaboja());
            circle.changeColor(this.typNaboja.getFarbaNaboja());
            circle.makeVisible();
            this.ray.add(circle);
            rayX += normalizovanyVektor.getSmerX();
            rayY += normalizovanyVektor.getSmerY();
        }
    }

    /**
     * Skryje paprsok
     */
    private void hidePaprsok() {
        for (Circle circle : this.ray) {
            circle.makeInvisible();
        }
        this.ray = new ArrayList<>();
    }
}
