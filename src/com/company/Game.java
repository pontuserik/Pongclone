package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Game {
    List<GameObject> gameObjects = new ArrayList<>();

    public void getKeyPress(Terminal terminal) throws InterruptedException {
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

    public void doGame()throws InterruptedException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();
        getObjectsList(gameObjects);
        drawScreen(terminal);
        getKeyPress(terminal);
        terminal.exitPrivateMode();
    }

    public void getObjectsList(List<GameObject> gameObjects) {
        gameObjects.add(new Ball(new Position(20, 20)));
        gameObjects.add(new Paddle(new Position(2, 20)));
        gameObjects.add(new Paddle(new Position(42, 20)));
    }

    public void drawScreen(Terminal terminal){

        for (GameObject gameObject : gameObjects) {
            drawObject(gameObject, terminal);
        }

    }

    public void drawObject(GameObject gameObject, Terminal terminal) {

       switch(gameObject.getGameObjectType()) {
           case "Ball":
               drawBall(terminal, gameObject);
               break;
           case "Paddle":
               drawPaddle(terminal, gameObject);
               break;
       }

    }

    public void drawBall(Terminal terminal, GameObject gameObject){
        Position currentPosition = gameObject.getCurrentPosition();
        terminal.moveCursor(currentPosition.x, currentPosition.y);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(0,0);
    }

    public void drawPaddle(Terminal terminal, GameObject gameObject){
        Position currentPosition = gameObject.getCurrentPosition();
        terminal.moveCursor(currentPosition.x, currentPosition.y - 2);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(currentPosition.x, currentPosition.y - 1);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(currentPosition.x, currentPosition.y);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(currentPosition.x, currentPosition.y + 1);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(currentPosition.x, currentPosition.y + 2);
        terminal.putCharacter(gameObject.getRepresentation());
        terminal.moveCursor(0,0);
    }
}