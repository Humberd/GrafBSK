/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import grafika.paint.figury.DrawingClass;
import grafika.paint.figury.Line;
import grafika.paint.figury.Point;

/**
 *
 * @author Sawik
 */
public class Translate extends Transformation {

    public Translate(DrawingPanel panel) {
        super(panel);
    }

    @Override
    public String getName() {
        return "Translate";
    }

//    public void transform(DrawingClass drawingClass) {
//        int x = getCurrentX() - getStartX();
//        int y = getCurrentY() - getStartY();
//        
//        drawingClass.setStartX(drawingClass.getStartX()+x);
//        drawingClass.setStartY(drawingClass.getStartY()+y);
//        drawingClass.setCurrentX(drawingClass.getCurrentX()+x);
//        drawingClass.setCurrentY(drawingClass.getCurrentY()+y);
//        
//        setStartX(getCurrentX());
//        setStartY(getCurrentY());
//    }
    @Override
    public void transform() throws GraphicsException {
        Transformable tr = getTransformableDrawing();
        DrawingClass curr = getCurrentDrawing();
        Point vector;
        int x = currentPoint.x - startPoint.x;
        int y = currentPoint.y - startPoint.y;
        vector = new Point(x, y);
        tr.translate(vector);
    }

    @Override
    public void setStage(int stage) {
        super.setStage(0);
    }
}
