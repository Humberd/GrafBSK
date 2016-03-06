package grafika.zad2;

import grafika.PPM.FileType;
import grafika.PPM.PPMextension;
import grafika.exceptions.FileException;
import java.awt.BorderLayout;
import java.awt.Color;
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
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.ppm", "ppm");
                fileChooser.setFileFilter(filter);
                fileChooser.setPreferredSize(new Dimension(500, 500));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (openedFile != null) {
                        try {
                            openedFile.closeFile();
                        } catch (FileException ex) {
                            Logger.getLogger(ImageWindow.class.getName()).log(Level.SEVERE, null, ex);
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
            default:
                throw new FileException("No class can handle extension \"" + fileExtension + "\"");
        }

    }
    
    private void errorHandler(Exception ex) {
        System.err.println("Line ["+openedFile.getLinesRead()+"]: "+ex.getMessage());
    }
}
