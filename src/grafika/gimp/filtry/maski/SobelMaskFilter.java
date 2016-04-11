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

public class SobelMaskFilter extends FilterWindow {

    private static final int[][] Sx = new int[][]{{
        -1, 0, 1}, {
        -2, 0, 2}, {
        -1, 0, 1}};

    private static final int[][] Sy = new int[][]{{
        -1, -2, -1}, {
        0, 0, 0}, {
        1, 2, 1}};

    public SobelMaskFilter(ImageWindow imageWindow) {
        super("Sobel Mask", imageWindow);
    }

    public void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newWorkStealingPool();

        final int maskSize = 3;
        int maskSideLength = (maskSize - 1) / 2;
        int maskItems = (int) Math.pow(maskSize, 2);

        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(() -> {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    int redSumX = 0;
                    int redSumY = 0;
                    int greenSumX = 0;
                    int greenSumY = 0;
                    int blueSumX = 0;
                    int blueSumY = 0;

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
                            redSumX += red * Sx[my + 1][mx + 1];
                            redSumY += red * Sy[my + 1][mx + 1];
                            greenSumX += green * Sx[my + 1][mx + 1];
                            greenSumY += green * Sy[my + 1][mx + 1];
                            blueSumX += blue * Sx[my + 1][mx + 1];
                            blueSumY += blue * Sy[my + 1][mx + 1];
                        }
                    }
//                    int sumX = (redSumX + greenSumX + blueSumX) / 3;
//                    int sumY = (redSumY + greenSumY + blueSumY) / 3;
//                    int result = (int) Math.sqrt(Math.pow(sumX, 2) + Math.pow(sumY, 2));
//                    if (result > 255) {
//                        result = 255;
//                    }
                    int newRed = Math.abs(redSumX) + Math.abs(redSumY);
                    int newGreen = Math.abs(greenSumX) + Math.abs(greenSumY);
                    int newBlue = Math.abs(blueSumX) + Math.abs(blueSumY);
                    if (newRed > 255) {
                        newRed = 255;
                    }
                    if (newGreen > 255) {
                        newGreen = 255;
                    }
                    if (newBlue > 255) {
                        newBlue = 255;
                    }
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

    }

}
