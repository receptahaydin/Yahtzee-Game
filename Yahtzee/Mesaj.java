package Yahtzee;

public class Mesaj implements java.io.Serializable {

    public static enum Message_Type {
        Name, RivalConnected, CHANGE, GameControl, FINISH
    }

    public Message_Type type;

    public Object content;

    public Mesaj(Message_Type t) {
        this.type = t;
    }
}
