import chess.controller.ChessGameController;
import org.junit.Assert;
import org.junit.Test;

public class GameControllerTest {
    @Test
    public void gameControllerTest(){
        ChessGameController chessGameController = new ChessGameController();
        chessGameController.chessCellClicked(1, 1);
        chessGameController.chessCellClicked(6, 1);
        chessGameController.chessCellClicked(1, 1);
        chessGameController.chessCellClicked(2, 1);
        chessGameController.undoButtonClicked();
        chessGameController.redoButtonClicked();
        chessGameController.forfeitButtonClicked(0);

        Assert.assertFalse(chessGameController.isEndGame());

        chessGameController.startGameButtonClicked(2);
        chessGameController.startGameButtonClicked(2);
    }
}
