import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.*;
import chess.model.chessPiece.ChessPiece;
import chess.model.chessPiece.ChessPiece.ChessPieceColor;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessBoardSetUpTest {
    static ChessBoard chessBoard;
    static int whiteBackRank;
    static int whiteFrontRank;
    static int blackBackRank;
    static int blackFrontRank;

    @BeforeClass
    public static void beforeClass(){
        chessBoard = new ChessBoard(0);
        whiteBackRank = 0;
        whiteFrontRank = 1;
        blackBackRank = chessBoard.getRankSize()-1;
        blackFrontRank = chessBoard.getRankSize()-2;
    }

    @Test
    public void defaultPawnsSetupTest() {
        for (int file = 0; file < chessBoard.getFileSize(); file++){
            Class chessPieceClass = Pawn.class;
            checkChessPiece(chessPieceClass, whiteFrontRank, file, ChessPieceColor.WHITE);
            checkChessPiece(chessPieceClass, blackFrontRank, file, ChessPieceColor.BLACK);
        }
    }

    @Test
    public void defaultRooksSetupTest() {
        int leftFile = 0;
        int rightFile = chessBoard.getFileSize()-1;
        checkChessPiece(Rook.class, whiteBackRank, leftFile, ChessPieceColor.WHITE);
        checkChessPiece(Rook.class, blackBackRank, leftFile, ChessPieceColor.BLACK);
        checkChessPiece(Rook.class, whiteBackRank, rightFile, ChessPieceColor.WHITE);
        checkChessPiece(Rook.class, blackBackRank, rightFile, ChessPieceColor.BLACK);
    }

    @Test
    public void defaultKnightsSetupTest() {
        int leftFile = 1;
        int rightFile = chessBoard.getFileSize()-2;
        checkChessPiece(Knight.class, whiteBackRank, leftFile, ChessPieceColor.WHITE);
        checkChessPiece(Knight.class, blackBackRank, leftFile, ChessPieceColor.BLACK);
        checkChessPiece(Knight.class, whiteBackRank, rightFile, ChessPieceColor.WHITE);
        checkChessPiece(Knight.class, blackBackRank, rightFile, ChessPieceColor.BLACK);
    }

    @Test
    public void defaultBishopsSetupTest() {
        int leftFile = 2;
        int rightFile = chessBoard.getFileSize()-3;
        checkChessPiece(Bishop.class, whiteBackRank, leftFile, ChessPieceColor.WHITE);
        checkChessPiece(Bishop.class, blackBackRank, leftFile, ChessPieceColor.BLACK);
        checkChessPiece(Bishop.class, whiteBackRank, rightFile, ChessPieceColor.WHITE);
        checkChessPiece(Bishop.class, blackBackRank, rightFile, ChessPieceColor.BLACK);
    }

    @Test
    public void defaultQueenSetupTest() {
        int queenFile = 3;
        checkChessPiece(Queen.class, whiteBackRank, queenFile, ChessPieceColor.WHITE);
        checkChessPiece(Queen.class, blackBackRank, queenFile, ChessPieceColor.BLACK);
    }

    @Test
    public void defaultKingSetupTest() {
        int queenFile = 4;
        checkChessPiece(King.class, whiteBackRank, queenFile, ChessPieceColor.WHITE);
        checkChessPiece(King.class, blackBackRank, queenFile, ChessPieceColor.BLACK);
    }

    @Test
    public void defaultSetUpEmptyCellTest(){
        ChessPiece emptyCellPiece = chessBoard.getChessPiece(3, 3);
        assertNull(emptyCellPiece);
        emptyCellPiece = chessBoard.getChessPiece(4, 7);
        assertNull(emptyCellPiece);
        emptyCellPiece = chessBoard.getChessPiece(5, 5);
        assertNull(emptyCellPiece);
    }

    @Test
    public void cellOutOfBounds() {
        ChessPiece outOfBoundsPiece = chessBoard.getChessPiece(8,4);
        assertNull(outOfBoundsPiece);
    }

    // TODO
    @Test
    public void customSizeSetup() {
        ChessBoard chessBoard2 = new ChessBoard(16, 16);
    }

    private void checkChessPiece(Class chessPieceClass, int rank, int file, ChessPiece.ChessPieceColor color){
        ChessPiece chessPiece = chessBoard.getChessPiece(rank, file);
        assertTrue(chessPieceClass.isInstance(chessPiece));
        assertEquals(chessPiece.getChessPieceColor(), color);
    }
}
