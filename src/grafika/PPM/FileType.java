package grafika.PPM;

import grafika.exceptions.FileException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class FileType {

    private static String fileExtension;
    private String filePath;
    private BufferedImage image;

    public FileType(String fileExtension, String filePath) {
        FileType.fileExtension = fileExtension;
        this.filePath = filePath;
    }

    public abstract void openFile() throws FileException;

    public abstract void closeFile() throws FileException;

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

}
