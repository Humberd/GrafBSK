package grafika.paint.figury;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Point {

    public int x;
    public int y;

    private int drawRadius = 7;

    private Color drawColor = Color.BLACK;

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        private boolean isMoving = false;

        @Override
        public void mouseDragged(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            
            System.out.println("hello");
            if (isMoving) {
                x = p.x;
                y = p.y;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            double radius = Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
            if (radius <= drawRadius) {
                isMoving = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            isMoving = false;
        }
    };

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int drawRadius) {
        this(x, y);
        setDrawRadius(drawRadius);
    }

    public Point(int x, int y, Color color) {
        this(x, y);
        setDrawColor(color);
    }

    public Point(int x, int y, int drawradius, Color color) {
        this(x, y, drawradius);
        setDrawColor(color);
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public void add(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    public void draw(Graphics2D g2) {
        draw(g2, new Point(0, 0));
    }

    public void draw(Graphics2D g2, Point vector) {
        if (drawRadius != 0) {
            int drawX = (x + vector.x) - (drawRadius / 2);
            int drawY = (y + vector.y) - (drawRadius / 2);
            g2.fillOval(drawX, drawY, drawRadius, drawRadius);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Point getPoint(java.awt.Point p) {
        return new Point((int) p.getX(), (int) p.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getDrawRadius() {
        return drawRadius;
    }

    public void setDrawRadius(int drawRadius) {
        this.drawRadius = drawRadius;
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public Color getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }
}
