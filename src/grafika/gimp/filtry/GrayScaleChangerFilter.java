package grafika.gimp.filtry;

import grafika.gimp.ImageWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class GrayScaleChangerFilter extends FilterWindow {

    private JRadioButton average;
    private JRadioButton luminosity;

    private GrayFilterType filter;

    public GrayScaleChangerFilter(ImageWindow imageWindow) {
        super("Gray Scale", imageWindow);
    }

    @Override
    protected void customInit() {
//        setShowPreviewImageCheckBox(false);
        addComponents();
    }

    private void createNewImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        WritableRaster raster = newImage.getRaster();
        
        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                Color prevPixelColor = new Color(baseImage.getRGB(x, y));
                int red = prevPixelColor.getRed();
                int green = prevPixelColor.getGreen();
                int blue = prevPixelColor.getBlue();

                int gray = filter.count(red, green, blue);
                newImage.setRGB(x, y, new Color(gray, gray, gray).getRGB());
//                raster.setPixel(x, y,new int[] {gray});
            }
        }

        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
    }

    private void addComponents() {
        average = new JRadioButton("Average");
        average.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter = new AverageGrayFilter();
                createNewImage();
            }
        });
        luminosity = new JRadioButton("Luminosity");
        luminosity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter = new LuminosityGrayFilter();
                createNewImage();
            }
        });

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;

        ButtonGroup group = new ButtonGroup();
        c.anchor = GridBagConstraints.LINE_START;
        group.add(average);
        group.add(luminosity);
        add(average, c);
        c.insets = new Insets(0, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 1;
        add(luminosity, c);
    }

    @Override
    public void closeMe() {
        getWindow().setGrayScaleChangerWindow(null);
    }
}

interface GrayFilterType {

    public int count(int red, int green, int blue);
}

class AverageGrayFilter implements GrayFilterType {

    @Override
    public int count(int red, int green, int blue) {
        int result = (red + green + blue) / 3;
        return result;
    }

}

class LuminosityGrayFilter implements GrayFilterType {

    @Override
    public int count(int red, int green, int blue) {
        int result = (int) (0.21 * red + 0.72 * green + 0.07 * blue);
        return result;
    }

}
