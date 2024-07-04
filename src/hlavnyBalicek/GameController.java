package hlavnyBalicek;

import java.util.ArrayList;
import java.util.Collections;
import cestaPackage.Cesta;
import enemaci.Enemy;
import enemaci.HealerShielder;
import enemaci.TypEnemy;
import enemaci.Podkopavac;
import enemaci.Mag;
import enemaci.UtociaciEnemy;
import enemaci.Najazdnik;
import fri.shapesge.Manager;
import veze.Kanon;
import veze.Laser;
import veze.TypVeze;
import veze.Veza;
import veze.Plamenomet;
import veze.GoldMine;
import veze.Monument;
import veze.MachineGun;
import veze.Fusion;
import veze.Raketomet;

/**
 * Hlavná trieda, Ktorá sa stará o beh hry, zhromažďuje všetky objekty potrebné na fungovanie hry,je zodpovedná za budovanie veží a za klikanie
 */

public class GameController {
    private int coinTimer = 2000;
    private int sekundy = 0;
    private int minuty = 0;
    private ArrayList<Enemy> enemiesNaPloche;
    private ArrayList<Veza> vezeNaPloche;
    private ArrayList<Karta> zostavakariet;
    private ArrayList<Clickable> klikatelneObjekty;
    private TypVeze oznacenaVeza;
    private int mince = 1000;
    private Cesta cesta;
    private Manager manager;
    private EnemySpawner enemySpawner;
    private GameInformation gameInformation;
    private Program program;

    /**
     * Inicializovanie parametrov
     * @param program Trieda obsahujúca okno pred/po skončení hry
     */
    public GameController(Program program) {
        this.manager = new Manager();
        this.manager.manageObject(this);
        this.program = program;
        this.cesta = new Cesta();
        this.enemiesNaPloche = new ArrayList<Enemy>();
        this.vezeNaPloche = new ArrayList<Veza>();
        this.zostavakariet = new ArrayList<Karta>();
        this.klikatelneObjekty = new ArrayList<Clickable>();
        this.generujKarty();
        this.gameInformation = new GameInformation();
        this.enemySpawner = new EnemySpawner(this, this.gameInformation);
        Minca minca = new Minca(this, false, null);
        this.klikatelneObjekty.add(minca);
        this.gameInformation.updatePocetMinci(this.mince);
    }

    /**
     * Prejde všetky klikateľné objekty a ak aj je kliknutý vykoná efekt, taktiež spawnuje veže
     * @param x poziciaX na ktorú klikol uživateľ
     * @param y poziciaY na ktorú klikol uživateľ
     */
    public void klik(int x, int y) {
        if (this.oznacenaVeza != null) {
            if (this.spawnableOnClickedPosition(x, y, this.oznacenaVeza)) {
                this.spawnVeza(x, y);
                this.oznacenaVeza = null;
                this.gameInformation.updateOznacenaVeza(this.oznacenaVeza);
            }
        }
        for (Clickable clickable : this.klikatelneObjekty) {
            if (clickable.isClicked(x, y)) {
                clickable.efekt();
                if (clickable instanceof Karta) {
                    this.gameInformation.updateOznacenaVeza(this.oznacenaVeza);
                }
                break;
            }
        }
    }

    /**
     * Tik ktorý pridáva celkový odohratý čas
     */
    public void casTik() {
        if (this.sekundy == 59) {
            this.minuty++;
            this.sekundy = 0;
        } else {
            this.sekundy++;
        }
        if (this.gameInformation != null) {
            this.gameInformation.updateCasPrezitia(this.minuty, this.sekundy);
        }
    }

    /**
     * Koktroluje hp objektov, ak majú pod 0 tak objekt vymažu z hry, pravidelne generuje náhodne mince na plátno a ukončí hru ak nepriateľ prejde na koniec
     */

