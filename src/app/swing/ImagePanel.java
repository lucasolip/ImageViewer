package app.swing;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;

public class ImagePanel extends JPanel implements ImageDisplay {

    private BufferedImage bitmap;
    private BufferedImage nextBitmap;
    private Image image;
    private int offset = 0;
    private Shift shift;

    public ImagePanel() {
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }
    
    @Override
    public void paint(Graphics g) {
        g.clearRect(0,0,getWidth(), getHeight());

        if (bitmap != null) {
            Box box = new Box(bitmap.getWidth(), bitmap.getHeight(), this.getWidth(), this.getHeight());
            g.drawImage(bitmap, box.x + offset, box.y, box.width, box.height, null);
        }
        if (nextBitmap != null && offset != 0) {
            Box nextBox = new Box(nextBitmap.getWidth(), nextBitmap.getHeight(), this.getWidth(), this.getHeight());
            g.drawImage(nextBitmap, offset < 0 ? nextBox.width + nextBox.x + offset : offset - nextBox.width + nextBox.x, nextBox.y, nextBox.width, nextBox.height, null);
        }
    }

    @Override
    public void show(Image image) {
        this.image = image;
        bitmap = loadBitmap(image);
        repaint();
    }

    @Override
    public Image image() {
        return this.image;
    }
    
    private BufferedImage loadBitmap(Image image) {
        try {
            return ImageIO.read(new File(image.getName()));
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
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

    private class MouseHandler implements MouseListener, MouseMotionListener {

        private int initial;

        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            this.initial = event.getX();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            int shift = shift(event.getX());
            if (Math.abs(shift) > getWidth() / 2) show(imageAt(shift));
            offset = 0;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int x = shift(event.getX());
            if (x == 0) nextBitmap = null;
            else if (x != 0 && ImagePanel.this.offset/x <= 0) {
                nextBitmap = loadBitmap(imageAt(x));
                ImagePanel.this.offset = x;
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent event) {
        }

        private int shift(int x) {
            return x - initial;
        }

        private Image imageAt(int x) {
            if (x > 0) return shift.left();
            if (x < 0) return shift.right();
            return null;
        }
    }
    
}
