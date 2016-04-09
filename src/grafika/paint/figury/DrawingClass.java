package grafika.paint.figury;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public abstract class DrawingClass implements Drawable {

    private boolean isDrawing = false;
    private boolean isTransforming = false;
    private Point startPoint = new Point(0, 0);
    private Point currentPoint = new Point(0, 0);
//    private int startX = 0;
//    private int startY = 0;
//    private int currentX = 0;
//    private int currentY = 0;
    private Color color;
    private Color defaultColor = Color.BLACK;

    private static DrawingClass defaultDrawingClass = new Line();

    public DrawingClass() {
        color = defaultColor;
    }

    public DrawingClass(Color color) {
        if (color != null) {
            this.color = color;
        } else {
            this.color = defaultColor;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Color temp = g2.getColor();
        g2.setColor(color);
        drawShape(g2);
        g2.setColor(temp);
    }

    public abstract void drawShape(Graphics2D g2);

    public abstract DrawingClass getNewInstance(Color color);

    public static DrawingClass getDefaultDrawingClass() {
        return defaultDrawingClass;
    }

    public ArrayList<MouseAdapter> getMouseAdapters() {
        return new ArrayList<>(0);
    }

    public boolean isIsDrawing() {
        return isDrawing;
    }

    public void setIsDrawing(boolean isDrawing) {
        this.isDrawing = isDrawing;
    }

//    public int getStartX() {
//        return startX;
//    }
//
//    public void setStartX(int startX) {
//        this.startX = startX;
//    }
//
//    public int getStartY() {
//        return startY;
//    }
//
//    public void setStartY(int startY) {
//        this.startY = startY;
//    }
//
//    public int getCurrentX() {
//        return currentX;
//    }
//
//    public void setCurrentX(int currentX) {
//        this.currentX = currentX;
//    }
//
//    public int getCurrentY() {
//        return currentY;
//    }
//
//    public void setCurrentY(int currentY) {
//        this.currentY = currentY;
//    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public boolean isIsTransforming() {
        return isTransforming;
    }

    public void setIsTransforming(boolean isTransforming) {
        this.isTransforming = isTransforming;
    }
}
