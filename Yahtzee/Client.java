package Yahtzee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static Socket socket;
    public static ObjectInputStream sInput;
    public static ObjectOutputStream sOutput;
    public static boolean isPaired = false;
    public static ListenThread listen;

    public static void Start(String ip, int port) {
        try {
            Client.socket = new Socket(ip, port);
            Client.listen = new ListenThread();
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listen.start();

            Mesaj msg = new Mesaj(Mesaj.Message_Type.Name);
            msg.content = Giris.nameTxt.getText();
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listen.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String message) {
        System.out.println(message);
    }

    public static void Send(Mesaj msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class ListenThread extends Thread {

    @Override
    public void run() {
        while (Client.socket.isConnected()) {
            try {
                Mesaj msg = (Mesaj) Client.sInput.readObject();
                switch (msg.type) {
                    case Name:
                        break;
                    case RivalConnected:
                        System.out.println("Rival Connected Girildi!");
                        String rivalName = (String) msg.content;
                        Client.isPaired = true;
                        Giris.control.setText("Eşleşme yapıldı!");
                        Giris.gs.player1.setText("You");
                        Giris.gs.player2.setText(rivalName);
                        Giris.gs.setVisible(true);
                        break;
                    case GameControl:
                        System.out.println("GameControl girdi!");
                        Giris.gs.roundControl = (int) msg.content;
                        System.out.println((int) msg.content);
                        if ((int) msg.content == 1) {
                            Giris.gs.changeTurn(true);
                        } else if ((int) msg.content == 0) {
                            Giris.gs.changeTurn(false);
                        }
                        break;
                    case CHANGE:
                        System.out.println("Chante Turn Girdi!");
                        SkorMesajı score = (SkorMesajı) msg.content;
                        Giris.gs.getRivalButtonByGivenType(score.score_type).setText(String.valueOf(score.content));
                        Giris.gs.roundControl = 1;
                        Giris.gs.changeTurn(true);
                        break;
                    case FINISH:
                        Giris.gs.finish = true;
                        Giris.gs.totalPlayer2.setText((String) msg.content);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
