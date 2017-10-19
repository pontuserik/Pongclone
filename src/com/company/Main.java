package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.terminal.Terminal;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.nio.charset.Charset;
        public class Main {
            public static void main(String[] args)
                    throws InterruptedException, MidiUnavailableException, InvalidMidiDataException, IOException {

                Terminal terminal = TerminalFacade.createTerminal(System.in,
                        System.out, Charset.forName("UTF8"));
                terminal.enterPrivateMode();
                terminal.setCursorVisible(false);
                StartGame startGame = new StartGame();
                Game game = new Game();
                GameOver gameOver = new GameOver();

                int players = startGame.startGameApp(terminal);
                int player1Score = game.doGame(terminal, players);
                gameOver.endGame(player1Score, terminal);
                }
            }





