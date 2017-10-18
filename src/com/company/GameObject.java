package com.company;

public interface GameObject {
    String getGameObjectType();
    char getRepresentation();

    Position getCurrentPosition();
    void setCurrentPosition(Position newPosition);

    Move getMove();
    void setMove(Move newMove);

}
