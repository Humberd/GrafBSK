package grafika.zad1;

import java.awt.Graphics2D;

public abstract class DrawingClass {

    private boolean isDrawing = false;
    private int startX = 0;
    private int startY = 0;
    private int currentX = 0;
    private int currentY = 0;
    
    private static DrawingClass defaultDrawingClass = new Line();

    public abstract void draw(Graphics2D g2);
    
    public abstract DrawingClass getNewInstance();
    
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
}
