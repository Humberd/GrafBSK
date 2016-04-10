package grafika.gimp.filtry.morfologiczne;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Thinning implements MorphologicalType {

    private int[][] mask1 = new int[][]{
        {0, 0, 0},
        {-1, 1, -1},
        {1, 1, 1}};
    private int[][] mask2 = new int[][]{
        {-1, 0, 0},
        {1, 1, 0},
        {-1, 1, -1}};

//    @Override
//    public BufferedImage filterImage(BufferedImage baseImage) {
//        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//
//        final int maskSize = mask1.length;
//        final int maskSideLength = (maskSize - 1) / 2;
//        final int maskItems = (int) Math.pow(maskSize, 2);
//
//        for (int y = 0; y < baseImage.getHeight(); y++) {
//            for (int x = 0; x < baseImage.getWidth(); x++) {
//                int result = maskOperation(x, y, maskSideLength, baseImage);
//                newImage.setRGB(x, y, new Color(result, result, result).getRGB());
//            }
//        }
//        return newImage;
//    }
    @Override
    public BufferedImage filterImage(BufferedImage baseImage) {
        BufferedImage newImage = baseImage;
        for (int i = 0; i < 4; i++) {
            newImage = filter(newImage, mask1);
            newImage = filter(newImage, mask2);
            mask1 = rotateArray(mask1);
            mask2 = rotateArray(mask2);
        }

        return newImage;
    }

    private BufferedImage filter(BufferedImage baseImage, int[][] mask) {
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        final int maskSize = mask.length;
        final int maskSideLength = (maskSize - 1) / 2;
        final int maskItems = (int) Math.pow(maskSize, 2);

        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                int result = maskOperation(x, y, maskSideLength, baseImage, mask);
                newImage.setRGB(x, y, new Color(result, result, result).getRGB());
            }
        }
        return newImage;
    }

    private int maskOperation(int x, int y, int maskSideLength, BufferedImage baseImage, int[][] mask) {
        int background = 255;
        int shape = 0;
        for (int my = -maskSideLength; my <= maskSideLength; my++) {
            if (my + y < 0 || my + y >= baseImage.getHeight()) {
                return new Color(baseImage.getRGB(x, y)).getRed();
            }
            for (int mx = -maskSideLength; mx <= maskSideLength; mx++) {
                if (mx + x < 0 || mx + x >= baseImage.getWidth()) {
                    return new Color(baseImage.getRGB(x, y)).getRed();
                }
                if (mx == 0 && my == 0) {
                    continue;
                }
                if (mask[my + maskSideLength][mx + maskSideLength] == -1) {
                    continue;
                }
                int maskValue = (mask[my + maskSideLength][mx + maskSideLength] == 1) ? 0 : 255;
                if (new Color(baseImage.getRGB(x + mx, y + my)).getRed() != maskValue) {
                    return new Color(baseImage.getRGB(x, y)).getRed();
                }
            }
        }
        return background;
    }

//    private int maskOperation(int x, int y, int maskSideLength, BufferedImage baseImage, int[][] mask) {
//        int background = 255;
//        int shape = 0;
//        for (int my = -maskSideLength; my <= maskSideLength; my++) {
//            if (my + y < 0 || my + y >= baseImage.getHeight()) {
//                return background;
//            }
//            for (int mx = -maskSideLength; mx <= maskSideLength; mx++) {
//                if (mx + x < 0 || mx + x >= baseImage.getWidth()) {
//                    return background;
//                }
//                if (mx == 0 && my == 0) {
//                    continue;
//                }
//                if (mask[mx + maskSideLength][my + maskSideLength] == -1) {
//                    continue;
//                }
//                int maskValue = (mask[mx + maskSideLength][my + maskSideLength] == 1) ? 0 : 255;
//                if (new Color(baseImage.getRGB(x + mx, y + my)).getRed() != maskValue) {
//                    return background;
//                }
//            }
//        }
//        return new Color(baseImage.getRGB(x, y)).getRed();
//    }
    private int[][] rotateArray(int[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = mat[r][c];
            }
        }
        return ret;
    }

}
