package com.company;

import java.io.BufferedReader;
import java.io.FileReader;


public class Main {

    public static void main(String[] args) throws Exception{
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(
                "jackfruit_history_202209051511.csv"));
        String line = reader.readLine();
        while (line != null) {
            process(line);
        }
        reader.close();
    }

    private static void process(String line) {

    }
}
