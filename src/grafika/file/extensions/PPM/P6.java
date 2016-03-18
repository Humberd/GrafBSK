package grafika.file.extensions.PPM;

import grafika.exceptions.FileException;
import java.util.Scanner;

public class P6 extends PPMType {

    public P6() {
        super();
        super.setState(P6ColumnReader.getInstance());
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

class P6ColumnReader implements PPMState {

    private static P6ColumnReader instance = new P6ColumnReader();

    private P6ColumnReader() {

    }

    public static P6ColumnReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int cols = scanner.nextInt();
            if (cols > 0) {
                source.setColumns(cols);
                source.setState(P6RowReader.getInstance());
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

class P6RowReader implements PPMState {

    private static P6RowReader instance = new P6RowReader();

    private P6RowReader() {

    }

    public static P6RowReader getInstance() {
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
                source.setState(P6MaxColorValueReader.getInstance());
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

class P6MaxColorValueReader implements PPMState {

    private static P6MaxColorValueReader instance = new P6MaxColorValueReader();

    private P6MaxColorValueReader() {

    }

    public static P6MaxColorValueReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        Scanner scanner = new Scanner(line);
        if (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            if (value != 255 && value != 65535) {
                throw new FileException("Maximum color value '" + value + "' must be either '255' or '65535'");
            }
            source.setMaximumColorValue(value);
            source.setState(P6ColorReader.getInstance());
//            if (scanner.hasNext()) {
//                line = line.replaceFirst(value + "", "");
//                line = line.replace(" ", "");
//                source.readLine(line);
//            }
        }
    }
}

class P6ColorReader implements PPMState {

    private static P6ColorReader instance = new P6ColorReader();

    private P6ColorReader() {

    }

    public static P6ColorReader getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
        
        for (int charNumber = 0; charNumber < line.length(); charNumber++) {
            int colorValue = 0;
            if (source.getMaximumColorValue() == 255) {
                colorValue = line.charAt(charNumber);
            } else if (source.getMaximumColorValue() == 65535){
                colorValue = ((line.charAt(charNumber)& 0xFF) << 8) | (line.charAt(++charNumber) & 0xFF);
                colorValue = (int) ((float) colorValue / (float) source.getMaximumColorValue() * 255);
            }
            
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
                    source.setState(P6ImageIsDone.getInstance());
                    break;
                }
            }
        }
    }
}

class P6ImageIsDone implements PPMState {

    private static P6ImageIsDone instance = new P6ImageIsDone();

    private P6ImageIsDone() {

    }

    public static P6ImageIsDone getInstance() {
        return instance;
    }

    @Override
    public void interpret(String line, PPMType source) throws FileException {
    }

}
