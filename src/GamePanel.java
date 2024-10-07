import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    public static final int WIDTH = 600; // Breite des Panels
    public static final int HEIGHT = 600; // Höhe des Panels
    private int foodEaten = 0; // Zähler für Futter-Treffer
    private Snake snake; // Beispiel für die Schlange
    private Point food; // Beispiel für das Futter
    private SnakeGame snakeGame; // Referenz auf die SnakeGame-Instanz
    private final int size = 30; // Größe des Schlangenteils

    public GamePanel(SnakeGame snakeGame) {
        this.snakeGame = snakeGame; // Setze die Referenz auf die SnakeGame-Instanz
        this.snake = new Snake(); // Initialisiere die Schlange

        // Initialisiere dein Spiel (Timer, Listener usw.)
        Timer timer = new Timer(200, this);
        timer.start();
        generateFood(); // Futter generieren, wenn das Panel erstellt wird

        // Tasteneingaben für Steuerung der Schlange
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> snake.setDirection(0);
                    case KeyEvent.VK_RIGHT -> snake.setDirection(1);
                    case KeyEvent.VK_DOWN -> snake.setDirection(2);
                    case KeyEvent.VK_LEFT -> snake.setDirection(3);
                }
            }
        });
        setFocusable(true); // Fokus auf das Panel setzen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Spiel-Logik hier (Bewegung der Schlange, Kollision usw.)
        snake.move(); // Schlange bewegen

        // Überprüfen, ob die Schlange das Futter frisst
        if (snakeEatsFood()) {
            foodEaten++;
            snake.grow(); // Schlange wachsen lassen
            if (foodEaten == 3) {
                snakeGame.askQuestion(); // Frage aus der SnakeGame-Klasse stellen
                foodEaten = 0; // Zähler zurücksetzen
            }
            generateFood(); // Neues Futter generieren
        }

        repaint(); // Aktualisiere die Anzeige
    }

    // Methode zum Überprüfen, ob die Schlange das Futter frisst
    private boolean snakeEatsFood() {
        Rectangle snakeHead = new Rectangle(snake.getHeadX(), snake.getHeadY(), snake.getWidth(), snake.getHeight());
        Rectangle foodRect = new Rectangle(food.x, food.y, 30, 30); // Futter hat eine Größe von 30x30

        return snakeHead.intersects(foodRect); // Überprüft, ob die Schlange das Futter berührt
    }

    // Methode zum Generieren von Futter an einer zufälligen Position
    private void generateFood() {
        int x = (int) (Math.random() * (getWidth() / size)) * size; // Position auf einem 30-Pixel-Raster
        int y = (int) (Math.random() * (getHeight() / size)) * size; // Position auf einem 30-Pixel-Raster
        food = new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Zeichne das Raster
        drawGrid(g); // Methode zum Zeichnen des Rasters
        // Zeichne das Spiel
        drawFood(g); // Methode zum Zeichnen des Futters
        snake.draw(g); // Methode zum Zeichnen der Schlange
    }

    // Methode zum Zeichnen des Rasters
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY); // Farbe für das Raster
        int width = getWidth();
        int height = getHeight();

        // Vertikale Linien
        for (int x = 0; x < width; x += size) {
            g.drawLine(x, 0, x, height); // von (x, 0) bis (x, height)
        }

        // Horizontale Linien
        for (int y = 0; y < height; y += size) {
            g.drawLine(0, y, width, y); // von (0, y) bis (width, y)
        }
    }

    // Methode zum Zeichnen des Futters
    private void drawFood(Graphics g) {
        g.setColor(Color.RED); // Farbe für das Futter
        g.fillRect(food.x, food.y, size, size); // Futter als Quadrat zeichnen
    }
}