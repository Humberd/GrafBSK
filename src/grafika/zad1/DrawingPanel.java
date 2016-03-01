package grafika.zad1;

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

    public DrawingPanel(Prymitywy prymitywy) {
        super();
        this.prymitywy = prymitywy;
        MyAdapter myAdapter = new MyAdapter();
        setCurrentDrawing(DrawingClass.getDefaultDrawingClass());
        setCurrentDrawingTemplate(DrawingClass.getDefaultDrawingClass());
        addMouseMotionListener(myAdapter);
        addMouseListener(myAdapter);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHEAST;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.ipadx = 3;
        editModeLabel.setOpaque(true);
        editModeLabel.setVisible(false);
        add(editModeLabel, c);
        c.anchor = GridBagConstraints.NORTHWEST;
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
//        if (transformation != null) {
//            transformation.transform(g2);
//        }
        currentDrawing.draw(g2);
        if (isInEditMode()) {
            prymitywy.updateEditValues(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getCurrentX(), currentDrawing.getCurrentY());
        }
    }

    public void updateModelValues(int startX, int startY, int endX, int endY) {
        currentDrawing.setStartX(startX);
        currentDrawing.setStartY(startY);
        currentDrawing.setCurrentX(endX);
        currentDrawing.setCurrentY(endY);
        repaint();
    }

    public void updateModelValues(int endX, int endY) {
        currentDrawing.setCurrentX(endX);
        currentDrawing.setCurrentY(endY);
        repaint();
    }

    private class MyAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (transformation == null) {
                updateModelValues(e.getX(), e.getY());
            } else {
                transformation.setCurrentX(e.getX());
                transformation.setCurrentY(e.getY());
                transformation.transform(currentDrawing);
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (transformation == null) {
                pushNewDrawing();
                currentDrawing.setIsDrawing(true);
                updateModelValues(e.getX(), e.getY(), e.getX(), e.getY());
            } else {
                transformation.setStartX(e.getX());
                transformation.setStartY(e.getY());
                transformation.setCurrentX(e.getX());
                transformation.setCurrentY(e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            currentDrawing.setIsDrawing(false);
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
        pushNewDrawing(currentDrawingTemplate.getNewInstance());
    }

    public void pushNewDrawing(DrawingClass drawingClass) {
//        if (isInEditMode() == false) {
//            undoList.addLast(new HistoryPush(drawingClass));
//            redoList.clear();
//            drawingList.addLast(getCurrentDrawing());
//        }
        undoList.addLast(new HistoryPush(drawingClass));
        redoList.clear();
        drawingList.addLast(getCurrentDrawing());
        setCurrentDrawing(drawingClass);
    }

    public void popLastDrawing() {
        undoList.addLast(new HistoryPop(drawingList.pop()));
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

}
