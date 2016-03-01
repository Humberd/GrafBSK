package grafika.PPM;

import grafika.exceptions.FileException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PPMextension extends FileType {

    private PPMType ppmType;
    private BufferedReader reader;

    public PPMextension(String filePath) {
        super("ppm", filePath);
    }

    @Override
    public void openFile() throws FileException {
        try {
            reader = new BufferedReader(new FileReader(getFilePath()));
        } catch (FileNotFoundException ex) {
            throw new FileException("File \"" + getFilePath() + "\" does not exist.");
        }

        try {
            for (String currentLine; (currentLine = reader.readLine()) != null;) {
                
            }
        } catch (IOException ex) {
            throw new FileException("Exception while reading from the file \"" + getFilePath() + "\"");
        }
    }

    @Override
    public void closeFile() throws FileException {
        try {
            reader.close();
        } catch (IOException ex) {
            throw new FileException("Exception while closing the file \"" + getFilePath() + "\"");
        }
    }
}
