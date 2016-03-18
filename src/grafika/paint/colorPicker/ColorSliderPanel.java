package grafika.paint.colorPicker;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

public abstract class ColorSliderPanel extends JPanel {

    private LinkedHashMap<JSpinner, JSlider> slidersMap = new LinkedHashMap<>();
    private BindingGroup bindingGroup = new BindingGroup();

    public ColorSliderPanel() {
        super();
    }

    public void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 0;
        int i = 0;
        if (slidersMap != null) {
            for (Entry<JSpinner, JSlider> entry : slidersMap.entrySet()) {
                c.gridx = 0;
                c.gridy = i;
                c.ipadx = 5;
                add(new JLabel(entry.getValue().getName()), c);
                c.gridx = 1;
                c.gridy = i;
                add(entry.getValue(), c);
                c.gridx = 2;
                c.gridy = i++;
                add(entry.getKey(), c);
                Binding sliderSpinnerBinding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, entry.getKey(), ELProperty.create("${value}"), entry.getValue(), BeanProperty.create("value"));

                bindingGroup.addBinding(sliderSpinnerBinding);
            }
        }
        bindingGroup.bind();
    }

    public LinkedHashMap<JSpinner, JSlider> getSlidersMap() {
        return slidersMap;
    }

    public void setSlidersMap(LinkedHashMap<JSpinner, JSlider> slidersMap) {
        this.slidersMap = slidersMap;
    }

    public BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    public void setBindingGroup(BindingGroup bindingGroup) {
        this.bindingGroup = bindingGroup;
    }

}
