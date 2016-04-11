package grafika.gimp;

import grafika.file.extensions.JPEG.JPGextension;
import grafika.file.extensions.PPM.PPMextension;
import grafika.exceptions.FileException;
import grafika.file.extensions.PNG.PNGextension;
import grafika.gimp.filtry.BrightnessChangerFilter;
import grafika.gimp.filtry.ColorChangerFilter;
import grafika.gimp.filtry.GrayScaleChangerFilter;
import grafika.gimp.filtry.binarne.BinarizationWindow;
import grafika.gimp.filtry.histogramy.HistogramFilter;
import grafika.gimp.filtry.histogramy.HistogramWindow;
import grafika.gimp.filtry.maski.AverageMaskFilter;
import grafika.gimp.filtry.maski.HighPassMaskFilter;
import grafika.gimp.filtry.maski.MedianMaskFilter;
import grafika.gimp.filtry.maski.SobelMaskFilter;
import grafika.gimp.filtry.morfologiczne.MorphologicalWindow;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageWindow extends JPanel {

    private JMenuBar menuBar = new JMenuBar();
    private FileType openedFile;
    private ImageEditor imageEditor = new ImageEditor();

    private ColorChangerFilter colorChangerWindow;
    private BrightnessChangerFilter brightnessChangerWindow;
    private GrayScaleChangerFilter grayScaleChangerWindow;
    private AverageMaskFilter averageMaskWindow;
    private MedianMaskFilter medianMaskWindow;
    private HistogramFilter histogramFilterWindow;
    private BinarizationWindow binarizationWindow;
    private MorphologicalWindow morphologicalWindow;
    private GreenFields greenFieldsWindow;

    public ImageWindow() {
        super();
        setName("image Editor");
        addComponents();
//        try {
//            selectClassByExtensionName(new File("C:/Users/Sawik/Documents/pb.png"));
//            openedFile.openFile();
//            imageEditor.pushNewImage(openedFile.getImage());
//        } catch (FileException ex) {
//            Logger.getLogger(ImageWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        addFileMenu();

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.01;
        c.weighty = 0.01;
        c.gridx = 0;
        c.gridy = 0;
        add(menuBar, c);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(imageEditor, c);
    }

    private void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openFileItem = new JMenuItem("Open");
        KeyStroke ctrlO = KeyStroke.getKeyStroke("control O");
        openFileItem.setAccelerator(ctrlO);
        fileMenu.add(openFileItem);
        attachItem(openFileItem, fileMenu, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                setFilter("ppm", fileChooser);
                setFilter("jpg", fileChooser);
                setFilter("png", fileChooser);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (openedFile != null) {
                        try {
                            openedFile.closeFile();
                        } catch (FileException ex) {
                            errorHandler(ex);
                        }
                    }
                    try {
                        selectClassByExtensionName(fileChooser.getSelectedFile());
                        openedFile.openFile();
                        getImageEditor().pushNewImage(openedFile.getImage());
                    } catch (FileException ex) {
                        errorHandler(ex);
                    }
                }
            }
        });

        JMenuItem saveFileItem = new JMenuItem("Save");
        KeyStroke ctrlS = KeyStroke.getKeyStroke("control S");
        saveFileItem.setAccelerator(ctrlS);
        fileMenu.add(saveFileItem);
        attachItem(saveFileItem, fileMenu, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setApproveButtonText("Save");
                fileChooser.setToolTipText("Save");
                fileChooser.setDialogTitle("Save");
                setFilter("jpg", fileChooser);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (openedFile != null) {
                        try {
                            FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                            FileType fileType = selectSaveClassByExtensionName(fileChooser.getSelectedFile().getAbsolutePath(), filter.getExtensions()[0]);
                            fileType.saveFile(openedFile.getImage(), fileChooser.getSelectedFile().getAbsolutePath());
                        } catch (FileException ex) {
                            errorHandler(ex);
                        }
                    } else {
                        //info o errorze
                        errorHandler("Cannot save an empty image");
                    }
                }
            }
        });
        ////////////////////////
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem undoFileItem = new JMenuItem("Undo");
        KeyStroke ctrlZ = KeyStroke.getKeyStroke("control Z");
        undoFileItem.setAccelerator(ctrlZ);
        editMenu.add(undoFileItem);
        attachItem(undoFileItem, editMenu, new Runnable() {
            @Override
            public void run() {
                getImageEditor().undo();
            }
        });

        JMenuItem redoFileItem = new JMenuItem("Redo");
        KeyStroke ctrlY = KeyStroke.getKeyStroke("control Y");
        redoFileItem.setAccelerator(ctrlY);
        editMenu.add(redoFileItem);
        attachItem(redoFileItem, editMenu, new Runnable() {
            @Override
            public void run() {
                getImageEditor().redo();
            }
        });

        ////////////////////////////
        JMenu windowsMenu = new JMenu("Windows");
        menuBar.add(windowsMenu);

        final JMenuItem histogramWindowItem = new JMenuItem("Histogram");
        KeyStroke ctrlH = KeyStroke.getKeyStroke("control H");
        histogramWindowItem.setAccelerator(ctrlH);
        attachItem(histogramWindowItem, windowsMenu, new Runnable() {
            @Override
            public void run() {
                if (imageEditor.getHistogramWindow() == null) {
                    imageEditor.setHistogramWindow(new HistogramWindow(ImageWindow.this));
                }
            }
        });
        ///////////////////////////
        JMenu filterMenu = new JMenu("Filters");
        menuBar.add(filterMenu);

        JMenu pointTransformations = new JMenu("Point Transformations");
        filterMenu.add(pointTransformations);

        KeyStroke testStroke = KeyStroke.getKeyStroke("control A");

        JMenuItem colorChangerItem = new JMenuItem("Color changer");
        attachItem(colorChangerItem, pointTransformations, new Runnable() {
            @Override
            public void run() {
                if (getColorChangerWindow() == null) {
                    setColorChangerWindow(new ColorChangerFilter(ImageWindow.this));
                }
            }
        });

        JMenuItem brightnessChangerItem = new JMenuItem("Brightness changer");
        attachItem(brightnessChangerItem, pointTransformations, new Runnable() {
            @Override
            public void run() {
                if (getBrightnessChangerWindow() == null) {
                    setBrightnessChangerWindow(new BrightnessChangerFilter(ImageWindow.this));
                }
            }
        });

        JMenuItem grayScaleChangerItem = new JMenuItem("Gray Scale changer");
        attachItem(grayScaleChangerItem, pointTransformations, new Runnable() {
            @Override
            public void run() {
                if (getGrayScaleChangerWindow() == null) {
                    setGrayScaleChangerWindow(new GrayScaleChangerFilter(ImageWindow.this));
                }
            }
        });
        /////////////////////////////////////////
        JMenu maskFilters = new JMenu("Mask Filters");
        filterMenu.add(maskFilters);

        JMenuItem averageMaskFilterItem = new JMenuItem("Average Mask Filter");
        attachItem(averageMaskFilterItem, maskFilters, new Runnable() {
            @Override
            public void run() {
                if (getAverageMaskWindow() == null) {
                    setAverageMaskWindow(new AverageMaskFilter(ImageWindow.this));
                }
            }
        });

        JMenuItem medianMaskFilterItem = new JMenuItem("Median Mask Filter");
        attachItem(medianMaskFilterItem, maskFilters, new Runnable() {
            @Override
            public void run() {
                if (getMedianMaskWindow() == null) {
                    setMedianMaskWindow(new MedianMaskFilter(ImageWindow.this));
                }
            }
        });

        JMenuItem sobelMaskFilterItem = new JMenuItem("Sobel Mask Filter");
        attachItem(sobelMaskFilterItem, maskFilters, new Runnable() {
            @Override
            public void run() {
                new SobelMaskFilter(ImageWindow.this);
            }
        });

        JMenuItem highPassFilterItem = new JMenuItem("High-Pass Mask Filter");
