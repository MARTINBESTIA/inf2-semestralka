package enemaci;

/**
 * Enum špecifikujúci parametre nepriateľov, ako napr, šírka rýchlosť celkové hp atď.
 */
public enum TypEnemy {
    HEAVY_RYTIER(50, 50, 1500, 5, "pics/enemies/golem.png", 8),
    MINI_RYTIER(20, 20, 100, 4, "pics/enemies/skeleton.png", 1),
    RYTIER(50, 50, 500, 3, "pics/enemies/goblin.png", 3),
    NAJAZDNIK(50, 50, 750, 7, "pics/enemies/najazdnik.png", 5),
    PODKOPAVAC(30, 38, 400, 3, "pics/enemies/podkopavac.png", 4),
    RANGE_ATTACKER(30, 30, 150, 4, "pics/enemies/rangeskeleton.png", 2),
    HEALER_SHIELDER(35, 40, 300, 6, "pics/enemies/healershielder.png", 10),
    MAG(50, 50, 300, 6, "pics/enemies/wizard.png", 15);


    private final int imageOffsetX;
    private final int imageOffsetY;
    private final int totalHp;
    private final int rychlostPohybu;
    private final String cestaObr;
    private final int strengthFactor;

    TypEnemy(int imageOffsetX, int imageOffsetY, int totalHp, int rychlostPohybu, String cestaObr, int strengthFactor) {
        this.imageOffsetX = imageOffsetX;
        this.imageOffsetY = imageOffsetY;
        this.totalHp = totalHp;
        this.rychlostPohybu = rychlostPohybu;
        this.cestaObr = cestaObr;
        this.strengthFactor = strengthFactor;
    }

    /**
     * @return vráti šírku
     */
    public int getImageOffsetX() {
        return this.imageOffsetX;
    }

    /**
     *
     * @return vráti výšku
     */
    public int getImageOffsetY() {
        return this.imageOffsetY;
    }

    /**
     *
     * @return  vráti celkové hp
     */
    public int getTotalHp() {
        return this.totalHp;
    }

    /**
     *
     * @return vráti celkovú rýchlosť pohybu
     */
    public int getRychlostPohybu() {
        return this.rychlostPohybu;
    }

    /**
     *
     * @return vráti cestu ku obrázku
     */
    public String getCestaObr() {
        return this.cestaObr;
    }

    /**
     *
     * @return vráti faktor sily
     */
    public int getStrengthFactor() {
        return this.strengthFactor;
    }
}
