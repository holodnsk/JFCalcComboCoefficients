package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import albertahandevaluator.AlbertaHand;


public class Main {

    static Hand hand;

    public static void main(String[] args) throws Exception{
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(
                "jackfruit_history_202209051511.csv"));
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
                // todo calc
                hand = new Hand();
            }

            if (line.contains("Seat 1: ")) {
                hand.fillHeroName(line);
            }

            if (line.contains("Seat 2: ")) {
                hand.fillOpponentName(line);
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

    public void fillHeroName(String line) {
        heroName = getPlayerName(line);
    }
    public void fillOpponentName(String line) {
        opponentName = getPlayerName(line);
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
}