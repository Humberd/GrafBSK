package grafika.gimp.filtry.maski;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.SimpleThreadPool;

public class HighPassMaskFilter extends FilterWindow {

    private static final int[][] Mask = new int[][]{{
        -1, -1, -1}, {
        -1, 9, -1}, {
        -1, -1, -1}};

    public HighPassMaskFilter(ImageWindow imageWindow) {
        super("High-Pass Mask", imageWindow);
    }

    public void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newWorkStealingPool();

        WritableRaster raster = newImage.getRaster();

        final int maskSize = 3;
        int maskSideLength = (maskSize - 1) / 2;
        int maskItems = (int) Math.pow(maskSize, 2);
        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(() -> {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    int redSum = 0;
                    int greenSum = 0;
                    int blueSum = 0;

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
                            redSum += red * Mask[my + 1][mx + 1];
                            greenSum += green * Mask[my + 1][mx + 1];
                            blueSum += blue * Mask[my + 1][mx + 1];
                        }
                    }
                    if (redSum > 255) {
                        redSum = 255;
                    } else if (redSum < 0) {
                        redSum = 0;
                    }
                    if (greenSum > 255) {
                        greenSum = 255;
                    } else if (greenSum < 0) {
                        greenSum = 0;
                    }
                    if (blueSum > 255) {
                        blueSum = 255;
                    } else if (blueSum < 0) {
                        blueSum = 0;
                    }
                    Color newPixelColor = new Color(redSum, greenSum, blueSum);
                    newImage.setRGB(x, y, newPixelColor.getRGB());
//                    raster.setPixel(x, y, new int[]{result});
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        pushNewImage(newImage);
        System.gc();
    }

    @Override
    protected void customInit() {

    }

    @Override
    protected void invokeAfterBuild() {
        super.invokeAfterBuild(); //To change body of generated methods, choose Tools | Templates.
        filterImage();
        setVisible(false);
    }

    @Override
    public void closeMe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
