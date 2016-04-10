package grafika.gimp.filtry.morfologiczne;

import java.awt.image.BufferedImage;

public class Opening implements MorphologicalType {

    private Erosion erosion;
    private Dilatation dilatation;

    public Opening() {
        erosion = new Erosion();
        dilatation = new Dilatation();
    }

    @Override
    public BufferedImage filterImage(BufferedImage baseImage) {
        return dilatation.filterImage(erosion.filterImage(baseImage));
    }

}
