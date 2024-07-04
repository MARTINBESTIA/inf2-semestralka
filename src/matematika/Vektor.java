package matematika;

/**
 * Reprezentuje vektor, drží smer
 */

public class Vektor {
    private PoziciaXY pociatocnaPozicia;
    private PoziciaXY koncovaPozicia;
    private int smerX;
    private int smerY;

    /**
     * Inicializácia vektora na základe dvoch pozícií XY
     * @param pociatocnaPozicia
     * @param koncovaPozicia
     */
    public Vektor(PoziciaXY pociatocnaPozicia, PoziciaXY koncovaPozicia) {
        this.pociatocnaPozicia = pociatocnaPozicia;
        this.koncovaPozicia = koncovaPozicia;
        this.smerX = koncovaPozicia.getX() - pociatocnaPozicia.getX();
        this.smerY = koncovaPozicia.getY() - pociatocnaPozicia.getY();
    }

    /**
     * Inicializácia vektora priamym vyjadreým smeru X a Y
     * @param smerX
     * @param smerY
     */
    public Vektor(int smerX, int smerY) {
        this.smerX = smerX;
        this.smerY = smerY;
    }

    /**
     * normalizuje vektor
     * @param accuracy miera ako moc normalizovať vektor, čím menšie tým menej presnejšie, ale viac normalizované
     * @param maxValuePossibleInVektor hodnota ktorú nemôžu konkrétne hodnoty vo vektore presiahnuť, inak sa setnú na maxValueInVektor
     * @param maxValueInVektor največšia možná hodnota ktorú môže vektor držať
     */
    public void normalizujVektor(int accuracy, int maxValuePossibleInVektor, int maxValueInVektor) {
        while (Math.abs(this.smerX) > accuracy && Math.abs(this.smerY) > accuracy) {
            this.smerX /= 2;
            this.smerY /= 2;
        }
        if (this.smerX > maxValuePossibleInVektor) {
            this.smerX = maxValueInVektor;
        }
        if (this.smerY > maxValuePossibleInVektor) {
            this.smerY = maxValueInVektor;
        }
        if (this.smerY < -maxValuePossibleInVektor) {
            this.smerY = -maxValueInVektor;
        }
        if (this.smerX < -maxValuePossibleInVektor) {
            this.smerX = -maxValueInVektor;
        }

    }

    /**
     * @return vráti pohyb v smere X
     */
    public int getSmerX() {
        return this.smerX;
    }
    /**
     * @return vráti pohyb v smere Y
     */
    public int getSmerY() {
        return this.smerY;
    }
}
