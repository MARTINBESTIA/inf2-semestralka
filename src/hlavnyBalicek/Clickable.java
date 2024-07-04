package hlavnyBalicek;

/**
 * Interface ktorý implementujú klikatelné objekty,
 * Metóda efekt je konkrétna funkcionalita, ktorá sa vykoná po kliknutí,
 * Metóda isClicked overí či sa kliklo na objekt na plátne
 */
public interface Clickable {
    void efekt();
    boolean isClicked(int mouseX, int mouseY);
}
