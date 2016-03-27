package grafika.gimp.filtry.binarne;

import java.awt.image.BufferedImage;

public class MeanIterativeSelection implements BinarizationType{

    @Override
    public int filterImage(BufferedImage baseImage) {
        return 125;
    }
}
