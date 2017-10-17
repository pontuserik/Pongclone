package com.company;

public class Paddle implements GameObject {
    String gameObjectType = "Paddle";
    Position currentPosition;
    char representation = '|';

    public Paddle(Position currentPosition) {
        this.currentPosition = currentPosition;
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
