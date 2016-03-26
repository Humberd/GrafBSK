package grafika.file.extensions.PNG;

import grafika.exceptions.FileException;
import grafika.gimp.FileType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PNGextension extends FileType{

    public PNGextension(String filePath) {
        super("png", filePath);
    }

    @Override
    public void openFile() throws FileException {
        try {
            setImage(ImageIO.read(new File(getFilePath())));
        } catch (IOException ex) {
            throw new FileException("Cannot open file '" + getFilePath() + "'");
        }
    }

    @Override
    public void closeFile() throws FileException {

    }

    @Override
    public void saveFile(BufferedImage image, String path) throws FileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
