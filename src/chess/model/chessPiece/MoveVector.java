package chess.model.chessPiece;

/**
 * A MoveVector class that specifies what type of moves a ChessPiece can make.
 */
public class MoveVector {
    private int rankDirection;
    private int fileDirection;
    private int distance;
    private boolean canCapture = true;
    private boolean canBeEmpty = true;
    private boolean canJumpOver = false;
    private int directionType = 1;
    private boolean isInitialMove = false;
    private boolean canCaptureOwnPiece = false;

    /**
     * A MoveVector constructor that sets member variables.
     * @param rankDirection int rank directionType of where the ChessPiece can move to
     * @param fileDirection int file directionType of where the ChessPiece can move to
     * @param distance int how far the ChessPiece can move
     */
    public MoveVector (int rankDirection, int fileDirection, int distance){
        this.rankDirection = rankDirection;
        this.fileDirection = fileDirection;
        this.distance = distance;
    }

    /**
     * Getter for the rankDirection
     * @return int rankDirection
     */
    public int getRankDirection() {
        return rankDirection;
    }

    /**
     * Getter for the fileDirection
     * @return int fileDirection
     */
    public int getFileDirection() {
        return fileDirection;
    }

    /**
     * Getter for the distance the ChessPiece can move
     * @return int distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Getter for canCapture
     * @return boolean of whether the ChessPiece can capture an opponent ChessPiece in this directionType.
     */
    public boolean canCapture() {
        return canCapture;
    }

    /**
     * Getter for canBeEmpty
     * @return boolean of whether the ChessPiece can move to an unoccupied ChessCell.
     */
    public boolean canBeEmpty() {
        return canBeEmpty;
    }

    /**
     * Getter for canJumpOver
     * @return boolean of whether the ChessPiece can jump over other ChessPieces to reach its destination.
     */
    public boolean canJumpOver() {
        return canJumpOver;
    }

    /**
     * Getter for directionType
     * @return int that specifies how many directions the ChessPiece can move with this specific MoveVector
     */
    public int getDirectionType() {
        return directionType;
    }

    /**
     * Getter for isInitialMove
     * @return true if this MoveVector is for a special initial move
     */
    public boolean isInitialMove() {
        return isInitialMove;
    }

    /**
     * Getter for canCaptureOwnPiece
     * @return true if this MoveVector can capture own ChessPiece
     */
    public boolean canCaptureOwnPiece() {
        return canCaptureOwnPiece;
    }

    /**
     * Setter for canCapture
     * @param canCapture boolean
     */
    void setCanCapture(boolean canCapture) {
        this.canCapture = canCapture;
    }

    /**
     * Setter for canBeEmpty
     * @param canBeEmpty boolean
     */
    void setCanBeEmpty(boolean canBeEmpty) {
        this.canBeEmpty = canBeEmpty;
    }

    /**
     * Setter for canJumpOver
     * @param canJumpOver boolean
     */
    void setCanJumpOver(boolean canJumpOver) {
        this.canJumpOver = canJumpOver;
    }

    /**
     * Setter for the number of directions
     * @param directionType int
     */
    void setDirectionType(int directionType) {
        this.directionType = directionType;
    }

    /**
     * Setter for isInitialMove
     * @param isInitialMove int
     */
    void setInitialMove(boolean isInitialMove) { this.isInitialMove = isInitialMove;
    }

    /**
     * Setter for canCaptureOwnPiece
     * @return canCaptureOwnPiece boolean
     */
    void setCaptureOwnPiece(boolean canCaptureOwnPiece) {
        this.canCaptureOwnPiece = canCaptureOwnPiece;
    }
}
