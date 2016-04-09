package grafika.paint.figury;

import grafika.paint.transformacje.Transformable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class Polygon extends DrawingClass implements Transformable {

    private Point[] points;
    private Point[] vectors;

    public Polygon(Point p) {
        this(new Point[]{p});
    }

    public Polygon(Point[] points) {
        super();
        this.points = points;
        this.vectors = new Point[points.length];
        for (int i = 0; i < this.vectors.length; i++) {
            this.vectors[i] = new Point(0, 0);
        }
    }

    public Polygon(int size) {
        super();
        if (size >= 0) {
            points = new Point[size];
            vectors = new Point[size];
            for (int i = 0; i < size; i++) {
                points[i] = new Point(0, 0);
                vectors[i] = new Point(0, 0);
            }
        } else {
            points = new Point[0];
            vectors = new Point[0];
        }
    }

    public Polygon() {
        this(0);
    }

    public int getSize() {
        return points.length;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
    }

    @Override
    public void drawShape(Graphics2D g2) {
        if (isIsDrawing()) {
            updatePoints();
        }
        for (int i = 0; i < points.length - 1; i++) {
//            System.out.print(vectors[i]);
            g2.drawLine(points[i].x + vectors[i].x, points[i].y + vectors[i].y, points[i + 1].x + vectors[i + 1].x, points[i + 1].y + vectors[i + 1].y);
        }
//        System.out.println("");
        if (points.length > 2) {
            g2.drawLine(points[points.length - 1].x + vectors[vectors.length - 1].x, points[points.length - 1].y + vectors[vectors.length - 1].y, points[0].x + vectors[0].x, points[0].y + vectors[0].y);
        }
        for (int i = 0; i < points.length; i++) {
            points[i].draw(g2, vectors[i]);
        }
    }

    public void updatePoints() {

    }

    public void addPoint(Point p) {
        Point[] newArray = new Point[points.length + 1];
        for (int i = 0; i < points.length; i++) {
            newArray[i] = points[i];
        }
        newArray[newArray.length - 1] = p;
        points = newArray;
    }

    public void removePoint(int index) {
        if (index < points.length && index >= 0) {
            Point[] newArray = new Point[points.length - 1];
            int counter = 0;
            for (int i = 0; i < points.length; i++) {
                if (i == index) {
                    continue;
                }
                newArray[counter++] = points[i];
            }
            points = newArray;
        }
    }

    public void editPoint(Point p, int index) {
        if (index < points.length && index >= 0) {
            points[index] = p;
        }
    }

    public void editLastPoint(Point p) {
        points[points.length - 1] = p;
    }

    @Override
    public DrawingClass getNewInstance(Color color) {
        Polygon pol = new Polygon();
        pol.setColor(color);
        return pol;
    }
////////////////////////////////////

    @Override
    public void translate(Point vector) {
        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = vector;
        }
    }

    @Override
    public void rotate(Point centerPoint, double angle) {
        System.out.println(centerPoint + " / " + angle);
    }

    @Override
    public void scale(Point centerPoint, DoublePoint vector) {
        System.out.println(centerPoint + " / " + vector);
    }

////////////////////////////////////////////
    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    @Override
    public void setIsTransforming(boolean isTransforming) {
        super.setIsTransforming(isTransforming);
        if (isTransforming) {
            for (int i = 0; i < points.length; i++) {
                points[i].add(vectors[i]);
            }
        }
        for (Point vector : vectors) {
            vector.reset();
        }
    }

    @Override
    public ArrayList<MouseAdapter> getMouseAdapters() {
        ArrayList<MouseAdapter> list = new ArrayList<>(points.length);
        for (Point point : points) {
            list.add(point.getMouseAdapter());
        }
        return list;
    }

}
