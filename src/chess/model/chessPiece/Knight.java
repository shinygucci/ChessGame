package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A Knight class that extends the ChessPiece class.
 */
public class Knight extends ChessPiece {
    /**
     * A public Knight Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Knight(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     */
    @Override
    public void initializeMoveVectorSet() {
        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(1, 2, 1);
        moveVector.setDirectionType(4);
        moveVector.setCanJumpOver(true);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(2, 1, 1);
        moveVector.setDirectionType(4);
        moveVector.setCanJumpOver(true);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
    }
}
