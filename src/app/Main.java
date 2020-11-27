package app;

import control.Command;
import control.ExitCommand;
import control.InitCommand;
import control.NextCommand;
import control.NullCommand;
import control.PrevCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.Image;
import view.ImageDisplay;
import view.ImageLoader;
import view.MockImageDisplay;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Map<String, Command> commands = initCommands(new ArrayList<>(), new MockImageDisplay(), new MockImageLoader());
        
        while (true) {
           commands.getOrDefault(scanner.next(), NullCommand.Instance).execute();        
        }
        
    }
    
    private static Map<String, Command> initCommands(List<Image> images, ImageDisplay imageDisplay, ImageLoader imageLoader) {
        HashMap<String, Command> result = new HashMap<>();
        result.put("q", new ExitCommand());
        result.put("Q", result.get("q"));
        result.put("p", new PrevCommand(images, imageDisplay));
        result.put("P", result.get("p"));
        result.put("n", new NextCommand(images, imageDisplay));
        result.put("N", result.get("n"));
        result.put("i", new InitCommand(imageLoader, images, imageDisplay));
        return result;
    }
}
