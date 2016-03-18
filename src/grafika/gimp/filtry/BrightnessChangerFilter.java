package grafika.gimp.filtry;

import grafika.gimp.ImageWindow;
import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;

public class BrightnessChangerFilter extends FilterWindow {

    private JSlider brightnessSlider;
    private JSpinner brightnessSpinner;
    private JLabel brightnessLabel;

    public BrightnessChangerFilter(ImageWindow imageWindow) {
        super("Brightness Changer", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    private void createNewImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        int sliderValue = brightnessSlider.getValue();

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                Color prevPixelColor = new Color(baseImage.getRGB(x, y));
                int red = prevPixelColor.getRed();
                int green = prevPixelColor.getGreen();
                int blue = prevPixelColor.getBlue();
                
                red = count(red, sliderValue);
                green = count(green, sliderValue);
                blue = count(blue, sliderValue);
                
                Color newPixelColor = new Color(red, green, blue);
                newImage.setRGB(x, y, newPixelColor.getRGB());
            }
        }
        
        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
    }
    
    private int count(int currValue, int opValue) {
        int result = currValue + opValue;
        if (result < 0) {
            return 0;
        }
        if (result > 255) {
            return 255;
        }
        return result;
    }

    private void addComponents() {
        brightnessSlider = new JSlider(-255, 255, 0);
        brightnessSlider.setValueIsAdjusting(false);
        brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                createNewImage();
            }
        });
        brightnessSpinner = new JSpinner(new SpinnerNumberModel(0, -255, 255, 1));
        brightnessLabel = new JLabel("Brightness: ");

        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, brightnessSlider, BeanProperty.create("value"), brightnessSpinner, BeanProperty.create("value"));
        binding.bind();

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;

        JPanel inputPanel = new JPanel();
        add(inputPanel, c);
        inputPanel.add(brightnessLabel);
        inputPanel.add(brightnessSlider);
        inputPanel.add(brightnessSpinner);

//        c.gridx = 0;
//        c.gridy = 1;
//        JPanel buttonsPanel = new JPanel();
//        add(buttonsPanel, c);
//        buttonsPanel.add(okButton);
//        buttonsPanel.add(cancelButton);
    }

    @Override
    public void closeMe() {
        getWindow().setBrightnessChangerWindow(null);
    }

}
