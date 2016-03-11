package grafika.zad1.colorPicker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JPanel;

public class RgbChoosingPanel extends ColorChoosingPanel {

    private BufferedImage matrix;
    private BufferedImage slider;
    private MatrixPanel matrixPanel;
    private SliderPanel sliderPanel;

    public RgbChoosingPanel() {
        super(255, 255, 255);
        super.setPcs(new PropertyChangeSupport(this));
        matrix = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        slider = new BufferedImage(30, 256, BufferedImage.TYPE_INT_RGB);
        matrixPanel = new MatrixPanel();
        sliderPanel = new SliderPanel();
//        setBackground(Color.red);
        addComponents();
    }

    private void addComponents() {
        setLayout(new FlowLayout());
        add(matrixPanel);
        add(sliderPanel);

        matrixPanel.refresh();
        sliderPanel.refresh();
    }

    private class MatrixPanel extends JPanel {

        private Stroke stroke = new BasicStroke(2);

        public MatrixPanel() {
            super();
            setLayout(null);
            setPreferredSize(new Dimension(matrix.getWidth(), matrix.getHeight()));
            MouseAdapter adapter = new MouseAdapter() {

                private void update(MouseEvent e) {
                    if (e.getX() > 255) {
                        setBlue(255);
                    } else if (e.getX() < 0) {
                        setBlue(0);
                    } else {
                        setBlue(e.getX());
                    }
                    if (e.getY() > 255) {
                        setGreen(255);
                    } else if (e.getY() < 0) {
                        setGreen(0);
                    } else {
                        setGreen(e.getY());
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    update(e);
                    System.out.println(getBlue());
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    update(e);
                }

            };
            addMouseMotionListener(adapter);
            addMouseListener(adapter);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(matrix, null, 0, 0);
            g2.setStroke(stroke);
            g2.setColor(Color.BLACK);
            g2.drawOval(getBlue() - 7, getGreen() - 7, 14, 14);

        }

        public void refresh() {
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    matrix.setRGB(y, x, new Color(getRed(), x, y).getRGB());
                }
            }
            repaint();
        }

    }

    private class SliderPanel extends JPanel {

        private Stroke stroke = new BasicStroke(2);

        public SliderPanel() {
            super();
            setLayout(null);
            setPreferredSize(new Dimension(slider.getWidth(), slider.getHeight()));
            MouseAdapter adapter = new MouseAdapter() {

                private void update(MouseEvent e) {
                    if (e.getY() > 255) {
                        setRed(255);
                    } else if (e.getY() < 0) {
                        setRed(0);
                    } else {
                        setRed(e.getY());
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    update(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    update(e);
                }

            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(slider, null, 0, 0);
            g2.setColor(Color.WHITE);
            g2.setStroke(stroke);
            g2.drawLine(0, getRed(), slider.getWidth(), getRed());
        }

        public void refresh() {
            for (int y = 0; y < slider.getHeight(); y++) {
                for (int x = 0; x < slider.getWidth(); x++) {
                    slider.setRGB(x, y, new Color(y, 0, 0).getRGB());
                }
            }
            repaint();
        }

    }

    public int getRed() {
        return getCurrentColor1Value();
    }

    public void setRed(int red) {
        getPcs().firePropertyChange("red", red - 1, red);
        matrixPanel.refresh();
        sliderPanel.refresh();
        setCurrentColor1Value(red);
    }

    public int getGreen() {
        return getCurrentColor2Value();
    }

    public void setGreen(int green) {
        getPcs().firePropertyChange("green", green - 1, green);
        setCurrentColor2Value(green);
        matrixPanel.repaint();
    }

    public int getBlue() {
        return getCurrentColor3Value();
    }

    public void setBlue(int blue) {
        getPcs().firePropertyChange("blue", blue - 1, blue);
        setCurrentColor3Value(blue);
        matrixPanel.repaint();
    }

    public int getMaxRed() {
        return getMaxColor1Value();
    }

    public int getMaxGreen() {
        return getMaxColor2Value();
    }

    public int getMaxBlue() {
        return getMaxColor3Value();
    }

    public int getDefaultRed() {
        return getDefaultColor1Value();
    }

    public int getDefaultGreen() {
        return getDefaultColor2Value();
    }

    public int getDefaultBlue() {
        return getDefaultColor3Value();
    }

}
