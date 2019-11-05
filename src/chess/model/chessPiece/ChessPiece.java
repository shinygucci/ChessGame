package chess.model.chessPiece;

import java.util.Set;

/**
 * A ChessPiece abstract class that will be extended by original ChessPieces.
 */
public abstract class ChessPiece {
    private ChessPieceColor chessPieceColor;
    private Set<MoveVector> moveVectorSet;
    // TODO
    private boolean isSetOnChessBoard;
    private boolean isInitialMove = false;
    private boolean hasInitialMove = false;

    /**
     * A ChessPiece constructor that sets member variables.
     * @param chessPieceColor ChessPieceColor of the owner of the ChessPiece
     */
    public ChessPiece(ChessPieceColor chessPieceColor){
        this.chessPieceColor = chessPieceColor;
        initializeMoveVectorSet();
        isSetOnChessBoard = false;
    }

    /**
     * Getter for the ChessPieceColor member variable.
     * @return value of the ChessPieceColor member variable
     */
    public ChessPieceColor getChessPieceColor() {
        return chessPieceColor;
    }

    /**
     * Getter for the MoveVector set member variable.
     * @return MoveVector set member variable
     */
    public Set<MoveVector> getMoveVectorSet() {
        return moveVectorSet;
    }

    /**
     * Setter for the MoveVector set member variable.
     * @param moveVectorSet MoveVector set that will update the member variable
     */
    public void setMoveVectorSet(Set<MoveVector> moveVectorSet) {
        this.moveVectorSet = moveVectorSet;
    }

    /**
     * abstract function that initializes MoveVectors for each ChessPiece.
     */
    abstract void initializeMoveVectorSet();

    /**
     * enum to determine the owner of a ChessPiece.
     */
    public enum ChessPieceColor {
        BLACK {
            @Override
            public ChessPieceColor changeColor() {
                return WHITE;
            }
        },
        WHITE {
            @Override
            public ChessPieceColor changeColor() {
                return BLACK;
            }
        };

        /**
         * Helper function to switch between colors.
         */
        public abstract ChessPieceColor changeColor();
    }

    /**
     * Getter for the isInitialMove member variable.
     * @return true if the ChessPiece has a special initial move and has not made any moves yet
     */
    public boolean isInitialMove() {
        return isInitialMove;
    }

    /**
     * Getter for the hasInitialMove member variable.
     * @return true if the ChessPiece has a special initial move
     */
    public boolean hasInitialMove() {
        return hasInitialMove;
    }

    /**
     * Setter for the isInitialMove
     * @param initialMove true if the ChessPiece has a special initial move
     *                       and false otherwise or if it already made its first move
     */
    public void setInitialMove(boolean initialMove) {
        this.isInitialMove = initialMove;
    }

    /**
     * Setter for the isInitialMove
     * @param hasInitialMove true if the ChessPiece has a special initial move and false otherwise
     */
    public void setHasInitialMove(boolean hasInitialMove) {
        this.hasInitialMove = hasInitialMove;
    }
}
