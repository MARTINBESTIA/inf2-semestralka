package naboje;

import enemaci.Enemy;
import matematika.Vektor;

/**
 * Náboj, ktorý ide priamo na terč, po explózií dá terču štít
 */
public class ShieldingNaboj extends Naboj {
    public ShieldingNaboj(int x, int y, TypNaboja typNaboja, Enemy target) {
        super(x, y, typNaboja, target, null);
    }

    /**
     * Špecifický typ pohybu náboja
     */
    @Override
    public void nabojLeti() {
        if (!this.vyletel()) {
            Vektor vektor = new Vektor(this.getAktualnaPoziciaXY(), this.getTarget().getPoziciaXY());
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

    protected void znicNaboj() {
        if (this.getTarget() != null) {
            ((Enemy)super.getTarget()).shieldEnemy();
        }
        this.hideNaboj();
        this.setTargetNull();
        this.setVyletelTrue();
    }
}
