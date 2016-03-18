package grafika.paint.colorPicker;

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

public class HsvChoosingPanel extends ColorChoosingPanel {

    private BufferedImage matrix;
    private BufferedImage slider;
    private MatrixPanel matrixPanel;
    private SliderPanel sliderPanel;

    public HsvChoosingPanel() {
        super(360, 255, 255);
        super.setPcs(new PropertyChangeSupport(this));
        matrix = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        slider = new BufferedImage(30, 360, BufferedImage.TYPE_INT_RGB);
        matrixPanel = new MatrixPanel();
        sliderPanel = new SliderPanel();
        setColorPreview(new JPanel());
        getColorPreview().setLayout(null);
        getColorPreview().setPreferredSize(new Dimension(60, 40));
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
                        setValue(255);
                    } else if (e.getX() < 0) {
                        setValue(0);
                    } else {
                        setValue(e.getX());
                    }
                    if (e.getY() > 255) {
                        setSaturation(255);
                    } else if (e.getY() < 0) {
                        setSaturation(0);
                    } else {
                        setSaturation(e.getY());
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
            g2.drawOval(getValue() - 7, getSaturation() - 7, 14, 14);
            getColorPreview().setBackground(new Color(Color.HSBtoRGB((float) getHue() / getMaxHue(), (float) getSaturation() / getMaxSaturation(), (float) getValue() / getMaxValue())));

        }

        public void refresh() {
//            JColorChooser.showDialog(null, "dfdf", Color.yellow);
            float foo = (float) getHue() / slider.getHeight();
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    int rgb = Color.HSBtoRGB(foo, (float) x / 255, (float) y/ 255);
                    matrix.setRGB(y, x, rgb);
//                    matrix.setRGB(y, x, new Color(getHue(), x, y).getRGB());
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
                    if (e.getY() > 360) {
                        setHue(360);
                    } else if (e.getY() < 0) {
                        setHue(0);
                    } else {
                        setHue(e.getY());
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
//            g2.drawImage(slider, null, 0, 0);
            g2.drawImage(slider, 0, 0, slider.getWidth(), slider.getHeight(), null);
            g2.setColor(Color.WHITE);
            g2.setStroke(stroke);
            g2.drawLine(0, getHue(), slider.getWidth(), getHue());
        }

        public void refresh() {
            for (int y = 0; y < slider.getHeight(); y++) {
                for (int x = 0; x < slider.getWidth(); x++) {
                    float foo = (float) y / slider.getHeight();
                    int rgb = Color.HSBtoRGB(foo, 1, 1);
                    slider.setRGB(x, y, rgb);
                }
            }
            repaint();
        }

    }

    public int getHue() {
        return getCurrentColor1Value();
    }

    public void setHue(int hue) {
        getPcs().firePropertyChange("hue", hue - 1, hue);
        matrixPanel.refresh();
        sliderPanel.repaint();
        setCurrentColor1Value(hue);
    }

    public int getSaturation() {
        return getCurrentColor2Value();
    }

    public void setSaturation(int saturation) {
        getPcs().firePropertyChange("saturation", saturation - 1, saturation);
        setCurrentColor2Value(saturation);
        matrixPanel.repaint();
    }

    public int getValue() {
        return getCurrentColor3Value();
    }

    public void setValue(int value) {
        getPcs().firePropertyChange("value", value - 1, value);
        setCurrentColor3Value(value);
        matrixPanel.repaint();
    }

    public int getMaxHue() {
        return getMaxColor1Value();
    }

    public int getMaxSaturation() {
        return getMaxColor2Value();
    }

    public int getMaxValue() {
        return getMaxColor3Value();
    }

    public int getDefaultHue() {
        return getDefaultColor1Value();
    }

    public int getDefaultSaturation() {
        return getDefaultColor2Value();
    }

    public int getDefaultValue() {
        return getDefaultColor3Value();
    }
}
