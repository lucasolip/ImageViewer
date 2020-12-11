package app.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;

public class ImagePanel extends JPanel implements ImageDisplay {

    private BufferedImage data;
    private Image image;

    public ImagePanel() {

    }
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0,0,getWidth(), getHeight());
        Box box = new Box(data.getWidth(), data.getHeight(), this.getWidth(), this.getHeight());
        g.drawImage(data, box.x, box.y, box.width, box.height, null);
    }

    private static BufferedImage read(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.data = read(new File(image.getName()));
        repaint();
    }

    @Override
    public Image image() {
        return this.image;
    }

    private static class Box {
        final int x;
        final int y;
        final int width;
        final int height;
        
        private Box (int imageWidth, int imageHeight, int panelWidth, int panelHeight) {
            double imageRatio = (double) imageWidth / imageHeight;
            double panelRatio = (double) panelWidth / panelHeight;
            this.width = (imageRatio >= panelRatio) ? panelWidth : imageWidth * panelHeight/imageHeight;
            this.height = (imageRatio <= panelRatio) ? panelHeight : imageHeight * panelWidth/imageWidth;
            this.x = (int) ((panelWidth - this.width) / 2);
            this.y = (int) ((panelHeight - this.height) / 2);
        }
    }
    
}
