package enemaci;

import cestaPackage.Cesta;
import matematika.Vektor;
import naboje.AimOnTargetNaboj;
import naboje.MachineGunNaboj;
import naboje.ShieldingNaboj;
import naboje.TypNaboja;
import veze.Veza;

import java.util.List;

/**
 * Konkrétny typ útočiaceho nepriateľa, ktorý keď má pod 50 percent hp si dá štít, spustí salvu útokov a premiesti nepriatela najbližšie ku
 * koncu cesty ešte bližšie
 * @author Martin Šimko
 * @version 1.1
 */
public class Mag extends UtociaciEnemy {
    private List<Enemy> enemiesNaPloche;
    private int chanellingTimer = 800;
    private boolean below50HpEfectActivated;
    private Veza target;
    private Enemy enemyMostForward;
    private boolean moveImobilizedEnemyUp = true;
    private boolean transportingFinished = false;
    public Mag(Cesta cestaPohybu, List<Veza> vezeNaPloche, List<Enemy> enemiesNaPloche) {
        super(cestaPohybu, TypEnemy.MAG, vezeNaPloche);
        this.enemiesNaPloche = enemiesNaPloche;
        this.below50HpEfectActivated = false;
        this.setRadius(300);
        super.setStav(AttackingEnemyStav.CHODENIE);

    }

    /**
     * Pohybuje sa a mení rôzne stavy na základe časovačov. V určitom stave sa chová rozdielne. V stave CHODENIE kráča, v stave Mierenie iba stojí na konci
     * vystrelí, v stave CHANNELING spusti svoje útoky, ktoré spúšťa ked má pod 50 percent
     */
    @Override
    public void tik() {
        if (this.getHitPoints() > 0) {
            this.updateHpText();
            if (this.getHitPoints() <= this.getTotalHitpoints() * 0.5 && !this.below50HpEfectActivated) {
                new ShieldingNaboj(this.getX(), this.getY(), TypNaboja.SHIELDING, this);
                super.setStav(AttackingEnemyStav.CHANNELING);
                this.below50HpEfectActivated = true;
            }
            switch (super.getStav()) {
                case CHODENIE -> {
                    super.pohybujSa(0);
                    super.updateHpText();
                    if (this.getKracanieTimer() <= 0) {
                        super.setMierenieTimer(150);
                        this.zmenaStavuNaMierenie();
                    } else {
                        super.setKracanieTimer(this.getKracanieTimer() - 1);
                    }
                }
                case MIERENIE -> {
                    if (this.getMierenieTimer() <= 0) {
                        super.setStav(AttackingEnemyStav.CHODENIE);
                        super.setKracanieTimer(400);
                        new AimOnTargetNaboj(this.getX() + 20, this.getY(), TypNaboja.MAG_NABOJ, this.target, null);
                    } else {
                        super.setMierenieTimer(super.getMierenieTimer() - 1);
                    }
                }
                case CHANNELING -> {
                    this.chanellingTimer--;
                    if (this.chanellingTimer == 700) {
                        this.clusterNabojov();
                        super.setKracanieTimer(400);
                    } else if (this.chanellingTimer <= 600 && this.chanellingTimer >= 400) {
                        this.clusterUtokov();
                    } else if (this.chanellingTimer == 301) {
                        this.setEnemyMostForward();
                    } else if (this.chanellingTimer <= 300) {
                        this.moveEnemyForward();
                    }
                }
            }

        }
    }
    private void setEnemyMostForward() {
        int xMostForfard = Integer.MIN_VALUE;
        Enemy mostForwardEnemy = null;
        for (Enemy enemy : this.enemiesNaPloche) {
            if (enemy.getX() > xMostForfard) {
                mostForwardEnemy = enemy;
                xMostForfard = enemy.getX();
            }
        }
        this.enemyMostForward = mostForwardEnemy;
        this.enemyMostForward.imobilize();
    }
    private void moveEnemyForward() {
        if (!this.transportingFinished) {
            if (this.enemyMostForward == null) {
                this.setEnemyMostForward();
                if (this.enemyMostForward == null) {
                    super.setStav(AttackingEnemyStav.CHODENIE);
                    return;
                }
            }
            if (this.enemyMostForward.getX() < 602) {
                if (this.moveImobilizedEnemyUp) {
                    this.enemyMostForward.setY(this.enemyMostForward.getY() - 4);
                } else {
                    this.enemyMostForward.setY(this.enemyMostForward.getY() + 4);
                }
                if (this.enemyMostForward.getY() < -150) {
                    this.enemyMostForward.pridajIndexPolickaCesty(250);
                    this.enemyMostForward.setY(-150);
                    this.moveImobilizedEnemyUp = !this.moveImobilizedEnemyUp;
                    int newPositionX = this.getXPositionAtCestaIndex(this.enemyMostForward.getIndexPolickaCesty());
                    this.enemyMostForward.setX(newPositionX);
                }
                if (!this.moveImobilizedEnemyUp && this.enemyMostForward.posYAttIndexPolickaCesty(this.getIndexPolickaCesty()) <= this.enemyMostForward.getY()) {
                    this.transportingFinished = true;
                    this.enemyMostForward.mobilize();
                    super.setStav(AttackingEnemyStav.CHODENIE);
                }
            } else {
                if (this.enemyMostForward != null) {
                    this.enemyMostForward.mobilize();
                }
                super.setStav(AttackingEnemyStav.CHODENIE);
            }
        }
    }

    private void clusterUtokov() {
        if (this.chanellingTimer % 20 == 0) {
            Veza veza = this.vezaInRange();
            if (veza != null) {
                new AimOnTargetNaboj(this.getX() + 20, this.getY(), TypNaboja.MAG_NABOJ, veza, null);
            }
        }
    }
    private void zmenaStavuNaMierenie() {
        if (this.vezaInRange() != null) {
            super.setStav(AttackingEnemyStav.MIERENIE);
        }
    }
    private void clusterNabojov() {
        int ySpawnOffset = 900;
        for (int i = 0; i < 36; i++) {
            Vektor vektor = new Vektor(0, ySpawnOffset / -(Math.abs(ySpawnOffset) / 3));
            new MachineGunNaboj<Veza>(30 + 30 * i, 400 + ySpawnOffset, vektor, TypNaboja.MAG_ABILITY_NABOJ, null, null, super.getVezeNaPloche());
            ySpawnOffset = -ySpawnOffset;
        }
    }
    protected Veza vezaInRange() {
        Veza spottedVeza = null;
        int najblizsiaVezaVzdialenost = Integer.MAX_VALUE;
        for (Veza veza : this.getVezeNaPloche()) {
            int vzdialenost = this.getVzdialenostEnemyOdVeze(veza);
            switch (this.getStav()) {
                case CHANNELING -> {
                    if (vzdialenost < najblizsiaVezaVzdialenost && veza.getHitPoints() > 0) {
                        spottedVeza = veza;
                        najblizsiaVezaVzdialenost = vzdialenost;
                    }
                }
                default -> {
                    if (vzdialenost < najblizsiaVezaVzdialenost && vzdialenost < this.getRadius() && veza.getHitPoints() > 0) {
                        spottedVeza = veza;
                        najblizsiaVezaVzdialenost = vzdialenost;
                    }
                }
            }
        }
        this.target = spottedVeza;
        return spottedVeza;
    }
}