//        highPassFilterItem.setAccelerator(testStroke);
        attachItem(highPassFilterItem, maskFilters, new Runnable() {
            @Override
            public void run() {
                new HighPassMaskFilter(ImageWindow.this);
            }
        });

        JMenuItem histogramFilterItem = new JMenuItem("Histogram Filters");
        attachItem(histogramFilterItem, filterMenu, new Runnable() {
            @Override
            public void run() {
                histogramWindowItem.doClick();
                if (getHistogramFilterWindow() == null) {
                    setHistogramFilterWindow(new HistogramFilter(ImageWindow.this));
                }
            }
        });

        JMenuItem binarizationItem = new JMenuItem("Binarization");
        attachItem(binarizationItem, filterMenu, new Runnable() {
            @Override
            public void run() {
                if (getBinarizationWindow() == null) {
                    setBinarizationWindow(new BinarizationWindow(ImageWindow.this));
                }
            }
        });

        JMenuItem morphologicalItem = new JMenuItem("Morphological Filters");
        attachItem(morphologicalItem, filterMenu, new Runnable() {
            @Override
            public void run() {
                if (getMorphologicalWindow() == null) {
                    setMorphologicalWindow(new MorphologicalWindow(ImageWindow.this));
                }
            }
        });
        
        JMenuItem greenFieldsItem = new JMenuItem("Green Fields");
        greenFieldsItem.setAccelerator(testStroke);
        attachItem(greenFieldsItem, filterMenu, new Runnable() {
            @Override
            public void run() {
                new GreenFields(ImageWindow.this);
            }
        });

        ////////////////////////////////////////////
    }

    private void attachItem(JMenuItem item, JMenu menu, Runnable runnable) {
        menu.add(item);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread th = new Thread(runnable);
                th.start();
            }
        });
    }

    private void selectClassByExtensionName(File file) throws FileException {
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        String[] fileExtensionTemp = fileName.split("\\.");
        String fileExtension = fileExtensionTemp[fileExtensionTemp.length - 1];
        fileExtension = fileExtension.toLowerCase();

        switch (fileExtension) {
            case "ppm":
                openedFile = new PPMextension(filePath);
                break;
            case "jpg":
                openedFile = new JPGextension(filePath);
                break;
            case "jpeg":
                openedFile = new JPGextension(filePath);
                break;
            case "png":
                openedFile = new PNGextension(filePath);
                break;
            default:
                throw new FileException("No class can handle extension \"" + fileExtension + "\"");
        }
    }

    private FileType selectSaveClassByExtensionName(String path, String extensionName) throws FileException {
        FileType fileType = null;
        switch (extensionName) {
            case "ppm":
                fileType = new PPMextension(path);
                break;
            case "jpg":
                fileType = new JPGextension(path);
                break;
            case "jpeg":
                fileType = new JPGextension(path);
                break;
            case "png":
                fileType = new PNGextension(path);
            default:
                throw new FileException("No class can handle extension \"" + extensionName + "\"");
        }
        return fileType;
    }

    private void setFilter(String filterName, JFileChooser chooser) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + filterName, filterName);
        chooser.addChoosableFileFilter(filter);
        if (filterName.equals("png")) {
            chooser.setFileFilter(filter);
        }
