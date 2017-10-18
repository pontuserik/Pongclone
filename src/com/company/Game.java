package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Game {


    List<GameObject> gameObjects = new ArrayList<>();

    public void getKeyPress(Terminal terminal, GameObject player1,
        GameObject opponent, GameObject ball) throws InterruptedException {
        while(true){
        //Wait for a key to be pressed
            Key key;
            do {
                Thread.sleep(100);
                moveBall(ball, player1, opponent);
                moveOpponent(ball, opponent);
                key = terminal.readInput();
                terminal.clearScreen();
                drawScreen(terminal);

            }
            while (key == null);
            System.out.println(key.getCharacter() + " " + key.getKind());


            switch (key.getKind()) {
                case ArrowDown:
                    moveObject(player1, new Move("PaddleDown"));
                    break;
                case ArrowUp:
                    moveObject(player1, new Move("PaddleUp"));
                    break;
            }
        }
    }

    public void doGame()throws InterruptedException {

        Terminal terminal = TerminalFacade.createTerminal(System.in,
                System.out, Charset.forName("UTF8"));
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
        getObjectsList(gameObjects);
        GameObject ball = gameObjects.get(0);
        GameObject player1 = gameObjects.get(1);
        GameObject opponent = gameObjects.get(2);

        int port = 5000;
        try {
            Thread t = new GreetingServer(port, opponent);
            t.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
        drawScreen(terminal);
        getKeyPress(terminal, player1, opponent, ball);
        terminal.exitPrivateMode();
    }

    public void getObjectsList(List<GameObject> gameObjects) {
        gameObjects.add(new Ball(new Position(50, 15)));
        gameObjects.add(new Paddle(new Position(2, 15)));
        gameObjects.add(new Paddle(new Position(98, 15)));
    }

    public void moveObject(GameObject gameObject, Move move) {

        Position currentPosition = gameObject.getCurrentPosition();

        switch (move.move) {
            case "PaddleDown":
                currentPosition = new Position(currentPosition.x, currentPosition.y + 1);
                gameObject.setCurrentPosition(currentPosition);
                break;
            case "PaddleUp":
                currentPosition = new Position(currentPosition.x, currentPosition.y - 1);
                gameObject.setCurrentPosition(currentPosition);
                break;
            case "OpponentPaddleUp":
                currentPosition = new Position(currentPosition.x, currentPosition.y - 1);
                gameObject.setCurrentPosition(currentPosition);
                break;
            case "OpponentPaddleDown":
                currentPosition = new Position(currentPosition.x, currentPosition.y + 1);
                gameObject.setCurrentPosition(currentPosition);
                break;
            case "PlayerBallMaxUp":
                // x + 1
                // y - 2
                gameObject.setCurrentPosition(new Position(currentPosition.x + 2, currentPosition.y - 2));
                break;
            case "PlayerBallMidUp":
                // x + 1
                // y + 1
                gameObject.setCurrentPosition(new Position(currentPosition.x + 2, currentPosition.y - 1));
                break;
            case "PlayerBallCenter":
                // x + 1
                gameObject.setCurrentPosition(new Position(currentPosition.x + 2, currentPosition.y));
                break;
            case "PlayerBallMidDown":
                // x + 1
                // y + 1
                gameObject.setCurrentPosition(new Position(currentPosition.x + 2, currentPosition.y + 1));
                break;
            case "PlayerBallMaxDown":
                // x + 1
                // y + 2
                gameObject.setCurrentPosition(new Position(currentPosition.x + 2, currentPosition.y + 2));
                break;
            case "OpponentBallMaxUp":
                // x - 1
                // y - 2
                gameObject.setCurrentPosition(new Position(currentPosition.x - 2, currentPosition.y - 2));
                break;
            case "OpponentBallMidUp":
                // x - 1
                // y + 1
                gameObject.setCurrentPosition(new Position(currentPosition.x - 2, currentPosition.y - 1));
                break;
            case "OpponentBallCenter":
                // x - 1
                gameObject.setCurrentPosition(new Position(currentPosition.x - 2, currentPosition.y));
                break;
            case "OpponentBallMidDown":
                // x - 1
                // y + 1
                gameObject.setCurrentPosition(new Position(currentPosition.x - 2, currentPosition.y + 1));
                break;
            case "OpponentBallMaxDown":
                // x - 1
                // y + 2
                gameObject.setCurrentPosition(new Position(currentPosition.x - 2, currentPosition.y + 2));
                break;

        }

    }

    public void moveOpponent(GameObject ball, GameObject opponent) {

        // Check the opponent.getPosition().y agains ball.getPosition().y
        // if <, ++opponent
        // if >, --opponent

        Position ballPosition = ball.getCurrentPosition();
        Position opponentPosition = opponent.getCurrentPosition();

        /*if (opponentPosition.y < ballPosition.y) {
            opponent.setCurrentPosition(new Position(opponentPosition.x, opponentPosition.y + 1));
            moveObject(opponent, new Move("OpponentPaddleDown"));
        } else if (opponentPosition.y > ballPosition.y) {
            opponent.setCurrentPosition(new Position(opponentPosition.x, opponentPosition.y - 1));
            moveObject(opponent, new Move("OpponentPaddleUp"));
        }
*/


    }

    public void moveBall(GameObject ball, GameObject player1, GameObject opponent){
        // Check position of ball
        Position ballCurrentPosition = ball.getCurrentPosition();
        Position playerCurrentPosition = player1.getCurrentPosition();
        Position opponentCurrentPosition = opponent.getCurrentPosition();
        if (ballCurrentPosition.x == playerCurrentPosition.x) {
            if (ballCurrentPosition.y == playerCurrentPosition.y - 2) {
                ball.setMove(new Move("PlayerBallMaxUp"));
                moveObject(ball, new Move("PlayerBallMaxUp"));
            } else if (ballCurrentPosition.y == playerCurrentPosition.y - 1) {
                ball.setMove(new Move("PlayerBallMidUp"));
                moveObject(ball, new Move("PlayerBallMidUp"));
            } else if (ballCurrentPosition.y == playerCurrentPosition.y) {
                ball.setMove(new Move("PlayerBallCenter"));
                moveObject(ball, new Move("PlayerBallCenter"));
            } else if (ballCurrentPosition.y == playerCurrentPosition.y + 1) {
                ball.setMove(new Move("PlayerBallMidDown"));
                moveObject(ball, new Move("PlayerBallMidDown"));
            } else if (ballCurrentPosition.y == playerCurrentPosition.y + 2) {
                ball.setMove(new Move("PlayerBallMaxDown"));
                moveObject(ball, new Move("PlayerBallMaxDown"));
            } else {
                // Dies.
            }
        } else if (ballCurrentPosition.x == opponentCurrentPosition.x) {
            if (ballCurrentPosition.y == opponentCurrentPosition.y - 2) {
                ball.setMove(new Move("OpponentBallMaxUp"));
                moveObject(ball, new Move("OpponentBallMaxUp"));
            } else if (ballCurrentPosition.y == opponentCurrentPosition.y - 1) {
                ball.setMove(new Move("OpponentBallMidUp"));
                moveObject(ball, new Move("OpponentBallMidUp"));
            } else if (ballCurrentPosition.y == opponentCurrentPosition.y) {
                ball.setMove(new Move("OpponentBallCenter"));
                moveObject(ball, new Move("OpponentBallCenter"));
            } else if (ballCurrentPosition.y == opponentCurrentPosition.y + 1) {
                ball.setMove(new Move("OpponentBallMidDown"));
                moveObject(ball, new Move("OpponentBallMidDown"));
            } else if (ballCurrentPosition.y == opponentCurrentPosition.y + 2) {
                ball.setMove(new Move("OpponentBallMaxDown"));
                moveObject(ball, new Move("OpponentBallMaxDown"));
            } else {
                // Dies.
            }
        } else if (ballCurrentPosition.y <= 0){
            switch(ball.getMove().move) {
                case "PlayerBallMaxUp":
                    ball.setMove(new Move("PlayerBallMaxDown"));
                    moveObject(ball, new Move("PlayerBallMaxDown"));
                    break;
                case "PlayerBallMidUp":
                    ball.setMove(new Move("PlayerBallMidDown"));
                    moveObject(ball, new Move("PlayerBallMidDown"));
                    break;
                case "OpponentBallMaxUp":
                    ball.setMove(new Move("OpponentBallMaxDown"));
                    moveObject(ball, new Move("OpponentBallMaxDown"));
                    break;
                case "OpponentBallMidUp":
                    ball.setMove(new Move("OpponentBallMidDown"));
                    moveObject(ball, new Move("OpponentBallMidDown"));
                    break;

            }
        } else if (ballCurrentPosition.y >= 30) {
            switch (ball.getMove().move) {
                case "PlayerBallMaxDown":
                    ball.setMove(new Move("PlayerBallMaxUp"));
                    moveObject(ball, new Move("PlayerBallMaxUp"));
                    break;
                case "PlayerBallMidDown":
                    ball.setMove(new Move("PlayerBallMidUp"));
                    moveObject(ball, new Move("PlayerBallMidUp"));
                    break;
                case "OpponentBallMaxDown":
                    ball.setMove(new Move("OpponentBallMaxUp"));
                    moveObject(ball, new Move("OpponentBallMaxUp"));
                    break;
                case "OpponentBallMidDown":
                    ball.setMove(new Move("OpponentBallMidUp"));
                    moveObject(ball, new Move("OpponentBallMidUp"));
                    break;
            }
        } else {
            moveObject(ball, ball.getMove());
        }
        // If ball.getPosition() == player1.getPosition() {
        //    Set the movement to some type according to where the ball hits the paddle
        // } else if ball.getPosition() == opponent.getPosition() {
        //    Set the movement to some type according to where the ball hits the opponent.
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

class GreetingServer extends Thread {
    private ServerSocket serverSocket;
    private GameObject opponent;

    public GreetingServer(int port, GameObject opponent) throws IOException {
        serverSocket = new ServerSocket(port);
        this.opponent = opponent;
        //serverSocket.setSoTimeout(100000);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress());
                while (true) {

                    String temp = in.readUTF();
                    System.out.println("Recieved " + temp);
                    if(temp.equals("1")) {
                        opponent.setCurrentPosition(new Position(opponent.getCurrentPosition().x, opponent.getCurrentPosition().y + 1));

                        opponent.setMove(new Move("OpponentPaddleUp"));

                    }
                    if(temp.equals("2")) {
                        opponent.setCurrentPosition(new Position(opponent.getCurrentPosition().x, opponent.getCurrentPosition().y - 1));

                        opponent.setMove(new Move("OpponentPaddleDown"));

                    }

                }
                // server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }
}
