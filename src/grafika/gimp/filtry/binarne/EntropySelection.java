package grafika.gimp.filtry.binarne;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.SimpleThreadPool;

public class EntropySelection implements BinarizationType {

    @Override
    public int filterImage(BufferedImage baseImage) {
        ExecutorService executor = Executors.newWorkStealingPool();

        int[] grays = new int[256];
        for (int tempy = 0; tempy < baseImage.getHeight(); tempy++) {
            int y = tempy;
            executor.execute(() -> {
                for (int x = 0; x < baseImage.getWidth(); x++) {
                    Color pixelColor = new Color(baseImage.getRGB(x, y));
                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();
                    int gray = (int) ((red + green + blue) / 3);
                    grays[gray]++;
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] entropies = new double[256];
        int sum = baseImage.getHeight() * baseImage.getWidth();
        for (int i = 0; i < entropies.length; i++) {
            double px = (double) (grays[i]) / sum;
            entropies[i] = px * Math.log10(px);
        }
        double min = 255;
        int index = 0;
        for (int i = 0; i < entropies.length; i++) {
            if (entropies[i] < min) {
                min = entropies[i];
                index = i;
            }
        }
        return index;
    }
}
