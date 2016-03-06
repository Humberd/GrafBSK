package grafika.PPM;

import grafika.exceptions.FileException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Scanner;

public class P3 extends PPMType {

    public P3() {
        super();
        super.setState(P3ColumnReader.getInstance());
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

class P3ColumnReader implements PPMState {

    private static P3ColumnReader instance = new P3ColumnReader();

    private P3ColumnReader() {

    }

    public static P3ColumnReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int cols = scanner.nextInt();
            if (cols > 0) {
                source.setColumns(cols);
                source.setState(P3RowReader.getInstance());
                if (scanner.hasNextInt()) {
                    line = line.replaceFirst(cols + "", "");
                    source.readLine(line);
                }
            } else {
                throw new FileException("Columns number value is incorrect");
            }
        } else {
            throw new FileException("Cannot read columns number value");
        }
    }
}

class P3RowReader implements PPMState {

    private static P3RowReader instance = new P3RowReader();

    private P3RowReader() {

    }

    public static P3RowReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);

        if (scanner.hasNextInt()) {
            int rows = scanner.nextInt();
            if (rows > 0) {
                source.setRows(rows);
                source.setMinimumRGBsRead(source.getColumns() * source.getRows());
                source.initializeRGBarrays(source.getColumns(), source.getRows());
                source.setState(P3MaxColorValueReader.getInstance());
                if (scanner.hasNextInt()) {
                    line = line.replaceFirst(rows + "", "");
                    source.readLine(line);
                }
            } else {
                throw new FileException("Rows number value is incorrect");
            }
        } else {
            throw new FileException("Cannot read rows number value");
        }
    }
}

class P3MaxColorValueReader implements PPMState {

    private static P3MaxColorValueReader instance = new P3MaxColorValueReader();

    private P3MaxColorValueReader() {

    }

    public static P3MaxColorValueReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            if (value > 0) {
                source.setMaximumColorValue(value);
                source.setState(P3ColorReader.getInstance());
                if (scanner.hasNextInt()) {
                    line = line.replaceFirst(value + "", "");
                    source.readLine(line);
                }
            } else {
                throw new FileException("Maximum color value must be higher than 0");
            }
        } else {
            throw new FileException("Cannot read maximum color value");
        }
    }
}

class P3ColorReader implements PPMState {

    private static P3ColorReader instance = new P3ColorReader();

    private P3ColorReader() {

    }

    public static P3ColorReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        //sprawdzam czy mam juz wszystkie pixele wypelnione
//        if (source.getCurrentColumnPixelRead() == source.getColumns() && source.getCurrentRowPixelRead() == source.getRows()) {
//            return;
//        }
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
                    source.setState(P3ImageIsDone.getInstance());
                    break;
                }
            }
        }
    }
}

class P3ImageIsDone implements PPMState {

    private static P3ImageIsDone instance = new P3ImageIsDone();

    private P3ImageIsDone() {

    }

    public static P3ImageIsDone getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
    }

}