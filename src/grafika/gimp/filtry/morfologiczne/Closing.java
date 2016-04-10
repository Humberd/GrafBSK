package grafika.gimp.filtry.morfologiczne;

import java.awt.image.BufferedImage;

public class Closing implements MorphologicalType {

    private Erosion erosion;
    private Dilatation dilatation;

    public Closing() {
        erosion = new Erosion();
        dilatation = new Dilatation();
    }

    @Override
    public BufferedImage filterImage(BufferedImage baseImage) {
        return erosion.filterImage(dilatation.filterImage(baseImage));
    }

}
