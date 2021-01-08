package view;

import model.Image;

public interface ImageDisplay {
    public void show(Image image);
    
    public Image image();
    
    void on(Shift shift);
    
    interface Shift {
        Image left();
        Image right();
    }
}
