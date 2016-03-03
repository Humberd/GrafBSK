package grafika.zad2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;

public class ImageEditor extends JPanel {

    private BufferedImage image;
    private AffineTransform transform;

    public ImageEditor() {
        super();
        MouseAdapter adapter = new MouseAdapter() {
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

    public void setImage(BufferedImage image) {
        this.image = image;
        this.transform = new AffineTransform();
        transform.setToIdentity();
        repaint();
    }
}
