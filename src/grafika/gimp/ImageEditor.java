package grafika.gimp;

import grafika.gimp.filtry.histogramy.HistogramWindow;
import grafika.paint.History;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;

public class ImageEditor extends JPanel {

    private BufferedImage image;
    private BufferedImage previewImage;
    private LinkedList<History> undoImages = new LinkedList<>();
    private LinkedList<History> redoImages = new LinkedList<>();

    private HistogramWindow histogramWindow;

    public ImageEditor() {
        super();
        MouseAdapter mouseAdapter = new MouseAdapter() {
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (previewImage != null) {
            g2.drawImage(previewImage, 0, 0, getWidth(), getHeight(), this);
        } else if (image != null) {
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

    public BufferedImage getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(BufferedImage previewImage) {
        this.previewImage = previewImage;
        repaint();
        updateHistogram();
    }

    public HistogramWindow getHistogramWindow() {
        return histogramWindow;
    }

    public void setHistogramWindow(HistogramWindow histogramWindow) {
        this.histogramWindow = histogramWindow;
    }

    public void updateHistogram() {
        if (histogramWindow != null) {
            histogramWindow.updateHistogram();
        }
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
        this.previewImage = null;
        repaint();
        updateHistogram();

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
            updateHistogram();
        }
    }

    public void redo() {
        if (!redoImages.isEmpty()) {
            History p = redoImages.removeLast();
            undoImages.addLast(p);
            p.redo();
            repaint();
            updateHistogram();
        }
    }
}
