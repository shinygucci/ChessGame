import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.ChessPiece.ChessPieceColor;
import chess.model.chessPiece.Pawn;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPiecePawnTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void initialMoveTest() {
        Pawn whitePawn = new Pawn(ChessPieceColor.WHITE);
        Pawn blackPawn = new Pawn(ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whitePawn, 1, 1, false);
        chessBoard.setChessPiece(blackPawn, 6, 6);

        //White first move
        assertFalse(chessBoard.moveChessPiece(1, 1, 0, 1));
        assertFalse(chessBoard.moveChessPiece(1, 1, 1, 2));
        assertFalse(chessBoard.moveChessPiece(1, 1, 2, 2));
        assertFalse(chessBoard.moveChessPiece(1, 1, 4, 1));
        assertFalse(chessBoard.moveChessPiece(1, 1, 3, 7));
        assertTrue(chessBoard.moveChessPiece(1, 1, 3, 1));

        //Black first move
        assertFalse(chessBoard.moveChessPiece(6, 6, 7, 6));
        assertFalse(chessBoard.moveChessPiece(6, 6, 7, 7));
        assertFalse(chessBoard.moveChessPiece(6, 6, 6, 7));
        assertFalse(chessBoard.moveChessPiece(6, 6, 2, 6));
        assertTrue(chessBoard.moveChessPiece(6, 6, 5, 6));

        //White second move
        assertFalse(chessBoard.moveChessPiece(3, 1, 5, 1));
        assertTrue(chessBoard.moveChessPiece(3, 1, 4, 1));
    }

    @Test
    public void attackTest() {
        Pawn whitePawn1 = new Pawn(ChessPieceColor.WHITE);
        Pawn whitePawn2 = new Pawn(ChessPieceColor.WHITE);
        Pawn blackPawn1 = new Pawn(ChessPieceColor.BLACK);
        Pawn blackPawn2 = new Pawn(ChessPieceColor.BLACK);
        Pawn blackPawn3 = new Pawn(ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whitePawn1, 1, 1, false);
        chessBoard.setChessPiece(whitePawn2, 2, 0, false);
        chessBoard.setChessPiece(blackPawn1, 2, 1, false);
        chessBoard.setChessPiece(blackPawn2, 2, 2, false);
        chessBoard.setChessPiece(blackPawn3, 4, 3);

        assertFalse(chessBoard.moveChessPiece(1, 1, 2, 1));
        assertFalse(chessBoard.moveChessPiece(1, 1, 2, 0));
        assertTrue(chessBoard.moveChessPiece(1, 1, 2, 2));

        // Moving diagonally to an empty cell should fail.
        assertFalse(chessBoard.moveChessPiece(4, 3, 3, 2));
        assertTrue(chessBoard.moveChessPiece(2, 1, 1, 1));
        assertTrue(chessBoard.moveChessPiece(2, 2, 3, 2));

        // Check if previous invalid attempt works now.
        assertTrue(chessBoard.moveChessPiece(4, 3, 3, 2));
    }
}