    public void tik() {
        this.hitpointsChecker();
        this.pridavajMince();
        this.freezeGameIfEnemyPassed();
    }
    private void generujKarty() {
        var kartaKanon = new Karta(20, TypVeze.KANON, this);
        this.zostavakariet.add(kartaKanon);
        this.klikatelneObjekty.add(kartaKanon);
        var kartalaser = new Karta(120, TypVeze.LASER, this);
        this.zostavakariet.add(kartalaser);
        this.klikatelneObjekty.add(kartalaser);
        var plamenomet = new Karta(220, TypVeze.PLAMENOMET, this);
        this.zostavakariet.add(plamenomet);
        this.klikatelneObjekty.add(plamenomet);
        var machinegun = new Karta(320, TypVeze.MACHINEGUN, this);
        this.zostavakariet.add(machinegun);
        this.klikatelneObjekty.add(machinegun);
        var monument = new Karta(420, TypVeze.MONUMENT, this);
        this.zostavakariet.add(monument);
        this.klikatelneObjekty.add(monument);
        var goldmine = new Karta(520, TypVeze.GOLDMINE, this);
        this.zostavakariet.add(goldmine);
        this.klikatelneObjekty.add(goldmine);
        var fusion = new Karta(620, TypVeze.FUSION, this);
        this.zostavakariet.add(fusion);
        this.klikatelneObjekty.add(fusion);
        var raketomet = new Karta(720, TypVeze.RAKETOMET, this);
        this.zostavakariet.add(raketomet);
        this.klikatelneObjekty.add(raketomet);
    }
    private void hitpointsChecker() {
        if (this.vezeNaPloche != null) {
            for (Veza veza : this.vezeNaPloche) {
                if ((veza).getHitPoints() <= 0) {
                    veza.znicVezu();
                    this.vezeNaPloche.remove(veza);
                    this.klikatelneObjekty.remove((Clickable)veza);
                    break;
                }
            }
        }
        if (this.enemiesNaPloche != null) {
            for (Enemy enemy : this.enemiesNaPloche) {
                if (((HittableObject)enemy).getHitPoints() <= 0) {
                    enemy.umri();
                    this.enemiesNaPloche.remove(enemy);
                    break;
                }
            }
        }
    }

    /**
     * Pridanie mincí o množstvo v parametri
     * @param mnozstvo počet pridaných mincí
     */
    public void pridajMince(int mnozstvo) {
        this.mince += mnozstvo;
        this.gameInformation.updatePocetMinci(this.mince);
    }
    private void pridavajMince() {
        if (this.coinTimer <= 0) {
            this.klikatelneObjekty.add(new Minca(this, false, null));
            this.coinTimer = 2000;
        } else {
            this.coinTimer--;
        }
    }

    /**
     * Odstráni mincu
     * @param minca
     */
    public void odstranMincu(Minca minca) {
        this.klikatelneObjekty.remove(minca);
    }

