package com.company;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.*;

public class GameOver {

    private List<Integer> highScore = new ArrayList<>();

    public void endGame(int points, Terminal terminal)throws IOException {
        String banner = "gameover.txt";
        File fileBanner = new File(banner);
        Scanner printBanner = new Scanner(fileBanner);
        String input = "highscore.txt";
        File f = new File((input));
        if (f.exists()) {
            Scanner sc = new Scanner(f);
            int counter = 0;
            while (sc.hasNextInt()) {
                if(counter == 9) {
                    break;
                }
                int tempInt = sc.nextInt();
                highScore.add(tempInt);
                counter++;
            }
            highScore.add(points);
            Collections.sort(highScore);
            Collections.reverse(highScore);
        }
        else{
            f.createNewFile();
            highScore.add(points);
        }


        // creates a FileWriter Object

        FileWriter writer = new FileWriter(input);
        BufferedWriter bw = new BufferedWriter(writer);





        for (int i = 0; i < highScore.size() && i<10 ;i++) {
            bw.write(highScore.get(i).toString() + "\n");
            System.out.println(highScore.get(i));
        }
        // Writes the content to the file
        bw.flush();
        bw.close();
        writer.close();

        terminal.clearScreen();

        int q = 1;
        while(printBanner.hasNextLine()) {
            String tempLine = printBanner.nextLine();
            terminal.moveCursor(12,q++);
            for (int i = 0; i< tempLine.length();i++) {
                terminal.putCharacter(tempLine.charAt(i));
            }
        }

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


