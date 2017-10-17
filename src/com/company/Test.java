package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) throws IOException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();


        GameOver gameOver = new GameOver();


        gameOver.endGame(4999,terminal);

    }
}
