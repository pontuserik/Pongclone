package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException, InvalidMidiDataException, MidiUnavailableException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();

        StartGame sg = new StartGame();
        sg.startGameApp(terminal);


/*
        GameOver go = new GameOver();
        go.endGame(20000,"Jimmy",terminal);
*/

    }
}