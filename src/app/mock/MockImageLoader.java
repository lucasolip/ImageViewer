package app.mock;

import java.util.ArrayList;
import java.util.List;
import model.Image;
import view.ImageLoader;

public class MockImageLoader implements ImageLoader {

    @Override
    public List<Image> load() {
        ArrayList<Image> list = new ArrayList<>();
        list.add(new Image("Hola"));
        list.add(new Image("Mundo"));
        list.add(new Image("Bienvenido"));
        return list;
    }    
}
