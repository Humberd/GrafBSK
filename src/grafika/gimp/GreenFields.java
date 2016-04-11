package grafika.gimp;

import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class GreenFields extends FilterWindow {

    public GreenFields(ImageWindow imageWindow) {
        super("Greeny Fields Chooser", imageWindow);
    }

    public void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int imageSize = baseImage.getWidth() * baseImage.getHeight();
        int redMax = 120;
        int redMin = 0;
        int greenPixels = 0;
        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                Color prevPixelColor = new Color(baseImage.getRGB(x, y));
                int red = prevPixelColor.getRed();
                int green = prevPixelColor.getGreen();
                int blue = prevPixelColor.getBlue();

                int resultColor = 255;

                if (red <= redMax && red >= redMin) {
                    if (green <= 255 - red && blue <= red) {
                        resultColor = 0;
                        greenPixels++;
                    }
                }
                Color newPixelColor = new Color(resultColor, resultColor, resultColor);
                newImage.setRGB(x, y, newPixelColor.getRGB());
            }
        }
        int percentGreen = (int) (((double) (greenPixels) / imageSize) * 100);
        pushNewImage(newImage);
        JOptionPane.showMessageDialog(null, percentGreen + "%", "Percent Green Counter", JOptionPane.INFORMATION_MESSAGE);
        System.gc();
    }

    @Override
    protected void customInit() {
    }

    @Override
    protected void invokeAfterBuild() {
        super.invokeAfterBuild();
        filterImage();
        setVisible(false);
    }

    @Override
    public void closeMe() {
        getWindow().setGreenFieldsWindow(null);
    }

}
