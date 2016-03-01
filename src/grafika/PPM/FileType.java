package grafika.PPM;

import grafika.exceptions.FileException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileType {

    private static String fileExtension;
    private String filePath;

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

}
