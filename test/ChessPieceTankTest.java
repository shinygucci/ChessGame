import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessPieceTankTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void moveAbsorberTest(){
        Tank whiteTank = new Tank(ChessPiece.ChessPieceColor.WHITE);
        Pawn whitePawn = new Pawn(ChessPiece.ChessPieceColor.WHITE);
        Bishop blackBishop = new Bishop(ChessPiece.ChessPieceColor.BLACK);
        Rook blackRook = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Queen blackQueen = new Queen(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteTank, 2, 7, false);
        chessBoard.setChessPiece(whitePawn, 1, 2, false);
        chessBoard.setChessPiece(blackBishop, 4, 4, false);
        chessBoard.setChessPiece(blackRook, 2, 3, false);
        chessBoard.setChessPiece(blackQueen, 0, 5);

        //White pawn initial move
        assertTrue(chessBoard.moveChessPiece(1, 2, 3, 2));
        //Black rook hits white tank
        assertTrue(chessBoard.moveChessPiece(2, 3, 2, 7));
        //White tank captures black rook
        assertTrue(chessBoard.moveChessPiece(2, 7, 2, 6));
        //Black bishop hits white tank
        assertTrue(chessBoard.moveChessPiece(4, 4, 2, 6));
        //White tank captures black bishop
        assertTrue(chessBoard.moveChessPiece(2, 6, 3, 5));
        //Black queen captures white tank
        assertTrue(chessBoard.moveChessPiece(0, 5, 3, 5));
        //White tank is not there anymore so can't move it
        assertFalse(chessBoard.moveChessPiece(3, 5, 2, 5));
        //Move white pawn
        assertTrue(chessBoard.moveChessPiece(3, 2, 4, 2));
        //Move black queen
        assertTrue(chessBoard.moveChessPiece(3, 5, 2, 5));
    }
}
