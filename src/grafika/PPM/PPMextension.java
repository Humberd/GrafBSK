package grafika.PPM;

import grafika.exceptions.FileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
//            byte[] array = Files.readAllBytes(new File(getFilePath()).toPath());
//            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(array)));
//            reader = new BufferedReader(new FileReader(getFilePath()));
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFilePath()),Charset.forName("ISO-8859-1")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PPMextension.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //zczytanie jaką metoda bedzie sie dekodowac
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                incrementLinesRead();
                currentLine = currentLine.trim();
                if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                    continue;
                } else if (currentLine.equals("P3")) {
                    ppmType = new P3();
                    readP3();
                    break;
                } else if (currentLine.equals("P6")) {
                    ppmType = new P6();
                    readP6();
                    break;
                }
                throw new FileException("A file does not start neither with 'P6' nor 'P3'");
            }

            //jesli plik jest poprawny
            if (ppmType.getColumns() == ppmType.getCurrentColumnPixelRead() && ppmType.getRows() == ppmType.getCurrentRowPixelRead()) {
//                for (int i = 0; i < ppmType.getRows(); i++) {
//                    for (int j = 0; j < ppmType.getColumns(); j++) {
//                        System.out.print("[" + ppmType.getRedPixels()[i][j] + "," + ppmType.getGreenPixels()[i][j] + "," + ppmType.getBluePixels()[i][j] + "]");
//                    }
//                    System.out.println();
//                }
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

    private void readP3() throws IOException, FileException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            incrementLinesRead();
            currentLine = currentLine.trim();
            if (currentLine.length() == 0 || currentLine.charAt(0) == '#') {
                continue;
            } else {
                ppmType.readLine(currentLine);
            }
        }
    }

    private void readP6() throws IOException, FileException {
        String currentLine;
        while (ppmType.getMaximumColorValue() == 0) {
            currentLine = reader.readLine();
            incrementLinesRead();
            currentLine = currentLine.trim();
            if (currentLine.length() != 0 && currentLine.charAt(0) != '#') {
                ppmType.readLine(currentLine);
            }
        }
        char[] foo = new char[5000];
        while ((reader.read(foo)) != -1) {
            ppmType.readLine(new String(foo));
//            for (char f : foo) {
//                System.out.println((int)f);
//            }
        }
    }
}
