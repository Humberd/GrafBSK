package grafika.zad2.filtry;

import grafika.zad2.ImageEditor;
import grafika.zad2.ImageWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;

public abstract class FilterWindow extends JDialog {

    private ImageEditor imageEditor;
    private ImageWindow imageWindow;

    public FilterWindow(String title, ImageWindow imageWindow) {
        super();
        this.imageEditor = imageWindow.getImageEditor();
        this.imageWindow = imageWindow;
        setTitle(title);
        init();
    }

    protected void init() {
        ////
        customInit();
        ////

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMe();
            }

        });
        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected abstract void customInit();

    public BufferedImage getImage() {
        return imageEditor.getImage();
    }

    public void setImage(BufferedImage image) {
        imageEditor.pushNewImage(image);
    }

    public ImageWindow getWindow() {
        return imageWindow;
    }

    public abstract void closeMe();

}
