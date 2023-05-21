package Yahtzee;

import java.util.Random;
import javax.swing.JLabel;

public class Zar {
    
    JLabel lbl;
    int value;
    
    public Zar(JLabel label, int value){
        this.lbl = label;
        this.value = value;
        this.lbl.setIcon(Resimler.getImageIcon(this.value));
    }
    
    public void roll(){ 
        this.value = new Random().nextInt(5) + 1;
        this.lbl.setIcon(Resimler.getImageIcon(this.value));
    }
    
    public JLabel getLabel(){
        return this.lbl;
    }
    
}
