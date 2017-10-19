package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GreetingServer extends Thread {
    private ServerSocket serverSocket;
    private GameObject opponent;

    public GreetingServer(int port, GameObject opponent) throws IOException {
        serverSocket = new ServerSocket(port);
        this.opponent = opponent;
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
                if (temp.equals("1")) {
                    opponent.setCurrentPosition(new Position(opponent.getCurrentPosition().x, opponent.getCurrentPosition().y + 1));
                    opponent.setMove(new Move("OpponentPaddleUp"));
                }
                if (temp.equals("2")) {
                    opponent.setCurrentPosition(new Position(opponent.getCurrentPosition().x, opponent.getCurrentPosition().y - 1));
                    opponent.setMove(new Move("OpponentPaddleDown"));
                }
            }
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


