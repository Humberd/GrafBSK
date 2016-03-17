package grafika.zad2;

import grafika.zad1.History;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;

public class ImageEditor extends JPanel {

    private BufferedImage image;
    private LinkedList<History> undoImages = new LinkedList<>();
    private LinkedList<History> redoImages = new LinkedList<>();
    private AffineTransform transform;

    public ImageEditor() {
        super();
        MouseAdapter mouseAdapter = new MouseAdapter() {
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (image != null) {
            int startX = (int) (((float) getWidth() / 2) - image.getWidth());
            int startY = (int) (((float) getHeight() / 2) - image.getHeight());
//            g2.drawImage(image, transform, startX, startY);
//            g2.drawImage(image, startX, startY, this);
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

    }

    public BufferedImage getImage() {
        return image;
    }

    private class HistoryPop implements History {

        private BufferedImage currentImage;

        public HistoryPop(BufferedImage currentImage) {
            this.currentImage = currentImage;
        }

        @Override
        public void undo() {
            image = currentImage;
        }

        @Override
        public void redo() {
            image = null;
        }

    }

    private class HistoryPush implements History {

        private BufferedImage currentImage;
        private BufferedImage previousImage;

        public HistoryPush(BufferedImage currentImage, BufferedImage previousImage) {
            this.currentImage = currentImage;
            this.previousImage = previousImage;
        }

        @Override
        public void undo() {
            image = previousImage;
        }

        @Override
        public void redo() {
            image = currentImage;
        }

    }

    public void pushNewImage(BufferedImage image) {
        undoImages.addLast(new HistoryPush(image, this.image));
        redoImages.clear();
        this.image = image;
        repaint();

    }

    public void popLastImage() {
        undoImages.addLast(new HistoryPop(getImage()));
        redoImages.clear();
    }

    public void undo() {
        if (!undoImages.isEmpty()) {
            History p = undoImages.removeLast();
            redoImages.addLast(p);
            p.undo();
            repaint();
        }
    }

    public void redo() {
        if (!redoImages.isEmpty()) {
            History p = redoImages.removeLast();
            undoImages.addLast(p);
            p.redo();
            repaint();
        }
    }
}
