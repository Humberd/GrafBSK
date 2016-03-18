package grafika.paint.colorPicker;

import java.awt.Color;
import java.util.Arrays;

public class ColorConverter {

    private RgbChoosingPanel rgbPanel;
    private HsvChoosingPanel hsvPanel;
    private CmykChoosingPanel cmykPanel;

    public ColorConverter() {

    }

    public void rgbChange() {
//        rgbToHsv();
//        rgbToCmyk();
    }

    public void hsvChange() {
//        hsvToRgb();
//        hsvToCmyk();
    }

    public void cmykChange() {
//        cmykToRgb();
//        cmykToHsv();
    }

    public void rgbToHsv() {
//        int red = rgbPanel.getRed();
//        int green = rgbPanel.getGreen();
//        int blue = rgbPanel.getBlue();
//        System.out.println(red + " " + green + " " + blue);
//        float[] hsv = Color.RGBtoHSB(red, red, blue, null);
//        System.out.println(Arrays.toString(hsv));
//        hsvPanel.setHue((int) (hsv[0] * 360));
//        hsvPanel.setSaturation((int) (hsv[1] * 255));
//        hsvPanel.setValue((int) (hsv[2] * 255));
    }
    Thread rgbToCmykThread;
    public void rgbToCmyk() {
        if (rgbPanel == null || cmykPanel == null) {
            return;
        }

        float red = (float) rgbPanel.getRed() / rgbPanel.getMaxRed();
        float green = (float) rgbPanel.getGreen() / rgbPanel.getMaxGreen();
        float blue = (float) rgbPanel.getBlue() / rgbPanel.getMaxBlue();
        float black = min(1 - red, 1 - green, 1 - blue);
        float cyan = (1 - red - black) / (1 - black);
        float magneta = (1 - green - black) / (1 - black);
        float yellow = (1 - blue - black) / (1 - black);
        if (black == 1) {
            cyan = 0;
            magneta = 0;
            yellow = 0;
            black = cmykPanel.getMaxBlack();
        } else {
            cyan *= cmykPanel.getMaxCyan();
            magneta *= cmykPanel.getMaxMagneta();
            yellow *= cmykPanel.getMaxYellow();
            black *= cmykPanel.getMaxBlack();
        }
        cmykPanel.setCyanConverter((int) cyan, false);
        cmykPanel.setMagnetaConverter((int) magneta, false);
        cmykPanel.setYellowConverter((int) yellow, false);
        cmykPanel.setBlackConverter((int) black, false);
    }

    public void hsvToRgb() {

    }

    public void hsvToCmyk() {

    }

    public void cmykToRgb() {
        float cyan = (float) cmykPanel.getCyan() / cmykPanel.getMaxCyan();
        float magneta = (float) cmykPanel.getMagneta() / cmykPanel.getMaxMagneta();
        float yellow = (float) cmykPanel.getYellow() / cmykPanel.getMaxYellow();
        float black = (float) cmykPanel.getBlack() / cmykPanel.getMaxBlack();
        
        float red = 1 - min(1, cyan * (1 - black) + black);
        float green = 1 - min(1, magneta * (1 - black) + black);
        float blue = 1 - min(1, yellow * (1 - black) + black);
        
        red *= rgbPanel.getMaxRed();
        green *= rgbPanel.getMaxGreen();
        blue *= rgbPanel.getMaxBlue();
        rgbPanel.setRedConverter((int) red, false);
        rgbPanel.setGreenConverter((int) green, false);
        rgbPanel.setBlueConverter((int) blue, false);
    }

    public void cmykToHsv() {

    }

    private float min(float a, float b, float c) {
        return min(min(a, b), c);
    }

    private float min(float a, float b) {
        return a < b ? a : b;
    }

    public RgbChoosingPanel getRgbPanel() {
        return rgbPanel;
    }

    public void setRgbPanel(RgbChoosingPanel rgbPanel) {
        this.rgbPanel = rgbPanel;
    }

    public HsvChoosingPanel getHsvPanel() {
        return hsvPanel;
    }

    public void setHsvPanel(HsvChoosingPanel hsvPanel) {
        this.hsvPanel = hsvPanel;
    }

    public CmykChoosingPanel getCmykPanel() {
        return cmykPanel;
    }

    public void setCmykPanel(CmykChoosingPanel cmykPanel) {
        this.cmykPanel = cmykPanel;
    }
}
