/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika.zad1;

import java.awt.Graphics2D;

/**
 *
 * @author Sawik
 */
public abstract class Transformation {
    private int startX = 0;
    private int startY = 0;
    private int currentX = 0;
    private int currentY = 0;
    
    public abstract int[] transform(int startX, int startY, int endX, int endY, Graphics2D g2);
    public abstract void transform(Graphics2D g2);
    public abstract void transform(DrawingClass drawingClass);
    public abstract String getName();

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
