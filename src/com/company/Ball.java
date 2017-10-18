package com.company;

public class Ball implements GameObject {
    String gameObjectType = "Ball";
    Position currentPosition;
    char representation = '\u25A0';
    Move move = new Move("OpponentBallCenter");

    public Ball(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void setCurrentPosition(Position newPosition) {
        currentPosition = newPosition;
    }

    public void setMove(Move newMove) {
        move = newMove;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public char getRepresentation(){
        return representation;
    }

    @Override
    public String getGameObjectType() {
        return gameObjectType;
    }
}