    /**
     * označí typ veže, ktorá môže byť spawnutá, volá sa pri kliknutí na kartu
     * @param typVeze typ kliknutej veže
     */
    public void oznacVezu(TypVeze typVeze) {
        this.oznacenaVeza = typVeze;
    }
    private void spawnVeza(int x, int y) {
        if (this.oznacenaVeza != null && this.oznacenaVeza.getCena() <= this.mince) {
            Veza vezaNaPridanie = null;
            switch (this.oznacenaVeza) {
                case KANON:
                    vezaNaPridanie = new Kanon(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
                case LASER:
                    vezaNaPridanie = new Laser(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
                case PLAMENOMET:
                    vezaNaPridanie = new Plamenomet(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
                case MACHINEGUN:
                    vezaNaPridanie = new MachineGun(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
                case GOLDMINE:
                    vezaNaPridanie = new GoldMine(x, y, this);
                    break;
                case MONUMENT:
                    vezaNaPridanie = new Monument(x, y, Collections.unmodifiableList(this.klikatelneObjekty));
                    break;
                case FUSION:
                    vezaNaPridanie = new Fusion(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
                case RAKETOMET:
                    vezaNaPridanie = new Raketomet(x, y, Collections.unmodifiableList(this.enemiesNaPloche));
                    break;
            }
            this.vezeNaPloche.add(vezaNaPridanie);
            this.klikatelneObjekty.add(vezaNaPridanie);
            this.mince -= this.oznacenaVeza.getCena();
            this.gameInformation.updatePocetMinci(this.mince);
            this.kartyNaPrednuVrstvu();
        }
        this.oznacenaVeza = null;
    }
    private void kartyNaPrednuVrstvu() {
        for (Clickable clickable : this.klikatelneObjekty) {
            if (clickable instanceof Karta) {
                ((Karta)clickable).skry();
                ((Karta)clickable).zobrazKartu();
            }
        }
    }

    /**
     * pridá mincu do zoznamu
     * @param minca
     */
    public void pridajObjektMincu(Minca minca) {
        this.klikatelneObjekty.add((Clickable)minca);
    }

    /**
     * odstráni mincu zo zoznamu klikatelnych objektov
     * @param minca
     */
    public void odstranObjektMincu(Minca minca) {
        this.klikatelneObjekty.remove((Clickable)minca);
    }

    /**
     * Vytvorí novú inštanciu Nepriateľa na základe parametra
     * @param typEnemy typ nepriateľa ktorý sa má spawnuť
     */
    public void spawnEnemy(TypEnemy typEnemy) {
        switch (typEnemy) {
            case RANGE_ATTACKER -> {
                this.enemiesNaPloche.add(new UtociaciEnemy(this.cesta, TypEnemy.RANGE_ATTACKER, Collections.unmodifiableList(this.vezeNaPloche)));
            }
            case HEALER_SHIELDER -> {
                this.enemiesNaPloche.add(new HealerShielder(this.cesta, Collections.unmodifiableList(this.enemiesNaPloche)));
            }
            case PODKOPAVAC -> {
                this.enemiesNaPloche.add(new Podkopavac(this.cesta));
            }
            case MAG -> {
                this.enemiesNaPloche.add(new Mag(this.cesta, Collections.unmodifiableList(this.vezeNaPloche), Collections.unmodifiableList(this.enemiesNaPloche)));
            }
            case MINI_RYTIER -> {
                this.enemiesNaPloche.add(new Enemy(this.cesta, TypEnemy.MINI_RYTIER));
            }
            case RYTIER -> {
                this.enemiesNaPloche.add(new Enemy(this.cesta, TypEnemy.RYTIER));
            }
            case NAJAZDNIK -> {
                this.enemiesNaPloche.add(new Najazdnik(this.cesta));
            }
            case HEAVY_RYTIER -> {
                this.enemiesNaPloche.add(new Enemy(this.cesta, TypEnemy.HEAVY_RYTIER));
            }
        }
    }

    /**
     * Overí či, je možné položiť vežu na plátno, resp, či sa ju uživateľ nesnaží položiť na cestu
     * @param mouseX x položenia
     * @param mouseY y položenia
     * @param typVeze veža, ktorá sa snaží byť položená
     * @return boolean či to možné je alebo nie
     */
    private boolean spawnableOnClickedPosition(int mouseX, int mouseY, TypVeze typVeze) {
        int poziciaYCestySRovnakymMouseX = this.cesta.getElementAtPositionX(mouseX).getY();
        boolean clickedOutOfCestaY;
        if (mouseY > poziciaYCestySRovnakymMouseX) {
            int leftUpperCornerY = this.cesta.getElementAtPositionX(-typVeze.getxImageWidth() / 2 + mouseX).getY();
            int rightUpperCornerY = this.cesta.getElementAtPositionX(typVeze.getxImageWidth() / 2 + mouseX).getY();
            int heigthY = this.cesta.getElementAtPositionX(mouseX).getY();
            clickedOutOfCestaY = (((mouseY - leftUpperCornerY) >= typVeze.getyImageWidth() / 2) && ((mouseY - rightUpperCornerY) >= typVeze.getyImageWidth() / 2) && (mouseY - heigthY) >= typVeze.getyImageWidth() / 2);
        } else {
            int leftBottomCornerY = this.cesta.getElementAtPositionX(-typVeze.getxImageWidth() / 2 + mouseX).getY();
            int rightBottomCornerY = this.cesta.getElementAtPositionX(typVeze.getyImageWidth() / 2 + mouseX).getY();
            int heigthY = this.cesta.getElementAtPositionX(mouseX).getY();
            clickedOutOfCestaY = (((leftBottomCornerY - mouseY) >= typVeze.getyImageWidth() / 2) && ((rightBottomCornerY - mouseY) >= typVeze.getyImageWidth() / 2) && (heigthY - mouseY) >= typVeze.getyImageWidth() / 2);
        }

        return clickedOutOfCestaY;
    }
    private void freezeGameIfEnemyPassed() {
        if (this.passedEnemy()) {
            for (Enemy enemy : this.enemiesNaPloche) {
                enemy.freeze();
            }
            for (Veza veza : this.vezeNaPloche) {
                veza.freeze();
            }
            this.enemySpawner.freeze();
            this.manager.stopManagingObject(this);
            this.program.koniecHry(this.gameInformation.getCas(), this.gameInformation.getPocetVln());
        }
    }
    private boolean passedEnemy() {
        if (this.enemiesNaPloche == null || this.enemiesNaPloche.isEmpty()) {
            return false;
        }
        for (Enemy enemy : this.enemiesNaPloche) {
            if (enemy.getX() > 870) {
                return true;
            }
        }
        return false;
    }

    /**
     * Skryje celé plátno
     */
    public void hidePlatno() {
        for (Enemy enemy : this.enemiesNaPloche) {
            enemy.skry();
        }
        for (Veza veza : this.vezeNaPloche) {
            veza.skry();
        }
        for (Karta karta : this.zostavakariet) {
            karta.skry();
        }
        this.gameInformation.skry();
        for (Clickable clickable : this.klikatelneObjekty) {
            if (clickable instanceof Minca) {
                ((Minca)clickable).skry();
            }
        }
        this.cesta.skry();
    }
}
