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
public class Scale extends Transformation {

    @Override
    public int[] transform(int startX, int startY, int endX, int endY, Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "Scale";
    }

    @Override
    public void transform(Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public void transform(DrawingClass drawingClass) {
        int xCenter = (drawingClass.getStartX() + drawingClass.getCurrentX()) / 2;
        int yCenter = (drawingClass.getStartY() + drawingClass.getCurrentY()) / 2;

    }

}
