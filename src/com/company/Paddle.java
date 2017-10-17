package com.company;

public class Paddle implements GameObject {
    String gameObjectType = "Paddle";
    Position currentPosition;
    char representation = '\u2588';
    Move move;

    public Paddle(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void setCurrentPosition(Position newPosition) {
        currentPosition = newPosition;
    }

    @Override
    public Move getMove() {
        return move;
    }

    public void setMove(Move newMove) {
        move = newMove;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public char getRepresentation() {
        return representation;
    }

    @Override
    public String getGameObjectType() {
        return gameObjectType;
    }
}
