package chess.model.chessBoard;

import chess.model.chessBoard.utils.ChessMovement;
import chess.model.chessPiece.*;
import chess.model.chessPiece.ChessPiece;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A ChessBoard class that handles ChessPiece movements.
 */
public class ChessBoard {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private int DEFAULT_RANK_SIZE = 8;
    private int DEFAULT_FILE_SIZE = 8;
    private final int rankSize;
    private final int fileSize;
    private ChessCell[][] chessCells;
    private Deque<ChessMovement> chessMovements;
    private Deque<ChessMovement> chessUndoneMovements;

    private ChessPiece.ChessPieceColor currentTurnsColor;
    private boolean isCheck;

    /**
     * A ChessBoard constructor that creates a default ChessBoard or a custom ChessBoard for the user to edit.
     * @param mode int to determine what kind of ChessBoard the user wants to create.
     */
    public ChessBoard (int mode) {
        isCheck = false;
        rankSize = DEFAULT_RANK_SIZE;
        fileSize = DEFAULT_FILE_SIZE;
        chessMovements = new LinkedList<>();
        chessUndoneMovements = new LinkedList<>();
        setUpCells(rankSize, fileSize);
        setCurrentTurnsColor(ChessPiece.ChessPieceColor.WHITE);

        switch (mode) {
            // Default 8x8 setup.
            case 0:
                logger.info("Default Chessboard");
                setUpDefaultChessBoard();
                break;
            // Empty 8x8 setup.
            case 1:
                logger.info("Empty Chessboard");
                break;
            // Custom 8x8 setup.
            case 2:
                logger.info("Custom Chessboard");
                setUpCustomChessBoard();
                break;
        }
    }

    /**
     * A ChessBoard constructor that uses rankSize and fileSize that the user specifies.
     * @param rankSize int the rankSize user specifies
     * @param fileSize int the fileSize user specifies
     */
    public ChessBoard (int rankSize, int fileSize) {
        isCheck = false;
        this.rankSize = rankSize;
        this.fileSize = fileSize;
        setUpCells(rankSize, fileSize);
    }

