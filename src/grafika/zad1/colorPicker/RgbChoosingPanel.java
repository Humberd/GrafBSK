package grafika.zad1.colorPicker;

public class RgbChoosingPanel extends ColorChoosingPanel{

    public RgbChoosingPanel() {
        super(255, 255, 255);
    }
    
    public int getRed() {
//        System.out.println("red");
        return getCurrentColor1Value();
    }
    
    public void setRed(int red) {
//        System.out.println(red);
        setCurrentColor1Value(red);
    }
    
    public int getGreen() {
//        System.out.println("green");
        return getCurrentColor2Value();
    }
    
    public void setGreen(int green) {
//        System.out.println(green);
        setCurrentColor2Value(green);
    }
    
    public int getBlue() {
//        System.out.println("blue");
        return getCurrentColor3Value();
    }
    
    public void setBlue(int blue) {
//        System.out.println(blue);
        setCurrentColor3Value(blue);
    }
    
    public int getMaxRed() {
        return getMaxColor1Value();
    }
    
    public int getMaxGreen() {
        return getMaxColor2Value();
    }
    
    public int getMaxBlue() {
        return getMaxColor3Value();
    }
    
    public int getDefaultRed() {
        return getDefaultColor1Value();
    }
    
    public int getDefaultGreen() {
        return getDefaultColor2Value();
    }
    
    public int getDefaultBlue() {
        return getDefaultColor3Value();
    }
    
}
