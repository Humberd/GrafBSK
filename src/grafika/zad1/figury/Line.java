/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika.zad1.figury;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Sawik
 */
public class Line extends DrawingClass{

    public Line() {
        super();
    }
    
    public Line(Color color) {
        super(color);
    }

    @Override
    public void drawShape(Graphics2D g2) {
        g2.drawLine(getStartX(), getStartY(), getCurrentX(), getCurrentY());
    }

    @Override
    public DrawingClass getNewInstance(Color color) {
        return new Line(color);
    }  
}
