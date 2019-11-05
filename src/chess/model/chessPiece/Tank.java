package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A King class that extends the ChessPiece class.
 */
public class Tank extends ChessPiece {
    private int hitPoint;

    /**
     * A public King Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Tank(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
        hitPoint = 3;
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     */
    @Override
    public void initializeMoveVectorSet() {
        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(0, 1, 1);
        moveVector.setDirectionType(2);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(1, 1, 1);
        moveVector.setDirectionType(4);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
    }

    /**
     * Getter for hitPoint, an integer that represents how many capture attempts are needed.
     * for this ChessPiece to be captured.
     * @return int current hitPoint
     */
    public int getHitPoint() {
        return hitPoint;
    }

    /**
     * Subtracts one from hitPoint when the opponent attempted to capture this ChessPiece.
     */
    public void hit() {
        hitPoint--;
    }
}
