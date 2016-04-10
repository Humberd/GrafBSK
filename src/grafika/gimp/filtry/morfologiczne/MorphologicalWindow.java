package grafika.gimp.filtry.morfologiczne;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

public class MorphologicalWindow extends FilterWindow {

    private JRadioButton erosionRadio;
    private JRadioButton dilatationRadio;
    private JRadioButton openingRadio;
    private JRadioButton closingRadio;
    private JRadioButton thinningRadio;
    private JRadioButton thickeningRadio;

    private JButton filterButton;

    private MorphologicalType filterType;

    public MorphologicalWindow(ImageWindow imageWindow) {
        super("Morfological Transformations", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    public void filterImage() {
        BufferedImage baseImage;
        baseImage = getImage();
        if (baseImage == null) {
            return;
        }
        BufferedImage newImage = filterType.filterImage(baseImage);
        setInnerPreviewImage(newImage);
        if (getPreviewImageCheckBox().isSelected()) {
            setOuterPreviewImage(newImage);
        }
        System.gc();
    }

    private void addComponents() {
        erosionRadio = new JRadioButton("Erosion");
        erosionRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Erosion();
                filterImage();
            }
        });

        dilatationRadio = new JRadioButton("Dilatation");
        dilatationRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Dilatation();
                filterImage();
            }
        });

        openingRadio = new JRadioButton("Opening");
        openingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Opening();
                filterImage();
            }
        });

        closingRadio = new JRadioButton("Closing");
        closingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Closing();
                filterImage();
            }
        });

        thinningRadio = new JRadioButton("Thinning");
        thinningRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Thinning();
                filterImage();
            }
        });

        thickeningRadio = new JRadioButton("Thickening");
        thickeningRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterType = new Thickening();
                filterImage();
            }
        });

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(erosionRadio);
        radioGroup.add(dilatationRadio);
        radioGroup.add(openingRadio);
        radioGroup.add(closingRadio);
        radioGroup.add(thinningRadio);
        radioGroup.add(thickeningRadio);

        filterButton = new JButton();
        Action filterButtonAction = new AbstractAction("Filter") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getInnerPreviewImage() != null) {
                    pushNewImage(getInnerPreviewImage());
                }
                if (filterType != null) {
                    filterImage();
                }
            }
        };
        filterButtonAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
        filterButton.setAction(filterButtonAction);
        filterButton.getActionMap().put("Filter", filterButtonAction);
        filterButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) filterButtonAction.getValue(Action.ACCELERATOR_KEY), "Filter");

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(8, 0, 2, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        add(filterButton, c);

        c.insets = new Insets(2, 7, 2, 7);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 1;
        add(erosionRadio, c);
        c.gridx = 0;
        c.gridy = 2;
        add(dilatationRadio, c);
        c.gridx = 0;
        c.gridy = 3;
        add(openingRadio, c);
        c.gridx = 0;
        c.gridy = 4;
        add(closingRadio, c);
        c.gridx = 0;
        c.gridy = 5;
        add(thinningRadio, c);
        c.gridx = 0;
        c.gridy = 6;
        add(thickeningRadio, c);
    }

    @Override
    public void closeMe() {
        getWindow().setMorphologicalWindow(null);
    }

}
