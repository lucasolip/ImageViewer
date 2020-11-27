package control;

import java.util.List;
import model.Image;
import view.ImageDisplay;
import view.ImageLoader;

public class InitCommand implements Command {

    private final ImageLoader imageLoader;
    private final List<Image> images;
    private final ImageDisplay imageDisplay;
    
    public InitCommand(ImageLoader imageLoader, List<Image> images, ImageDisplay imageDisplay) {
       this.imageDisplay = imageDisplay; 
       this.imageLoader = imageLoader;
       this.images = images;
    }

    @Override
    public void execute() {
        images.clear();
        images.addAll(imageLoader.load());
        imageDisplay.show(images.get(0));
    }
    
}
