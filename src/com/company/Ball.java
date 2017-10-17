package com.company;

public class Ball implements GameObject {
    String gameObjectType = "Ball";
    Position currentPosition;
    char representation = 'O';

    public Ball(Position currentPosition) {
        this.currentPosition = currentPosition;
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
