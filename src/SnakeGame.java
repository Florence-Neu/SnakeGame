import javax.swing.*;

public class SnakeGame extends JFrame {

    public SnakeGame() {
        // Titel des Fensters setzen
        setTitle("Snake Game");

        // Fenstergröße festlegen
        setSize(600, 600);

        // Standardaktion beim Schließen des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel zum Zeichnen des Spiels hinzufügen
        add(new GamePanel());

        // Fenster sichtbar machen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Neues Spiel erstellen
        new SnakeGame();
    }
}