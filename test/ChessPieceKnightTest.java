import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.Knight;
import chess.model.chessPiece.ChessPiece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceKnightTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void moveToEmptyCellTest(){
        Knight whiteKnight = new Knight(ChessPiece.ChessPieceColor.WHITE);
        Knight blackKnight = new Knight(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteKnight, 3, 3, false);
        chessBoard.setChessPiece(blackKnight, 4, 3);

        //White first move
        assertFalse(chessBoard.moveChessPiece(3, 3, 5, 5));
        assertFalse(chessBoard.moveChessPiece(3, 3, 3, 8));
        assertFalse(chessBoard.moveChessPiece(3, 3, 0, 6));
        assertFalse(chessBoard.moveChessPiece(3, 3, 7, 4));
        assertFalse(chessBoard.moveChessPiece(3, 3, 1, 7));
        assertTrue(chessBoard.moveChessPiece(3, 3, 4, 5));

        //Black first move
        assertFalse(chessBoard.moveChessPiece(4, 3, 4, 5));
        assertFalse(chessBoard.moveChessPiece(4, 3, 7, 3));
        assertFalse(chessBoard.moveChessPiece(4, 3, 1, 6));
        assertFalse(chessBoard.moveChessPiece(4, 3, 0, -1));
        assertFalse(chessBoard.moveChessPiece(4, 3, 2, 0));
        assertTrue(chessBoard.moveChessPiece(4, 3, 2, 2));

        //Black second move (white's turn)
        assertFalse(chessBoard.moveChessPiece(2, 2, 4, 1));

        //White second move
        assertTrue(chessBoard.moveChessPiece(4, 5, 2, 6));

        //Black second move
        assertTrue(chessBoard.moveChessPiece(2, 2, 4, 1));
    }

    @Test
    public void moveToOccupiedCellTest() {
        Knight whiteKnight1 = new Knight(ChessPiece.ChessPieceColor.WHITE);
        Knight whiteKnight2 = new Knight(ChessPiece.ChessPieceColor.WHITE);
        Knight whiteKnight3 = new Knight(ChessPiece.ChessPieceColor.WHITE);
        Knight blackKnight1 = new Knight(ChessPiece.ChessPieceColor.BLACK);
        Knight blackKnight2 = new Knight(ChessPiece.ChessPieceColor.BLACK);
        Knight blackKnight3 = new Knight(ChessPiece.ChessPieceColor.BLACK);
        Knight blackKnight4 = new Knight(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteKnight1, 3, 3, false);
        chessBoard.setChessPiece(whiteKnight2, 4, 5, false);
        chessBoard.setChessPiece(whiteKnight3, 2, 2, false);
        chessBoard.setChessPiece(blackKnight1, 4, 3, false);
        chessBoard.setChessPiece(blackKnight2, 4, 4, false);
        chessBoard.setChessPiece(blackKnight3, 3, 4, false);
        chessBoard.setChessPiece(blackKnight4, 5, 4);

        //Black to capture opponent piece (wrong turn)
        assertFalse(chessBoard.moveChessPiece(3, 4, 2, 2));

        //White to own piece (invalid)
        assertFalse(chessBoard.moveChessPiece(3, 3, 4, 5));

        //White to capture opponent piece (valid)
        assertTrue(chessBoard.moveChessPiece(3, 3, 5, 4));

        //Black to capture opponent piece (valid)
        assertTrue(chessBoard.moveChessPiece(3, 4, 2, 2));
    }
}
