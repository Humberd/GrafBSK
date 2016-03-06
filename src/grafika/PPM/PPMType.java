package grafika.PPM;

import grafika.exceptions.FileException;

public abstract class PPMType {

    private PPMState state;
    private int minimumRGBsRead;
    private int columns;
    private int rows;
    private int[][] redPixels;
    private boolean redPixelsRead = false;
    private int[][] greenPixels;
    private boolean greenPixelsRead = false;
    private int[][] bluePixels;
    private boolean bluePixelsRead = false;
    private int currentColumnPixelRead = 0;
    private int currentRowPixelRead = 0;
    private int maximumColorValue = 0;

    public abstract void readLine(String line) throws FileException;

    public abstract void saveLine(String line);

    public PPMState getState() {
        return state;
    }

    public void setState(PPMState state) {
        this.state = state;
    }

    public int getMinimumRGBsRead() {
        return minimumRGBsRead;
    }

    public void setMinimumRGBsRead(int minimumRGBsRead) {
        this.minimumRGBsRead = minimumRGBsRead;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int[][] getRedPixels() {
        return redPixels;
    }

    public void setRedPixels(int[][] redPixels) {
        this.redPixels = redPixels;
    }

    public void addRedPixel(int pixel) {
        this.redPixels[currentRowPixelRead][currentColumnPixelRead] = pixel;
    }

    public int[][] getGreenPixels() {
        return greenPixels;
    }

    public void setGreenPixels(int[][] greenPixels) {
        this.greenPixels = greenPixels;
    }
    
    public void addGreenPixel(int pixel) {
        this.greenPixels[currentRowPixelRead][currentColumnPixelRead] = pixel;
    }

    public int[][] getBluePixels() {
        return bluePixels;
    }

    public void setBluePixels(int[][] bluePixels) {
        this.bluePixels = bluePixels;
    }
    
    public void addBluePixel(int pixel) {
        this.bluePixels[currentRowPixelRead][currentColumnPixelRead] = pixel;
    }

    public void initializeRGBarrays(int cols, int rows) {
        setGreenPixels(new int[rows][cols]);
        setRedPixels(new int[rows][cols]);
        setBluePixels(new int[rows][cols]);
    }

    public int getMaximumColorValue() {
        return maximumColorValue;
    }

    public void setMaximumColorValue(int maximumColorValue) {
        this.maximumColorValue = maximumColorValue;
    }

    public boolean isRedPixelsRead() {
        return redPixelsRead;
    }

    public void setRedPixelsRead(boolean redPixelsRead) {
        this.redPixelsRead = redPixelsRead;
    }

    public boolean isGreenPixelsRead() {
        return greenPixelsRead;
    }

    public void setGreenPixelsRead(boolean greenPixelsRead) {
        this.greenPixelsRead = greenPixelsRead;
    }

    public boolean isBluePixelsRead() {
        return bluePixelsRead;
    }

    public void setBluePixelsRead(boolean bluePixelsRead) {
        this.bluePixelsRead = bluePixelsRead;
    }

    public int getCurrentColumnPixelRead() {
        return currentColumnPixelRead;
    }

    public void setCurrentColumnPixelRead(int currentColumnPixelRead) {
        this.currentColumnPixelRead = currentColumnPixelRead;
    }

    public int getCurrentRowPixelRead() {
        return currentRowPixelRead;
    }

    public void setCurrentRowPixelRead(int currentRowPixelRead) {
        this.currentRowPixelRead = currentRowPixelRead;
    }
}
