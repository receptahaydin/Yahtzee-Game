package Yahtzee;

import Yahtzee.SkorMesajı.Scores;

public class ZarAlgo {

    public static int CALCULATE(Zar dices[], Scores score_type) {
        int score = 0;
        if (score_type.equals(Scores.ONES)) {
            score = ones(dices);
        } else if (score_type.equals(Scores.TWOS)) {
            score = twos(dices);
        } else if (score_type.equals(Scores.THREES)) {
            score = threes(dices);
        } else if (score_type.equals(Scores.FOURS)) {
            score = fours(dices);
        } else if (score_type.equals(Scores.FIVES)) {
            score = fives(dices);
        } else if (score_type.equals(Scores.SIXES)) {
            score = sixes(dices);
        } else if (score_type.equals(Scores.THREEKIND)) {
            score = threeOfKind(dices);
        } else if (score_type.equals(Scores.FOURKIND)) {
            score = fourOfKind(dices);
        } else if (score_type.equals(Scores.FULLHOUSE)) {
            score = fullHouse(dices);
        } else if (score_type.equals(Scores.SMALLSTR)) {
            score = smallStraight(dices);
        } else if (score_type.equals(Scores.LARGESTR)) {
            score = largeStraight(dices);
        } else if (score_type.equals(Scores.CHANCE)) {
            score = chance(dices);
        }  else if (score_type.equals(Scores.YAHTZEE)) {
            score = yahtzee(dices);
        }
        return score;
    }

