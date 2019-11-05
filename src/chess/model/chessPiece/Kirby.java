package chess.model.chessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A King class that extends the ChessPiece class.
 */
public class Kirby extends ChessPiece implements MovementChanging {
    /**
     * A public King Constructor
     * @param chessPieceColor the color of the owner of the ChessPiece
     */
    public Kirby(ChessPieceColor chessPieceColor){
        super(chessPieceColor);
    }

    /**
     * Initializes the MoveVectorSet which holds all the possible moves.
     * @return moveVectorSet
     */
    public void initializeMoveVectorSet() {
        Set<MoveVector> possibleMoves = new HashSet<>();
        MoveVector moveVector = new MoveVector(0, 1, 1);
        moveVector.setDirectionType(2);
        possibleMoves.add(moveVector);

        moveVector = new MoveVector(1, 1, 1);
        moveVector.setDirectionType(4);
        possibleMoves.add(moveVector);

        setMoveVectorSet(possibleMoves);
        setCaptureOwnPiece();
    }

    private void setCaptureOwnPiece(){
        Set<MoveVector> possibleMoves = getMoveVectorSet();
        for (MoveVector moveVector: possibleMoves) {
            moveVector.setCaptureOwnPiece(true);
        }
        setMoveVectorSet(possibleMoves);
    }

    @Override
    public void updateMoveVectorSet(Set<MoveVector> moveVectorSetAdded) {
        Set<MoveVector> possibleMoves = getMoveVectorSet();
        possibleMoves.addAll(moveVectorSetAdded);
        setMoveVectorSet(possibleMoves);
        setCaptureOwnPiece();
    }
}
