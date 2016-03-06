package grafika.zad2;

import grafika.JPEG.JPGextension;
import grafika.PPM.PPMextension;
import grafika.exceptions.FileException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageWindow extends JPanel {

    private JMenuBar menuBar = new JMenuBar();
    private FileType openedFile;
    private ImageEditor imageEditor = new ImageEditor();

    public ImageWindow() {
        super();
        setName("image Editor");
        addComponents();
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
                        imageEditor.setImage(openedFile.getImage());
                    } catch (FileException ex) {
                        errorHandler(ex);
                    }
                }
            }
        });

        JMenuItem saveFileItem = new JMenuItem("Save");
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
//                setFilter("ppm", fileChooser);
                setFilter("jpg", fileChooser);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    System.out.println(fileChooser.getFileFilter);
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
        JOptionPane.showMessageDialog(this, text,"Error",JOptionPane.ERROR_MESSAGE);
        System.err.println(text);
    }

    private void errorHandler(Exception ex) {
        errorHandler("Line < = [" + openedFile.getLinesRead() + "]: " + ex.getMessage());
    }
}
