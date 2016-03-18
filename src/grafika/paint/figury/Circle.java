/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika.paint.figury;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sawik
 */
public class Circle extends DrawingClass {

    private static Circle circle = new Circle();

    public Circle() {
        super();
    }
    
    public Circle(Color color) {
        super(color);
    }

    public static Circle getInstance() {
        return circle;
    }

    @Override
    public void drawShape(Graphics2D g2) {
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
        g2.drawOval(begx, begy, diffx, diffy);
    }

    @Override
    public DrawingClass getNewInstance(Color color) {
        return new Circle(color);
    }
}
