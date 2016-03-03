package grafika.PPM;

import grafika.exceptions.FileException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Scanner;

public class P3 extends PPMType {

    public P3() {
        super();
        super.setState(P3NotStarted.getInstance());
    }

    @Override
    public void readLine(String line) throws FileException {
        getState().interpret(line, this);
    }

    @Override
    public void saveLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class P3NotStarted implements PPMState {

    private static P3NotStarted instance = new P3NotStarted();

    private P3NotStarted() {

    }

    public static P3NotStarted getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
//        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(line.getBytes()));
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int cols = scanner.nextInt();
            if (cols > 0) {
                source.setColumns(cols);
                if (scanner.hasNextInt()) {
                    int rows = scanner.nextInt();
                    if (rows > 0) {
                        source.setRows(rows);
                    } else {
                        throw new FileException("Rows number value is incorrect");
                    }
                } else {
                    throw new FileException("Cannot read rows number value");
                }
            } else {
                throw new FileException("Columns number value is incorrect");
            }
        } else {
            throw new FileException("Cannot read columns number value");
        }
        source.setMinimumRGBsRead(source.getColumns() * source.getRows());
        source.initializeRGBarrays(source.getColumns(), source.getRows());
        source.setState(P3ColRowDone.getInstance());
    }

}

class P3ColRowDone implements PPMState {

    private static P3ColRowDone instance = new P3ColRowDone();

    private P3ColRowDone() {

    }

    public static P3ColRowDone getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            source.setMaximumColorValue(value);
        } else {
            throw new FileException("Cannot read maximum color value number");
        }
        source.setState(P3MaxColorValueDone.getInstance());
    }

}

class P3MaxColorValueDone implements PPMState {

    private static P3MaxColorValueDone instance = new P3MaxColorValueDone();

    private P3MaxColorValueDone() {

    }

    public static P3MaxColorValueDone getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        //sprawdzam czy mam juz wszystkie pixele wypelnione
        if (source.getCurrentColumnPixelRead() == source.getColumns() && source.getCurrentRowPixelRead() == source.getRows()) {
            return;
        }
        while (scanner.hasNextInt()) {
            int colorValue = scanner.nextInt();
            if (colorValue > source.getMaximumColorValue()) {
                throw new FileException("Color value: '" + colorValue + "' exceeds declared maximum color value: '" + source.getMaximumColorValue() + "'");
            } else if (colorValue < 0) {
                throw new FileException("Color value: '" + colorValue + "' is lower than 0");
            }
            colorValue = (int) ((float) colorValue / (float) source.getMaximumColorValue() * 255);

            if (source.isRedPixelsRead() == false) {
                source.addRedPixel(colorValue);
//                System.out.println("Red: " + colorValue);
                source.setRedPixelsRead(true);

            } else if (source.isGreenPixelsRead() == false) {
                source.addGreenPixel(colorValue);
//                System.out.println("Green: " + colorValue);
                source.setGreenPixelsRead(true);

            } else if (source.isBluePixelsRead() == false) {
                source.addBluePixel(colorValue);
//                System.out.println("Blue: " + colorValue);
                source.setRedPixelsRead(false);
                source.setGreenPixelsRead(false);
                //przestawiam i sprawdzam numery kolumn i wierszy pixeli do wstawienia
                if (source.getCurrentColumnPixelRead() < source.getColumns() - 1) {
                    //jesli konczy sie kolumna (od lewej do prawej)
                    source.setCurrentColumnPixelRead(source.getCurrentColumnPixelRead() + 1);

                } else if (source.getCurrentRowPixelRead() < source.getRows() - 1) {
                    //jesli konczy sie wiersz (od gory do dolu)
                    source.setCurrentColumnPixelRead(0);
                    source.setCurrentRowPixelRead(source.getCurrentRowPixelRead() + 1);

                } else {
                    //maksymalny rozmiar tablicy przekroczony
                    source.setCurrentRowPixelRead(source.getRows());
                    source.setCurrentColumnPixelRead(source.getColumns());
                    break;
                }
            }
        }
    }

}

class P3ContentDone implements PPMState {

    @Override
    public void interpret(String line, PPMType source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
