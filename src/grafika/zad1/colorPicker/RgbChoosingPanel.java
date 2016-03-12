package grafika.zad1.colorPicker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
        setColorPreview(new JPanel());
        getColorPreview().setLayout(null);
        getColorPreview().setPreferredSize(new Dimension(60, 40));
//        setBackground(Color.red);
        addComponents();
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        add(matrixPanel, c);
        add(sliderPanel, c);
        add(getColorPreview(), c);

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
            getColorPreview().setBackground(new Color(getRed(), getGreen(), getBlue()));

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
        setRedConverter(red, true);
//        fireConversionPropertyChange();
    }

    public void setRedConverter(int red, boolean converter) {
        if (converter) {
            getConverter().rgbToCmyk();
        }
        getPcs().firePropertyChange("red", red - 1, red);
        matrixPanel.refresh();
        sliderPanel.repaint();
        setCurrentColor1Value(red);
    }

    public int getGreen() {
        return getCurrentColor2Value();
    }

    public void setGreen(int green) {
        setGreenConverter(green, true);
    }

    public void setGreenConverter(int green, boolean converter) {
        if (converter) {
            getConverter().rgbToCmyk();
        }
        getPcs().firePropertyChange("green", green - 1, green);
        setCurrentColor2Value(green);
        matrixPanel.repaint();
//        fireConversionPropertyChange();
    }

    public int getBlue() {
        return getCurrentColor3Value();
    }

    public void setBlue(int blue) {
        setBlueConverter(blue, true);
    }

    public void setBlueConverter(int blue, boolean converter) {
        if (converter) {
            getConverter().rgbToCmyk();
        }
        getPcs().firePropertyChange("blue", blue - 1, blue);
        setCurrentColor3Value(blue);
        matrixPanel.repaint();
//        fireConversionPropertyChange();
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

    private void fireConversionPropertyChange() {
        float red = (float) getRed() / getMaxRed();
        float green = (float) getGreen() / getMaxGreen();
        float blue = (float) getBlue() / getMaxBlue();
        float black = min(1 - red, 1 - green, 1 - blue);
        float cyan = (1 - red - black) / (1 - black);
        float magneta = (1 - green - black) / (1 - black);
        float yellow = (1 - blue - black) / (1 - black);
//        System.out.print("[" + getRed() + ", " + getGreen() + ", " + getBlue() + "] ->");
//        System.out.print("[" + cyan + ", " + magneta + ", " + yellow + ", " + black + "]");
//        System.out.println("");
        if (black == 1) {
            cyan = 0;
            magneta = 0;
            yellow = 0;
            black = 100;
        } else {
            cyan *= 100;
            magneta *= 100;
            yellow *= 100;
            black *= 100;
        }
        getPcs().firePropertyChange("cyan", cyan - 1, cyan);
        getPcs().firePropertyChange("magneta", magneta - 1, magneta);
        getPcs().firePropertyChange("yellow", yellow - 1, yellow);
        getPcs().firePropertyChange("black", black - 1, black);
    }

    private float min(float a, float b, float c) {
        return min(min(a, b), c);
    }

    private float min(float a, float b) {
        return a < b ? a : b;
    }
}
