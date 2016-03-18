package grafika.gimp.filtry;

import grafika.gimp.ImageWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ColorChangerFilter extends FilterWindow {

    private JCheckBox redCheckBox;
    private JCheckBox greenCheckBox;
    private JCheckBox blueCheckBox;

    private JSpinner inputField;

    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;

    private OperationInterface operation;

    public ColorChangerFilter(ImageWindow imageWindow) {
        super("Color Changer", imageWindow);
    }

    @Override
    protected void customInit() {
        setShowButtons(false);
        setShowPreviewImageCheckBox(false);
        addComponents();
    }

    private void createNewImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        boolean redChecked = redCheckBox.isSelected();
        boolean greenChecked = greenCheckBox.isSelected();
        boolean blueChecked = blueCheckBox.isSelected();

        int opValue = (int) inputField.getValue();

        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                Color prevPixelColor = new Color(baseImage.getRGB(x, y));
                int red = prevPixelColor.getRed();
                int green = prevPixelColor.getGreen();
                int blue = prevPixelColor.getBlue();

                if (redChecked) {
                    red = operation.count(red, opValue);
                }
                if (greenChecked) {
                    green = operation.count(green, opValue);
                }
                if (blueChecked) {
                    blue = operation.count(blue, opValue);
                }

                Color newPixelColor = new Color(red, green, blue);
                newImage.setRGB(x, y, newPixelColor.getRGB());
            }
        }

        pushNewImage(newImage);
    }

    private void addComponents() {
        redCheckBox = new JCheckBox("Red");
        greenCheckBox = new JCheckBox("Green");
        blueCheckBox = new JCheckBox("Blue");

        inputField = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = new Add();
                createNewImage();
            }
        });

        subtractButton = new JButton("Subtract");
        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = new Subtract();
                createNewImage();
            }
        });

        multiplyButton = new JButton("Multiply");
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = new Multiply();
                createNewImage();
            }
        });

        divideButton = new JButton("Divide");
        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = new Divide();
                createNewImage();
            }
        });
        ///////////////////////////
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.ipady = 10;

        c.gridx = 0;
        c.gridy = 0;
        JPanel colorPanel = new JPanel();
        add(colorPanel, c);
        colorPanel.add(redCheckBox);
        colorPanel.add(greenCheckBox);
        colorPanel.add(blueCheckBox);

        c.gridx = 0;
        c.gridy = 1;
        JPanel inputPanel = new JPanel();
        add(inputPanel, c);
        inputPanel.add(inputField);

        c.gridx = 0;
        c.gridy = 2;
        JPanel operationPanel = new JPanel();
        add(operationPanel, c);
        operationPanel.add(addButton);
        operationPanel.add(subtractButton);
        operationPanel.add(multiplyButton);
        operationPanel.add(divideButton);
    }

    @Override
    public void closeMe() {
        getWindow().setColorChangerWindow(null);
    }
}

interface OperationInterface {

    public int count(int currValue, int opValue);
}

class Add implements OperationInterface {

    @Override
    public int count(int currValue, int opValue) {
        int result = currValue + opValue;
        if (result > 255) {
            return 255;
        }
        return result;
    }

}

class Subtract implements OperationInterface {

    @Override
    public int count(int currValue, int opValue) {
        int result = currValue - opValue;
        if (result < 0) {
            return 0;
        }
        return result;
    }

}

class Multiply implements OperationInterface {

    @Override
    public int count(int currValue, int opValue) {
        int result = currValue * opValue;
        if (result > 255) {
            return 255;
        }
        return result;
    }

}

class Divide implements OperationInterface {

    @Override
    public int count(int currValue, int opValue) {
        if (opValue == 0) {
            return currValue;
        }
        int result = currValue / opValue;
        return result;
    }

}
