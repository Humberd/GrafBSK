package grafika.gimp.filtry.binarne;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.GrayFilter;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import test.SimpleThreadPool;

public class BinarizationWindow extends FilterWindow {

    private JRadioButton manualRadio;
    private JSlider manualSlider;
    private JLabel manualLabel;

    private JRadioButton percentRadio;
    private JSlider percentSlider;
    private JLabel percentLabel;

    private JRadioButton meanRadio;
    private JRadioButton entropyRadio;
    private JRadioButton minimumRadio;
    private JRadioButton fuzzyRadio;

    private BinarizationType filterType;

    public BinarizationWindow(ImageWindow imageWindow) {
        super("Binarization", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    public void filterImage() {
        BufferedImage baseImage = getImage();
        BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        int threshold = filterType.filterImage(baseImage);

        ExecutorService executor = Executors.newWorkStealingPool();

        for (int tempy = 0; tempy < newImage.getHeight(); tempy++) {
            int y = tempy;
            executor.submit(() -> {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    Color prevPixelColor = new Color(baseImage.getRGB(x, y));
                    int red = prevPixelColor.getRed();
                    int green = prevPixelColor.getGreen();
                    int blue = prevPixelColor.getBlue();

                    int gray = (int) ((red+blue+green)/3);

                    int binaryColor = 0;
                    if (gray > threshold) {
                        binaryColor = 255;
                    }
                    newImage.setRGB(x, y, new Color (binaryColor, binaryColor, binaryColor).getRGB());
                }
            });
            for (int x = 0; x < newImage.getWidth(); x++) {
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
        System.gc();
    }

    private void addComponents() {
        manualRadio = new JRadioButton("Manual Selection");
        manualRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new ManualSelection(manualSlider);
                filterImage();
            }
        });
        manualRadio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                manualSlider.setEnabled(button.isSelected());
            }
        });
        percentRadio = new JRadioButton("Percent Black Selection");
        percentRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new PercentBlackSelection(percentSlider);
                filterImage();
            }
        });
        percentRadio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JRadioButton button = (JRadioButton) e.getSource();
                percentSlider.setEnabled(button.isSelected());
            }
        });
        meanRadio = new JRadioButton("Mean Iterative Selection");
        meanRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new MeanIterativeSelection();
                filterImage();
            }
        });
        entropyRadio = new JRadioButton("Entropy Selection");
        entropyRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new EntropySelection();
                filterImage();
            }
        });
        minimumRadio = new JRadioButton("Minimum Error Selection");
        fuzzyRadio = new JRadioButton("Fuzzy Minimum Error Selection");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(manualRadio);
        radioGroup.add(percentRadio);
        radioGroup.add(meanRadio);
        radioGroup.add(entropyRadio);
        radioGroup.add(minimumRadio);
        radioGroup.add(fuzzyRadio);

        manualSlider = new JSlider(0, 255, 127);
        manualSlider.setPaintTicks(true);
        manualSlider.setPaintLabels(true);
        manualSlider.setMajorTickSpacing(50);
        manualSlider.setMinorTickSpacing(10);
        manualSlider.setEnabled(false);
        manualSlider.setPreferredSize(new Dimension(300, 50));
        manualSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                manualLabel.setText(manualSlider.getValue() + "");
                filterImage();
            }
        });
        manualLabel = new JLabel(" 127");

        percentSlider = new JSlider(0, 100, 50);
        percentSlider.setPaintTicks(true);
        percentSlider.setPaintLabels(true);
        percentSlider.setMajorTickSpacing(20);
        percentSlider.setMinorTickSpacing(5);
        percentSlider.setEnabled(false);
        percentSlider.setPreferredSize(new Dimension(300, 50));
        percentSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                percentLabel.setText(percentSlider.getValue() + "");
                filterImage();
            }
        });

        percentLabel = new JLabel("  50");

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(2, 7, 2, 7);
        c.anchor = GridBagConstraints.LINE_START;

        c.gridx = 0;
        c.gridy = 0;
        add(manualRadio, c);
        c.gridx = 0;
        c.gridy = 1;
        add(manualSlider, c);
        c.gridx = 1;
        c.gridy = 1;
        add(manualLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        add(percentRadio, c);
        c.gridx = 0;
        c.gridy = 3;
        add(percentSlider, c);
        c.gridx = 1;
        c.gridy = 3;
        add(percentLabel, c);
        c.gridx = 0;
        c.gridy = 4;
        add(meanRadio, c);
        c.gridx = 0;
        c.gridy = 5;
        add(entropyRadio, c);
//        c.gridx = 0;
//        c.gridy = 6;
//        add(minimumRadio, c);
//        c.gridx = 0;
//        c.gridy = 7;
//        add(fuzzyRadio, c);
    }

    @Override
    public void closeMe() {
        getWindow().setBinarizationWindow(null);
    }

    @Override
    protected void invokeAfterBuild() {
        super.invokeAfterBuild(); //To change body of generated methods, choose Tools | Templates.
        entropyRadio.doClick();
    }

}
