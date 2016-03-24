package grafika.gimp.filtry.maski;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

public class MedianMaskFilter extends FilterWindow {

    private JSlider medianSlider;
    private JLabel medianSliderLabel;

    public MedianMaskFilter(ImageWindow imageWindow) {
        super("Median Mask", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    private void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newWorkStealingPool();

        int maskSize = medianSlider.getValue();
        int maskSideLength = (maskSize - 1) / 2;
        int maskItems = (int) Math.pow(maskSize, 2);

        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int x = 0; x < newImage.getWidth(); x++) {
                        List<Integer> redMask = new ArrayList<>(maskItems);
                        List<Integer> greenMask = new ArrayList<>(maskItems);
                        List<Integer> blueMask = new ArrayList<>(maskItems);
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
                                redMask.add(red);
                                greenMask.add(green);
                                blueMask.add(blue);
                            }
                        }
                        redMask.sort(Integer::compare);
                        greenMask.sort(Integer::compare);
                        blueMask.sort(Integer::compare);

                        int newRed = countMedian(redMask);
                        int newGreen = countMedian(greenMask);
                        int newBlue = countMedian(blueMask);
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

    private int countMedian(List<Integer> list) {
        int middle = list.size() / 2;
        if (list.size() % 2 == 1) {
            return list.get(middle);
        } else {
            return (list.get(middle - 1) + list.get(middle)) / 2;
        }
    }

    private void addComponents() {
        medianSlider = new JSlider(3, 49, 3);
        medianSlider.setMajorTickSpacing(10);
        medianSlider.setMinorTickSpacing(2);
        medianSlider.setPaintLabels(true);
        medianSlider.setPaintTicks(true);
        medianSlider.setSnapToTicks(true);
        medianSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!medianSlider.getValueIsAdjusting()) {
                    medianSliderLabel.setText(medianSlider.getValue() + "");
                    filterImage();
                }
            }
        });
        medianSliderLabel = new JLabel("   3");

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;

        JPanel inputPanel = new JPanel();
        add(inputPanel, c);
        inputPanel.add(medianSlider);
        inputPanel.add(medianSliderLabel);
    }

    @Override
    public void closeMe() {
        getWindow().setMedianMaskWindow(null);
    }

    @Override
    protected void invokeAfterBuild() {
        super.invokeAfterBuild();
        filterImage();
    }
}
