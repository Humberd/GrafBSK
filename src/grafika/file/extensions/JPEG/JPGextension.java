package grafika.file.extensions.JPEG;

import grafika.exceptions.FileException;
import grafika.gimp.FileType;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JPGextension extends FileType {

    public JPGextension(String filePath) {
        super("jpg", filePath);
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
//        try {
//            reader.close();
//        } catch (IOException ex) {
//            throw new FileException("Exception while closing the file \"" + getFilePath() + "\"");
//        }
    }

    @Override
    public void saveFile(BufferedImage image, String path) throws FileException {
//        try {
//            File outputFile = new File(path + "." + getFileExtension());
//            ImageIO.write(image, getFileExtension(), outputFile);
//        } catch (IOException e) {
//            throw new FileException("Error while saving to the file");
//        }
        JOptionPane optionPane = new JOptionPane();
        JSlider slider = getSlider(optionPane);
        optionPane.setMessage(new Object[]{"Compression Quality (%):", slider});
        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        optionPane.setPreferredSize(new Dimension(400, (int) optionPane.getPreferredSize().getHeight()));
        JDialog dialog = optionPane.createDialog(null, "Compression Quality");
        dialog.setVisible(true);

        if ((int) optionPane.getValue() == JOptionPane.OK_OPTION) {
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName(getFileExtension()).next();
            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            float quality = ((float)((int) optionPane.getInputValue()) / 100);
            jpgWriteParam.setCompressionQuality(quality);

            try {
                ImageOutputStream outputStream = new FileImageOutputStream(new File(path + "." + getFileExtension()));
                jpgWriter.setOutput(outputStream);
                IIOImage outputImage = new IIOImage(image, null, null);
                jpgWriter.write(null, outputImage, jpgWriteParam);
                jpgWriter.dispose();
            } catch (IOException ex) {
                throw new FileException("Error while saving to the file");
            }
        }

    }

    private JSlider getSlider(final JOptionPane optionPane) {
        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(70);
        optionPane.setInputValue(new Integer(slider.getValue()));
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider theSlider = (JSlider) e.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    optionPane.setInputValue(new Integer(theSlider.getValue()));
                }
            }
        };

        slider.addChangeListener(changeListener);
        return slider;
    }

}
