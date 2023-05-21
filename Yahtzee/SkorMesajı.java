package Yahtzee;

public class SkorMesajı implements java.io.Serializable {
    
    public Scores score_type;
    public int content;
    
    public static enum Scores {
        ONES, TWOS, THREES, FOURS, FIVES, SIXES, THREEKIND, FOURKIND, FULLHOUSE, SMALLSTR, LARGESTR, CHANCE, YAHTZEE
    }
     
    public SkorMesajı(Scores score){
        this.score_type = score;
    }  
}
