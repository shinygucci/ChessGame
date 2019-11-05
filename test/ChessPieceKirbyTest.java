import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceKirbyTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void moveAbsorberTest(){
        Kirby whiteAbsorber = new Kirby(ChessPiece.ChessPieceColor.WHITE);
        Kirby blackAbsorber = new Kirby(ChessPiece.ChessPieceColor.BLACK);
        Bishop whiteBishop = new Bishop(ChessPiece.ChessPieceColor.WHITE);
        Rook blackRook = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Queen whiteQueen = new Queen(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteAbsorber, 2, 7, false);
        chessBoard.setChessPiece(blackAbsorber, 4, 0, false);
        chessBoard.setChessPiece(whiteBishop, 3, 7, false);
        chessBoard.setChessPiece(blackRook, 5, 5, false);
        chessBoard.setChessPiece(whiteQueen, 3, 1, false);
        chessBoard.setChessPiece(blackKing, 5, 0);

        //White first move
        //Make a bishop move (invalid)
        assertFalse(chessBoard.moveChessPiece(2, 7, 0, 5));
        //Make a rook move (invalid)
        assertFalse(chessBoard.moveChessPiece(2, 7, 2, 1));
        //Capture bishop with a basic move (valid)
        assertTrue(chessBoard.moveChessPiece(2, 7, 3, 7));

        //Black first move
        //Make a Knight move (invalid)
        assertFalse(chessBoard.moveChessPiece(4, 0, 5, 2));
        //Make a rook move (invalid)
        assertFalse(chessBoard.moveChessPiece(4, 0, 6, 0));
        //Capture own king with a basic move (invalid)
        assertFalse(chessBoard.moveChessPiece(4, 0, 5, 0));
        //Capture opponent queen with a basic move and check opponent king (valid)
        assertTrue(chessBoard.moveChessPiece(4, 0, 3, 1));

        //White second move
        //Make a rook move (invalid)
        assertFalse(chessBoard.moveChessPiece(3, 7, 5, 7));
        //Capture opponent rook with a bishop move
        assertTrue(chessBoard.moveChessPiece(3, 7, 5, 5));
        //Black is checked
        assertTrue(chessBoard.isCheck());

        //Black second move
        //Move king to a still checked cell (invalid)
        assertFalse(chessBoard.moveChessPiece(5, 0, 5, 1));
        //Make a basic move (invalid, still under check)
        assertFalse(chessBoard.moveChessPiece(3, 1, 4, 1));
        //Make a Queen move (invalid, still under check)
        assertFalse(chessBoard.moveChessPiece(3, 1, 6, 1));
        //Make a Queen move (valid, blocks opponent's check)
        assertTrue(chessBoard.moveChessPiece(3, 1, 5, 1));

        //White third move
        //Make a rook move (valid)
        assertTrue(chessBoard.moveChessPiece(5, 5, 5, 7));

        //Black third move
        //Make a Queen move (valid)
        assertTrue(chessBoard.moveChessPiece(5, 1, 5, 3));

        //White fourth move
        //Make a bishop move (valid)
        assertTrue(chessBoard.moveChessPiece(5, 7, 3, 5));
    }
}
