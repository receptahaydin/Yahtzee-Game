package Yahtzee;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Resimler {
    
    private static int WIDTH = 110, HEIGHT = 110;
    
    public static ImageIcon getImageIcon(int value){       
        String path = "src/Resimler/"+value+".png";    
        ImageIcon icon = new ImageIcon(path);
        Image scale = icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        return new ImageIcon(scale);     
        
    }

}
