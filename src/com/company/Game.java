package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;

public class Game {

    public void doGame()throws InterruptedException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();


     while(true){
//Wait for a key to be pressed
        Key key;
        do {
            Thread.sleep(5);
            key = terminal.readInput();
        }
        while (key == null);
        System.out.println(key.getCharacter() + " " + key.getKind());


        switch (key.getKind()) {
            case ArrowDown:
                break;
            case ArrowUp:
                break;
            case ArrowLeft:
                break;
            case ArrowRight:
                break;
        }
        }
    }
}