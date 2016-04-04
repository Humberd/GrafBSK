package grafika.gimp.filtry.binarne;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.SimpleThreadPool;

public class MeanIterativeSelection implements BinarizationType {

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

        int baseMean = intervalMean(0, 255, grays);
        int nextMean = 0;
        do {
            nextMean = (intervalMean(0, baseMean - 1, grays) + intervalMean(baseMean, 255, grays)) / 2;
            if (nextMean != baseMean) {
                baseMean = nextMean;
            } else {
                break;
            }
        } while (true);
        return baseMean;
    }

    private int intervalMean(int a, int b, int[] tab) {
        if (a < b && a >= 0 && b <= 255 && tab.length - 1 >= b) {
            int sum = 0;
            int pixelsSum = 0;
            for (int i = a; i <= b; i++) {
                sum += tab[i];
                pixelsSum += tab[i] * i;
            }
            if (sum == 0) {
                sum = 1;
            }
            int mean = (int) (pixelsSum / sum);
            return mean;
        }
        return 0;
    }
}
