package grafika.gimp.filtry.binarne;

import java.awt.image.BufferedImage;
import javax.swing.JSlider;

public class ManualSelection implements BinarizationType{

    private JSlider slider;
    
    public ManualSelection(JSlider slider) {
        this.slider = slider;
    }
    
    @Override
    public int filterImage(BufferedImage baseImage) {
        return slider.getValue();
    }
}
