package grafika.gimp.filtry.maski;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import test.SimpleThreadPool;

public class AverageMaskFilter extends FilterWindow {

    private JSlider averageSlider;
    private JLabel averageSliderLabel;

    public AverageMaskFilter(ImageWindow imageWindow) {
        super("Average Mask", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    private void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newWorkStealingPool();

        int maskSize = averageSlider.getValue();
        int maskSideLength = (maskSize - 1) / 2;
        int maskItems = (int) Math.pow(maskSize, 2);

        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int x = 0; x < newImage.getWidth(); x++) {
                        int redMaskSum = 0;
                        int greenMaskSum = 0;
                        int blueMaskSum = 0;

                        for (int my = -maskSideLength; my <= maskSideLength; my++) {
                            if (my + y < 0 || my + y >= newImage.getHeight()) {
                                continue;
                            }
                            for (int mx = -maskSideLength; mx <= maskSideLength; mx++) {
                                if (mx + x < 0 || mx + x >= newImage.getWidth()) {
                                    continue;
                                }
                                Color maskPixelColor = new Color(baseImage.getRGB(x + mx, y + my));
                                int red = maskPixelColor.getRed();
                                int green = maskPixelColor.getGreen();
                                int blue = maskPixelColor.getBlue();
                                redMaskSum += red;
                                greenMaskSum += green;
                                blueMaskSum += blue;
                            }
                        }
                        int newRed = redMaskSum / maskItems;
                        int newGreen = greenMaskSum / maskItems;
                        int newBlue = blueMaskSum / maskItems;
                        Color newPixelColor = new Color(newRed, newGreen, newBlue);
                        newImage.setRGB(x, y, newPixelColor.getRGB());
                    }
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
        System.gc();
    }

    private void addComponents() {
        averageSlider = new JSlider(3, 49, 3);
        averageSlider.setMajorTickSpacing(10);
        averageSlider.setMinorTickSpacing(2);
        averageSlider.setPaintLabels(true);
        averageSlider.setPaintTicks(true);
        averageSlider.setSnapToTicks(true);
        averageSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!averageSlider.getValueIsAdjusting()) {
                    averageSliderLabel.setText(averageSlider.getValue() + "");
                    filterImage();
                }
            }
        });
        averageSliderLabel = new JLabel("   3");

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;

        JPanel inputPanel = new JPanel();
        add(inputPanel, c);
        inputPanel.add(averageSlider);
        inputPanel.add(averageSliderLabel);
    }

    @Override
    public void closeMe() {
        getWindow().setAverageMaskWindow(null);
    }

}
