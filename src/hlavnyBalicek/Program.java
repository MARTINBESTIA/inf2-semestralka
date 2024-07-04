package hlavnyBalicek;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
/**
 * Spúšta hru a poskytuje jednoduché GUI pre zapnutie a vypnutie hry, taktiež ukazuje štatistiky po skončení hry
 */
public class Program {
    private JFrame okno;
    private JButton playButton;
    private JButton quitButton;
    private JLabel nadpis;
    private JPanel centerPanel;
    private JLabel celkoveVysledky;
    private JLabel cas;
    private JLabel pocetVln;
    private GameController gameController;


    /**
     * Inicializuje okno
     */
    public Program() {
        this.initializeJFrame();
        this.okno.pack();
        this.okno.setVisible(true);
    }

    private void initializeJFrame() {
        this.okno = new JFrame("Tower defense");
        this.okno.setLayout(new BorderLayout());
        this.playButton = new JButton("Play");
        Font fontButton = new Font("Aptos", Font.BOLD, 30);
        Font fontNadpis = new Font("Arial", Font.BOLD, 60);
        this.quitButton = new JButton("Quit");
        this.quitButton.setFont(fontButton);
        this.playButton.setFont(fontButton);
        this.playButton.addActionListener(e -> {
            if (this.gameController != null) {
                this.gameController.hidePlatno();
                this.gameController = null;
            }
            Program.this.gameController = new GameController(Program.this);
            Program.this.okno.setVisible(false);
        });
        this.quitButton.addActionListener(e -> {
            System.exit(0);
        });
        this.nadpis = new JLabel("Tower defense");
        this.nadpis.setFont(fontNadpis);
        this.nadpis.setHorizontalAlignment(SwingConstants.CENTER);
        this.nadpis.setVerticalAlignment(SwingConstants.CENTER);
        this.okno.add(this.nadpis, BorderLayout.NORTH);
        this.centerPanel = new JPanel(new GridLayout(5, 5, 30, 30));
        this.okno.add(this.centerPanel, BorderLayout.CENTER);
        for (int i = 0; i < 25; i++) {
            switch (i) {
                case 7 -> {
                    this.celkoveVysledky = new JLabel();
                    this.centerPanel.add(this.celkoveVysledky);
                }
                case 8 -> {
                    this.cas = new JLabel();
                    this.centerPanel.add(this.cas);
                }
                case 9 -> {
                    this.pocetVln = new JLabel();
                    this.centerPanel.add(this.pocetVln);
                }
                case 12 -> {
                    this.centerPanel.add(this.playButton);
                }
                case 17 -> {
                    this.centerPanel.add(this.quitButton);
                }
                default -> {
                    this.centerPanel.add(new JLabel(""));
                }
            }
        }
    }

    /**
     * Ukončí hru a zobrázi game over JFrame
     * @param cas čas ktorý uživateľ prežil
     * @param pocetVln počet vln ktoré sa spawnli
     */
    public void koniecHry(String cas, String pocetVln) {
        this.nadpis.setText("Koniec hry");
        Font font = new Font("Arial", Font.BOLD, 15);
        this.playButton.setText("Play again");
        this.celkoveVysledky.setText("Vysledky:");
        this.cas.setText(cas);
        this.pocetVln.setText("Počet vĺn: " + pocetVln);

        this.okno.pack();
        this.okno.setVisible(true);
    }
}
