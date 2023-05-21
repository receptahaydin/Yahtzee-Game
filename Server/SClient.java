package Server;

import Yahtzee.Mesaj;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {

    int clientID;
    Socket socket;
    public String name = "NoName";
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    Listen listenThread;
    PairingThread pairThread;
    SClient rival;
    public boolean paired = false;

    public SClient(int clientID, Socket socket) {
        try {
            this.socket = socket;
            this.clientID = clientID;
            this.listenThread = new Listen(this);
            this.pairThread = new PairingThread(this);
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Send(Mesaj message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class Listen extends Thread {

        SClient sclient;

        public Listen(SClient sclient) {
            this.sclient = sclient;
        }

        @Override
        public void run() {
            while (sclient.socket.isConnected()) {
                try {
                    Mesaj msg = (Mesaj) sclient.sInput.readObject();
                    switch (msg.type) {
                        case Name:
                            sclient.name = msg.content.toString();
                            sclient.pairThread.start();
                            break;
                        case CHANGE:
                            sclient.rival.Send(msg);
                            System.out.println("Mesaj yollandı!");
                            break;
                        case GameControl:
                            Server.Send(sclient.rival, msg);
                            break;
                        case FINISH:
                            Server.Send(sclient.rival, msg);
                            break;
                    }
                } catch (IOException ex) {
                    System.out.println("Thread Exception!");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Class Not Found!");
                }catch(IllegalThreadStateException te){
                    System.out.println("Illegal Thread!");
                }
            }
        }

    }

    public class PairingThread extends Thread {

        SClient sclient;

        public PairingThread(SClient sclient) {
            this.sclient = sclient;
        }

        @Override
        public void run() {
            while (this.sclient.paired == false && this.sclient.socket.isConnected()) {
                try {
                    Server.pairingSemp.acquire(1);
                    if (!sclient.paired) {
                        SClient selectedPair = null;
                        while (selectedPair == null && this.sclient.socket.isConnected()) {
                            for (SClient client : Server.sclients) {
                                if (sclient != client && client.rival == null) {
                                    selectedPair = client;
                                    selectedPair.paired = true;
                                    selectedPair.rival = sclient;
                                    sclient.rival = selectedPair;
                                    sclient.paired = true;

                                    Mesaj msg1 = new Mesaj(Mesaj.Message_Type.RivalConnected);
                                    msg1.content = sclient.name;
                                    Server.Send(sclient.rival, msg1);

                                    Mesaj msg2 = new Mesaj(Mesaj.Message_Type.RivalConnected);
                                    msg2.content = sclient.rival.name;
                                    Server.Send(sclient, msg2);

                                    Mesaj msg3 = new Mesaj(Mesaj.Message_Type.GameControl);
                                    int a = 0;
                                    msg3.content = a;
                                    Server.Send(sclient, msg3);

                                    Mesaj msg4 = new Mesaj(Mesaj.Message_Type.GameControl);
                                    int b = 1;
                                    msg4.content = b;
                                    Server.Send(sclient.rival, msg4);                               
                                    break;
                                }
                            }
                            sleep(1000);
                        }
                    }
                    Server.pairingSemp.release(1);
                } catch (InterruptedException ex) {
                    System.out.println("Pairing Thread Exception!");
                }catch(IllegalThreadStateException te){
                    System.out.println("Pairing Illegal Thread!");
                }
            }
        }
    }
}
