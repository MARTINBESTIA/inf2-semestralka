package naboje;

/**
 * Enum použitý na špecifikovanie parametrov rôznych typov nábojov ako napr, rýchlosť, damage, veľkosť atď
 */
public enum TypNaboja {
    RAKETOMET(1, true, true, "black", 100, 35),
    MACHINEGUN(3, true, false, "black", 3, 15),
    PLAMENOMET(4, false, true, "red", 3, 20),
    KANON(2, true, true, "black", 50, 25),
    FUSIONKANON(2, true, true, "blue", 80, 15),
    FUSIONEXPLOSIVE(2, true, true, "blue", 40, 20),
    LASER(10, false, true, "yellow", 100, 25),
    LASER_BOOSTED(10, false, true, "yellow", 175, 35),
    FUSIONLASER(10, false, true, "blue", 30, 15),
    MINI_GULICKA(3, false, true, "black", 40, 12),
    MAG_ABILITY_NABOJ(5, false, false, "#660066", 100, 20),
    MAG_NABOJ(2, true, true, "#660066", 80, 25),
    SHIELDING(1, false, true, "#e066ff", 0, 18);




    private final int rychlostNaboja;
    private final boolean explodeOnTarget;
    private final boolean aimOnTarget;
    private final String farbaNaboja;
    private final int damage;
    private final int sirkaNaboja;

    TypNaboja(int rychlostNaboja, boolean explodeOnTarget, boolean aimOnTarget, String farbaNaboja, int damage, int sirkaNaboja) {
        this.rychlostNaboja = rychlostNaboja;
        this.explodeOnTarget = explodeOnTarget;
        this.aimOnTarget = aimOnTarget;
        this.farbaNaboja = farbaNaboja;
        this.damage = damage;
        this.sirkaNaboja = sirkaNaboja;
    }

    /**
     * @return vráti rýchlosť náboja
     */
    public int getRychlostNaboja() {
        return this.rychlostNaboja;
    }

    /**
     * @return vráti, či náboj mieri priamo na nepriateľa
     */
    public boolean getAimOnTarget() {
        return this.aimOnTarget;
    }

    /**
     * @return Vráti farbu náboja
     */
    public String getFarbaNaboja() {
        return this.farbaNaboja;
    }

    /**
     *
     * @return Vráti damage náboja
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return Vráti šírku náboja
     */
    public int getSirkaNaboja() {
        return this.sirkaNaboja;
    }
}
