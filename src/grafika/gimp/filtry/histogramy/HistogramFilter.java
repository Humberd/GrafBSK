package grafika.gimp.filtry.histogramy;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import test.SimpleThreadPool;

public class HistogramFilter extends FilterWindow {

    private JRadioButton widenRadio;
    private JRadioButton equalizeRadio;

    HistogramFilterType filterType;

    public HistogramFilter(ImageWindow imageWindow) {
        super("Histogram Filters", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    public void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = filterType.filterImage(baseImage);
        
        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
        System.gc();
    }

    private void addComponents() {
        widenRadio = new JRadioButton("Widen");
        widenRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Widen();
                filterImage();
            }
        });

        equalizeRadio = new JRadioButton("Equalize");
        equalizeRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                filterType = new Equalize();
//                filterImage();
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(widenRadio);
        group.add(equalizeRadio);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 5, 2, 5);
        c.anchor = GridBagConstraints.LINE_START;
        add(widenRadio, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(2, 5, 10, 5);
        add(equalizeRadio, c);

    }

    @Override
    public void closeMe() {
        getWindow().setHistogramFilterWindow(null);
    }

    @Override
    protected void invokeAfterBuild() {
        super.invokeAfterBuild(); //To change body of generated methods, choose Tools | Templates.
//        widenRadio.doClick();
    }
    
}

interface HistogramFilterType {

    public BufferedImage filterImage(final BufferedImage baseImage);
}

class Widen implements HistogramFilterType {

    @Override
    public BufferedImage filterImage(final BufferedImage baseImage) {
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newWorkStealingPool();

        int[] reds = new int[256];
        int[] greens = new int[256];
        int[] blues = new int[256];

        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(() -> {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    Color pixelColor = new Color(baseImage.getRGB(x, y));
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    reds[red]++;
                    greens[green]++;
                    blues[blue]++;
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////////////////////
        int imageSize = baseImage.getHeight() * baseImage.getWidth();
        int threshold = (int) ((imageSize / 256) * 0.01);
        if (threshold == 0) {
            threshold = 1;
        }
        //////////////////////////
        int redLowestValue = getMinValue(reds, threshold);
        int redHighestValue = getMaxValue(reds, threshold);

        int greenLowestValue = getMinValue(greens, threshold);
        int greenHighestValue = getMaxValue(greens, threshold);

        int blueLowestValue = getMinValue(blues, threshold);
        int blueHighestValue = getMaxValue(blues, threshold);

        ///////////////////////////
        executor = Executors.newWorkStealingPool();
        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(() -> {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    Color pixelColor = new Color(baseImage.getRGB(x, y));
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    int newRed = (int) (((double)(red - redLowestValue) / (double)(redHighestValue - redLowestValue)) * 255);
                    int newGreen = (int) (((double)(green - greenLowestValue) / (double)(greenHighestValue - greenLowestValue)) * 255);
                    int newBlue = (int) (((double)(blue - blueLowestValue) / (double)(blueHighestValue - blueLowestValue)) * 255);
                    newRed = validateColor(newRed);
                    newGreen = validateColor(newGreen);
                    newBlue = validateColor(newBlue);
                    Color newPixelColor = new Color(newRed, newGreen, newBlue);
                    newImage.setRGB(x, y, newPixelColor.getRGB());
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newImage;
    }
    
    private int validateColor(int value) {
        if (value > 255) {
            return 255;
        } else if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }

    private int getMinValue(int[] tab, int threshold) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] >= threshold) {
                return i;
            }
        }
        return 0;
    }

    private int getMaxValue(int[] tab, int threshold) {
        for (int i = tab.length - 1; i >= 0; i--) {
            if (tab[i] >= threshold) {
                return i;
            }
        }
        return 255;
    }

}

class Equalize implements HistogramFilterType {

    @Override
    public BufferedImage filterImage(final BufferedImage baseImage) {
        return null;
    }

}
