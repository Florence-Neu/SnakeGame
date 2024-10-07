import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private int direction; // 0: Up, 1: Right, 2: Down, 3: Left
    private final int size = 30; // Größe des Schlangenteils
    private int score;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(300, 300)); // Startposition
        direction = 1; // Startbewegung nach rechts
        score = 0;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head.x, head.y);

        // Bewegungsrichtung ändern
        switch (direction) {
            case 0 -> newHead.y -= size; // nach oben
            case 1 -> newHead.x += size; // nach rechts
            case 2 -> newHead.y += size; // nach unten
            case 3 -> newHead.x -= size; // nach links
        }

        // Wrapping: Schlange erscheint auf der anderen Seite, wenn sie den Rand verlässt
        if (newHead.x < 0) {
            newHead.x = GamePanel.WIDTH - size; // Rechts erscheinen
        } else if (newHead.x >= GamePanel.WIDTH) {
            newHead.x = 0; // Links erscheinen
        }

        if (newHead.y < 0) {
            newHead.y = GamePanel.HEIGHT - size; // Unten erscheinen
        } else if (newHead.y >= GamePanel.HEIGHT) {
            newHead.y = 0; // Oben erscheinen
        }

        // Füge neuen Kopf hinzu und entferne das letzte Segment
        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        // Schlange wachsen lassen
        Point tail = body.getLast();
        body.add(new Point(tail.x, tail.y));
    }

    public void setDirection(int newDirection) {
        // Verhindere, dass die Schlange sich in die entgegengesetzte Richtung bewegt
        if (Math.abs(newDirection - direction) != 2) {
            direction = newDirection;
        }
    }

    public int getHeadX() {
        return body.getFirst().x;
    }

    public int getHeadY() {
        return body.getFirst().y;
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x, p.y, size, size);
        }
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }
}