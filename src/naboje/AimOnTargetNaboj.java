package naboje;

import hlavnyBalicek.HittableObject;
import matematika.Vektor;
import veze.StrielajucaVeza;

/**
 * Náboj ktorý letí priamo na terč a následne sa zničí
 */
public class AimOnTargetNaboj extends Naboj {

    /**
     * inicializácia atribútov
     * @param x
     * @param y
     * @param typNaboja
     * @param target
     * @param strielajucaVeza
     */
    public AimOnTargetNaboj(int x, int y, TypNaboja typNaboja, HittableObject target, StrielajucaVeza strielajucaVeza) {
        super(x, y, typNaboja, target, strielajucaVeza);
    }
    @Override
    /**
     * Náboj ktorý letí priamo na terč a následne sa zničí
     */
    public void nabojLeti() {
        if (this.getTarget() == null) {
            super.znicNaboj();
        }
        if (!this.vyletel()) {
            Vektor vektor = null;
            try {
                vektor = new Vektor(this.getAktualnaPoziciaXY(), this.getTarget().getPoziciaXY());
            } catch (NullPointerException e) {
                super.znicNaboj();
                return;
            }
            vektor.normalizujVektor(2, 4, 2);
            this.pripocitajX(vektor.getSmerX() * this.getRychlostNaboja());
            this.pripocitajY(vektor.getSmerY() * this.getRychlostNaboja());
            this.changeImagePosition(this.getX(), this.getY());
            if (super.zasiahnutyTarget(this.getTarget())) {
                this.getTarget().recieveDamage(this.getDamage());
                this.znicNaboj();
            }
        }
    }




}
