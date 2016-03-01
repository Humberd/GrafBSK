package grafika.PPM;

public abstract class PPMType {

    private PPMState state;
    private int linesRead = 0;
    private int RGBsRead = 0;
    private int minimumRGBsRead;

    public abstract void readLine(String line);

    public abstract void saveLine();

    public PPMState getState() {
        return state;
    }

    public void setState(PPMState state) {
        this.state = state;
    }

    public int getLinesRead() {
        return linesRead;
    }

    public void setLinesRead(int linesRead) {
        this.linesRead = linesRead;
    }

    public void incrementLinesRead() {
        this.linesRead++;
    }

    public int getRGBsRead() {
        return RGBsRead;
    }

    public void setRGBsRead(int RGBsRead) {
        this.RGBsRead = RGBsRead;
    }
    
    public void incrementRGBsRead() {
        this.RGBsRead++;
    }

    public int getMinimumRGBsRead() {
        return minimumRGBsRead;
    }

    public void setMinimumRGBsRead(int minimumRGBsRead) {
        this.minimumRGBsRead = minimumRGBsRead;
    }
}
