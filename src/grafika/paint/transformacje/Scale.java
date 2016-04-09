package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import grafika.paint.figury.DrawingClass;

public class Scale extends Transformation{

    public Scale(DrawingPanel panel) {
        super(panel);
    }

    @Override
    public void transform() throws GraphicsException {
        Transformable tr = getTransformableDrawing();
        DrawingClass curr = getCurrentDrawing();
        tr.scale(startPoint, scale);
    }

    @Override
    public String getName() {
        return "Scale";
    }

    @Override
    public void createWindow() {
        setWindow(new ScaleWindow(getDrawingPanel()));
    }
}
