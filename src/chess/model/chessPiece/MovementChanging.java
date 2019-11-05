package chess.model.chessPiece;
import java.util.Set;

public interface MovementChanging {
    /**
     * abstract function that updates the MoveVector set.
     */
    void updateMoveVectorSet(Set<MoveVector> capturedChessPieceMoveVectorSet);
}
