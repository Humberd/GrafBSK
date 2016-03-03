package grafika.PPM;

import grafika.exceptions.FileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
            //zczytanie jaką metoda bedzie sie dekodowac
            for (String currentLine; (currentLine = reader.readLine()) != null;) {
                currentLine = currentLine.trim();
                if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                    continue;
                } else if (currentLine.equals("P3")) {
                    ppmType = new P3();
                    break;
                } else if (currentLine.equals("P6")) {
                    ppmType = new P6();
                    break;
                }
                throw new FileException("A file does not start with 'P6' nor 'P3'");
            }

            for (String currentLine; (currentLine = reader.readLine()) != null;) {
                currentLine = currentLine.trim();
                if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                    continue;
                } else {
                    ppmType.readLine(currentLine);
                }
            }

            if (ppmType.getColumns() == ppmType.getCurrentColumnPixelRead() && ppmType.getRows() == ppmType.getCurrentRowPixelRead()) {
                //jesli plik jest poprawny
                for (int i = 0; i < ppmType.getRows(); i++) {
                    for (int j = 0; j < ppmType.getColumns(); j++) {
                        System.out.print("[" + ppmType.getRedPixels()[i][j] + "," + ppmType.getGreenPixels()[i][j] + "," + ppmType.getBluePixels()[i][j] + "]");
                    }
                    System.out.println();
                }
                BufferedImage image = new BufferedImage(ppmType.getColumns(), ppmType.getRows(), BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < ppmType.getRows(); i++) {
                    for (int j = 0; j < ppmType.getColumns(); j++) {
                        int red = ppmType.getRedPixels()[i][j];
                        int green = ppmType.getGreenPixels()[i][j];
                        int blue = ppmType.getBluePixels()[i][j];
                        image.setRGB(j, i, new Color(red, green, blue).getRGB());
                    }
                }
                setImage(image);
            } else {
                throw new FileException("Not enough color pixels");
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
