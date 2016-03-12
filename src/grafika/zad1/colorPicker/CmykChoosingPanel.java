package grafika.zad1.colorPicker;

import java.beans.PropertyChangeSupport;

public class CmykChoosingPanel extends ColorChoosingPanel {

    public CmykChoosingPanel() {
        super(100, 100, 100, 100);
        super.setPcs(new PropertyChangeSupport(this));
    }

    public int getCyan() {
        return getCurrentColor1Value();
    }

    public void setCyan(int cyan) {
        setCyanConverter(cyan, true);
    }
    
    public void setCyanConverter(int cyan, boolean convert) {
        if (convert) {
            getConverter().cmykToRgb();
        }
        getPcs().firePropertyChange("cyan", cyan - 1, cyan);
        setCurrentColor1Value(cyan);
    }

    public int getMagneta() {
        return getCurrentColor2Value();
    }

    public void setMagneta(int magneta) {
        setMagnetaConverter(magneta, true);
    }
    
    public void setMagnetaConverter(int magneta, boolean convert) {
        if (convert) {
            getConverter().cmykToRgb();
        }
        getPcs().firePropertyChange("magneta", magneta - 1, magneta);
        setCurrentColor2Value(magneta);
    }

    public int getYellow() {
        return getCurrentColor3Value();
    }

    public void setYellow(int yellow) {
        setYellowConverter(yellow, true);
    }
    
    public void setYellowConverter(int yellow, boolean convert) {
        if (convert) {
            getConverter().cmykToRgb();
        }
        getPcs().firePropertyChange("yellow", yellow - 1, yellow);
        setCurrentColor3Value(yellow);
    }

    public int getBlack() {
        return getCurrentColor4Value();
    }

    public void setBlack(int black) {
        setBlackConverter(black, true);
    }
    
    public void setBlackConverter(int black, boolean convert) {
        if (convert) {
            getConverter().cmykToRgb();
        }
        getPcs().firePropertyChange("black", black -1, black);
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

    private void fireConversionPropertyChange() {
        float cyan = (float) getCyan() / 100;
        float magneta = (float) getMagneta() / 100;
        float yellow = (float) getYellow() / 100;
        float black = (float) getBlack() / 100;
        float red = 1 - min(1, cyan * (1 - black) + black);
        float green = 1 - min(1, magneta * (1 - black) + black);
        float blue = 1 - min(1, yellow * (1 - black) + black);
        System.out.println("[" + red + ", " + green + ", " + blue + "]");
        red *= 255;
        green *= 255;
        blue *= 255;
//        getPcs().firePropertyChange("red", red - 1, red);
//        getPcs().firePropertyChange("green", green - 1, green);
//        getPcs().firePropertyChange("blue", blue - 1, blue);
    }

    private float min(float a, float b, float c) {
        return min(min(a, b), c);
    }

    private float min(float a, float b) {
        return a < b ? a : b;
    }
}
