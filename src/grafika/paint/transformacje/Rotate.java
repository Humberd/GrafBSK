package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import grafika.paint.figury.DrawingClass;
import grafika.paint.figury.Line;
import grafika.paint.figury.Point;
import java.awt.Color;

public class Rotate extends Transformation {

    public Rotate(DrawingPanel panel) {
        super(panel);
    }

    @Override
    public void transform() throws GraphicsException {
//        if (getStage() == 0) {
//
//        } else if (getStage() == 1) {
//            if (getHelperShape() == null) {
////                Line line = new Line();
////                line.setStarter(new Point(0, 0));
////                line.setEnder(new Point(0, 0));
////                line.setColor(Color.RED);
////                setHelperShape(line);
//            }
//        }
        Transformable tr = getTransformableDrawing();
        DrawingClass curr = getCurrentDrawing();
        tr.rotate(startPoint, angle);
    }

    @Override
    public String getName() {
        return "Rotate";
    }

    @Override
    public void createWindow() {
        setWindow(new RotateWindow(getDrawingPanel()));
    }

}
