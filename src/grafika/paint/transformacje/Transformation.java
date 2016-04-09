package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import grafika.paint.figury.DoublePoint;
import grafika.paint.figury.DrawingClass;
import grafika.paint.figury.Point;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Transformation {

    private DrawingPanel drawingPanel;
    protected Point startPoint = null;
    protected Point currentPoint = null;
    protected Point endPoint = null;
    protected double angle;
    protected DoublePoint scale;

    private DrawingClass helperShape = null;

    private TransformationWindow window;

    private int stage = 0;

    public Transformation(DrawingPanel panel) {
        this.drawingPanel = panel;
    }

    public DrawingClass getCurrentDrawing() {
        return drawingPanel.getCurrentDrawing();
    }

    public Transformable getTransformableDrawing() throws GraphicsException {
        if (getCurrentDrawing() instanceof Transformable) {
            return (Transformable) getCurrentDrawing();
        } else {
            throw new GraphicsException("Figure is not Transformable");
        }
    }

    public void drawHelper(Graphics2D g2) {
        if (helperShape != null) {
            helperShape.draw(g2);
        }
    }

    public abstract void transform() throws GraphicsException;

    public abstract String getName();

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        setStage((getStage() + 1) % 3);
        this.startPoint = startPoint;
    }

    public void setStartPoint(java.awt.Point startPoint) {
        setStartPoint(Point.getPoint(startPoint));
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
        try {
            transform();
        } catch (GraphicsException ex) {
            Logger.getLogger(Transformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCurrentPoint(java.awt.Point currentPoint) {
        setCurrentPoint(Point.getPoint(currentPoint));
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
        try {
            transform();
        } catch (GraphicsException ex) {
            Logger.getLogger(Transformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEndPoint(java.awt.Point currentPoint) {
        setEndPoint(Point.getPoint(currentPoint));
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public DrawingClass getHelperShape() {
        return helperShape;
    }

    public void setHelperShape(DrawingClass helperShape) {
        this.helperShape = helperShape;
    }

    public TransformationWindow getWindow() {
        return window;
    }

    public void setWindow(TransformationWindow window) {
        this.window = window;
    }
    
    public void createWindow() {
        
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setScale(DoublePoint scale) {
        this.scale = scale;
    }

}
