import javax.swing.*;

public class SnakeGame extends JFrame {
    private Question[] questions = {
            new Question("Was ist die Hauptstadt von Deutschland?", "Berlin"),
            new Question("Wie viele Kontinente gibt es?", "7"),
            new Question("Was ist 5 + 3?", "8")
            // Weitere Fragen hinzufügen
    };
    private int score = 0;

    public SnakeGame() {
        // Titel des Fensters setzen
        setTitle("Snake Game");

        // Fenstergröße festlegen
        setSize(617, 640);

        // Fenstergröße fixieren
        setResizable(false); // Verhindert das Verändern der Fenstergröße

        // Standardaktion beim Schließen des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel zum Zeichnen des Spiels hinzufügen
        add(new GamePanel(this)); // Übergebe die Instanz von SnakeGame an GamePanel

        // Fenster zentrieren
        setLocationRelativeTo(null); // Fenster wird in der Bildschirmmitte angezeigt

        // Fenster sichtbar machen
        setVisible(true);
    }

    public void askQuestion() {
        Question q = questions[0]; // Immer die erste Frage wählen

        String userAnswer = JOptionPane.showInputDialog(q.getQuestion());
        if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getAnswer())) {
            JOptionPane.showMessageDialog(null, "Richtig! Du erhältst einen Bonus.", "Antwort", JOptionPane.INFORMATION_MESSAGE);
            score += 10; // Bonuspunkte hinzufügen
        } else {
            JOptionPane.showMessageDialog(null, "Leider falsch. Die richtige Antwort war: " + q.getAnswer(), "Antwort", JOptionPane.ERROR_MESSAGE);
            // Hier Bonuspunkte nicht hinzufügen
        }
    }

    public static void main(String[] args) {
        // Neues Spiel im Event Dispatch Thread starten
        SwingUtilities.invokeLater(SnakeGame::new); // Stellt sicher, dass die GUI im richtigen Thread erstellt wird
    }
}