/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika.zad1.transformacje;

import grafika.zad1.transformacje.Transformation;
import grafika.zad1.figury.DrawingClass;
import java.awt.Graphics2D;

/**
 *
 * @author Sawik
 */
public class Translate extends Transformation{

    @Override
    public int[] transform(int startX, int startY, int endX, int endY, Graphics2D g2) {
        int[] coords = new int[4];
        
        
        setStartX(getCurrentX());
        setStartY(getCurrentY());
        
        return coords;
    }

    @Override
    public String getName() {
        return "Translate";
    }

    @Override
    public void transform(Graphics2D g2) {
        int x = getCurrentX() - getStartX();
        int y = getCurrentY() - getStartY();
        g2.translate(x, y);
    }

    @Override
    public void transform(DrawingClass drawingClass) {
        int x = getCurrentX() - getStartX();
        int y = getCurrentY() - getStartY();
        
        drawingClass.setStartX(drawingClass.getStartX()+x);
        drawingClass.setStartY(drawingClass.getStartY()+y);
        drawingClass.setCurrentX(drawingClass.getCurrentX()+x);
        drawingClass.setCurrentY(drawingClass.getCurrentY()+y);
        
        setStartX(getCurrentX());
        setStartY(getCurrentY());
    }
    
}
