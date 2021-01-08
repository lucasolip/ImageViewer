package app.swing;

import control.Command;
import control.ImagePresenter;
import control.NextCommand;
import control.PrevCommand;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import model.Image;
import view.ImageDisplay;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main().execute();
    }
    
    private List<Image> images;
    private ImageDisplay imageDisplay;
    private Map<String, Command> commands = new HashMap<>();
    private ImagePresenter imagePresenter;

    public Main() {
        this.setTitle("Image Viewer");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(imagePanel());
        
        //this.add(toolbar(), BorderLayout.SOUTH);
    }
    
    private void execute() {
        this.images = new FileImageLoader(new File("images")).load();
        this.imageDisplay.show(images.get(0));
        imagePresenter = new ImagePresenter(images, imageDisplay);
        //commands.put("<", new NextCommand(images, imageDisplay));
        //commands.put(">", new PrevCommand(images, imageDisplay));
        this.setVisible(true);
    }

    private JPanel imagePanel() {
        ImagePanel panel = new ImagePanel();
        this.imageDisplay = panel;
        return panel;
    }

    private JMenuBar toolbar() {
        JMenuBar toolbar = new JMenuBar();
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolbar.add(button("<"));
        toolbar.add(button(">"));
        
        return toolbar;
    }

    private JButton button(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(name).execute();
            }
        });
        return button;
    }
}
