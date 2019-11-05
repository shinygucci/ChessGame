package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A Pawn class that extends the ChessPiece class.
 */
public class Pawn extends ChessPiece {
    private int moveCount;

    /**
     * A public Pawn Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Pawn(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
        setInitialMove(true);
        setHasInitialMove(true);
        moveCount = 0;
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     */
    @Override
    public void initializeMoveVectorSet() {
        int direction = 1;
        if(getChessPieceColor() == ChessPieceColor.BLACK) direction = -1;

        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(1, 0, 1);
        moveVector.setCanCapture(false);
        moveVector.setDirectionType(direction);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(1, 1, 1);
        moveVector.setCanBeEmpty(false);
        moveVector.setDirectionType(direction);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(1, -1, 1);
        moveVector.setCanBeEmpty(false);
        moveVector.setDirectionType(direction);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(2, 0, 1);
        moveVector.setCanCapture(false);
        moveVector.setDirectionType(direction);
        moveVector.setInitialMove(true);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
    }

    /**
     * Getter for moveCount member variable to be used for undoing moves to see if it reached the initial move.
     * @return int how many times this Pawn moved.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Increments moveCount when this Pawn has been moved.
     */
    public void incrementMoveCount() {
        moveCount++;
    }

    /**
     * Decrements moveCount when this Pawn's move was undone.
     */
    public void decrementMoveCount() {
        moveCount--;
    }
}
