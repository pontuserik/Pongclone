package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

import static com.googlecode.lanterna.input.Key.Kind.Enter;

public class StartGame {

    public int startGameApp(Terminal terminal) throws IOException, InterruptedException, MidiUnavailableException, InvalidMidiDataException {

        Sequencer sequencer = startMusic();

        doThread threading = new doThread(sequencer);

        threading.start();

        showBanner(terminal);

        return menu(terminal, sequencer, threading);


    }




    private int menu(Terminal terminal, Sequencer sequencer, doThread threading) throws InterruptedException {
        int position = 1;
        int y = 20;
        int  players = 1;
        terminal.moveCursor(18,20);
        terminal.putCharacter('*');
        while(true){
            Key key;
            do {
                Thread.sleep(5);
                key = terminal.readInput();
            }
            while (key == null);
            System.out.println(key.getCharacter() + " " + key.getKind());


            switch (key.getKind()) {
                case ArrowDown:
                    if(y<24) {
                        terminal.moveCursor(18, y);
                        terminal.putCharacter(' ');
                        y += 2;
                        terminal.moveCursor(18, y);
                        terminal.putCharacter('*');
                        position++;
                    }
                        break;

                case ArrowUp:
                    if(y>20) {
                        terminal.moveCursor(18, y);
                        terminal.putCharacter(' ');
                        y -= 2;
                        terminal.moveCursor(18, y);
                        terminal.putCharacter('*');
                        position--;
                    }
                        break;

                case Enter:
                    if(position == 1) {
                        threading.halt(sequencer);
                        System.out.println("One Player");
                        players = 1;
                        break;
                    }

                    if(position == 2) {
                        threading.halt(sequencer);
                        System.out.println("Two Players");
                        players = 2;
                        break;
                    }


                    if (position == 3) {
                        System.exit(0);
                        threading.halt(sequencer);
                        break;
                    }
                    break;
                default:
                    threading.halt(sequencer);
                    break;


            }
            if(key.getKind() == Enter) {
                break;
            }
        }
        return players;

    }

    private void showBanner(Terminal terminal) throws FileNotFoundException {
        int q = 3;
        String banner = "intro.txt";
        File fileBanner = new File(banner);
        Scanner printBanner = new Scanner(fileBanner);
        while(printBanner.hasNextLine()) {
            String tempLine = printBanner.nextLine();
            terminal.moveCursor(12,q++);
            for (int i = 0; i< tempLine.length();i++) {
                terminal.putCharacter(tempLine.charAt(i));
            }
        }
        terminal.setCursorVisible(false);

        String onePlayer = "One Player";
        String twoPlayer = "Two Players";
        String quitGame = "Quit Game";

        terminal.moveCursor(20,20);
        for (int i = 0; i< onePlayer.length();i++) {
            terminal.putCharacter(onePlayer.charAt(i));
        }

        terminal.moveCursor(20,22);
        for (int i = 0; i< twoPlayer.length();i++) {
            terminal.putCharacter(twoPlayer.charAt(i));
        }


        terminal.moveCursor(20,24);
        for (int i = 0; i< quitGame.length();i++) {
            terminal.putCharacter(quitGame.charAt(i));
        }

        terminal.moveCursor(18,20);
        terminal.putCharacter('*');
    }

    private Sequencer startMusic() throws MidiUnavailableException, IOException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();

        sequencer.open();
        String path3 = "tetris.mid";
        InputStream tetris = new BufferedInputStream(new FileInputStream(new File(path3)));

        sequencer.setSequence(tetris);
        return sequencer;
    }
}
