import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> body; // Liste von Segmenten der Schlange
    private int direction; // Richtung der Schlange (0=oben, 1=rechts, 2=unten, 3=links)

    public Snake() {
        body = new ArrayList<>();
        body.add(new Point(5, 5)); // Startpunkt der Schlange
        direction = 1; // Startbewegung nach rechts
    }

    public void move() {
        // Kopf der Schlange
        Point head = body.get(0);
        Point newHead = new Point(head);

        // Bewegung in die aktuelle Richtung
        switch (direction) {
            case 0 -> newHead.y -= 1; // nach oben
            case 1 -> newHead.x += 1; // nach rechts
            case 2 -> newHead.y += 1; // nach unten
            case 3 -> newHead.x -= 1; // nach links
        }

        // Neuen Kopf an den Anfang der Liste setzen
        body.add(0, newHead);
        body.remove(body.size() - 1); // Das letzte Segment entfernen (Schlange bewegt sich)
    }

    public void grow() {
        // Fügt ein neues Segment an der letzten Position der Schlange hinzu
        body.add(new Point(body.get(body.size() - 1)));
    }

    public boolean checkCollision() {
        // Prüfen, ob der Kopf mit einem anderen Teil der Schlange kollidiert
        Point head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true; // Kollision mit sich selbst
            }
        }
        return false;
    }

    public boolean hitWall(int width, int height) {
        // Prüfen, ob die Schlange die Wand berührt
        Point head = body.get(0);
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }

    public Point getHead() {
        return body.get(0); // Kopf der Schlange
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public List<Point> getBody() {
        return body; // Methode, die die Liste der Segmente der Schlange zurückgibt
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x * 25, p.y * 25, 25, 25); // Jedes Segment der Schlange zeichnen
        }
    }
}