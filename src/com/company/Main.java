package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import albertahandevaluator.AlbertaHand;
import albertahandevaluator.AlbertaHandEvaluator;


public class Main {

    static Hand hand;
    static Map<String, Integer> headLineCombosCount = new HashMap<>();
    static Map<String, Integer> headLineCombosWinCount = new HashMap<>();
    static Map<String, Integer> middleLineCombosCount = new HashMap<>();
    static Map<String, Integer> middleLineCombosWinCount = new HashMap<>();
    static Map<String, Integer> tailLineCombosCount = new HashMap<>();
    static Map<String, Integer> tailLineCombosWinCount = new HashMap<>();


    public static void main(String[] args) throws Exception {
        BufferedReader reader;


        reader = new BufferedReader(new FileReader(
                "jackfruit_history_202210101007.csv"));
        String line = reader.readLine();

        while (line != null) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            process(line);
        }
        double CountHIGH = 0;
        double WinHIGH = 0;
        double CountPAIR = 0;
        double WinPAIR = 0;
        double CountTWOPAIR = 0;
        double WinTWOPAIR = 0;
        double CountTHREEKIND = 0;
        double WinTHREEKIND = 0;
        double CountSTRAIGHT = 0;
        double WinSTRAIGHT = 0;
        double CountFLUSH = 0;
        double WinFLUSH = 0;
        double CountFULLHOUSE = 0;
        double WinFULLHOUSE = 0;
        double CountFOURKIND = 0;
        double WinFOURKIND = 0;
        double CountSTRAIGHTFLUSH = 0;
        double WinSTRAIGHTFLUSH = 0;

        for (Map.Entry<String, Integer> entry : headLineCombosCount.entrySet()) {
            if (entry.getKey().contains(" High Straight Flush")) {
                CountSTRAIGHTFLUSH+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinSTRAIGHTFLUSH+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("Four of a Kind, ")) {
                CountFOURKIND+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinFOURKIND+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("a Full House, ")) {
                CountFULLHOUSE+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinFULLHOUSE+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("a Flush, ")) {
                CountFLUSH+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinFLUSH+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains(" High Straight")) {
                CountSTRAIGHT+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinSTRAIGHT+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("Three of a Kind, ")) {
                CountTHREEKIND+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinTHREEKIND+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("Two Pair, ")) {
                CountTWOPAIR+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinTWOPAIR+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains("a Pair of ")) {
                CountPAIR+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinPAIR+=headLineCombosWinCount.get(entry.getKey());
            }
            if (entry.getKey().contains(" High") && !entry.getKey().contains("Straight") && !entry.getKey().contains("a Flush")) {
                CountHIGH+=entry.getValue();
                if (headLineCombosWinCount.get(entry.getKey())!=null)
                    WinHIGH+=headLineCombosWinCount.get(entry.getKey());
            }

        }
        System.out.println("HIGH "+WinHIGH/(CountHIGH/100));
        System.out.println("PAIR "+WinPAIR/(CountPAIR/100));
        System.out.println("TWOPAIR "+WinTWOPAIR/(CountTWOPAIR/100));
        System.out.println("THREEKIND "+WinTHREEKIND/(CountTHREEKIND/100));
        System.out.println("STRAIGHT "+WinSTRAIGHT/(CountSTRAIGHT/100));
        System.out.println("FLUSH "+WinFLUSH/(CountFLUSH/100));
        System.out.println("FULLHOUSE "+WinFULLHOUSE/(CountFULLHOUSE/100));
        System.out.println("FOURKIND "+WinFOURKIND/(CountFOURKIND/100));
        System.out.println("STRAIGHTFLUSH "+WinSTRAIGHTFLUSH/(CountSTRAIGHTFLUSH/100));

