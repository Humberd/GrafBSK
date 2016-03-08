package grafika.zad1.figury;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class DrawingClass {

    private boolean isDrawing = false;
    private int startX = 0;
    private int startY = 0;
    private int currentX = 0;
    private int currentY = 0;
    private Color color;
    private Color defaultColor = Color.BLACK;
    
    private static DrawingClass defaultDrawingClass = new Line();
    
    public DrawingClass() {
        color = defaultColor;
    }
    
    public DrawingClass(Color color) {
        if (color != null) {
            this.color =color;
        } else {
            this.color = defaultColor;
        }
    }

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

    public boolean isIsDrawing() {
        return isDrawing;
    }

    public void setIsDrawing(boolean isDrawing) {
        this.isDrawing = isDrawing;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
