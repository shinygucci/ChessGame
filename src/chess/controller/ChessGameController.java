package chess.controller;

import chess.model.chessBoard.ChessBoard;
import chess.model.chessPiece.ChessPiece;
import chess.view.ChessGameGUI;

import java.awt.*;
import java.util.logging.Logger;

/**
 * Controller class that has the main game loop for the ChessGame.
 */
public class ChessGameController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ChessBoard chessBoard;
    private ChessGameGUI chessGameGUI;
    private boolean isOriginChessCellSelected;
    private int selectedChessCellRank;
    private int selectedChessCellFile;
    private int previouslySelectedChessCellRank;
    private int previouslySelectedChessCellFile;
    private boolean newGameRequested = false;
    private int topPlayerPoints = 0;
    private int bottomPlayerPoints = 0;

    /**
     * Constructor for the ChessGameController
     */
    public ChessGameController() {
        chessBoard = new ChessBoard(0);
        chessGameGUI = new ChessGameGUI(chessBoard, this);
        chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
        chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");

        setOriginChessCellSelected(false);
    }

    /**
     * Takes an appropriate action when a ChessCell is clicked.
     * @param rank of the ChessCell that was clicked
     * @param file of the ChessCell that was clicked
     */
    public void chessCellClicked(int rank, int file) {
        chessGameGUI.clearPreviousMoveInfoLabelText();

        if (isOriginChessCellSelected()) {
            boolean moveSuccessful;
            moveSuccessful = chessBoard.moveChessPiece(selectedChessCellRank, selectedChessCellFile, rank, file);
            setPreviouslySelectedChessCellRank(selectedChessCellRank);
            setPreviouslySelectedChessCellFile(selectedChessCellFile);
            setSelectedChessCellRank(rank);
            setSelectedChessCellFile(file);
            if (moveSuccessful) {
                chessGameGUI.setPreviousMoveInfoLabelText("Successfully moved to rank " + rank + " file " + file + ".");
                chessGameGUI.setChessCellBorder(rank, file, Color.GREEN);
                chessGameGUI.updateChessPieces();
                if(isEndGame()){
                    chessGameGUI.setStartGameButtonText("New Game");
                    newGameRequested = true;
                    return;
                }
            } else {
                chessGameGUI.setPreviousMoveInfoLabelText("Selected chess piece cannot move to rank " + rank + " file " + file + ".");
                chessGameGUI.setChessCellBorder(previouslySelectedChessCellRank, previouslySelectedChessCellFile, Color.RED);
                chessGameGUI.setChessCellBorder(rank, file, Color.RED);
                chessGameGUI.updateChessPieces();
            }
            setOriginChessCellSelected(false);
            chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
            chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");
        } else if(chessBoard.canMove(chessBoard.getChessCell(rank, file))) {
            chessGameGUI.setChessCellBorder(selectedChessCellRank, selectedChessCellFile, Color.GRAY);
            chessGameGUI.setChessCellBorder(previouslySelectedChessCellRank, previouslySelectedChessCellFile, Color.GRAY);
            chessGameGUI.clearPreviousMoveInfoLabelText();
            chessGameGUI.setNextMoveInfoLabelText("Select where to move rank " + rank + " file " + file + ".");
            setOriginChessCellSelected(true);
            setSelectedChessCellRank(rank);
            setSelectedChessCellFile(file);
            chessGameGUI.setChessCellBorder(selectedChessCellRank, selectedChessCellFile, Color.GREEN);
        } else {
            chessGameGUI.setChessCellBorder(selectedChessCellRank, selectedChessCellFile, Color.GRAY);
            chessGameGUI.setChessCellBorder(rank, file, Color.RED);
            setSelectedChessCellRank(rank);
            setSelectedChessCellFile(file);
            if(chessBoard.getCurrentTurnsColor() == chessBoard.getChessCell(rank, file).getChessPiece().getChessPieceColor()) {
                chessGameGUI.setNextMoveInfoLabelText("The chess piece you selected has no valid moves.");
            } else {
                chessGameGUI.setNextMoveInfoLabelText("Select your own chess piece.");
            }
        }
    }

    /**
     * Tries to undo a move after the undoButton is clicked.
     */
    public void undoButtonClicked(){
        chessGameGUI.setChessCellBorder(selectedChessCellRank, selectedChessCellFile, Color.GRAY);
        chessGameGUI.setChessCellBorder(previouslySelectedChessCellRank, previouslySelectedChessCellFile, Color.GRAY);
        chessBoard.undoMovement();
        chessGameGUI.updateChessPieces();
        chessGameGUI.setPreviousMoveInfoLabelText("Undid previous move.");
        chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
        chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");
    }

    /**
     * Tries to redo a move after the undoButton is clicked.
     */
    public void redoButtonClicked(){
        chessBoard.redoMovement();
        chessGameGUI.updateChessPieces();
        chessGameGUI.setPreviousMoveInfoLabelText("Redid previous move.");
        chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
        chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");
    }

    /**
     * Start a new game or request a tie game
     * @param mode of the new game
     */
    public void startGameButtonClicked(int mode){
        if(newGameRequested){
            chessGameGUI.setStartGameButtonText("Request Tie");
            chessBoard = new ChessBoard(mode);
            chessGameGUI.setChessBoard(chessBoard);
            chessGameGUI.clearPreviousMoveInfoLabelText();
            chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
            chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");
            addTopPlayerPoints(1);
            addBottomPlayerPoints(1);
            chessGameGUI.setTopPlayerPoints(topPlayerPoints);
            chessGameGUI.setBottomPlayerPoints(bottomPlayerPoints);
        } else {
            chessGameGUI.setStartGameButtonText("Confirm Tie");
        }
        newGameRequested = !newGameRequested;
    }

    /**
     * Current player forfeits the game and a new game will start.
     */
    public void forfeitButtonClicked(int mode){
        chessGameGUI.setStartGameButtonText("Request Tie");
        if(chessBoard.getCurrentTurnsColor() == ChessPiece.ChessPieceColor.WHITE){
            addTopPlayerPoints(3);
        } else {
            addBottomPlayerPoints(3);
        }
        chessGameGUI.setTopPlayerPoints(topPlayerPoints);
        chessGameGUI.setBottomPlayerPoints(bottomPlayerPoints);
        chessBoard = new ChessBoard(mode);
        chessGameGUI.setPreviousMoveInfoLabelText(chessBoard.getCurrentTurnsColor() + " resigned");
        chessGameGUI.setChessBoard(chessBoard);
        chessGameGUI.setTurnLabelText(chessBoard.getCurrentTurnsColor() + "'s turn.");
        chessGameGUI.setNextMoveInfoLabelText("Select a chess piece to move.");
    }

    /**
     * Check if current game has ended i.e. no more valid moves to make.
     * @return true if it is the end of the game.
     */
    public boolean isEndGame(){
        if(chessBoard.isStalemate()){
            chessGameGUI.clearNextMoveInfoLabelText();
            chessGameGUI.setTurnLabelText("Stalemate!");
            return true;
        } else if(chessBoard.isCheckmate()){
            chessGameGUI.clearNextMoveInfoLabelText();
            chessGameGUI.setTurnLabelText("Checkmate! " + chessBoard.getCurrentTurnsColor().changeColor() + " won!");
            return true;
        } else if(chessBoard.isCheck()){
            chessGameGUI.setPreviousMoveInfoLabelText("Check!");
        }
        return false;
    }

    /**
     * Checks if the player has selected a ChessCell they want to move.
     * @return true if the player selected.
     */
    private boolean isOriginChessCellSelected() {
        return isOriginChessCellSelected;
    }

    /**
     * Sets a member variable for checking if the player has selected a ChessCell they want to move.
     */
    private void setOriginChessCellSelected(boolean originChessCellSelected) {
        isOriginChessCellSelected = originChessCellSelected;
    }

    /**
     * Sets a member variable for the rank of the ChessCell that is currently selected.
     * @param selectedChessCellRank int
     */
    private void setSelectedChessCellRank(int selectedChessCellRank) {
        this.selectedChessCellRank = selectedChessCellRank;
    }

    /**
     * Sets a member variable for the rank of the ChessCell that was previously selected.
     * @param previouslySelectedChessCellRank int
     */
    private void setPreviouslySelectedChessCellRank(int previouslySelectedChessCellRank) {
        this.previouslySelectedChessCellRank = previouslySelectedChessCellRank;
    }

    /**
     * Sets a member variable for the file of the ChessCell that is currently selected.
     * @param selectedChessCellFile int
     */
    private void setSelectedChessCellFile(int selectedChessCellFile) {
        this.selectedChessCellFile = selectedChessCellFile;
    }

    /**
     * Sets a member variable for the file of the ChessCell that was previously selected.
     * @param previouslySelectedChessCellFile int
     */
    private void setPreviouslySelectedChessCellFile(int previouslySelectedChessCellFile) {
        this.previouslySelectedChessCellFile = previouslySelectedChessCellFile;
    }

    /**
     * getter for the top player's total points
     * @return top player's total points
     */
    private int getTopPlayerPoints() {
        return topPlayerPoints;
    }

    /**
     * Adds points to the top player
     * @param addedPoints amount of points to be added.
     */
    private void addTopPlayerPoints(int addedPoints){
        topPlayerPoints += addedPoints;
    }

    /**
     * getter for the bottom player's total points
     * @return bottom player's total points
     */
    private int getBottomPlayerPoints() {
        return bottomPlayerPoints;
    }

    /**
     * Adds points to the bottom player
     * @param addedPoints amount of points to be added.
     */
    private void addBottomPlayerPoints(int addedPoints){
        bottomPlayerPoints += addedPoints;
    }
}
