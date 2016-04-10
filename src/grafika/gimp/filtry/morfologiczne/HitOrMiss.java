package grafika.gimp.filtry.morfologiczne;

import grafika.paint.figury.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class HitOrMiss {

    public static BufferedImage filterImage(BufferedImage baseImage, int[][] mask) {
        if (validateMask(mask)) {
            BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            int maskSize = mask.length;
            int maskSideLength = (maskSize - 1) / 2;
            int maskItems = (int) Math.pow(maskSize, 2);

            for (int y = 0; y < baseImage.getHeight(); y++) {
                for (int x = 0; x < baseImage.getWidth(); x++) {
                    int result = maskOperation(x, y, maskSideLength, baseImage, mask);
                    newImage.setRGB(x, y, new Color(result, result, result).getRGB());
                }
            }

            return newImage;
        } else {
            return baseImage;
        }
    }

    private static boolean validateMask(int[][] mask) {
        if (mask.length % 2 == 0) {
            return false;
        }
        for (int i = 0; i < mask.length; i++) {
            if (mask[i].length != mask.length) {
                return false;
            }
        }
        return true;
    }

    private static int maskOperation(int x, int y, int maskSideLength, BufferedImage baseImage, int[][] mask) {
        int background = 255;
        int shape = 0;
        for (int my = -maskSideLength; my <= maskSideLength; my++) {
            if (my + y < 0 || my + y >= baseImage.getHeight()) {
                return background;
            }
            for (int mx = -maskSideLength; mx <= maskSideLength; mx++) {
                if (mx + x < 0 || mx + x >= baseImage.getWidth()) {
                    return background;
                }
                if (mx == 0 && my == 0) {
                    continue;
                }
                if (new Color(baseImage.getRGB(x + mx, y + my)).getRed() != mask[mx][my]) {
                    return background;
                }
            }
        }
        return shape;
    }
}
