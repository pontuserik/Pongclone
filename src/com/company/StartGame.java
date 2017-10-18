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

public class StartGame {

    public void startGameApp(Terminal terminal) throws IOException, InterruptedException, MidiUnavailableException, InvalidMidiDataException {

        Sequencer sequencer = startMusic();

        doThread threading = new doThread(sequencer);

        threading.start();

        showBanner(terminal);

        menu(terminal, sequencer, threading);



    }



    private void menu(Terminal terminal, Sequencer sequencer, doThread threading) throws InterruptedException {
        int position = 1;
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
                    terminal.moveCursor(18,20);
                    terminal.putCharacter(' ');
                    terminal.moveCursor(18,22);
                    terminal.putCharacter('*');
                    position = 2;
                    break;
                case ArrowUp:
                    terminal.moveCursor(18,20);
                    terminal.putCharacter('*');
                    terminal.moveCursor(18,22);
                    terminal.putCharacter(' ');;
                    position = 1;
                    break;
                case Enter:
                    if(position == 1) {
                        threading.halt(sequencer);
                        break;
                    }
                    if (position == 2) {
                        System.exit(0);
                        threading.halt(sequencer);
                    }
                default:
                    threading.halt(sequencer);
                    break;
            }
            break;
        }
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

        String startGame = "Start Game";
        String quitGame = "Quit Game";

        terminal.moveCursor(20,20);
        for (int i = 0; i< startGame.length();i++) {
            terminal.putCharacter(startGame.charAt(i));
        }

        terminal.moveCursor(20,22);
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