        reader.close();
    }

    private static void process(String line) {
//        System.out.println(line);
        try {
            if (line.contains("PokerStars Hand ")) {
                if (hand != null) {
                    Map<String, Boolean> headLineResult = hand.calcHeadLineResult();
                    String headLineComboName = getComboName(headLineResult);
                    boolean isHeadLineWin = isHeroWinLinw(headLineResult);
                    fillTablesWithHighCards(headLineComboName, isHeadLineWin, headLineCombosCount, headLineCombosWinCount);

                    Map<String, Boolean> middleLineResult = hand.calcMiddleLineResult();
                    String middleLineComboName = getComboName(middleLineResult);
                    boolean isMiddleLineWin = isHeroWinLinw(middleLineResult);
                    fillTablesWithHighCards(middleLineComboName, isMiddleLineWin, middleLineCombosCount, middleLineCombosWinCount);

                    Map<String, Boolean> tailLineResult = hand.calcTailLineResult();
                    String headTailComboName = getComboName(tailLineResult);
                    boolean isTailLineWin = isHeroWinLinw(tailLineResult);
                    fillTablesWithHighCards(headTailComboName, isTailLineWin, tailLineCombosCount, tailLineCombosWinCount);

                }
                hand = new Hand();
            }

            if (line.contains("Seat 1: ")) {
                hand.fillSeat1Name(line);
            }

            if (line.contains("Seat 2: ")) {
                hand.fillSeat2Name(line);
            }

            if (line.contains("Dealt to ")) {
                hand.setHeroAndApponentNames(line);
            }

            if (line.contains(hand.heroName + ": shows head")) {
                hand.fillHeroHeadLine(line);
            }
            if (line.contains(hand.heroName + ": shows middle")) {
                hand.fillHeroMiddleLine(line);
            }
            if (line.contains(hand.heroName + ": shows tail")) {
                hand.fillHeroTailLine(line);
            }
            if (line.contains(hand.opponentName + ": shows head")) {
                hand.fillOpponentHeadLine(line);
            }
            if (line.contains(hand.opponentName + ": shows middle")) {
                hand.fillOpponentMiddleLine(line);
            }
            if (line.contains(hand.opponentName + ": shows tail")) {
                hand.fillOpponentTailLine(line);
            }
            if (line.contains("*** RIVER ***")) {
                hand.fillRiver(line);
            }
            if (line.contains("collected") && line.contains("from pot")) {
                hand.WinnerName(line);
                hand.WinAmmount(line);
            }
        } catch (Exception e) {
            System.out.println("exception line:" + line);
        }
    }

    private static void fillTablesWithOutCards(Map<String, Integer> lineCombosCount, Map<String, Integer> lineCombosWinCount) {

    }

    private static void fillTablesWithHighCards(String headLineComboName, boolean isHeadLineWin, Map<String, Integer> headLineCombosCount, Map<String, Integer> headLineCombosWinCount) {
        if (headLineCombosCount.containsKey(headLineComboName)) {
            int comboCount = headLineCombosCount.get(headLineComboName);
            headLineCombosCount.put(headLineComboName, comboCount + 1);
            if (isHeadLineWin) {
                if (headLineCombosWinCount.containsKey(headLineComboName)) {
                    int winCount = headLineCombosWinCount.get(headLineComboName);
                    headLineCombosWinCount.put(headLineComboName, winCount + 1);
                } else {
                    headLineCombosWinCount.put(headLineComboName, 1);
                }
            }
        } else {
            headLineCombosCount.put(headLineComboName, 1);
            if (isHeadLineWin) {
                headLineCombosWinCount.put(headLineComboName, 1);
            }
        }
    }

    private static boolean isHeroWinLinw(Map<String, Boolean> headLineResult) {
        for (Boolean value : headLineResult.values()) {
            return value;
        }
        return false;
    }

    private static String getComboName(Map<String, Boolean> headLineResult) {
        for (String comboname : headLineResult.keySet()) {
            return comboname;
        }
        return null;
    }


}

