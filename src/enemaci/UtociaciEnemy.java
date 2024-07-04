package enemaci;

import cestaPackage.Cesta;
import naboje.AimOnTargetNaboj;
import naboje.TypNaboja;
import veze.Veza;

import java.util.Collections;
import java.util.List;

/**
 * Typ nepriateľa, ktorý môže útočiť po vežiach, teda sa okrem iného môže dostať do stavu CHODENIE a MIERENIE kde robí
 * chodenie a mierenie
 */
public class UtociaciEnemy extends Enemy {
    private int radius = 200;
    private List<Veza> vezeNaPloche;
    private Veza target;
    private AttackingEnemyStav stav;
    private int mierenieTimerTotal = 100;
    private int kracanieTimerTotal = 300;
    private int mierenieTimer;
    private int kracanieTimer;

    /**
     * Inicializuje nepriateľa okrem iného ešte eviduje zoznam veží na plátne
     * @param cestaPohybu cesta pohybu
     * @param typEnemy typ nepriateľa
     * @param vezeNaPloche zoznam veží na plátne
     */
    public UtociaciEnemy(Cesta cestaPohybu, TypEnemy typEnemy, List<Veza> vezeNaPloche) {
        super(cestaPohybu, typEnemy);
        this.vezeNaPloche = vezeNaPloche;
        this.stav = AttackingEnemyStav.CHODENIE;
        this.mierenieTimer = this.mierenieTimerTotal;
        this.kracanieTimer = this.kracanieTimerTotal;
    }

    /**
     * Vykonáva kráčanie a strielanie podľa stavu, v ktorom sa nachádza
     */
    @Override
    public void tik() {
        if (this.getHitPoints() > 0) {
            super.updateHpText();
            switch (this.stav) {
                case CHODENIE -> {
                    super.tik();
                    if (this.kracanieTimer <= 0) {
                        this.mierenieTimer = this.mierenieTimerTotal;
                        this.zmenaStavov();
                    } else {
                        this.kracanieTimer--;
                    }
                }
                case MIERENIE -> {
                    if (this.mierenieTimer <= 0) {
                        this.stav = AttackingEnemyStav.CHODENIE;
                        this.kracanieTimer = this.kracanieTimerTotal;
                        new AimOnTargetNaboj(this.getX() + 20, this.getY(), TypNaboja.MINI_GULICKA, this.target, null);
                    } else {
                        this.mierenieTimer--;
                    }
                }
            }
        }
    }
    protected Veza vezaInRange() {
        Veza spottedVeza = null;
        int najblizsiaVezaVzdialenost = Integer.MAX_VALUE;
        for (Veza veza : this.vezeNaPloche) {
            int vzdialenost = this.getVzdialenostEnemyOdVeze(veza);
            if (vzdialenost < najblizsiaVezaVzdialenost && vzdialenost < this.radius) {
                spottedVeza = veza;
                najblizsiaVezaVzdialenost = vzdialenost;
            }
        }
        this.target = spottedVeza;
        return spottedVeza;
    }
    protected int getVzdialenostEnemyOdVeze(Veza veza) {
        return (int)Math.sqrt(Math.pow(Math.abs(this.getX() - veza.getX()), 2) + Math.pow(Math.abs(this.getY() - veza.getY()), 2));
    }
    protected void zmenaStavov() {
        if (this.vezaInRange() != null) {
            this.stav = AttackingEnemyStav.MIERENIE;
        }
    }
    protected int getRadius() {
        return this.radius;
    }
    protected void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Vráti v akom stave je Nepriateľ
     * @return
     */
    public AttackingEnemyStav getStav() {
        return this.stav;
    }

    /**
     * @param stav nastavý stav na tento parameter
     */
    public void setStav(AttackingEnemyStav stav) {
        this.stav = stav;
    }

    /**
     * Vráti nemodifikovateľný zoznam veží na ploche
     * @return
     */
    public List<Veza> getVezeNaPloche() {
        return Collections.unmodifiableList(this.vezeNaPloche);
    }
    protected int getMierenieTimer() {
        return this.mierenieTimer;
    }
    protected void setMierenieTimer(int mierenieTimer) {
        this.mierenieTimer = mierenieTimer;
    }
    protected int getKracanieTimer() {
        return this.kracanieTimer;
    }
    protected void setKracanieTimer(int kracanieTimer) {
        this.kracanieTimer = kracanieTimer;
    }
}
