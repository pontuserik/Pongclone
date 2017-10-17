package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException, InvalidMidiDataException, MidiUnavailableException {

        StartGame gm = new StartGame();
        gm.startGameApp();

    }
}
