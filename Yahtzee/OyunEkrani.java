package Yahtzee;

import java.util.ArrayList;
import Yahtzee.SkorMesajı.Scores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class OyunEkrani extends javax.swing.JFrame {

    public static OyunEkrani Game;
    public boolean finish = false;
    public int roundControl;
    public static ArrayList<Skor> puanlar;
    public static ArrayList<Skor> rakipPuanlar;
    public static ArrayList<JToggleButton> activeButtons;

    Zar zarlar[] = new Zar[5];
    int rollCount = 0;

    public OyunEkrani() {
        initComponents();
        this.setResizable(false);
        this.getContentPane().setBackground(new java.awt.Color(241,221,189));
        puanlar = new ArrayList();
        rakipPuanlar = new ArrayList();
        activeButtons = new ArrayList();
        Game = this;
        initScores();
        disableRivalButtons(false);
        zarlar[0] = new Zar(dice01lbl, 1);
        zarlar[1] = new Zar(dice02lbl, 2);
        zarlar[2] = new Zar(dice03lbl, 3);
        zarlar[3] = new Zar(dice04lbl, 4);
        zarlar[4] = new Zar(dice05lbl, 5);
        revalidate();
    }

    public Skor getMyButtonByGivenType(Scores score_type) {
        for (Skor myPoint : puanlar) {
            if (myPoint.getScore_type() == score_type) {
                return myPoint;
            }
        }
        return null;
    }

    public JButton getRivalButtonByGivenType(Scores score_type) {
        for (Skor rakip : rakipPuanlar) {
            if (rakip.getScore_type() == score_type) {
                return rakip.getButton();
            }
        }
        return null;
    }

    public void initScores() {

        // oyuncu skorları
        puanlar.add(new Skor(Scores.ONES, onesValue1));
        puanlar.add(new Skor(Scores.TWOS, twosValue1));
        puanlar.add(new Skor(Scores.THREES, threesValue1));
        puanlar.add(new Skor(Scores.FOURS, foursValue1));
        puanlar.add(new Skor(Scores.FIVES, fivesValue1));
        puanlar.add(new Skor(Scores.SIXES, sixesValue1));
        puanlar.add(new Skor(Scores.THREEKIND, threeKind1));
        puanlar.add(new Skor(Scores.FOURKIND, fourKind1));
        puanlar.add(new Skor(Scores.FULLHOUSE, full1));
        puanlar.add(new Skor(Scores.SMALLSTR, smallS1));
        puanlar.add(new Skor(Scores.LARGESTR, largeS1));
        puanlar.add(new Skor(Scores.CHANCE, chance1));
        puanlar.add(new Skor(Scores.YAHTZEE, yahtzee1));

        // rakibin skorları
        rakipPuanlar.add(new Skor(Scores.ONES, onesValue2));
        rakipPuanlar.add(new Skor(Scores.TWOS, twosValue2));
        rakipPuanlar.add(new Skor(Scores.THREES, threesValue2));
        rakipPuanlar.add(new Skor(Scores.FOURS, foursValue2));
        rakipPuanlar.add(new Skor(Scores.FIVES, fivesValue2));
        rakipPuanlar.add(new Skor(Scores.SIXES, sixesValue2));
        rakipPuanlar.add(new Skor(Scores.THREEKIND, threeKind2));
        rakipPuanlar.add(new Skor(Scores.FOURKIND, fourKind2));
        rakipPuanlar.add(new Skor(Scores.FULLHOUSE, full2));
        rakipPuanlar.add(new Skor(Scores.SMALLSTR, smallS2));
        rakipPuanlar.add(new Skor(Scores.LARGESTR, largeS2));
        rakipPuanlar.add(new Skor(Scores.CHANCE, chance2));
        rakipPuanlar.add(new Skor(Scores.YAHTZEE, yahtzee2));

        activeButtons.add(h1);
        activeButtons.add(h2);
        activeButtons.add(h3);
        activeButtons.add(h4);
        activeButtons.add(h5);

        addEventListenerToButtons();
    }

    public void changeTurn(boolean control) {
        roll.setEnabled(control);

        for (JToggleButton activeButton : activeButtons) {
            activeButton.setEnabled(control);
        }

        for (Zar zar : zarlar) {
            zar.getLabel().setEnabled(control);
            if (zar.getLabel().isEnabled()) {
                zar.roll();
            }
        }

        for (Skor myPoint : puanlar) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setEnabled(control);
            }
        }
    }

    public void disableRivalButtons(boolean control) {
        onesValue2.setEnabled(control);
        twosValue2.setEnabled(control);
        threesValue2.setEnabled(control);
        foursValue2.setEnabled(control);
        fivesValue2.setEnabled(control);
        sixesValue2.setEnabled(control);
        threeKind2.setEnabled(control);
        fourKind2.setEnabled(control);
        full2.setEnabled(control);
        smallS2.setEnabled(control);
        largeS2.setEnabled(control);
        chance2.setEnabled(control);
        yahtzee2.setEnabled(control);
    }

    public void addEventListenerToButtons() {
        for (Skor myPoint : puanlar) {
            myPoint.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (roundControl == 1) {
                        SkorMesajı score = new SkorMesajı(myPoint.getScore_type());
                        try {
                            score.content = Integer.parseInt(myPoint.getButton().getText());
                        } catch (Exception e) {
                            myPoint.getButton().setText("0");
                            score.content = 0;
                        }
                        roundControl = 0;
                        myPoint.isButtonChoosen = true;
                        Mesaj msg = new Mesaj(Mesaj.Message_Type.CHANGE);
                        msg.content = score;
                        System.out.println("msg.content = " + msg.content);
                        Client.Send(msg);
                        System.out.println("Mesaj Yollandı!");
                        bonusANDsumControl();
                        disableButtons();
                    }
                }
            });
        }
    }

    public void disableButtons() {
        for (Skor myPoint : puanlar) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setText("-");
            }
            myPoint.getButton().setEnabled(false);
        }
        roll.setEnabled(false);
        for (JToggleButton activeButton : activeButtons) {
            activeButton.setEnabled(false);
        }
    }

    public void bonusANDsumControl() {
        int bonusResult = 0;
        int sumResult = 0;
        int total = 0;
        if (getMyButtonByGivenType(Scores.ONES).isButtonChoosen
                && getMyButtonByGivenType(Scores.TWOS).isButtonChoosen
                && getMyButtonByGivenType(Scores.THREES).isButtonChoosen
                && getMyButtonByGivenType(Scores.FOURS).isButtonChoosen
                && getMyButtonByGivenType(Scores.FIVES).isButtonChoosen
                && getMyButtonByGivenType(Scores.SIXES).isButtonChoosen) {

            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.ONES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.TWOS).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.THREES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.FOURS).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.FIVES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.SIXES).getButton().getText());

            if (bonusResult >= 63) {
                sumResult = bonusResult + 35;
                bonusPlayer01.setText("35");
                sumPlayer01.setText(String.valueOf(sumResult));
            } else {
                sumResult = bonusResult;
                bonusPlayer01.setText("0");
                sumPlayer01.setText(String.valueOf(sumResult));
            }
            
            if (getMyButtonByGivenType(Scores.THREEKIND).isButtonChoosen
                    && getMyButtonByGivenType(Scores.FOURKIND).isButtonChoosen
                    && getMyButtonByGivenType(Scores.FULLHOUSE).isButtonChoosen
                    && getMyButtonByGivenType(Scores.SMALLSTR).isButtonChoosen
                    && getMyButtonByGivenType(Scores.LARGESTR).isButtonChoosen
                    && getMyButtonByGivenType(Scores.CHANCE).isButtonChoosen
                    && getMyButtonByGivenType(Scores.YAHTZEE).isButtonChoosen) {

                total += Integer.parseInt(getMyButtonByGivenType(Scores.THREEKIND).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.FOURKIND).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.FULLHOUSE).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.SMALLSTR).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.LARGESTR).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.CHANCE).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.YAHTZEE).getButton().getText());

                total += sumResult;
                totalPlayer1.setText(String.valueOf(total));
                Mesaj finishMsg = new Mesaj(Mesaj.Message_Type.FINISH);
                finishMsg.content = totalPlayer1.getText();
                Client.Send(finishMsg);
                
                if (finish) {
                    int player2Total = Integer.parseInt(totalPlayer2.getText());
                    String finishStr = "";
                    
                    if(total > player2Total){
                        finishStr = "Kazandın! Skor: "+total;
                        JOptionPane.showMessageDialog(this,finishStr);
                    }else if(player2Total > total){
                        finishStr = "Kaybettin! Rakibin Skoru: "+player2Total;
                        JOptionPane.showMessageDialog(this,finishStr);
                    }
                }

            }
        }
    }

    public int rivalScores(){
        int score = 0;
        for (Skor rakip : rakipPuanlar) {
            score += Integer.parseInt(rakip.getButton().getText());
        }
        score += Integer.parseInt(sumPlayer02.getText());
        return score;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        player1 = new javax.swing.JLabel();
        player2 = new javax.swing.JLabel();
        ones = new javax.swing.JLabel();
        twos = new javax.swing.JLabel();
        threes = new javax.swing.JLabel();
        fours = new javax.swing.JLabel();
        fives = new javax.swing.JLabel();
        Sixes = new javax.swing.JLabel();
        onesValue1 = new javax.swing.JButton();
        onesValue2 = new javax.swing.JButton();
        foursValue2 = new javax.swing.JButton();
        twosValue1 = new javax.swing.JButton();
        twosValue2 = new javax.swing.JButton();
        threesValue1 = new javax.swing.JButton();
        threesValue2 = new javax.swing.JButton();
        fivesValue1 = new javax.swing.JButton();
        fivesValue2 = new javax.swing.JButton();
        sixesValue2 = new javax.swing.JButton();
        bonus = new javax.swing.JLabel();
        threeKind = new javax.swing.JLabel();
        sum = new javax.swing.JLabel();
        threeKind1 = new javax.swing.JButton();
        sixesValue1 = new javax.swing.JButton();
        threeKind2 = new javax.swing.JButton();
        bonusPlayer2 = new javax.swing.JLabel();
        totalPlayer2 = new javax.swing.JLabel();
        sumPlayer02 = new javax.swing.JLabel();
        sumPlayer01 = new javax.swing.JLabel();
        fourKind2 = new javax.swing.JButton();
        fourKind1 = new javax.swing.JButton();
        fourKind = new javax.swing.JLabel();
        largeS = new javax.swing.JLabel();
        full2 = new javax.swing.JButton();
        full1 = new javax.swing.JButton();
        smallS1 = new javax.swing.JButton();
        chance1 = new javax.swing.JButton();
        smallS2 = new javax.swing.JButton();
        largeS2 = new javax.swing.JButton();
        fullH = new javax.swing.JLabel();
        smallS = new javax.swing.JLabel();
        chance2 = new javax.swing.JButton();
        largeS1 = new javax.swing.JButton();
        yahtzee2 = new javax.swing.JButton();
        yahtzee1 = new javax.swing.JButton();
        large = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        bonusPlayer01 = new javax.swing.JLabel();
        totalPlayer1 = new javax.swing.JLabel();
        largeS3 = new javax.swing.JLabel();
        dice01lbl = new javax.swing.JLabel();
        dice02lbl = new javax.swing.JLabel();
        dice03lbl = new javax.swing.JLabel();
        dice04lbl = new javax.swing.JLabel();
        dice05lbl = new javax.swing.JLabel();
        foursValue1 = new javax.swing.JButton();
        roll = new javax.swing.JButton();
        h5 = new javax.swing.JToggleButton();
        h1 = new javax.swing.JToggleButton();
        h2 = new javax.swing.JToggleButton();
        h3 = new javax.swing.JToggleButton();
        h4 = new javax.swing.JToggleButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Y A H T Z E E");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(865, 830));
        setMinimumSize(new java.awt.Dimension(865, 830));
        setPreferredSize(new java.awt.Dimension(865, 830));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        player1.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        player1.setText("Player 1");
        getContentPane().add(player1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        player2.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        player2.setText("Player 2");
        getContentPane().add(player2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, -1, -1));

        ones.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        ones.setText("Ones");
        getContentPane().add(ones, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 110, 30));

        twos.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        twos.setText("Twos");
        getContentPane().add(twos, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 110, 32));

        threes.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        threes.setText("Threes");
        getContentPane().add(threes, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 110, 32));

        fours.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        fours.setText("Fours");
        getContentPane().add(fours, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 110, 32));

        fives.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        fives.setText("Fives");
        getContentPane().add(fives, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 240, 110, 32));

        Sixes.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        Sixes.setText("Sixes");
        getContentPane().add(Sixes, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, 110, 32));

        onesValue1.setText("-");
        onesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(onesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 94, 33));

        onesValue2.setText("-");
        onesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(onesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 94, 33));

        foursValue2.setText("-");
        foursValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foursValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(foursValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 94, 33));

        twosValue1.setText("-");
        twosValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twosValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(twosValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 94, 33));

        twosValue2.setText("-");
        twosValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twosValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(twosValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 94, 33));

        threesValue1.setText("-");
        threesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(threesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 94, 33));

        threesValue2.setText("-");
        threesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(threesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 94, 33));

        fivesValue1.setText("-");
        fivesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fivesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(fivesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 94, 33));

        fivesValue2.setText("-");
        fivesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fivesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(fivesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 240, 94, 33));

        sixesValue2.setText("-");
        sixesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(sixesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 290, 94, 35));

        bonus.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        bonus.setText("Bonus");
        getContentPane().add(bonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 340, 110, 32));

        threeKind.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        threeKind.setText("Three of a kind");
        getContentPane().add(threeKind, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 110, 32));

        sum.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        sum.setText("Sum");
        getContentPane().add(sum, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 110, 32));

        threeKind1.setText("-");
        getContentPane().add(threeKind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 94, 33));

        sixesValue1.setText("-");
        sixesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(sixesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 94, 33));

        threeKind2.setText("-");
        getContentPane().add(threeKind2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 420, 94, 33));

        bonusPlayer2.setText("             -");
        getContentPane().add(bonusPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 340, 94, 33));

        totalPlayer2.setText("               -");
        getContentPane().add(totalPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 770, 94, 33));

        sumPlayer02.setText("              -");
        getContentPane().add(sumPlayer02, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 380, 93, 33));

        sumPlayer01.setText("               -");
        getContentPane().add(sumPlayer01, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, 94, 33));

        fourKind2.setText("-");
        getContentPane().add(fourKind2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 470, 94, 33));

        fourKind1.setText("-");
        getContentPane().add(fourKind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 94, 33));

        fourKind.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        fourKind.setText("Four of a kind");
        getContentPane().add(fourKind, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, 110, 32));

        largeS.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        largeS.setText("Chance");
        getContentPane().add(largeS, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 670, 110, 32));

        full2.setText("-");
        getContentPane().add(full2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 520, 94, 33));

        full1.setText("-");
        getContentPane().add(full1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, 94, 33));

        smallS1.setText("-");
        smallS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smallS1ActionPerformed(evt);
            }
        });
        getContentPane().add(smallS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 570, 94, 33));

        chance1.setText("-");
        getContentPane().add(chance1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 670, 94, 33));

        smallS2.setText("-");
        getContentPane().add(smallS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 570, 94, 33));

        largeS2.setText("-");
        getContentPane().add(largeS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 620, 94, 33));

        fullH.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        fullH.setText("Full House");
        getContentPane().add(fullH, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 520, 110, 32));

        smallS.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        smallS.setText("Small straight");
        getContentPane().add(smallS, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 570, 110, 32));

        chance2.setText("-");
        getContentPane().add(chance2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 670, 94, 33));

        largeS1.setText("-");
        getContentPane().add(largeS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 94, 33));

        yahtzee2.setText("-");
        getContentPane().add(yahtzee2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 720, 94, 33));

        yahtzee1.setText("-");
        getContentPane().add(yahtzee1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 720, 94, 33));

        large.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        large.setText("Large straight");
        getContentPane().add(large, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 620, 110, 32));

        total.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        total.setText("TOTAL SCORE");
        getContentPane().add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 770, 110, 32));

        bonusPlayer01.setText("               -");
        getContentPane().add(bonusPlayer01, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 340, 94, 33));

        totalPlayer1.setText("               -");
        getContentPane().add(totalPlayer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 770, 94, 33));

        largeS3.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        largeS3.setText("YAHTZEE");
        getContentPane().add(largeS3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 720, 110, 32));

        dice01lbl.setText("jLabel1");
        getContentPane().add(dice01lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 110, 110));

        dice02lbl.setText("jLabel1");
        getContentPane().add(dice02lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 670, 110, 110));

        dice03lbl.setText("jLabel1");
        getContentPane().add(dice03lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 110, 110));

        dice04lbl.setText("jLabel1");
        getContentPane().add(dice04lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, 110, 110));

        dice05lbl.setText("jLabel1");
        getContentPane().add(dice05lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 110, 110));

        foursValue1.setText("-");
        foursValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foursValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(foursValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, 94, 33));

        roll.setBackground(new java.awt.Color(255, 255, 255));
        roll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resimler/zar.png"))); // NOI18N
        roll.setText("ROLL DICES");
        roll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollActionPerformed(evt);
            }
        });
        getContentPane().add(roll, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 710, 180, 70));

        h5.setBackground(new java.awt.Color(255, 255, 255));
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });
        getContentPane().add(h5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 40, 110));

        h1.setBackground(new java.awt.Color(255, 255, 255));
        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });
        getContentPane().add(h1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 40, 110));

        h2.setBackground(new java.awt.Color(255, 255, 255));
        h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2ActionPerformed(evt);
            }
        });
        getContentPane().add(h2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, 40, 110));

        h3.setBackground(new java.awt.Color(255, 255, 255));
        h3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3ActionPerformed(evt);
            }
        });
        getContentPane().add(h3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 40, 110));

        h4.setBackground(new java.awt.Color(255, 255, 255));
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        getContentPane().add(h4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 40, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onesValue1ActionPerformed


    }//GEN-LAST:event_onesValue1ActionPerformed

    private void onesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onesValue2ActionPerformed

    }//GEN-LAST:event_onesValue2ActionPerformed

    private void twosValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twosValue1ActionPerformed

    }//GEN-LAST:event_twosValue1ActionPerformed

    private void twosValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twosValue2ActionPerformed

    }//GEN-LAST:event_twosValue2ActionPerformed

    private void threesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threesValue1ActionPerformed

    }//GEN-LAST:event_threesValue1ActionPerformed

    private void threesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threesValue2ActionPerformed

    }//GEN-LAST:event_threesValue2ActionPerformed

    private void foursValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foursValue1ActionPerformed

    }//GEN-LAST:event_foursValue1ActionPerformed

    private void foursValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foursValue2ActionPerformed

    }//GEN-LAST:event_foursValue2ActionPerformed

    private void fivesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fivesValue1ActionPerformed

    }//GEN-LAST:event_fivesValue1ActionPerformed

    private void fivesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fivesValue2ActionPerformed

    }//GEN-LAST:event_fivesValue2ActionPerformed

    private void sixesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixesValue1ActionPerformed

    }//GEN-LAST:event_sixesValue1ActionPerformed

    private void sixesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixesValue2ActionPerformed

    }//GEN-LAST:event_sixesValue2ActionPerformed

    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h1ActionPerformed
        if (h1.isSelected()) {
            dice01lbl.setEnabled(false);
        } else {
            dice01lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h1ActionPerformed

    private void h2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h2ActionPerformed
        if (h2.isSelected()) {
            dice02lbl.setEnabled(false);
        } else {
            dice02lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h2ActionPerformed

    private void h3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3ActionPerformed
        if (h3.isSelected()) {
            dice03lbl.setEnabled(false);
        } else {
            dice03lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h3ActionPerformed

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h4ActionPerformed
        if (h4.isSelected()) {
            dice04lbl.setEnabled(false);
        } else {
            dice04lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h4ActionPerformed

    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h5ActionPerformed
        if (h5.isSelected()) {
            dice05lbl.setEnabled(false);
        } else {
            dice05lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h5ActionPerformed

    private void rollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rollActionPerformed
        rollCount++;
        
        if (rollCount == 3) {
            roll.setEnabled(false);
        }

        for (Zar z : zarlar) {
            if (z.getLabel().isEnabled()) {
                z.roll();
            }
        }

        for (Skor myPoint : puanlar) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setText(String.valueOf(ZarAlgo.CALCULATE(zarlar, myPoint.getScore_type())));
            }
        }

        revalidate();
    }//GEN-LAST:event_rollActionPerformed

    private void smallS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smallS1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_smallS1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OyunEkrani gs = new OyunEkrani();
                gs.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Sixes;
    private javax.swing.JLabel bonus;
    private javax.swing.JLabel bonusPlayer01;
    private javax.swing.JLabel bonusPlayer2;
    private javax.swing.JButton chance1;
    private javax.swing.JButton chance2;
    private javax.swing.JLabel dice01lbl;
    private javax.swing.JLabel dice02lbl;
    private javax.swing.JLabel dice03lbl;
    private javax.swing.JLabel dice04lbl;
    private javax.swing.JLabel dice05lbl;
    private javax.swing.JLabel fives;
    private javax.swing.JButton fivesValue1;
    private javax.swing.JButton fivesValue2;
    private javax.swing.JLabel fourKind;
    private javax.swing.JButton fourKind1;
    private javax.swing.JButton fourKind2;
    private javax.swing.JLabel fours;
    private javax.swing.JButton foursValue1;
    private javax.swing.JButton foursValue2;
    private javax.swing.JButton full1;
    private javax.swing.JButton full2;
    private javax.swing.JLabel fullH;
    private javax.swing.JToggleButton h1;
    private javax.swing.JToggleButton h2;
    private javax.swing.JToggleButton h3;
    private javax.swing.JToggleButton h4;
    private javax.swing.JToggleButton h5;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel large;
    private javax.swing.JLabel largeS;
    private javax.swing.JButton largeS1;
    private javax.swing.JButton largeS2;
    private javax.swing.JLabel largeS3;
    private javax.swing.JLabel ones;
    private javax.swing.JButton onesValue1;
    private javax.swing.JButton onesValue2;
    public javax.swing.JLabel player1;
    public javax.swing.JLabel player2;
    private javax.swing.JButton roll;
    private javax.swing.JButton sixesValue1;
    private javax.swing.JButton sixesValue2;
    private javax.swing.JLabel smallS;
    private javax.swing.JButton smallS1;
    private javax.swing.JButton smallS2;
    private javax.swing.JLabel sum;
    private javax.swing.JLabel sumPlayer01;
    private javax.swing.JLabel sumPlayer02;
    private javax.swing.JLabel threeKind;
    private javax.swing.JButton threeKind1;
    private javax.swing.JButton threeKind2;
    private javax.swing.JLabel threes;
    private javax.swing.JButton threesValue1;
    private javax.swing.JButton threesValue2;
    private javax.swing.JLabel total;
    public javax.swing.JLabel totalPlayer1;
    public javax.swing.JLabel totalPlayer2;
    private javax.swing.JLabel twos;
    private javax.swing.JButton twosValue1;
    private javax.swing.JButton twosValue2;
    private javax.swing.JButton yahtzee1;
    private javax.swing.JButton yahtzee2;
    // End of variables declaration//GEN-END:variables
}