//        chooser.setFileFilter(filter);
    }

    private void errorHandler(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
        System.err.println(text);
    }

    private void errorHandler(Exception ex) {
        errorHandler("Line < = [" + openedFile.getLinesRead() + "]: " + ex.getMessage());
    }

    public ImageEditor getImageEditor() {
        return imageEditor;
    }

    public void setImageEditor(ImageEditor imageEditor) {
        this.imageEditor = imageEditor;
    }

    public ColorChangerFilter getColorChangerWindow() {
        return colorChangerWindow;
    }

    public void setColorChangerWindow(ColorChangerFilter colorChangerWindow) {
        this.colorChangerWindow = colorChangerWindow;
    }

    public BrightnessChangerFilter getBrightnessChangerWindow() {
        return brightnessChangerWindow;
    }

    public void setBrightnessChangerWindow(BrightnessChangerFilter brightnessChangerWindow) {
        this.brightnessChangerWindow = brightnessChangerWindow;
    }

    public GrayScaleChangerFilter getGrayScaleChangerWindow() {
        return grayScaleChangerWindow;
    }

    public void setGrayScaleChangerWindow(GrayScaleChangerFilter grayScaleChangerWindow) {
        this.grayScaleChangerWindow = grayScaleChangerWindow;
    }

    public AverageMaskFilter getAverageMaskWindow() {
        return averageMaskWindow;
    }

    public void setAverageMaskWindow(AverageMaskFilter averageMaskWindow) {
        this.averageMaskWindow = averageMaskWindow;
    }

    public MedianMaskFilter getMedianMaskWindow() {
        return medianMaskWindow;
    }

    public void setMedianMaskWindow(MedianMaskFilter medianMaskWindow) {
        this.medianMaskWindow = medianMaskWindow;
    }

    public HistogramFilter getHistogramFilterWindow() {
        return histogramFilterWindow;
    }

    public void setHistogramFilterWindow(HistogramFilter histogramFilterWindow) {
        this.histogramFilterWindow = histogramFilterWindow;
    }

    public BinarizationWindow getBinarizationWindow() {
        return binarizationWindow;
    }

    public void setBinarizationWindow(BinarizationWindow binarizationWindow) {
        this.binarizationWindow = binarizationWindow;
    }

    public MorphologicalWindow getMorphologicalWindow() {
        return morphologicalWindow;
    }

    public void setMorphologicalWindow(MorphologicalWindow morphologicalWindow) {
        this.morphologicalWindow = morphologicalWindow;
    }

    public GreenFields getGreenFieldsWindow() {
        return greenFieldsWindow;
    }

    public void setGreenFieldsWindow(GreenFields greenFieldsWindow) {
        this.greenFieldsWindow = greenFieldsWindow;
    }
}
