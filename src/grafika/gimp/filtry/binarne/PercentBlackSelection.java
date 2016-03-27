package grafika.gimp.filtry.binarne;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSlider;
import test.SimpleThreadPool;

public class PercentBlackSelection implements BinarizationType {

    private JSlider slider;

    public PercentBlackSelection(JSlider slider) {
        this.slider = slider;
    }

    @Override
    public int filterImage(BufferedImage baseImage) {
        int imageSize = baseImage.getHeight() * baseImage.getWidth();
        double percent = (double) (slider.getValue()) / 100;
        double rightSide = imageSize * percent;

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
                    int gray = (int) (0.21 * red + 0.72 * green + 0.07 * blue);
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

        int sum = 0;
        for (int i = 0; i < grays.length; i++) {
            sum += grays[i];
            if (sum >= rightSide) {
                System.out.println(i);
                return i;
            }
        }
        return 255;
    }
}
