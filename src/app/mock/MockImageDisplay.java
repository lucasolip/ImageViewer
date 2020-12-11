package app.mock;

import model.Image;
import view.ImageDisplay;

public class MockImageDisplay implements ImageDisplay {

    private Image image;
    
    @Override
    public void show(Image image) {
        this.image = image;
        System.out.println(image.getName());
    }
    
    @Override
    public Image image() {
        return image;
    }
    
}
