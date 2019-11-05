package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A Rook class that extends the ChessPiece class.
 */
public class Rook extends ChessPiece {
    /**
     * A public Rook Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Rook(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     */
    @Override
    public void initializeMoveVectorSet() {
        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(1, 0, Integer.MAX_VALUE);
        moveVector.setDirectionType(2);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
    }
}
