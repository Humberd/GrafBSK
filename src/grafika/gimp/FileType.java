package grafika.gimp;

import grafika.exceptions.FileException;
import java.awt.image.BufferedImage;

public abstract class FileType {

    private static String fileExtension;
    private String filePath;
    private BufferedImage image;
    private int linesRead;

    public FileType(String fileExtension, String filePath) {
        linesRead = 0;
        FileType.fileExtension = fileExtension;
        this.filePath = filePath;
    }

    public abstract void openFile() throws FileException;

    public abstract void closeFile() throws FileException;
    
    public abstract void saveFile(BufferedImage image, String path) throws FileException;

    public static String getFileExtension() {
        return fileExtension;
    }

    public String getFilePath() {
        return filePath;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getLinesRead() {
        return linesRead;
    }

    public void setLinesRead(int linesRead) {
        this.linesRead = linesRead;
    }
    
    public int incrementLinesRead() {
        return this.linesRead++;
    }

}
