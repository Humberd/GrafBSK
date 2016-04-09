package grafika.paint.figury;

import java.awt.Color;
import java.awt.Graphics2D;

public class Line extends Polygon{

    public Line() {
        super(2);
    }
    
    public Line(Color color) {
        this();
        super.setColor(color);
    }

//    @Override
//    public void drawShape(Graphics2D g2) {
//        Point[] points = getPoints();
//        g2.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
//    }

    public void setStarter(Point p) {
        getPoints()[0] = p;
    }
    
    public void setEnder(Point p) {
        getPoints()[1] = p;
    }
    
    @Override
    public void updatePoints() {
        Point[] points = getPoints();
        points[0] = getStartPoint();
        points[1] = getCurrentPoint();
    }
    
    @Override
    public DrawingClass getNewInstance(Color color) {
        return new Line(color);
    }  
}
