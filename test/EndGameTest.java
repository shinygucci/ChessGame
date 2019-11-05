import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EndGameTest {
    static ChessBoard chessBoard;

    @Before
    public void setUp(){
        chessBoard = new ChessBoard(1);
    }

    @Test
    public void CheckMateTest1(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Rook whiteRook = new Rook(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 4, 5, false);
        chessBoard.setChessPiece(blackKing, 4, 7, false);
        chessBoard.setChessPiece(whiteRook, 0, 0);

        chessBoard.moveChessPiece(0, 0, 0, 7);
        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.isCheckmate());
    }

    @Test
    public void CheckMateTest2(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);

        Queen whiteQueen = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Knight whiteKnight = new Knight(ChessPiece.ChessPieceColor.WHITE);
        Knight blackKnight = new Knight(ChessPiece.ChessPieceColor.BLACK);
        Rook blackRook = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Bishop blackBishop1 = new Bishop(ChessPiece.ChessPieceColor.BLACK);
        Bishop blackBishop2 = new Bishop(ChessPiece.ChessPieceColor.BLACK);

        Pawn whitePawn1 = new Pawn(ChessPiece.ChessPieceColor.WHITE);
        Pawn whitePawn2 = new Pawn(ChessPiece.ChessPieceColor.WHITE);
        Pawn blackPawn1 = new Pawn(ChessPiece.ChessPieceColor.BLACK);
        Pawn blackPawn2 = new Pawn(ChessPiece.ChessPieceColor.BLACK);
        Pawn blackPawn3 = new Pawn(ChessPiece.ChessPieceColor.BLACK);
        Pawn blackPawn4 = new Pawn(ChessPiece.ChessPieceColor.BLACK);
        Pawn blackPawn5 = new Pawn(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteKing, 0, 2, false);
        chessBoard.setChessPiece(blackKing, 6, 6, false);

        chessBoard.setChessPiece(whiteQueen, 7, 1, false);
        chessBoard.setChessPiece(whiteKnight, 4, 4, false);
        chessBoard.setChessPiece(blackKnight, 2, 2, false);
        chessBoard.setChessPiece(blackRook, 1, 2, false);
        chessBoard.setChessPiece(blackBishop1, 2, 1, false);
        chessBoard.setChessPiece(blackBishop2, 3, 1, false);

        chessBoard.setChessPiece(whitePawn1, 1, 6, false);
        chessBoard.setChessPiece(whitePawn2, 3, 7, false);
        chessBoard.setChessPiece(blackPawn1, 4, 1, false);
        chessBoard.setChessPiece(blackPawn2, 5, 2, false);
        chessBoard.setChessPiece(blackPawn3, 6, 5, false);
        chessBoard.setChessPiece(blackPawn4, 5, 6, false);
        chessBoard.setChessPiece(blackPawn5, 4, 7);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.isCheckmate());
    }

    @Test
    public void CheckMateTest3(){
        chessBoard = new ChessBoard(0);

        assertTrue(chessBoard.moveChessPiece(1, 5, 2, 5));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.moveChessPiece(6, 4, 4, 4));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.moveChessPiece(1, 6, 3, 6));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.moveChessPiece(7, 3, 3, 7));
        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.isCheckmate());
    }

    @Test
    public void StaleMateTest1(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Pawn whitePawn = new Pawn(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 4, 5, false);
        chessBoard.setChessPiece(blackKing, 7, 5, false);
        chessBoard.setChessPiece(whitePawn, 6, 5);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.moveChessPiece(4, 5, 5, 5));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.isStalemate());
    }

    @Test
    public void StaleMateTest2(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Rook whiteRook = new Rook(ChessPiece.ChessPieceColor.WHITE);
        Bishop blackBishop = new Bishop(ChessPiece.ChessPieceColor.BLACK);

        chessBoard.setChessPiece(whiteKing, 4, 1, false);
        chessBoard.setChessPiece(blackKing, 7, 0, false);
        chessBoard.setChessPiece(whiteRook, 7, 7, false);
        chessBoard.setChessPiece(blackBishop, 7, 1);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.moveChessPiece(4, 1, 5, 1));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.isStalemate());
    }

    @Test
    public void StaleMateTest3(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Rook whiteRook = new Rook(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 2, 2, false);
        chessBoard.setChessPiece(blackKing, 0, 0, false);
        chessBoard.setChessPiece(whiteRook, 1, 7);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.moveChessPiece(1, 7, 1, 1));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.isStalemate());
    }

    @Test
    public void StaleMateTest4(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Queen whiteQueen = new Queen(ChessPiece.ChessPieceColor.WHITE);
        Pawn blackPawn = new Pawn(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 4, 6, false);
        chessBoard.setChessPiece(blackKing, 0, 0, false);
        chessBoard.setChessPiece(whiteQueen, 2, 2, false);
        chessBoard.setChessPiece(blackPawn, 1, 0);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.moveChessPiece(2, 2, 2, 1));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.isStalemate());
    }

    @Test
    public void StaleMateTest5(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        King blackKing = new King(ChessPiece.ChessPieceColor.BLACK);
        Bishop whiteBishop = new Bishop(ChessPiece.ChessPieceColor.WHITE);
        Pawn whitePawn = new Pawn(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 4, 0, false);
        chessBoard.setChessPiece(blackKing, 7, 0, false);
        chessBoard.setChessPiece(whiteBishop, 3, 5, false);
        chessBoard.setChessPiece(whitePawn, 6, 0);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.moveChessPiece(4, 0, 5, 0));
        assertFalse(chessBoard.isCheckmate());
        assertTrue(chessBoard.isStalemate());
    }

    @Test
    public void CheckBlockTest(){
        King whiteKing = new King(ChessPiece.ChessPieceColor.WHITE);
        Rook blackRook = new Rook(ChessPiece.ChessPieceColor.BLACK);
        Bishop whiteBishop = new Bishop(ChessPiece.ChessPieceColor.WHITE);

        chessBoard.setChessPiece(whiteKing, 4, 1, false);
        chessBoard.setChessPiece(blackRook, 4, 5, false);
        chessBoard.setChessPiece(whiteBishop, 2, 2);

        assertFalse(chessBoard.isStalemate());
        assertTrue(chessBoard.isCheck());
        assertTrue(chessBoard.moveChessPiece(2, 2, 4, 4));
        assertFalse(chessBoard.isCheckmate());
        assertFalse(chessBoard.isCheck());
    }
}