class Hand {
    String seat1Name;
    String seat2Name;
    String heroName;
    String heroHeadLine;
    String heroMiddleLine;
    String heroTailLine;
    String opponentName;
    String opponentHeadLine;
    String opponentMiddleLine;
    String opponentTailLine;
    String river;
    String winner;
    String winAmmount;

    public void fillSeat1Name(String line) {
        seat1Name = getPlayerName(line);
    }

    public void fillSeat2Name(String line) {
        seat2Name = getPlayerName(line);
    }

    public void fillHeroHeadLine(String line) {
        heroHeadLine = getPlayerShows(line);
    }

    public void fillHeroMiddleLine(String line) {
        heroMiddleLine = getPlayerShows(line);
    }

    public void fillHeroTailLine(String line) {
        heroTailLine = getPlayerShows(line);
    }

    public void fillOpponentHeadLine(String line) {
        opponentHeadLine = getPlayerShows(line);
    }

    public void fillOpponentMiddleLine(String line) {
        opponentMiddleLine = getPlayerShows(line);
    }

    public void fillOpponentTailLine(String line) {
        opponentTailLine = getPlayerShows(line);
    }

    private String getPlayerName(String line) {
        return line.substring(line.indexOf(": ") + 2, line.indexOf(" ("));
    }

    private String getPlayerShows(String line) {
        return line.substring(line.indexOf("[") + 1, line.indexOf("]"));
    }

    public void fillRiver(String line) {
        river = line.substring(line.indexOf("[") + 1, line.indexOf("]")) + line.substring(line.indexOf("] [")).replace("] [", " ").replace("]", "");
    }

    public void WinnerName(String line) {
        winner = line.split(" ")[0];
    }

    public void WinAmmount(String line) {
        winAmmount = line.split(" ")[2].replace("$", "").replace(".00", "");
    }

    public void setHeroAndApponentNames(String line) {
        heroName = line.split(" ")[2];
        if (seat1Name.equals(heroName))
            opponentName = seat2Name;

        if (seat2Name.equals(heroName))
            opponentName = seat1Name;

    }

    public Map<String, Boolean> calcHeadLineResult() {
        Map<String, Boolean> result = getLineResult(heroHeadLine, opponentHeadLine);
        return result;
    }

    public Map<String, Boolean> calcMiddleLineResult() {
        Map<String, Boolean> result = getLineResult(heroMiddleLine, opponentMiddleLine);
        return result;
    }

    public Map<String, Boolean> calcTailLineResult() {
        Map<String, Boolean> result = getLineResult(heroTailLine, opponentTailLine);
        return result;
    }

    private Map<String, Boolean> getLineResult(String heroHeadLine, String opponentHeadLine) {
        boolean heroWinHeadLine = isHeroWinLine(heroHeadLine, opponentHeadLine);
        String heroHeadLineComboName = getLineComboName(heroHeadLine);

        Map<String, Boolean> result = new HashMap<>();
        result.put(heroHeadLineComboName, heroWinHeadLine);
        return result;
    }

    private String getLineComboName(String line) {
        return AlbertaHandEvaluator.nameHand(new AlbertaHand(line + " " + river));
    }

    private boolean isHeroWinLine(String heroLine, String opponentLine) {
        AlbertaHand heroAlbertaHand = new AlbertaHand(heroLine + " " + river);
        int heroHeadHandPower = AlbertaHandEvaluator.rankHand(heroAlbertaHand);

        AlbertaHand opponentAlbertaHand = new AlbertaHand(opponentLine + " " + river);
        int opponentHeadHandPower = AlbertaHandEvaluator.rankHand(opponentAlbertaHand);

        return heroHeadHandPower > opponentHeadHandPower;
    }

    enum Combos {
        HIGH,
        PAIR,
        TWOPAIR,
        THREEKINF,
        STRAIGHT,
        FLUSH,
        FULLHOUSE,
        FOURKIND,
        STRAIGHTFLUSH;
    }

}