    /**
     * Attempts to move a ChessPiece to a different cell.
     * @param currentRank rank of the ChessPiece the user is attempting to move from.
     * @param currentFile file of the ChessPiece the user is attempting to move from.
     * @param newRank rank of the ChessPiece the user is attempting to move to.
     * @param newFile file of the ChessPiece the user is attempting to move to.
     * @return true if successful movement of the ChessPiece, false otherwise.
     */
    public boolean moveChessPiece(int currentRank, int currentFile, int newRank, int newFile){
        if(isEndGame()){
            return false;
        }
        isCheck = false;

        ChessCell chessCell = getChessCell(currentRank, currentFile);
        ChessPiece chessPiece = chessCell.getChessPiece();
        ChessPiece destinationChessPiece;

        if (chessPiece == null){
            logger.info("No ChessPiece on this cell.");
            return false;
        }

        if (chessPiece.getChessPieceColor() != currentTurnsColor){
            logger.info("You cannot move your opponent's chess piece.");
            return false;
        }

        if(isValidMove(chessCell, newRank, newFile)){
            if(chessUndoneMovements.size() != 0){
                chessUndoneMovements = new LinkedList<>();
            }

            if(chessPiece.hasInitialMove()){
                Pawn pawn = (Pawn)chessPiece;
                pawn.incrementMoveCount();
            }
            if(chessPiece.isInitialMove()){
                chessPiece.setInitialMove(false);
            }
            destinationChessPiece = getChessPiece(newRank, newFile);
            if(destinationChessPiece != null && chessPiece instanceof MovementChanging){
                ((MovementChanging) chessPiece).updateMoveVectorSet(destinationChessPiece.getMoveVectorSet());
            }

            setCurrentTurnsColor(currentTurnsColor.changeColor());

            //Decrement hit point if Tank can still endure an enemies attack.
            if(destinationChessPiece instanceof Tank && ((Tank) destinationChessPiece).getHitPoint() > 1){
                logger.info("You hit a tank!");
                ((Tank) destinationChessPiece).hit();
                //Attacking ChessPiece will move to a cell next to the tank unless it's a canJumpOver ChessPiece in which case the ChessPiece will just move back to its original cell.
                if (!(chessPiece instanceof Knight)) {
                    unsetChessPiece(currentRank, currentFile);
                    int rankDifference = newRank - currentRank;
                    int fileDifference = newFile - currentFile;
                    int oneRankBeforeNew, oneFileBeforeNew;
                    if (rankDifference != 0) oneRankBeforeNew = newRank - (rankDifference/Math.abs(rankDifference));
                    else oneRankBeforeNew = newRank;
                    if (fileDifference != 0) oneFileBeforeNew = newFile - (fileDifference/Math.abs(fileDifference));
                    else oneFileBeforeNew = newFile;
                    setChessPiece(chessPiece, oneRankBeforeNew, oneFileBeforeNew);
                }
                return true;
            }

            logger.info("Moving a ChessPiece from rank " + currentRank + " file " + currentFile + " to rank " + newRank + " file " + newFile);
            unsetChessPiece(currentRank, currentFile);
            setChessPiece(chessPiece, newRank, newFile);
            chessMovements.addFirst(new ChessMovement(currentRank, currentFile, newRank, newFile, destinationChessPiece));
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Undoes a previous movement.
     * @return true if there was a previous movement
     */
    public boolean undoMovement(){
        if (chessMovements.size() == 0){
            return false;
        }

        ChessMovement mostRecentMovement = chessMovements.removeFirst();
        chessUndoneMovements.addFirst(mostRecentMovement);
        int newRank = mostRecentMovement.getNewRank();
        int newFile = mostRecentMovement.getNewFile();
        int oldRank = mostRecentMovement.getOldRank();
        int oldFile = mostRecentMovement.getOldFile();

        ChessPiece movedChessPiece = getChessPiece(newRank, newFile);
        ChessPiece capturedChessPiece = mostRecentMovement.getCapturedChessPiece();

        if(capturedChessPiece != null){
            setChessPiece(capturedChessPiece, newRank, newFile);
        } else {
            unsetChessPiece(newRank, newFile);
        }
        setCurrentTurnsColor(getCurrentTurnsColor().changeColor());

        if(movedChessPiece instanceof Pawn){
            Pawn pawn = (Pawn) movedChessPiece;
            pawn.decrementMoveCount();
            if(pawn.getMoveCount() == 0){
                movedChessPiece.setInitialMove(true);
            }
        }
        setChessPiece(movedChessPiece, oldRank, oldFile);

        return true;
    }

    /**
     * Redoes a previously undone movement.
     * @return true if there was an undone movement
     */
    public boolean redoMovement(){
        if (chessUndoneMovements.size() == 0){
            return false;
        }

        ChessMovement mostRecentMovement = chessUndoneMovements.removeFirst();
        chessMovements.addFirst(mostRecentMovement);
        int newRank = mostRecentMovement.getNewRank();
        int newFile = mostRecentMovement.getNewFile();
        int oldRank = mostRecentMovement.getOldRank();
        int oldFile = mostRecentMovement.getOldFile();
        ChessPiece movedChessPiece = getChessPiece(oldRank, oldFile);

        unsetChessPiece(oldRank, oldFile);
        setChessPiece(movedChessPiece, newRank, newFile);
        setCurrentTurnsColor(getCurrentTurnsColor().changeColor());

        return true;
    }

    /**
     * Checks if there are no more valid moves for the current player in turn, thus the end of the game.
     * @return true if no more moves.
     */
    private boolean isEndGame(){
        for (int rank = 0; rank < rankSize; rank++){
            for (int file = 0; file < fileSize; file++){
                final ChessCell chessCell = getChessCell(rank, file);
                final ChessPiece chessPiece = getChessPiece(rank, file);
                if (chessPiece == null){
                    continue;
                }
                else if (chessPiece.getChessPieceColor() == currentTurnsColor) {
                    if (!chessCell.getMovableCells().isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check if current user is checkmated.
     * @return true if checkmated.
     */
    public boolean isCheckmate(){
        return (isEndGame() && isCheck);
    }

    /**
     * Check if the game is stalemated.
     * @return true if stalemate.
     */
    public boolean isStalemate(){
        return (isEndGame() && !isCheck);
    }

    /**
     * Checks if a ChessPiece on a specified ChessCell has any valid move and is on current turn.
     * @param chessCell to check if there are valid moves.
     * @return true if there is any valid move.
     */
    public boolean canMove(ChessCell chessCell){
        if(hasValidMoves(chessCell) && currentTurnsColor == chessCell.getChessPiece().getChessPieceColor()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a ChessPiece on a specified ChessCell has any valid move.
     * @param chessCell to check if there are valid moves.
     * @return true if there is any valid move.
     */
    private boolean hasValidMoves(ChessCell chessCell){
        if (chessCell.getMovableCells().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper function for moveChessPiece() to determine if
     * @param chessCell
     * @param newRank
     * @param newFile
     * @return
     */
    private boolean isValidMove(ChessCell chessCell, int newRank, int newFile){
        if(!hasValidMoves(chessCell)){
            return false;
        }

        Set<ChessCell> movableCells = chessCell.getMovableCells();

        ChessCell destinationChessCell = getChessCell(newRank, newFile);
        if (movableCells.contains(destinationChessCell)){
            logger.info("Rank " + newRank + " file " + newFile + " is a valid destination.");
            return true;
        } else {
            logger.info("Rank " + newRank + " file " + newFile + " is not a valid destination.");
            return false;
        }
    }

    /**
     * Sets up cell objects for the ChessBoard.
     * @param rankSize int number of ranks
     * @param fileSize int number of files
     */
    private void setUpCells(int rankSize, int fileSize){
        chessCells = new ChessCell[rankSize][fileSize];
        for (int rank = 0; rank < rankSize; rank++){
            for (int file = 0; file < fileSize; file++){
                setChessCell(new ChessCell(null), rank, file);
            }
        }
    }

    /**
     * Processes all the possible moves of a ChessPiece and filters out invalid moves.
     * Valid moves will be stored in a HashSet member of a ChessCell.
     */
    private void setAllMovableCells(){
        for (int rank = 0; rank < rankSize; rank++){
            for (int file = 0; file < fileSize; file++){
                final ChessPiece chessPiece = getChessPiece(rank, file);
                if (chessPiece == null){
                    continue;
                }
                setMovableCells(rank, file);
            }
        }
    }

    /**
     * Determines whether a ChessPiece is checked by an opponent ChessPiece.
     * @param chessPieceColor color of the player to be checked
     * @return true if the player is checked.
     */
    private boolean isChecked(ChessPiece.ChessPieceColor chessPieceColor){
        for (int rank = 0; rank < rankSize; rank++){
            for (int file = 0; file < fileSize; file++){
                final ChessPiece chessPiece = getChessPiece(rank, file);
                if (chessPiece == null){
                    continue;
                }
                else if (chessPiece.getChessPieceColor() == chessPieceColor) {
                    if (kingCanBeCapturedFromThisCell(rank, file)){
                        logger.info("King can be captured from this cell :(");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Creates a ChessCell object for each ChessCells of the array in the ChessBoard.
     * @param chessCell new ChessCell object to be assigned to the array/
     * @param rank int rank position of the ChessCell to be set
     * @param file int position of the ChessCell to be set
     */
    private void setChessCell(ChessCell chessCell, int rank, int file){
        chessCells[rank][file] = chessCell;
    }

    /**
     * Sets a ChessPiece to a corresponding ChessCell with the option to update the movableCells for all ChessCells.
     * @param chessPiece ChessPiece to be set on the ChessCell
     * @param rank int rank position of the ChessCell
     * @param file int file position of the ChessCell
     * @param hasToUpdateMovableCells true if movableCells need to be updated
     */
    public void setChessPiece(ChessPiece chessPiece, int rank, int file, boolean hasToUpdateMovableCells) {
        getChessCell(rank, file).setChessPiece(chessPiece);

        if (hasToUpdateMovableCells){
            setAllMovableCells();
        }
    }

    /**
     * Calls setChessPiece with a default true boolean value to update all movableCells.
     * @param chessPiece ChessPiece to be set
     * @param rank int rank position of the ChessCell
     * @param file int file position of the ChessCell
     */
    public void setChessPiece(ChessPiece chessPiece, int rank, int file) {
        setChessPiece(chessPiece, rank, file, true);
    }

    /**
     * Sets all the movableCells of a ChessPiece on a given position.
     * @param rank int rank position of the ChessPiece whose movableCells will be set.
     * @param file int file position of the ChessPiece whose movableCells will be set.
     */
    private void setMovableCells(int rank, int file){
        ChessCell chessCell = getChessCell(rank, file);
        final ChessPiece chessPiece = getChessPiece(rank, file);

        logger.info("Setting movableCells of rank " + rank + " and file " + file + ".");

        final Set<MoveVector> moveVectorSet = chessPiece.getMoveVectorSet();
        Set<ChessCell> movableCells = new HashSet<>();

        for (MoveVector moveVector: moveVectorSet){
            if (moveVector.isInitialMove() && !chessPiece.isInitialMove()){
                continue;
            }
            movableCells.addAll(processMoveVector(moveVector, rank, file));
        }
        chessCell.setMovableCells(movableCells);
    }

    /**
     * Checks if the opponent King can be captured by the ChessPiece on a given ChessCell.
     * @param rank int rank position of the ChessPiece to be checked
     * @param file int file position of the ChessPiece to be checked
     * @return true if the opponent King can be captured
     */
    private boolean kingCanBeCapturedFromThisCell(int rank, int file){
        final ChessPiece chessPiece = getChessPiece(rank, file);
        final Set<MoveVector> moveVectorSet = chessPiece.getMoveVectorSet();

        for (MoveVector moveVector: moveVectorSet){
            if (moveVector.isInitialMove() && !chessPiece.isInitialMove()){
                continue;
            }
            if (moveVectorLeadsToCheck(moveVector, rank, file)){
                return true;
            }
        }
        return false;
    }

    /**
     * Processes a MoveVector of a given ChessPiece and makes several calls to processMoveVectorPerDirection to check
     * all the possible ChessCells the ChessPiece can move to.
     * @param moveVector one of the MoveVectors to be checked
     * @param currentRank int rank position of the ChessPiece whose MoveVector will be checked
     * @param currentFile int file position of the ChessPiece whose MoveVector will be checked
     * @return all the ChessCells the ChessPiece can move to generated by the MoveVector given.
     */
    private Set<ChessCell> processMoveVector(final MoveVector moveVector, final int currentRank, final int currentFile){
        Set<ChessCell> movableCells = new HashSet<>();
        int rankDirection = moveVector.getRankDirection();
        int fileDirection = moveVector.getFileDirection();

        switch (moveVector.getDirectionType()){
            case 1:
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection));
                break;
            case -1:
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, fileDirection));
                break;
            case 2: // MoveVectors with one direction set to 0 such as Rook.
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, -fileDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, fileDirection, rankDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, -fileDirection, -rankDirection));
                break;
            case 4: // MoveVectors with both directions set to non-zero.
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, -fileDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, fileDirection));
                movableCells.addAll(processMoveVectorPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, -fileDirection));
                break;
        }

        return movableCells;
    }

    /**
     * Processes one direction for a MoveVector of a given ChessPiece to check
     * all the possible ChessCells the ChessPiece can move to in that direction.
     * @param moveVector one of the MoveVector to be checked
     * @param currentRank int rank position of the ChessPiece whose MoveVector will be checked
     * @param rankDirection int file direction of the MoveVector being processed
     * @param fileDirection int rank direction of the MoveVector being processed
     * @param currentFile int file position of the ChessPiece whose MoveVector will be checked
     * @return all the ChessCells the ChessPiece can move to generated by the MoveVector given.
     */
    private Set<ChessCell> processMoveVectorPerDirection(final MoveVector moveVector, final int currentRank, final int currentFile,
                                             final int rankDirection, final int fileDirection){
        Set<ChessCell> movableCells = new HashSet<>();

        ChessPiece movingChessPiece = getChessPiece(currentRank, currentFile);
        for (int distance = 1; distance <= moveVector.getDistance(); distance++){
            int destinationRank = currentRank + rankDirection * distance;
            int destinationFile = currentFile + fileDirection * distance;
            logger.info("Checking if movable to rank " + destinationRank + " file " + destinationFile + ".");
            if (destinationRank >= getRankSize() || destinationFile >= getFileSize()
                    || destinationRank < 0 || destinationFile < 0) break;

            ChessPiece destinationChessPiece = getChessPiece(destinationRank, destinationFile);
            if (!moveVector.canBeEmpty() && destinationChessPiece == null){
                logger.info("Destination cannot be empty but it is empty.");
                continue;
            }
            if (!moveVector.canCapture() && destinationChessPiece != null){
                logger.info("Destination cannot be occupied but it is occupied.");
                continue;
            }
            if (destinationChessPiece != null
                    && movingChessPiece.getChessPieceColor() == destinationChessPiece.getChessPieceColor()
                    && !(moveVector.canCaptureOwnPiece() && !(destinationChessPiece instanceof King))){
                logger.info("Destination has an ally piece which cannot be captured.");
                continue;
            }
            if (!moveVector.canJumpOver() &&
                    obstacleExists(currentRank, currentFile, destinationRank, destinationFile)){
                logger.info("Cannot jump over other ChessPieces but there's an obstacle.");
                break;
            }
            if (movingChessPiece.getChessPieceColor() == currentTurnsColor &&
                    movementLeadsToACheckedPosition(currentRank, currentFile, destinationRank, destinationFile)){
                logger.info("Invalid Movement: leads to a checked position.");
                continue;
            }
            logger.info("Movable to rank " + destinationRank + " file " + destinationFile + ".");

            if (destinationChessPiece instanceof King) {
                logger.info("Check!");
                isCheck = true;
            }
            movableCells.add(getChessCell(destinationRank, destinationFile));
        }

        return movableCells;
    }

    /**
     * Checks if a MoveVector of a given ChessPiece can lead to a check by making several calls to
     * moveVectorLeadsToCheckPerDirection.
     * @param moveVector one of the MoveVectors of the ChessPiece
     * @param currentRank int rank position of the ChessPiece whose MoveVector will be checked
     * @param currentFile int file position of the ChessPiece whose MoveVector will be checked
     * @return true if the MoveVector can generate a check
     */
    private boolean moveVectorLeadsToCheck(final MoveVector moveVector, final int currentRank, final int currentFile){
        int rankDirection = moveVector.getRankDirection();
        int fileDirection = moveVector.getFileDirection();

        switch (moveVector.getDirectionType()){
            case 1:
                return (moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection));
            case -1:
                return (moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, fileDirection));
            case 2:
                return (moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, -rankDirection, -fileDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, fileDirection, rankDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, -fileDirection, -rankDirection));
            case 4:
                return (moveVectorLeadsToCheckPerDirection(
                        moveVector, currentRank, currentFile, rankDirection, fileDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                                moveVector, currentRank, currentFile, rankDirection, -fileDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                                moveVector, currentRank, currentFile, -rankDirection, fileDirection) ||
                        moveVectorLeadsToCheckPerDirection(
                                moveVector, currentRank, currentFile, -rankDirection, -fileDirection));
        }

        return false;
    }

    /**
     * Checks if one of the directions obtained by a MoveVector of a given ChessPiece can lead to a check.
     * @param moveVector one of the MoveVectors of the ChessPiece
     * @param currentRank int rank position of the ChessPiece whose MoveVector will be checked
     * @param currentFile int file position of the ChessPiece whose MoveVector will be checked
     * @param rankDirection int rank direction of the MoveVector being processed
     * @param fileDirection int file direction of the MoveVector being processed
     * @return true if the MoveVector can generate a check-
     */
    private boolean moveVectorLeadsToCheckPerDirection(final MoveVector moveVector, final int currentRank, final int currentFile,
                                                       final int rankDirection, final int fileDirection){
        ChessPiece movingChessPiece = getChessPiece(currentRank, currentFile);
        for (int distance = 1; distance <= moveVector.getDistance(); distance++){
            int destinationRank = currentRank + rankDirection * distance;
            int destinationFile = currentFile + fileDirection * distance;
            if (destinationRank >= getRankSize() || destinationFile >= getFileSize()
                    || destinationRank < 0 || destinationFile < 0) break;

            ChessPiece destinationChessPiece = getChessPiece(destinationRank, destinationFile);
            if (!moveVector.canBeEmpty() && destinationChessPiece == null){
                continue;
            }
            if (!moveVector.canCapture() && destinationChessPiece != null){
                continue;
            }
            if (destinationChessPiece != null
                    && movingChessPiece.getChessPieceColor() == destinationChessPiece.getChessPieceColor()
                    && !moveVector.canCaptureOwnPiece()){
                continue;
            }
            if (!moveVector.canJumpOver() &&
                    obstacleExists(currentRank, currentFile, destinationRank, destinationFile)){
                break;
            }
            if (destinationChessPiece instanceof King && destinationChessPiece.getChessPieceColor() == currentTurnsColor) {
                return true;
            }
        }

        return false;
    }

    /**
     * Helper function for moveVectorLeadsToCheck to see if moving to a specific cell will produce a check.
     * @param currentRank int rank position of a ChessPiece prior to movement.
     * @param currentFile int file position of a ChessPiece prior to movement.
     * @param destinationRank int rank position where the ChessPiece will be moved to see the check condition
     * @param destinationFile int file position where the ChessPiece will be moved to see the check condition
     * @return trie of moving to the given ChessCell will lead to a check.
     */
    private boolean movementLeadsToACheckedPosition(final int currentRank, final int currentFile,
                                         final int destinationRank, final int destinationFile){
        ChessPiece currentChessPiece = getChessPiece(currentRank, currentFile);
        ChessPiece destinationChessPiece = getChessPiece(destinationRank, destinationFile);
        unsetChessPiece(currentRank, currentFile);
        setChessPiece(currentChessPiece, destinationRank, destinationFile, false);

        boolean leadsToCheck = isChecked(currentTurnsColor.changeColor());
        if (leadsToCheck) {
            logger.info("Moving from rank " + currentRank + " file " + currentFile + " to rank " + destinationRank + " file " +
                    destinationFile + " leads to a checked position");
        }

        setChessPiece(currentChessPiece, currentRank, currentFile, false);
        setChessPiece(destinationChessPiece, destinationRank, destinationFile, false);

        return leadsToCheck;
    }

    /**
     * Checks if there are any other ChessPieces between two given ChessCells.
     * @param currentRank int rank position of the first ChessCell
     * @param currentFile int file position of the first ChessCell
     * @param destinationRank int rank position of the second ChessCell
     * @param destinationFile int file position of the second ChessCell
     * @return true if there are other ChessPieces in between the given ChessCells
     */
    private boolean obstacleExists(final int currentRank, final int currentFile,
                                   final int destinationRank, final int destinationFile){
        int rankToCheck = currentRank;
        int fileToCheck = currentFile;
        while (rankToCheck != destinationRank || fileToCheck != destinationFile){
            logger.info("Checking if there is an obstacle on rank " + rankToCheck + " file " + fileToCheck);
            int rankDifference = destinationRank - currentRank;
            int fileDifference = destinationFile - currentFile;
            if (rankDifference == 0) fileToCheck += (fileDifference / Math.abs(fileDifference));
            else if (currentFile == destinationFile) rankToCheck += (rankDifference / Math.abs(rankDifference));
            else {
                fileToCheck += (fileDifference / Math.abs(fileDifference));
                rankToCheck += (rankDifference / Math.abs(rankDifference));
            }
            if (rankToCheck == destinationRank && fileToCheck == destinationFile) return false;
            ChessPiece intermediateChessPiece = getChessPiece(rankToCheck, fileToCheck);
            if (intermediateChessPiece != null) return true;
        }
        return false;
    }

    /**
     * Removes a ChessPiece from a specified ChessCell.
     * @param rank int rank position of the ChessCell whose ChessPiece will be removed
     * @param file int file position of the ChessCell whose ChessPiece will be removed
     */
    private void unsetChessPiece(int rank, int file) {
        setChessPiece(null, rank, file, false);
    }

    /**
     * Returns the ChessCell of a specified position on the ChessBoard.
     * @param rank int rank position of the ChessCell to be retrieved
     * @param file int file position of the ChessCell to be retrieved
     * @return ChessCell of the specified position
     */
    public ChessCell getChessCell(int rank, int file){
        if (rank >= rankSize || rank < 0 || file >= fileSize || file < 0){
            logger.info("Getting chess from out of bounds");
            return null;
        }
        return chessCells[rank][file];
    }

    /**
     * Returns the ChessPiece of a specified position on the ChessBoard.
     * @param rank int rank position of the ChessPiece to be retrieved
     * @param file int file position of the ChessPiece to be retrieved
     * @return ChessPiece of the specified position
     */
    public ChessPiece getChessPiece(int rank, int file){
        if (rank >= rankSize || file >= fileSize){
            System.out.println("Out of bounds");
            return null;
        }
        else {
            return getChessCell(rank, file).getChessPiece();
        }
    }

    /**
     * Getter for the ChessBoard rankSize
     * @return rankSize
     */
    public int getRankSize() {
        return rankSize;
    }

    /**
     * Getter for the ChessBoard fileSize
     * @return fileSize
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * Getter for isCheck
     * @return isCheck
     */
    public boolean isCheck() {
        return isCheck;
    }

    /**
     * Get the color of the current turn
     * @return current Color
     */
    public ChessPiece.ChessPieceColor getCurrentTurnsColor() {
        return currentTurnsColor;
    }

    /**
     * Set the color of the current turn
     */
    public void setCurrentTurnsColor(ChessPiece.ChessPieceColor currentTurnsColor) {
        this.currentTurnsColor = currentTurnsColor;
    }

    /**
     * Sets up a ChessBoard with the traditional ChessPieces and positions.
     */
    private void setUpDefaultChessBoard(){
        logger.info("Setting up a default chessboard");
        setUpDefaultPawns();
        setUpDefaultRooks();
        setUpDefaultKnights();
        setUpDefaultBishops();
        setUpDefaultQueens();
        setUpDefaultKings();
    }

    /**
     * Sets up a ChessBoard with the traditional ChessPieces and positions.
     */
    private void setUpCustomChessBoard(){
        logger.info("Setting up a custom chessboard");
        setUpDefaultPawns();
        setChessPiece(new Kirby(ChessPiece.ChessPieceColor.WHITE), 1, 1, false);
        setChessPiece(new Kirby(ChessPiece.ChessPieceColor.BLACK), rankSize-2, 1, false);
        setChessPiece(new Tank(ChessPiece.ChessPieceColor.WHITE), 1, fileSize-2, false);
        setChessPiece(new Tank(ChessPiece.ChessPieceColor.BLACK), rankSize-2, fileSize-2, false);
        setUpDefaultRooks();
        setUpDefaultKnights();
        setUpDefaultBishops();
        setUpDefaultQueens();
        setUpDefaultKings();
    }

    /**
     * Sets up the Pawns for the traditional ChessBoard set up.
     */
    private void setUpDefaultPawns(){
        logger.info("Setting up default pawns");
        for (int file = 0; file < fileSize; file++){
            logger.info("Setting up a white pawn on file " + file);
            setChessPiece(new Pawn(ChessPiece.ChessPieceColor.WHITE), 1, file, false);
            logger.info("Setting up a black pawn on file " + file);
            setChessPiece(new Pawn(ChessPiece.ChessPieceColor.BLACK), rankSize-2, file, false);
        }
    }

    /**
     * Sets up the Rooks for the traditional ChessBoard set up.
     */
    private void setUpDefaultRooks(){
        logger.info("Setting up default rooks");
        setChessPiece(new Rook(ChessPiece.ChessPieceColor.WHITE), 0, 0, false);
        setChessPiece(new Rook(ChessPiece.ChessPieceColor.WHITE), 0, fileSize-1, false);
        setChessPiece(new Rook(ChessPiece.ChessPieceColor.BLACK), rankSize-1, 0, false);
        setChessPiece(new Rook(ChessPiece.ChessPieceColor.BLACK), rankSize-1, fileSize-1, false);
    }

    /**
     * Sets up the Knights for the traditional ChessBoard set up.
     */
    private void setUpDefaultKnights(){
        logger.info("Setting up default knights");
        setChessPiece(new Knight(ChessPiece.ChessPieceColor.WHITE), 0, 1, false);
        setChessPiece(new Knight(ChessPiece.ChessPieceColor.WHITE), 0, fileSize-2, false);
        setChessPiece(new Knight(ChessPiece.ChessPieceColor.BLACK), rankSize-1, 1, false);
        setChessPiece(new Knight(ChessPiece.ChessPieceColor.BLACK), rankSize-1, fileSize-2, false);
    }

    /**
     * Sets up the Bishops for the traditional ChessBoard set up.
     */
    private void setUpDefaultBishops(){
        logger.info("Setting up default bishops");
        setChessPiece(new Bishop(ChessPiece.ChessPieceColor.WHITE), 0, 2, false);
        setChessPiece(new Bishop(ChessPiece.ChessPieceColor.WHITE), 0, fileSize-3, false);
        setChessPiece(new Bishop(ChessPiece.ChessPieceColor.BLACK), rankSize-1, 2, false);
        setChessPiece(new Bishop(ChessPiece.ChessPieceColor.BLACK), rankSize-1, fileSize-3, false);
    }

    /**
     * Sets up the Queens for the traditional ChessBoard set up.
     */
    private void setUpDefaultQueens(){
        logger.info("Setting up default queens");
        setChessPiece(new Queen(ChessPiece.ChessPieceColor.WHITE), 0, 3, false);
        setChessPiece(new Queen(ChessPiece.ChessPieceColor.BLACK), rankSize-1, 3, false);
    }

    /**
     * Sets up the Kings for the traditional ChessBoard set up.
     */
    private void setUpDefaultKings(){
        logger.info("Setting up default kings");
        setChessPiece(new King(ChessPiece.ChessPieceColor.WHITE), 0, 4, false);
        setChessPiece(new King(ChessPiece.ChessPieceColor.BLACK), rankSize-1, 4);
    }
}
