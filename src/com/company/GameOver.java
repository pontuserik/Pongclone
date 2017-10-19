package com.company;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

import static com.googlecode.lanterna.input.Key.Kind.ArrowUp;
import static com.googlecode.lanterna.input.Key.Kind.Enter;

public class GameOver {

    private List<PlayerScore> highScore = new ArrayList<>();



    public void endGame(int points, Terminal terminal) throws IOException, InterruptedException, InvalidMidiDataException, MidiUnavailableException {
        Sequencer sequencer = startMusic();

        doThread threading = new doThread(sequencer);

        threading.start();
        String name = playerName(terminal);

        PlayerScore player = new PlayerScore(name, points);



        playerName(terminal);
        showBanner(terminal);
        printWriteFile(points,name);
        printHighscore(terminal);
    }


    private String playerName(Terminal terminal) throws InterruptedException {
        terminal.clearScreen();
        terminal.setCursorVisible(true);
        terminal.moveCursor(12,10);
        String writeName = "Vänligen skriv ditt namn:";
        for (int i = 0; i < writeName.length();i++) {
            terminal.putCharacter(writeName.charAt(i));
        }

        String name = "";
        String keyString = "";
        Key key;

        while(true) {
            key = terminal.readInput();
            if (key != null && key.getKind() != Enter) {
                keyString = key.toString();
                name = name + keyString.charAt(keyString.length() - 1);
                terminal.putCharacter(key.getCharacter());
            } else if (key != null && key.getKind() == Enter) {
                terminal.putCharacter((key.getCharacter()));
                break;
            }

        }
        return name;
    }
    private void printHighscore(Terminal terminal) {
        int y = 10;
        for (PlayerScore p : highScore) {
            String temp = p.name + " " + p.score;
            terminal.moveCursor(40, y++);
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

    private Sequencer startMusic() throws MidiUnavailableException, IOException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();

        sequencer.open();
        String path3 = "gameover.mid";
        InputStream gameover = new BufferedInputStream(new FileInputStream(new File(path3)));

        sequencer.setSequence(gameover);
        return sequencer;
    }
}


