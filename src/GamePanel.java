import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Snake snake;
    private Point food; // Essen
    private boolean gameOver; // Spielende-Status
    private int score; // Punktestand

    public GamePanel() {
        // Hintergrundfarbe setzen
        setBackground(Color.BLACK);

        snake = new Snake();
        spawnFood(); // Zufälliges Essen generieren

        timer = new Timer(200, this);
        timer.start();

        gameOver = false; // Am Anfang ist das Spiel nicht vorbei
        score = 0;

        // Tastensteuerung hinzufügen
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (!gameOver) {
                    if (key == KeyEvent.VK_UP && snake.getDirection() != 2) {
                        snake.setDirection(0); // nach oben
                    } else if (key == KeyEvent.VK_RIGHT && snake.getDirection() != 3) {
                        snake.setDirection(1); // nach rechts
                    } else if (key == KeyEvent.VK_DOWN && snake.getDirection() != 0) {
                        snake.setDirection(2); // nach unten
                    } else if (key == KeyEvent.VK_LEFT && snake.getDirection() != 1) {
                        snake.setDirection(3); // nach links
                    }
                } else if (key == KeyEvent.VK_ENTER) {
                    resetGame(); // Spiel zurücksetzen, wenn Enter gedrückt wird
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); // Zeichnet das Spielfeld, die Schlange, das Essen und Spielende
    }

    private void draw(Graphics g) {
        if (!gameOver) {
            // Spielfeld zeichnen
            g.setColor(Color.GRAY);
            // Rasterlinien zeichnen, ohne die letzte Linie zu zeichnen, um Abdeckung zu vermeiden
            for (int i = 0; i <= getWidth(); i += 25) { // <= für die letzte Linie
                g.drawLine(i, 0, i, getHeight()); // Vertikale Linien
            }
            for (int i = 0; i <= getHeight(); i += 25) { // <= für die letzte Linie
                g.drawLine(0, i, getWidth(), i); // Horizontale Linien
            }

            // Schlange zeichnen
            snake.draw(g);

            // Essen zeichnen
            g.setColor(Color.RED);
            g.fillRect(food.x * 25, food.y * 25, 25, 25); // Futter im Raster zeichnen

            // Punktestand anzeigen
            g.setColor(Color.WHITE);
            g.drawString("Punkte: " + score, 10, 10);
        } else {
            // Spielende-Bildschirm
            g.setColor(Color.WHITE);
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2 - 10);
            g.drawString("Drücke ENTER, um neu zu starten", getWidth() / 2 - 90, getHeight() / 2 + 10);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            snake.move(); // Schlange bewegen
            checkCollisions(); // Prüfen auf Kollisionen
            checkFood(); // Prüfen, ob das Essen eingesammelt wurde
        }
        repaint(); // Fenster aktualisieren
    }

    private void checkFood() {
        // Prüfen, ob die Schlange das Essen erreicht hat
        if (snake.getHead().equals(food)) {
            snake.grow(); // Schlange wächst
            score++; // Punkte erhöhen
            spawnFood(); // Neues Essen generieren

            // Geschwindigkeit erhöhen, wenn die Schlange länger wird
            if (timer.getDelay() > 50) {
                timer.setDelay(timer.getDelay() - 5); // Timer schneller machen
            }
        }
    }

    private void checkCollisions() {
        // Prüfen, ob die Schlange sich selbst getroffen hat
        if (snake.checkCollision()) {
            gameOver = true; // Spielende, wenn sie sich selbst trifft
        } else {
            // Wenn die Schlange die Wand berührt, sie an die gegenüberliegende Seite setzen
            Point head = snake.getHead();
            if (head.x < 0) { // Wenn die Schlange links aus dem Fenster geht
                head.x = (600 / 25) - 1; // An die rechte Seite setzen
            } else if (head.x >= (600 / 25)) { // Wenn die Schlange rechts aus dem Fenster geht
                head.x = 0; // An die linke Seite setzen
            }
            if (head.y < 0) { // Wenn die Schlange oben aus dem Fenster geht
                head.y = (600 / 25) - 1; // An die untere Seite setzen
            } else if (head.y >= (600 / 25)) { // Wenn die Schlange unten aus dem Fenster geht
                head.y = 0; // An die obere Seite setzen
            }
        }
    }

    private void spawnFood() {
        Random rand = new Random();
        boolean positionValid = false;

        while (!positionValid) {
            int x = rand.nextInt(600 / 25); // 600 ist die Breite des Spielfelds
            int y = rand.nextInt(600 / 25); // 600 ist die Höhe des Spielfelds

            // Überprüfen, ob die Position nicht auf der Schlange ist
            positionValid = true; // Setze zu Beginn an, dass die Position gültig ist
            for (Point segment : snake.getBody()) { // Gehe durch die Segmente der Schlange
                if (segment.equals(new Point(x, y))) {
                    positionValid = false; // Ungültige Position
                    break; // Breche die Schleife ab
                }
            }

            if (positionValid) {
                food = new Point(x, y); // Wenn die Position gültig ist, setze das Futter
            }
        }
    }

    private void resetGame() {
        snake = new Snake(); // Neue Schlange erstellen
        score = 0; // Punktestand zurücksetzen
        spawnFood(); // Neues Essen generieren
        timer.setDelay(100); // Geschwindigkeit zurücksetzen
        gameOver = false; // Spiel wieder starten
    }
}