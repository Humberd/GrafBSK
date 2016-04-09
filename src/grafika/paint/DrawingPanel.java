package grafika.paint;

import grafika.paint.transformacje.Transformation;
import grafika.paint.figury.DrawingClass;
import grafika.paint.figury.Point;
import grafika.paint.transformacje.TransformationWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

    private Prymitywy prymitywy;
    private Transformation transformation;
    private DrawingClass currentDrawingTemplate;
    private DrawingClass currentDrawing;
    private JLabel editModeLabel = new JLabel("Edit mode");
    private JLabel transformationModeLabel = new JLabel();
    private LinkedList<DrawingClass> drawingList = new LinkedList<>();
    private LinkedList<History> undoList = new LinkedList<>();
    private LinkedList<History> redoList = new LinkedList<>();
    private boolean inEditMode = false;
    private boolean inTranslateMode = false;
    private boolean inRotateMode = false;
    private boolean inScaleMode = false;
    
    private TransformationWindow transformationWindow;
    
    private MyAdapter myAdapter;

    private Color selectedColor;

    public DrawingPanel(Prymitywy prymitywy) {
        super();
        this.prymitywy = prymitywy;
        myAdapter = new MyAdapter();
        setCurrentDrawing(DrawingClass.getDefaultDrawingClass());
        setCurrentDrawingTemplate(DrawingClass.getDefaultDrawingClass());
        addMouseMotionListener(myAdapter);
        addMouseListener(myAdapter);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHEAST;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 3;
        editModeLabel.setOpaque(true);
        editModeLabel.setVisible(false);
        add(editModeLabel, c);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        transformationModeLabel.setOpaque(true);
        transformationModeLabel.setVisible(false);
        add(transformationModeLabel, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (DrawingClass drawing : drawingList) {
            drawing.draw(g2);
        }

        currentDrawing.draw(g2);
        if (isInEditMode()) {
            prymitywy.updateEditValues(currentDrawing.getStartPoint().x, currentDrawing.getStartPoint().y, currentDrawing.getCurrentPoint().x, currentDrawing.getCurrentPoint().y);
        }
        if (transformation != null) {
            transformation.drawHelper(g2);
        }
    }

    public void setStartPoint(java.awt.Point p) {
        currentDrawing.setStartPoint(Point.getPoint(p));
        repaint();
    }

    public void setCurrentPoint(java.awt.Point p) {
        currentDrawing.setCurrentPoint(Point.getPoint(p));
        repaint();
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public MyAdapter getMyAdapter() {
        return myAdapter;
    }

    public void setMyAdapter(MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    private class MyAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            if (transformation == null) {
                setCurrentPoint(p);
            } else {
                transformation.setCurrentPoint(p);
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            if (transformation == null) {
                pushNewDrawing();
                currentDrawing.setIsDrawing(true);
                setStartPoint(p);
                setCurrentPoint(p);
            } else {
                currentDrawing.setIsTransforming(true);
                transformation.setStartPoint(p);
                transformation.setCurrentPoint(p);
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            if (transformation == null) {
                currentDrawing.setIsDrawing(false);
            } else {
                currentDrawing.setIsTransforming(false);
                transformation.setEndPoint(p);
            }
        }

    }

    public DrawingClass getCurrentDrawing() {
        return currentDrawing;
    }

    public void setCurrentDrawing(DrawingClass currentDrawing) {
        this.currentDrawing = currentDrawing;
    }

    public void setCurrentDrawingTemplate(DrawingClass currentDrawingTemplate) {
        this.currentDrawingTemplate = currentDrawingTemplate;
    }

    public boolean isInEditMode() {
        return inEditMode;
    }

    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
        editModeLabel.setVisible(inEditMode);
    }

    public boolean isInTranslateMode() {
        return inTranslateMode;
    }

    public void setInTranslateMode(boolean inTranslateMode) {
        this.inTranslateMode = inTranslateMode;
    }

    public boolean isInRotateMode() {
        return inRotateMode;
    }

    public void setInRotateMode(boolean inRotateMode) {
        this.inRotateMode = inRotateMode;
    }

    public boolean isInScaleMode() {
        return inScaleMode;
    }

    public void setInScaleMode(boolean inScaleMode) {
        this.inScaleMode = inScaleMode;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        if (transformation == null) {
            transformationModeLabel.setVisible(false);
            this.transformation = null;
        } else if (this.transformation != null && this.transformation.getName().equals(transformation.getName())) {
            transformationModeLabel.setVisible(false);
            this.transformation = null;
        } else {
            transformationModeLabel.setText(transformation.getName());
            transformationModeLabel.setVisible(true);
            this.transformation = transformation;
            this.transformation.createWindow();
        }
    }

    ////////////////////////////////////////////
    private class HistoryPop implements History {

        private DrawingClass drClass;

        public HistoryPop(DrawingClass dr) {
            this.drClass = dr;
        }

        @Override
        public void undo() {
            drawingList.addLast(currentDrawing);
            setCurrentDrawing(drClass);
        }

        @Override
        public void redo() {
            setCurrentDrawing(drawingList.removeLast());
        }
    }

    private class HistoryPush implements History {

        private DrawingClass drClass;

        public HistoryPush(DrawingClass dr) {
            this.drClass = dr;
        }

        @Override
        public void undo() {
            setCurrentDrawing(drawingList.removeLast());
        }

        @Override
        public void redo() {
            drawingList.addLast(currentDrawing);
            setCurrentDrawing(drClass);
        }

    }

    public void pushNewDrawing() {
        pushNewDrawing(currentDrawingTemplate.getNewInstance(selectedColor));
    }

    public void pushNewDrawing(DrawingClass drawingClass) {
        undoList.addLast(new HistoryPush(drawingClass));
        redoList.clear();
        drawingList.addLast(getCurrentDrawing());
        setCurrentDrawing(drawingClass);
    }

    public void popLastDrawing() {
        DrawingClass c = getCurrentDrawing();
        setCurrentDrawing(drawingList.pop());
        undoList.addLast(new HistoryPop(c));
        redoList.clear();
    }

    public void undoLastDrawing() {
        if (!undoList.isEmpty()) {
            History p = undoList.removeLast();
            redoList.addLast(p);
            p.undo();
            repaint();
        }
    }

    public void redoPreviousDrawing() {
        if (!redoList.isEmpty()) {
            History p = redoList.removeLast();
            undoList.addLast(p);
            p.redo();
            repaint();
        }
    }

    private void addAdapterListener(MouseAdapter adapter) {
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void removeAdapterListener(MouseAdapter adapter) {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
    }

}
