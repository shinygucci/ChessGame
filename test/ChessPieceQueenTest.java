import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.Queen;
import chess.model.chessPiece.ChessPiece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceQueenTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void moveToEmptyCellTest(){
        Queen whiteQueen = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Queen blackQueen = new Queen(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteQueen, 3, 3, false);
        chessBoard.setChessPiece(blackQueen, 4, 3);

        //White first move
        assertFalse(chessBoard.moveChessPiece(3, 3, 7, 5));
        assertFalse(chessBoard.moveChessPiece(3, 3, 8, 8));
        assertFalse(chessBoard.moveChessPiece(3, 3, 6, 3));
        assertFalse(chessBoard.moveChessPiece(3, 3, 6, 4));
        assertFalse(chessBoard.moveChessPiece(3, 3, 1, 7));
        assertTrue(chessBoard.moveChessPiece(3, 3, 5, 5));

        //Black first move
        assertFalse(chessBoard.moveChessPiece(4, 3, 3, 5));
        assertFalse(chessBoard.moveChessPiece(4, 3, 7, 4));
        assertFalse(chessBoard.moveChessPiece(4, 3, 2, 6));
        assertFalse(chessBoard.moveChessPiece(4, 3, 0, -1));
        assertFalse(chessBoard.moveChessPiece(4, 3, 2, 0));
        assertTrue(chessBoard.moveChessPiece(4, 3, 4, 5));

        //White second move
        assertFalse(chessBoard.moveChessPiece(5, 5, 3, 6));
        assertFalse(chessBoard.moveChessPiece(5, 5, 1, 4));
        assertFalse(chessBoard.moveChessPiece(5, 5, 6, 3));
        assertFalse(chessBoard.moveChessPiece(5, 5, 7, 2));
        assertFalse(chessBoard.moveChessPiece(5, 5, 7, 4));
        assertTrue(chessBoard.moveChessPiece(5, 5, 7, 3));
    }

    @Test
    public void moveToOccupiedCellTest() {
        Queen whiteQueen1 = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Queen whiteQueen2 = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Queen whiteQueen3 = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Queen blackQueen1 = new Queen(ChessPiece.ChessPieceColor.BLACK);
        Queen blackQueen2 = new Queen(ChessPiece.ChessPieceColor.BLACK);
        Queen blackQueen3 = new Queen(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteQueen1, 3, 3, false);
        chessBoard.setChessPiece(whiteQueen2, 1, 5, false);
        chessBoard.setChessPiece(whiteQueen3, 1, 0, false);
        chessBoard.setChessPiece(blackQueen1, 4, 3, false);
        chessBoard.setChessPiece(blackQueen2, 6, 5, false);
        chessBoard.setChessPiece(blackQueen3, 4, 4);

        //White to own piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 1, 5));

        //White over own piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 2, 6));

        //White over opponent piece
        assertFalse(chessBoard.moveChessPiece(3, 3, 5, 5));

        //White to opponent piece
        assertTrue(chessBoard.moveChessPiece(3, 3, 4, 4));

        //Black to own piece
        assertFalse(chessBoard.moveChessPiece(4, 3, 6, 5));

        //Black over own piece
        assertFalse(chessBoard.moveChessPiece(4, 3, 7, 6));

        //Black to opponent piece
        assertTrue(chessBoard.moveChessPiece(4, 3, 1, 0));
    }
}
