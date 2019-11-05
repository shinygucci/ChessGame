package chess.model.chessBoard.utils;

import chess.model.chessPiece.ChessPiece;

/**
 * Class to be used to stack previous movements
 */
public class ChessMovement {
    final private int oldRank;
    final private int oldFile;
    final private int newRank;
    final private int newFile;
    final private ChessPiece capturedChessPiece;

    /**
     * Constructor for ChessMovement
     * @param oldRank int rank before movement
     * @param oldFile int file before movement
     * @param newRank int rank after movement
     * @param newFile int rank after movement
     * @param capturedChessPiece
     */
    public ChessMovement(int oldRank, int oldFile, int newRank, int newFile, ChessPiece capturedChessPiece){
        this.oldRank = oldRank;
        this.oldFile = oldFile;
        this.newRank = newRank;
        this.newFile = newFile;
        this.capturedChessPiece = capturedChessPiece;
    }

    /**
     * Getter for oldRank
     * @return int oldRank member variable
     */
    public int getOldRank() {
        return oldRank;
    }

    /**
     * Getter for oldFile
     * @return int oldFile member variable
     */
    public int getOldFile() {
        return oldFile;
    }

    /**
     * Getter for newRank
     * @return int newRank member variable
     */
    public int getNewRank() {
        return newRank;
    }

    /**
     * Getter for newFile
     * @return int newFile member variable
     */
    public int getNewFile() {
        return newFile;
    }

    /**
     * Getter for a ChessPiece that was captured after this movement.
     * null if no ChessPiece was captured.
     * @return ChessPiece that was captured
     */
    public ChessPiece getCapturedChessPiece() {
        return capturedChessPiece;
    }
}
