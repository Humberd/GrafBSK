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
public class Rectangle extends Polygon{
    
    public Rectangle() {
        super(4);
    }
    
    public Rectangle(Color color) {
        this();
        super.setColor(color);
    }

//    @Override
//    public void drawShape(Graphics2D g2) {
//        int diffx = getCurrentX() - getStartX();
//        int diffy = getCurrentY() - getStartY();
//        int begx = getStartX();
//        int begy = getStartY();
//        if (diffx < 0) {
//            begx = getCurrentX();
//            diffx = getStartX() - getCurrentX();
//        }
//        if (diffy < 0) {
//            begy = getCurrentY();
//            diffy = getStartY() - getCurrentY();
//        }
////        System.out.println("RECTANGLE: "+begx + ", " + begy + " | " + diffx + ", " + diffy);
//
//        g2.drawRect(begx, begy, diffx, diffy);
//    }
    
    @Override
    public void updatePoints() {
        Point[] points = getPoints();
        Point startPoint = getStartPoint();
        Point currentPoint = getCurrentPoint();
        
        points[0] = startPoint;
        points[1].setX(startPoint.x);
        points[1].setY(currentPoint.y);
        points[2] = currentPoint;
        points[3].setX(currentPoint.x);
        points[3].setY(startPoint.y);
        
    }

    @Override
    public DrawingClass getNewInstance(Color color) {
        return new Rectangle(color);
    }
}