    public static int ones(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 1) {
                result += 1;
            }
        }
        return result;
    }

    public static int twos(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 2) {
                result += 2;
            }
        }
        return result;
    }

    public static int threes(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 3) {
                result += 3;
            }
        }
        return result;
    }

    public static int fours(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 4) {
                result += 4;
            }
        }
        return result;
    }

    public static int fives(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 5) {
                result += 5;
            }
        }
        return result;
    }

    public static int sixes(Zar dices[]) {
        int result = 0;
        for (Zar d : dices) {
            if (d.value == 6) {
                result += 6;
            }
        }
        return result;
    }

    public static int threeOfKind(Zar dices[]) {
        int[] sayiDegerleri = {0, 0, 0, 0, 0, 0};
        int sum = 0;
        for (Zar d : dices) {
            if (d.value == 1) {
                sayiDegerleri[0]++;
            }
            if (d.value == 2) {
                sayiDegerleri[1]++;
            }
            if (d.value == 3) {
                sayiDegerleri[2]++;
            }
            if (d.value == 4) {
                sayiDegerleri[3]++;
            }
            if (d.value == 5) {
                sayiDegerleri[4]++;
            }
            if (d.value == 6) {
                sayiDegerleri[5]++;
            }
        }
        for (int i = 0; i < sayiDegerleri.length; i++) {
            if (sayiDegerleri[i] == 3) {
                for (Zar d : dices) {
                    sum += d.value;
                }
                return sum;
            }
        }
        return 0;
    }

    public static int fourOfKind(Zar dices[]) {
        int[] sayiDegerleri = {0, 0, 0, 0, 0, 0};
        int sum = 0;
        for (Zar d : dices) {
            if (d.value == 1) {
                sayiDegerleri[0]++;
            }
            if (d.value == 2) {
                sayiDegerleri[1]++;
            }
            if (d.value == 3) {
                sayiDegerleri[2]++;
            }
            if (d.value == 4) {
                sayiDegerleri[3]++;
            }
            if (d.value == 5) {
                sayiDegerleri[4]++;
            }
            if (d.value == 6) {
                sayiDegerleri[5]++;
            }
        }
        for (int i = 0; i < sayiDegerleri.length; i++) {
            if (sayiDegerleri[i] == 4) {
                for (Zar d : dices) {
                    sum += d.value;
                }
                return sum;
            }
        }
        return 0;
    }

    public static int fullHouse(Zar[] dices) {
        int[] sayiDegerleri = {0, 0, 0, 0, 0, 0};
        for (Zar d : dices) {
            if (d.value == 1) {
                sayiDegerleri[0]++;
            }
            if (d.value == 2) {
                sayiDegerleri[1]++;
            }
            if (d.value == 3) {
                sayiDegerleri[2]++;
            }
            if (d.value == 4) {
                sayiDegerleri[3]++;
            }
            if (d.value == 5) {
                sayiDegerleri[4]++;
            }
            if (d.value == 6) {
                sayiDegerleri[5]++;
            }
        }

        if (sayiDegerleri[0] == 3) {
            if (sayiDegerleri[1] == 2 || sayiDegerleri[2] == 2 || sayiDegerleri[3] == 2 || sayiDegerleri[4] == 2 || sayiDegerleri[5] == 2) {
                return 25;
            }
        } else if (sayiDegerleri[1] == 3) {
            if (sayiDegerleri[0] == 2 || sayiDegerleri[2] == 2 || sayiDegerleri[3] == 2 || sayiDegerleri[4] == 2 || sayiDegerleri[5] == 2) {
                return 25;
            }
        }
        if (sayiDegerleri[2] == 3) {
            if (sayiDegerleri[0] == 2 || sayiDegerleri[1] == 2 || sayiDegerleri[3] == 2 || sayiDegerleri[4] == 2 || sayiDegerleri[5] == 2) {
                return 25;
            }
        }
        if (sayiDegerleri[3] == 3) {
            if (sayiDegerleri[0] == 2 || sayiDegerleri[1] == 2 || sayiDegerleri[2] == 2 || sayiDegerleri[4] == 2 || sayiDegerleri[5] == 2) {
                return 25;
            }
        }
        if (sayiDegerleri[4] == 3) {
            if (sayiDegerleri[0] == 2 || sayiDegerleri[1] == 2 || sayiDegerleri[2] == 2 || sayiDegerleri[3] == 2 || sayiDegerleri[5] == 2) {
                return 25;
            }
        }
        if (sayiDegerleri[5] == 3) {
            if (sayiDegerleri[0] == 2 || sayiDegerleri[1] == 2 || sayiDegerleri[2] == 2 || sayiDegerleri[3] == 2 || sayiDegerleri[4] == 2) {
                return 25;
            }
        }
        return 0;
    }

    public static int smallStraight(Zar[] dices) {
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (int i = 0; i < 5; i++) {
            if (dices[i].value == 1) {
                ones++;
            }
            if (dices[i].value == 2) {
                twos++;
            }
            if (dices[i].value == 3) {
                threes++;
            }
            if (dices[i].value == 4) {
                fours++;
            }
            if (dices[i].value == 5) {
                fives++;
            }
            if (dices[i].value == 6) {
                sixes++;
            }
        }
        if (ones == 1 && twos == 1 && threes == 1 && fours == 1)
            return 30;
        if ((twos == 1 && threes == 1 && fours == 1 && fives == 1)) 
            return 30;
        if ((threes == 1 && fours == 1 && fives == 1 && sixes == 1)) 
            return 30;
 
        return 0;
    }

    public static int largeStraight(Zar[] dices) {
        int[] sayiDegerleri = {0, 0, 0, 0, 0, 0};
        for (Zar d : dices) {
            if (d.value == 1) {
                sayiDegerleri[0]++;
            }
            if (d.value == 2) {
                sayiDegerleri[1]++;
            }
            if (d.value == 3) {
                sayiDegerleri[2]++;
            }
            if (d.value == 4) {
                sayiDegerleri[3]++;
            }
            if (d.value == 5) {
                sayiDegerleri[4]++;
            }
            if (d.value == 6) {
                sayiDegerleri[5]++;
            }
        }

        if (sayiDegerleri[0] == 1 && sayiDegerleri[1] == 1 && sayiDegerleri[2] == 1 && sayiDegerleri[3] == 1 && sayiDegerleri[4] == 1) {
            return 40;
        }
        if (sayiDegerleri[1] == 1 && sayiDegerleri[2] == 1 && sayiDegerleri[3] == 1 && sayiDegerleri[4] == 1 && sayiDegerleri[5] == 1) {
            return 30;
        }
        return 0;
    }

    public static int chance(Zar[] dice) {
        int sum = 0;
        for (Zar d : dice) {
            sum += d.value;
        }
        return sum;
    }

    public static int yahtzee(Zar dices[]) {
        int[] sayiDegerleri = {0, 0, 0, 0, 0, 0};
        for (Zar d : dices) {
            if (d.value == 1) {
                sayiDegerleri[0]++;
            }
            if (d.value == 2) {
                sayiDegerleri[1]++;
            }
            if (d.value == 3) {
                sayiDegerleri[2]++;
            }
            if (d.value == 4) {
                sayiDegerleri[3]++;
            }
            if (d.value == 5) {
                sayiDegerleri[4]++;
            }
            if (d.value == 6) {
                sayiDegerleri[5]++;
            }
        }

        if (sayiDegerleri[0] == 5 || sayiDegerleri[1] == 5 || sayiDegerleri[2] == 5 || sayiDegerleri[3] == 5 || sayiDegerleri[4] == 5 || sayiDegerleri[5] == 5) {
            return 50;
        }
        return 0;
    }

}
