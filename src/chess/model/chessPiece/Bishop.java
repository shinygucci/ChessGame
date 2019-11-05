package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A Bishop class that extends the ChessPiece class.
 */
public class Bishop extends ChessPiece {
    /**
     * A public Bishop Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Bishop(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     */
    @Override
    public void initializeMoveVectorSet() {
        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(1, 1, Integer.MAX_VALUE);
        moveVector.setDirectionType(4);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
    }
}
