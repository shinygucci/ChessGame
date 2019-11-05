package chess.model.chessBoard;

import chess.model.chessPiece.ChessPiece;

import java.util.HashSet;
import java.util.Set;

/**
 * A ChessCell class that holds ChessPieces and their movableCells.
 */
public class ChessCell {
    private ChessPiece chessPiece;
    private Set<ChessCell> movableCells;

    /**
     * A ChessCell constructor that sets the ChessPiece parameter as a member.
     * @param chessPiece ChessPiece to be set on the ChessCell
     */
    public ChessCell(ChessPiece chessPiece){
        setChessPiece(chessPiece);
        setMovableCells(new HashSet<>());
    }

    /**
     * getter for the ChessPiece member variable.
     * @return ChessPiece set on the ChessCell
     */
    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    /**
     * getter for the movableCells member variable.
     * @return Set of a movableCells
     */
    public Set<ChessCell> getMovableCells() {
        return movableCells;
    }

    /**
     * Setter for the movableCells member variable.
     * @param movableCells new movableCells that will overwrite the member variable
     */
    public void setMovableCells(Set<ChessCell> movableCells) {
        this.movableCells = movableCells;
    }

    /**
     * Setter for the chessPiece member variable.
     * @param chessPiece new chessPiece that will overwrite the member variable
     */
    void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }
}
