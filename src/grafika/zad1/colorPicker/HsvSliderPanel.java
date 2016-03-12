package grafika.zad1.colorPicker;

import java.util.LinkedHashMap;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;

public class HsvSliderPanel extends ColorSliderPanel{
    
    private HsvChoosingPanel colorPanel;
    
    public HsvSliderPanel(HsvChoosingPanel panel) {
        super();
        this.colorPanel = panel;
        createMap();
    }
    
    private void createMap() {
        LinkedHashMap<JSpinner, JSlider> map = new LinkedHashMap<>();
        JSpinner hueSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultHue(), 0, colorPanel.getMaxHue(), 1));
        JSlider hueSlider = new JSlider(0, colorPanel.getMaxHue(), colorPanel.getDefaultHue());
        hueSlider.setName("H");
        map.put(hueSpinner, hueSlider);
        JSpinner saturationSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultSaturation(), 0, colorPanel.getMaxSaturation(), 1));
        JSlider saturationSlider = new JSlider(0, colorPanel.getMaxSaturation(), colorPanel.getDefaultSaturation());
        saturationSlider.setName("S");
        map.put(saturationSpinner, saturationSlider);
        JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultValue(), 0, colorPanel.getMaxValue(), 1));
        JSlider valueSlider = new JSlider(0, colorPanel.getMaxValue(), colorPanel.getDefaultValue());
        valueSlider.setName("V");
        map.put(valueSpinner, valueSlider);
        
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, hueSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("hue"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, saturationSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("saturation"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, valueSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("value"));
        getBindingGroup().addBinding(binding);
        
        super.setSlidersMap(map);
        super.addComponents();
    }
}
