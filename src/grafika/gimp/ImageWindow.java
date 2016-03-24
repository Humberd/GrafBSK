package grafika.gimp;

import grafika.file.extensions.JPEG.JPGextension;
import grafika.file.extensions.PPM.PPMextension;
import grafika.exceptions.FileException;
import grafika.gimp.filtry.BrightnessChangerFilter;
import grafika.gimp.filtry.ColorChangerFilter;
import grafika.gimp.filtry.GrayScaleChangerFilter;
import grafika.gimp.filtry.maski.AverageMaskFilter;
import grafika.gimp.filtry.maski.MedianMaskFilter;
import grafika.gimp.filtry.maski.SobelMaskFilter;
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

    public ImageWindow() {
        super();
        setName("image Editor");
        addComponents();
        //        KeyAdapter keyAdapter = new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                System.out.println(e.getModifiers()+"dupa");
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                System.out.println("ok");
//            }
//
//            
//        };
//        addKeyListener(keyAdapter);
//        try {
//            selectClassByExtensionName(new File("C:/Users/Sawik/Documents/testJPG.jpg"));
//            openedFile.openFile();
//            imageEditor.pushNewImage(openedFile.getImage());
//        } catch (FileException ex) {
//            Logger.getLogger(ImageWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            selectClassByExtensionName(new File("C:/Users/Sawik/Documents/lenaOriginal.jpg"));
            openedFile.openFile();
            imageEditor.pushNewImage(openedFile.getImage());
        } catch (FileException ex) {
            Logger.getLogger(ImageWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
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

//        setLayout(new BorderLayout());
//        addFileMenu();
//        add(menuBar, BorderLayout.NORTH);
//        add(imageEditor, BorderLayout.CENTER);
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
        sobelMaskFilterItem.setAccelerator(testStroke);
        attachItem(sobelMaskFilterItem, maskFilters, new Runnable() {
            @Override
            public void run() {
                new SobelMaskFilter(ImageWindow.this).filterImage();
            }
        });
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
            default:
                throw new FileException("No class can handle extension \"" + extensionName + "\"");
        }
        return fileType;
    }

    private void setFilter(String filterName, JFileChooser chooser) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + filterName, filterName);
        chooser.addChoosableFileFilter(filter);
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
}
