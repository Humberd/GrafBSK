package grafika.zad1.colorPicker;

public class CmykChoosingPanel extends ColorChoosingPanel{
    
    public CmykChoosingPanel() {
        super(100, 100, 100, 100);
    }
    
    public int getCyan() {
        return getCurrentColor1Value();
    }
    
    public void setCyan(int cyan) {
        setCurrentColor1Value(cyan);
    }
    
    public int getMagneta() {
        return getCurrentColor2Value();
    }
    
    public void setMagneta(int magneta) {
        setCurrentColor2Value(magneta);
    }
    
    public int getYellow() {
        return getCurrentColor3Value();
    }
    
    public void setYellow(int yellow) {
        setCurrentColor3Value(yellow);
    }
    
    public int getBlack() {
        return getCurrentColor4Value();
    }
    
    public void setBlack(int black) {
        setCurrentColor4Value(black);
    }
    
    public int getMaxCyan() {
        return getMaxColor1Value();
    }
    
    public int getMaxMagneta() {
        return getMaxColor2Value();
    }
    
    public int getMaxYellow() {
        return getMaxColor3Value();
    }
    
    public int getMaxBlack() {
        return getMaxColor4Value();
    }
    
    public int getDefaultCyan() {
        return getDefaultColor1Value();
    }
    
    public int getDefaultMagneta() {
        return getDefaultColor2Value();
    }
    
    public int getDefaultYellow() {
        return getDefaultColor3Value();
    }
    
    public int getDefaultBlack() {
        return getDefaultColor4Value();
    }
    
}
