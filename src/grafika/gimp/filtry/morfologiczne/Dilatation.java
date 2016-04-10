package grafika.gimp.filtry.morfologiczne;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Dilatation implements MorphologicalType {

    private static final int[][] mask = new int[][]{
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0}};

    @Override
    public BufferedImage filterImage(BufferedImage baseImage) {
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        final int maskSize = 3;
        final int maskSideLength = (maskSize - 1) / 2;
        final int maskItems = (int) Math.pow(maskSize, 2);

        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                int result = maskOperation(x, y, maskSideLength, baseImage);
                newImage.setRGB(x, y, new Color(result, result, result).getRGB());
            }
        }

        return newImage;
    }

    private int maskOperation(int x, int y, int maskSideLength, BufferedImage baseImage) {
        int background = 255;
        int shape = 0;
        for (int my = -maskSideLength; my <= maskSideLength; my++) {
            if (my + y < 0 || my + y >= baseImage.getHeight()) {
                continue;
            }
            for (int mx = -maskSideLength; mx <= maskSideLength; mx++) {
                if (mx + x < 0 || mx + x >= baseImage.getWidth()) {
                    continue;
                }
                if (mx == 0 && my == 0) {
                    continue;
                }
                if (new Color(baseImage.getRGB(x + mx, y + my)).getRed() == shape) {
                    return shape;
                }
            }
        }
        return background;
    }

}
