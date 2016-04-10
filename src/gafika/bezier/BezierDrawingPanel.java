package gafika.bezier;

import grafika.paint.figury.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JPanel;

public class BezierDrawingPanel extends JPanel {

    private BezierWindow bezierWindow;
    private LinkedList<Point> points = new LinkedList<>();
    private MouseAdapter mouseListeners = new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            java.awt.Point p = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            java.awt.Point p = e.getPoint();
            for (Point point : points) {
                if (point.isMoving()) {
                    return;
                }
            }
            addNewPoint(new Point(p.x, p.y));
        }
    };

    private int curveDotsNumber = 1000;
    private int curveDotsArray[][];

    public BezierDrawingPanel(BezierWindow window) {
        super();
        this.bezierWindow = window;
        setBackground(Color.WHITE);
        addMouseListeners(mouseListeners);
//        addNewPoint(new Point(255, 255));
//        addNewPoint(new Point(330, 150));
//        addNewPoint(new Point(520, 162));
//        addNewPoint(new Point(572, 310));
    }

    public void updateBezierCurve() {

        if (points.size() == 4) {
//            int tempDotsArray[][] = new int[1000][2];
//            double x = 0;
//            double y = 0;
//            for (double t = 0, counter = 0; t <= 1; t += 0.001, counter++) {
//                x = points.get(0).x * Math.pow(1 - t, 3) + 3 * points.get(1).x * t * Math.pow(1 - t, 2) + 3 * points.get(2).x * Math.pow(t, 2) * (1 - t) + points.get(3).x * Math.pow(t, 3);
//                y = points.get(0).y * Math.pow(1 - t, 3) + 3 * points.get(1).y * t * Math.pow(1 - t, 2) + 3 * points.get(2).y * Math.pow(t, 2) * (1 - t) + points.get(3).y * Math.pow(t, 3);
//                tempDotsArray[(int) counter][0] = (int) x;
//                tempDotsArray[(int) counter][1] = (int) y;
//            }
//            curveDotsArray = tempDotsArray;

        }
        if (points.size() > 2) {
            int N = points.size() - 1;
            long[] newtonSymbols = new long[points.size()];
            for (int i = 0; i < newtonSymbols.length; i++) {
                newtonSymbols[i] = quickNewtonSymbol(N, i);
            }
//            System.out.println(Arrays.toString(newtonSymbols));
            int tempDotsArray[][] = new int[curveDotsNumber][2];
            for (double t = 0, step = (double) 1 / curveDotsNumber, counter = 0; t <= 1; t += step, counter++) {
                double x = 0;
                double y = 0;
                for (int i = 0; i < points.size(); i++) {
                    double b = (newtonSymbols[i] * Math.pow(t, i) * Math.pow((double) (1) - t, N - i));
                    x += points.get(i).x * b;
                    y += points.get(i).y * b;
                }
                tempDotsArray[(int) counter][0] = (int) x;
                tempDotsArray[(int) counter][1] = (int) y;
//                System.out.println((int) x + "/" + (int) y);
            }
            curveDotsArray = tempDotsArray;
        } else {
            curveDotsArray = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        updateBezierCurve();
        for (Point point : points) {
            point.draw(g2);
        }
        if (bezierWindow.getShowLinesCheckBox().isSelected()) {
            for (int i = 0; i < points.size() - 1; i++) {
                g2.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
            }
        }
        if (curveDotsArray != null && curveDotsArray.length != 0) {
            for (int i = 0; i < curveDotsArray.length-1; i++) {
                g2.drawLine(curveDotsArray[i][0], curveDotsArray[i][1], curveDotsArray[i+1][0], curveDotsArray[i+1][1]);
            }
        }
    }

    private void addMouseListeners(MouseAdapter adapter) {
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void removeMouseListeners(MouseAdapter adapter) {
        removeMouseListener(adapter);
        removeMouseMotionListener(adapter);
    }

    private void addNewPoint(Point point) {
        point.setSurface(this);
        addMouseListeners(point.getMouseAdapter());
        points.add(point);
        repaint();
    }

    public void removeLastPoint() {
        Point point = points.removeLast();
        removeMouseListeners(point.getMouseAdapter());
        repaint();
    }

    public void clearPoints() {
        for (int i = 0, size = points.size(); i < size; i++) {
            removeLastPoint();
        }
    }

    public long slowNewtonSymbol(int n, int k) {
        if (n == k || k == 0) {
            return 1;
        }
        return slowNewtonSymbol(n - 1, k - 1) + slowNewtonSymbol(n - 1, k);
    }

    public long quickNewtonSymbol(int n, int k) {
//        if (k == 0 || k == n) {
//            return 1;
//        }
//        // take the lowest possible k to reduce computing using: n over k = n over (n-k)
//        k = java.lang.Math.min(k, n - k);
//
//        // holds the high number: fi. (1000 over 990) holds 991..1000
//        long highnumber[] = new long[k];
//        for (int i = 0; i < k; i++) {
//            highnumber[i] = n - i; // the high number first order is important
//        }    // holds the dividers: fi. (1000 over 990) holds 2..10
//        int dividers[] = new int[k - 1];
//        for (int i = 0; i < k - 1; i++) {
//            dividers[i] = k - i;
//        }
//
//        // for every dividers there is always exists a highnumber that can be divided by 
//        // this, the number of highnumbers being a sequence that equals the number of 
//        // dividers. Thus, the only trick needed is to divide in reverse order, so 
//        // divide the highest divider first trying it on the highest highnumber first. 
//        // That way you do not need to do any tricks with primes.
//        for (int divider : dividers) {
//            for (int i = 0; i < k; i++) {
//                if (highnumber[i] % divider == 0) {
//                    highnumber[i] /= divider;
//                    break;
//                }
//            }
//        }
//
//        // multiply remainder of highnumbers
//        long result = 1;
//        for (long high : highnumber) {
//            result *= high;
//        }
//        return result;

        if (k == 0) {
            return 1;
        }
        if (2 * k > n) {
            return quickNewtonSymbol(n, n - k);
        }
        long e = n - k + 1;
        for (int i = 2; i < k + 1; i++) {
            e *= n - k + i;
            e /= i;
        }
        return e;
    }

}
