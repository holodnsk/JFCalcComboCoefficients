package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import albertahandevaluator.AlbertaHand;
import albertahandevaluator.AlbertaHandEvaluator;


public class Main {

    static Hand hand;

    public static void main(String[] args) throws Exception{
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(
                "jackfruit_history_202210101007.csv"));
        String line = reader.readLine();

        while (line != null) {
            line  = reader.readLine();
            if (line == null) {
                break;
            }
            process(line);
        }
        reader.close();
    }

    private static void process(String line) {
        System.out.println(line);
        try {
            if (line.contains("PokerStars Hand ")) {
                if (hand != null) {
                    Map<String, Boolean> headLineResult = hand.calcHeadLineResult();
                    Map<String, Boolean> middleLineResult = hand.calcMiddleHeadLineResult();
                    Map<String, Boolean> tailLineResult = hand.calcTailLineResult();
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
            System.out.println("exception line:"+line);
        }
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
        return line.substring(line.indexOf(": ")+2,line.indexOf(" ("));
    }
    private String getPlayerShows(String line) {
        return line.substring(line.indexOf("[")+1,line.indexOf("]"));
    }

    public void fillRiver(String line) {
        river = line.substring(line.indexOf("[")+1,line.indexOf("]"))+line.substring(line.indexOf("] [")).replace("] ["," ").replace("]","");
    }

    public void WinnerName(String line) {
        winner = line.split(" ")[0];
    }

    public void WinAmmount(String line) {
        winAmmount = line.split(" ")[2].replace("$","").replace(".00","");
    }

    public void setHeroAndApponentNames(String line) {
        heroName = line.split(" ")[2];
        if (seat1Name.equals(heroName))
            opponentName=seat2Name;

        if (seat2Name.equals(heroName))
            opponentName=seat1Name;

    }

    public Map<String, Boolean> calcHeadLineResult() {
        Map<String, Boolean> result = getLineResult(heroHeadLine, opponentHeadLine);
        return result;
    }

    public Map<String, Boolean> calcMiddleHeadLineResult() {
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

        return heroHeadHandPower>opponentHeadHandPower;
    }


}