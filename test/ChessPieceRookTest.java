import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.Rook;
import chess.model.chessPiece.ChessPiece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceRookTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void moveToEmptyCellTest(){
        Rook whiteRook = new Rook(ChessPiece.ChessPieceColor.WHITE);
        Rook blackRook = new Rook(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteRook, 3, 3, false);
        chessBoard.setChessPiece(blackRook, 4, 3);

        //White first move
        assertFalse(chessBoard.moveChessPiece(3, 3, 5, 5));
        assertFalse(chessBoard.moveChessPiece(3, 3, 3, 8));
        assertFalse(chessBoard.moveChessPiece(3, 3, 0, 6));
        assertFalse(chessBoard.moveChessPiece(3, 3, 6, 4));
        assertFalse(chessBoard.moveChessPiece(3, 3, 1, 7));
        assertTrue(chessBoard.moveChessPiece(3, 3, 3, 5));

        //Black first move
        assertFalse(chessBoard.moveChessPiece(4, 3, 3, 5));
        assertFalse(chessBoard.moveChessPiece(4, 3, 7, 4));
        assertFalse(chessBoard.moveChessPiece(4, 3, 2, 6));
        assertFalse(chessBoard.moveChessPiece(4, 3, 0, -1));
        assertFalse(chessBoard.moveChessPiece(4, 3, 2, 0));
        assertTrue(chessBoard.moveChessPiece(4, 3, 7, 3));

        //White second move
        assertTrue(chessBoard.moveChessPiece(3, 5, 2, 5));

        //Black second move
        assertTrue(chessBoard.moveChessPiece(7, 3, 7, 0));
    }

    @Test
    public void moveToOccupiedCellTest() {
        Rook whiteRook1 = new Rook(ChessPiece.ChessPieceColor.WHITE);
        Rook whiteRook2 = new Rook(ChessPiece.ChessPieceColor.WHITE);
        Rook whiteRook3 = new Rook(ChessPiece.ChessPieceColor.WHITE);
        Rook blackRook1 = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Rook blackRook2 = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Rook blackRook3 = new Rook(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteRook1, 3, 3, false);
        chessBoard.setChessPiece(whiteRook2, 1, 5, false);
        chessBoard.setChessPiece(whiteRook3, 1, 0, false);
        chessBoard.setChessPiece(blackRook1, 4, 3, false);
        chessBoard.setChessPiece(blackRook2, 6, 5, false);
        chessBoard.setChessPiece(blackRook3, 4, 4);

        //White to own piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 1, 5));

        //White over own piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 2, 6));

        //White over opponent piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 5, 5));

        //White to opponent piece
        assertTrue(chessBoard.moveChessPiece(3, 3, 4, 3));

        //White on wrong turn
        assertFalse(chessBoard.moveChessPiece(4, 3, 3, 3));

        //Black to own piece
        assertFalse(chessBoard.moveChessPiece(6, 5, 2, 4));

        //Black to opponent piece
        assertTrue(chessBoard.moveChessPiece(6, 5, 2, 5));

        //Black on wrong turn
        assertFalse(chessBoard.moveChessPiece(2, 5, 1, 5));

        //White on own turn
        assertTrue(chessBoard.moveChessPiece(4, 3, 3, 3));

        //Black capture opponent piece
        assertTrue(chessBoard.moveChessPiece(2, 5, 1, 5));
    }
}
