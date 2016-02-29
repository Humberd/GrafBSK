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
public class Rectangle extends DrawingClass{
    
    public Rectangle() {
    }

    @Override
    public void draw(Graphics2D g2) {
        int diffx = getCurrentX() - getStartX();
        int diffy = getCurrentY() - getStartY();
        int begx = getStartX();
        int begy = getStartY();
        if (diffx < 0) {
            begx = getCurrentX();
            diffx = getStartX() - getCurrentX();
        }
        if (diffy < 0) {
            begy = getCurrentY();
            diffy = getStartY() - getCurrentY();
        }
//        System.out.println("RECTANGLE: "+begx + ", " + begy + " | " + diffx + ", " + diffy);

        g2.drawRect(begx, begy, diffx, diffy);
    }

    @Override
    public DrawingClass getNewInstance() {
        return new Rectangle();
    }
}
