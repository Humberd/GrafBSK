package grafika.paint.colorPicker;

import java.util.LinkedHashMap;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;

public class CmykSliderPanel extends ColorSliderPanel{
    
    private CmykChoosingPanel colorPanel;

    public CmykSliderPanel(CmykChoosingPanel panel) {
        super();
        this.colorPanel = panel;
        createMap();
    }
    
    private void createMap() {
        LinkedHashMap<JSpinner, JSlider> map = new LinkedHashMap<>();
        JSpinner cyanSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultCyan(), 0, colorPanel.getMaxCyan(), 1));
        JSlider cyanSlider = new JSlider(0, colorPanel.getMaxCyan(), colorPanel.getDefaultCyan());
        cyanSlider.setName("C");
        map.put(cyanSpinner, cyanSlider);
        JSpinner magnetaSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultMagneta(), 0, colorPanel.getMaxMagneta(), 1));
        JSlider magnetaSlider = new JSlider(0, colorPanel.getMaxMagneta(), colorPanel.getDefaultMagneta());
        magnetaSlider.setName("M");
        map.put(magnetaSpinner, magnetaSlider);
        JSpinner yellowSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultYellow(), 0, colorPanel.getMaxYellow(), 1));
        JSlider yellowSlider = new JSlider(0, colorPanel.getMaxYellow(), colorPanel.getDefaultYellow());
        yellowSlider.setName("Y");
        map.put(yellowSpinner, yellowSlider);
        JSpinner blackSpinner = new JSpinner(new SpinnerNumberModel(colorPanel.getDefaultBlack(), 0, colorPanel.getMaxBlack(), 1));
        JSlider blackSlider = new JSlider(0, colorPanel.getMaxBlack(), colorPanel.getDefaultBlack());
        blackSlider.setName("K");
        map.put(blackSpinner, blackSlider);
        
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, cyanSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("cyan"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, magnetaSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("magneta"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, yellowSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("yellow"));
        getBindingGroup().addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, blackSlider, BeanProperty.create("value"), colorPanel, BeanProperty.create("black"));
        getBindingGroup().addBinding(binding);
        
        super.setSlidersMap(map);
        super.addComponents();
    }
}
