package Yahtzee;

import Yahtzee.SkorMesajı.Scores;
import javax.swing.JButton;

public class Skor {
    
    Scores score_type;
    JButton button;
    public boolean isButtonChoosen = false;
    
    public Skor(Scores score_type, JButton button){
        this.score_type = score_type;
        this.button = button; 
    }
        
    public Scores getScore_type() {
        return score_type;
    }

    public void setScore_type(Scores score_type) {
        this.score_type = score_type;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    
}
