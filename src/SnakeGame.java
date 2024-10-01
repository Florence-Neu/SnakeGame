import javax.swing.*;

public class SnakeGame extends JFrame {

    public SnakeGame() {
        // Titel des Fensters setzen
        setTitle("Snake Game");

        // Fenstergröße festlegen
        setSize(600, 600);

        // Fenstergröße fixieren
        setResizable(false); // Verhindert das Verändern der Fenstergröße

        // Standardaktion beim Schließen des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel zum Zeichnen des Spiels hinzufügen
        add(new GamePanel());

        // Fenster zentrieren
        setLocationRelativeTo(null); // Fenster wird in der Bildschirmmitte angezeigt

        // Fenster sichtbar machen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Neues Spiel im Event Dispatch Thread starten
        SwingUtilities.invokeLater(SnakeGame::new); // Stellt sicher, dass die GUI im richtigen Thread erstellt wird
    }
}