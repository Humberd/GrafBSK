package grafika.zad1.colorPicker;

public class HsvChoosingPanel extends ColorChoosingPanel{

    public HsvChoosingPanel() {
        super(360, 255, 255);
    }
    
    public int getHue() {
        return getCurrentColor1Value();
    }
    
    public void setHue(int hue) {
        setCurrentColor1Value(hue);
    }
    
    public int getSaturation() {
        return getCurrentColor2Value();
    }
    
    public void setSaturation(int saturation) {
        setCurrentColor2Value(saturation);
    }
    
    public int getValue() {
        return getCurrentColor3Value();
    }
    
    public void setValue(int value) {
        setCurrentColor3Value(value);
    }
    
    public int getMaxHue() {
        return getMaxColor1Value();
    }
    
    public int getMaxSaturation() {
        return getMaxColor2Value();
    }
    
    public int getMaxValue() {
        return getMaxColor3Value();
    }
    
    public int getDefaultHue() {
        return getDefaultColor1Value();
    }
    
    public int getDefaultSaturation() {
        return getDefaultColor2Value();
    }
    
    public int getDefaultValue() {
        return getDefaultColor3Value();
    }
}
