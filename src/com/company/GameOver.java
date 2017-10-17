package com.company;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameOver {

    private List<Integer> highScore = new ArrayList<>();

    public void endGame(int points, Terminal terminal)throws IOException {

        String input = "highscore.txt";
        File f = new File((input));
        if (f.exists()) {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                highScore.add(sc.nextInt());
            }
            highScore.add(points);
            Collections.sort(highScore);
        }
        else{
            f.createNewFile();
            highScore.add(points);
        }


        // creates a FileWriter Object

        FileWriter writer = new FileWriter(input);





        for (Integer point: highScore) {
            writer.write(point);
            System.out.println(point);
        }
        // Writes the content to the file
        writer.flush();
        writer.close();

        terminal.clearScreen();
        int y = 10;


        for (Integer p:highScore) {
            String temp = p.toString();

            terminal.moveCursor(10,y++);
            for (int i = 0; i < temp.length(); i++) {
                terminal.putCharacter(temp.charAt(i));
            }

            }
        }


    }


