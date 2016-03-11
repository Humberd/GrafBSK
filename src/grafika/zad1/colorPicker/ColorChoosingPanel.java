package grafika.zad1.colorPicker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JPanel;

public abstract class ColorChoosingPanel extends JPanel {

    private PropertyChangeSupport pcs;

    private int maxColor1Value;
    private int maxColor2Value;
    private int maxColor3Value;
    private int maxColor4Value;

    private int defaultColor1Value = 0;
    private int defaultColor2Value = 0;
    private int defaultColor3Value = 0;
    private int defaultColor4Value = 0;

    private int currentColor1Value = defaultColor1Value;
    private int currentColor2Value = defaultColor2Value;
    private int currentColor3Value = defaultColor3Value;
    private int currentColor4Value = defaultColor4Value;

    public ColorChoosingPanel(int color1, int color2, int color3) {
        this(color1, color2, color3, 0);

    }

    public ColorChoosingPanel(int color1, int color2, int color3, int color4) {
        super();
        maxColor1Value = color1;
        maxColor2Value = color2;
        maxColor3Value = color3;
        maxColor4Value = color4;

    }

    protected int getMaxColor1Value() {
        return maxColor1Value;
    }

    protected void setMaxColor1Value(int maxColor1Value) {
        this.maxColor1Value = maxColor1Value;
    }

    protected int getMaxColor2Value() {
        return maxColor2Value;
    }

    protected void setMaxColor2Value(int maxColor2Value) {
        this.maxColor2Value = maxColor2Value;
    }

    protected int getMaxColor3Value() {
        return maxColor3Value;
    }

    protected void setMaxColor3Value(int maxColor3Value) {
        this.maxColor3Value = maxColor3Value;
    }

    protected int getMaxColor4Value() {
        return maxColor4Value;
    }

    protected void setMaxColor4Value(int maxColor4Value) {
        this.maxColor4Value = maxColor4Value;
    }

    protected int getDefaultColor1Value() {
        return defaultColor1Value;
    }

    protected void setDefaultColor1Value(int defaultColor1Value) {
        this.defaultColor1Value = defaultColor1Value;
    }

    protected int getDefaultColor2Value() {
        return defaultColor2Value;
    }

    protected void setDefaultColor2Value(int defaultColor2Value) {
        this.defaultColor2Value = defaultColor2Value;
    }

    protected int getDefaultColor3Value() {
        return defaultColor3Value;
    }

    protected void setDefaultColor3Value(int defaultColor3Value) {
        this.defaultColor3Value = defaultColor3Value;
    }

    protected int getDefaultColor4Value() {
        return defaultColor4Value;
    }

    protected void setDefaultColor4Value(int defaultColor4Value) {
        this.defaultColor4Value = defaultColor4Value;
    }

    protected int getCurrentColor1Value() {
        return currentColor1Value;
    }

    protected void setCurrentColor1Value(int currentColor1Value) {
        this.currentColor1Value = currentColor1Value;
    }

    protected int getCurrentColor2Value() {
        return currentColor2Value;
    }

    protected void setCurrentColor2Value(int currentColor2Value) {
        this.currentColor2Value = currentColor2Value;
    }

    protected int getCurrentColor3Value() {
        return currentColor3Value;
    }

    protected void setCurrentColor3Value(int currentColor3Value) {
        this.currentColor3Value = currentColor3Value;
    }

    protected int getCurrentColor4Value() {
        return currentColor4Value;
    }

    protected void setCurrentColor4Value(int currentColor4Value) {
        this.currentColor4Value = currentColor4Value;
    }

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public void setPcs(PropertyChangeSupport pcs) {
        if (this.pcs == null) {
            this.pcs = pcs;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (pcs != null) {
            this.pcs.addPropertyChangeListener(listener);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (pcs != null) {
            this.pcs.removePropertyChangeListener(listener);
        }
    }
}
