package veze;

import naboje.TypNaboja;

/**
 * Enum špecifkujúci parametre veže ako napr. počet životov, cena atď
 */

public enum TypVeze {
    GOLDMINE(100, 100, 1000, 100, "pics/towers/goldmine.png", 0, null, 250, 20),
    MONUMENT(50, 110, 250, 50, "pics/towers/monument.png", 0, null, 250, 20),
    FUSION(40, 120, 500, 800, "pics/towers/fusion.png", 300, null, 150, 0),
    RAKETOMET(80, 110, 600, 200, "pics/towers/raketomet.png", 250, TypNaboja.RAKETOMET, 500, 20),
    LASER(70, 110, 800, 300, "pics/towers/laser.png", 400, TypNaboja.LASER, 750, 20),
    MACHINEGUN(70, 110, 500, 150, "pics/towers/machinegun.png", 300, TypNaboja.MACHINEGUN, 20, 20),
    KANON(70, 110, 500, 100, "pics/towers/kanon.png", 250, TypNaboja.KANON, 250, 20),
    PLAMENOMET(60, 110, 300, 400, "pics/towers/firethrower.png", 130, TypNaboja.PLAMENOMET, 4, 20);

    private final int xImageWidth;
    private final int yImageWidth;
    private final int hitPoints;
    private final int cena;
    private final String cestaVeze;
    private final int radius;
    private final TypNaboja typNaboja;
    private final int reloadTime;
    private final int xImageOffset;


    TypVeze(int xImageWidth, int yImageWidth, int hitPoints, int cena, String cestaVeze, int radius, TypNaboja typNaboja, int reloadTime, int xImageOffset) {
        this.xImageWidth = xImageWidth;
        this.yImageWidth = yImageWidth;
        this.hitPoints = hitPoints;
        this.cena = cena;
        this.cestaVeze = cestaVeze;
        this.radius = radius;
        this.typNaboja = typNaboja;
        this.reloadTime = reloadTime;
        this.xImageOffset = xImageOffset;
    }

    /**
     * @return Vráti cenu veže
     */
    public int getCena() {
        return this.cena;
    }
    /**
     * @return Vráti cestu ku súboru obrázku veže
     */
    public String getCesta() {
        return this.cestaVeze;
    }

    /**
     * @return Vráti rádius veže
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * @return Vráti typ náboja, ktorý veža striela
     */
    public TypNaboja getTypNaboja() {
        return this.typNaboja;
    }

    /**
     * @return Vráti čas prebíjania veže
     */
    public int getReloadTime() {
        return this.reloadTime;
    }

    /**
     * @return o hodnotu vycentrovania obrázku na stred
     */
    public int getxImageOffset() {
        return this.xImageOffset;
    }

    /**
     * @return Vráti celkový počet životov veže
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * @return vráti šírku obrázka
     */
    public int getxImageWidth() {
        return this.xImageWidth;
    }

    /**
     * @return vráti výšku obrázka
     */
    public int getyImageWidth() {
        return this.yImageWidth;
    }
}
