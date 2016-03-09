package grafika.zad1.colorPicker;

import java.util.LinkedHashMap;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;

public class RgbSliderPanel extends ColorSliderPanel {

    private RgbChoosingPanel colorPanel;

    public RgbSliderPanel(RgbChoosingPanel panel) {
        super();
        this.colorPanel = panel;
        createMap();
    }

    private void createMap() {
        LinkedHashMap<JSpinner, JSlider> map = new LinkedHashMap<>();
        JSpinner redSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultRed(), 0, colorPanel.getMaxRed(), 1));
        JSlider redSlider = new JSlider(0, colorPanel.getMaxRed(), colorPanel.getDefaultRed());
        redSlider.setName("R");
        map.put(redSpinner, redSlider);
        JSpinner greenSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultGreen(), 0, colorPanel.getMaxGreen(), 1));
        JSlider greenSlider = new JSlider(0, colorPanel.getMaxGreen(), colorPanel.getDefaultGreen());
        greenSlider.setName("G");
        map.put(greenSpinner, greenSlider);
        JSpinner blueSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultBlue(), 0, colorPanel.getMaxBlue(), 1));
        JSlider blueSlider = new JSlider(0, colorPanel.getMaxBlue(), colorPanel.getDefaultBlue());
        blueSlider.setName("B");
        map.put(blueSpinner, blueSlider);

//        String[] fieldNames = {"red", "green", "blue"};
//        for (int i = 0; i < map.size(); i++) {
//            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, map.get(i), BeanProperty.create("value"), colorPanel, BeanProperty.create(fieldNames[i]));
//            getBindingGroup().addBinding(binding);
//        }
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, redSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("red"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, greenSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("green"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, blueSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("blue"));
        getBindingGroup().addBinding(binding);
        
        super.setSlidersMap(map);
        super.addComponents();

    }

}
