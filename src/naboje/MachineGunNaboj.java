package naboje;

import enemaci.Enemy;
import hlavnyBalicek.HittableObject;
import matematika.Vektor;
import veze.StrielajucaVeza;

import java.util.List;

/**
 * Náboj ktorý najskôr zistí pozíciu terču a potom letí za ním, pokiaľ tam už ale terč nie je, damage neuberie,
 * dá damage prvému objektu, ktorému môže rozdaď damage viď param
 * @param <E> parameter určujúci či Náboj uberá životy vežiam alebo nepriateľov, v závislosti kto ho vytvára
 */
public class MachineGunNaboj<E extends HittableObject> extends Naboj {
    private List<E> hittableObjects;
    private Vektor vektor;

    /**
     * inicializácia náboja
     * @param x
     * @param y
     * @param vektor
     * @param typNaboja
     * @param target
     * @param strielajucaVeza
     * @param hittableObjects
     */
    public MachineGunNaboj(int x, int y, Vektor vektor, TypNaboja typNaboja, Enemy target, StrielajucaVeza strielajucaVeza, List<E> hittableObjects) {
        super(x, y, typNaboja, target, strielajucaVeza);
        this.hittableObjects = hittableObjects;
        this.vektor = vektor;
    }

    /**
    * Náboj ktorý najskôr zistí pozíciu terču a potom letí za ním, pokiaľ tam už ale terč nie je, damage neuberie,
    * dá damage prvému objektu, ktorému môže rozdaď damage viď param
    */
    @Override
    public void nabojLeti() {
        if (!this.vyletel()) {
            this.pripocitajX(this.vektor.getSmerX());
            this.pripocitajY(this.vektor.getSmerY());
            this.changeImagePosition(this.getX(), this.getY());
            for (HittableObject hittableObject : this.hittableObjects) {
                if (hittableObject != null && super.zasiahnutyTarget(hittableObject)) {
                    hittableObject.recieveDamage(this.getDamage());
                    this.znicNaboj();
                    break;
                }
            }
            this.destroyIfOutOfCanvas();
        }
    }
}
