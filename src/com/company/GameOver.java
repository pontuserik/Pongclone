package com.company;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.*;

public class GameOver {

    private List<PlayerScore> highScore = new ArrayList<>();



    public void endGame(int points,String name, Terminal terminal)throws IOException {

        PlayerScore player = new PlayerScore(name, points);

        showBanner(terminal);
        printWriteFile(points,name);
        printHighscore(terminal);
    }

    private void printHighscore(Terminal terminal) {
        int y = 10;
        for (PlayerScore p : highScore) {
            String temp = p.score + " " + p.name;
            terminal.moveCursor(10, y++);
            for (int i = 0; i < temp.length(); i++) {
                terminal.putCharacter(temp.charAt(i));
            }
        }
    }

    private void showBanner(Terminal terminal) throws FileNotFoundException {
        terminal.clearScreen();
        terminal.setCursorVisible(false);
        String banner = "gameover.txt";
        File fileBanner = new File(banner);
        Scanner printBanner = new Scanner(fileBanner);

        int q = 1;
        while (printBanner.hasNextLine()) {
            String tempLine = printBanner.nextLine();
            terminal.moveCursor(12, q++);
            for (int i = 0; i < tempLine.length(); i++) {
                terminal.putCharacter(tempLine.charAt(i));
            }
        }
    }

    private void printWriteFile(int points, String name) throws IOException {
        String input = "highscore.txt";
        File f = new File((input));
        if (f.exists()) {
            Scanner sc = new Scanner(f);
            int counter = 0;
            while (sc.hasNextInt()) {
                if (counter == 9) {
                    break;
                }
                int tempInt = sc.nextInt();
                String tempName = sc.next();
                highScore.add(new PlayerScore(tempName,tempInt));
                counter++;
            }
            highScore.add(new PlayerScore(name, points));


            Collections.sort(highScore, new Comparator<PlayerScore>() {
                @Override
                public int compare(PlayerScore o1, PlayerScore o2) {
                    return o1.score-o2.score;
                }
            });
           Collections.reverse(highScore);

        } else {
            f.createNewFile();
            highScore.add(new PlayerScore(name,points));
        }
        FileWriter writer = new FileWriter(input);
        BufferedWriter bw = new BufferedWriter(writer);


        for (PlayerScore player: highScore){
            bw.write(player.score + " " + player.name + "\n");
        }
        bw.flush();
        bw.close();
        writer.close();
    }
}


