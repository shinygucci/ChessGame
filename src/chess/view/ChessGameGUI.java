package chess.view;

import chess.controller.ChessGameController;
import chess.model.chessBoard.ChessBoard;
import chess.model.chessBoard.ChessCell;
import chess.model.chessPiece.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A chessboard GUI class.
 */
public class ChessGameGUI extends JFrame {
    private static final String TITLE = "Chess Game";
    private ChessBoard CHESS_BOARD;
    private final ChessGameController CHESS_GAME_CONTROLLER;
    private final int ROW_SIZE;
    private final int COLUMN_SIZE;

    private final JPanel chessBoardPanel;

    private final JPanel chessGameActionPanel;

    private final JPanel middleInfoDisplayPanel;
    private final JLabel turnLabel;
    private final JLabel previousMoveInfoLabel;
    private final JLabel nextMoveInfoLabel;

    private final JPanel topPlayerPanel;
    private final JLabel topPlayerColorLabel;
    private final JTextField topPlayerNameTA;
    private final JLabel topPlayerPoints;

    private final JPanel bottomPlayerPanel;
    private final JLabel bottomPlayerColorLabel;
    private final JTextField bottomPlayerNameTA;
    private final JLabel bottomPlayerPoints;

    private final JPanel buttonsPanel;
    private final JButton undoButton;
    private final JButton redoButton;
    private final JButton emptyButton;
    private final JButton startGameButton;
    private final JButton forfeitButton;
    private final JButton startCustomGameButton;

    private CellButton[][] cellButtons;

    /**
     * Constructor for the GUI of the chessboard.
     */
    public ChessGameGUI(ChessBoard chessBoard, ChessGameController chessGameController) {
        super(TITLE);
        UIManager.put("TextField.font", new Font("Dialog", Font.BOLD, 18));
        UIManager.put("TextArea.font", new Font("Dialog", Font.BOLD, 18));
        UIManager.put("Label.font", new Font("Dialog", Font.BOLD, 18));
        CHESS_BOARD = chessBoard;
        CHESS_GAME_CONTROLLER = chessGameController;
        ROW_SIZE = chessBoard.getRankSize();
        COLUMN_SIZE = chessBoard.getFileSize();
        setPreferredSize(new Dimension(1280, 640));
        setResizable(false);

        chessBoardPanel = new JPanel(new GridLayout(ROW_SIZE, COLUMN_SIZE));
        chessGameActionPanel = new JPanel(new GridBagLayout());
        middleInfoDisplayPanel = new JPanel();
        previousMoveInfoLabel = new JLabel(" ");
        turnLabel = new JLabel();
        nextMoveInfoLabel = new JLabel();
        topPlayerPanel = new JPanel();
        topPlayerColorLabel = new JLabel("Black: ");
        topPlayerNameTA = new JTextField("Player 1");
        topPlayerPoints = new JLabel();
        bottomPlayerPanel = new JPanel();
        bottomPlayerColorLabel = new JLabel("White: ");
        bottomPlayerNameTA = new JTextField("Player 2");
        bottomPlayerPoints = new JLabel();
        buttonsPanel = new JPanel(new GridLayout(2, 3));
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        emptyButton = new JButton("empty");
        startGameButton = new JButton("Request Tie");
        forfeitButton = new JButton("Forfeit");
        startCustomGameButton = new JButton("Custom Game");

        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CHESS_GAME_CONTROLLER.undoButtonClicked();
            }
        });

        redoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CHESS_GAME_CONTROLLER.redoButtonClicked();
            }
        });

        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CHESS_GAME_CONTROLLER.startGameButtonClicked(0);
            }
        });

        forfeitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CHESS_GAME_CONTROLLER.forfeitButtonClicked(0);
            }
        });

        startCustomGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CHESS_GAME_CONTROLLER.startGameButtonClicked(2);
            }
        });

        setupGUI();

        setUpChessBoard();
    }

    /**
     * Sets up and initializes all the swing components
     */
    private void setupGUI(){
        middleInfoDisplayPanel.setLayout(new BoxLayout(middleInfoDisplayPanel, BoxLayout.PAGE_AXIS));
        GridBagConstraints middlePanelConstraints = new GridBagConstraints();
        middlePanelConstraints.gridy = 1;

        previousMoveInfoLabel.setAlignmentX(CENTER_ALIGNMENT);
        middleInfoDisplayPanel.add(previousMoveInfoLabel);

        turnLabel.setAlignmentX(CENTER_ALIGNMENT);
        middleInfoDisplayPanel.add(turnLabel);

        nextMoveInfoLabel.setAlignmentX(CENTER_ALIGNMENT);
        middleInfoDisplayPanel.add(nextMoveInfoLabel);


        topPlayerPanel.setLayout(new BoxLayout(topPlayerPanel, BoxLayout.LINE_AXIS));
        GridBagConstraints topPlayerJPanelConstraints = new GridBagConstraints();
        topPlayerJPanelConstraints.weighty = 0.5;
        topPlayerJPanelConstraints.gridy = 0;

        topPlayerNameTA.setOpaque(false);
        topPlayerNameTA.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        topPlayerPanel.add(topPlayerColorLabel);
        topPlayerPanel.add(topPlayerNameTA);
        topPlayerPanel.add(topPlayerPoints);


        bottomPlayerPanel.setLayout(new BoxLayout(bottomPlayerPanel, BoxLayout.LINE_AXIS));
        GridBagConstraints bottomPlayerJPanelConstraints = new GridBagConstraints();
        bottomPlayerJPanelConstraints.weighty = 0.5;
        bottomPlayerJPanelConstraints.gridy = 2;

        bottomPlayerNameTA.setOpaque(false);
        bottomPlayerNameTA.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        bottomPlayerPanel.add(bottomPlayerColorLabel);
        bottomPlayerPanel.add(bottomPlayerNameTA);
        bottomPlayerPanel.add(bottomPlayerPoints);


        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridy = 3;
        buttonConstraints.insets = new Insets(0, 0, 10, 0);
        buttonsPanel.add(emptyButton);
        emptyButton.setVisible(false);
        buttonsPanel.add(undoButton);
        buttonsPanel.add(redoButton);
        buttonsPanel.add(startCustomGameButton);
        startCustomGameButton.setVisible(false);
        buttonsPanel.add(startGameButton);
        buttonsPanel.add(forfeitButton);


        chessGameActionPanel.add(middleInfoDisplayPanel, middlePanelConstraints);
        chessGameActionPanel.add(topPlayerPanel, topPlayerJPanelConstraints);
        chessGameActionPanel.add(bottomPlayerPanel, bottomPlayerJPanelConstraints);
        chessGameActionPanel.add(buttonsPanel, buttonConstraints);
        add(chessBoardPanel, BorderLayout.LINE_START);
        add(chessGameActionPanel);

        pack();
    }

    /**
     * Sets up the chessBoard
     */
    private void setUpChessBoard() {
        cellButtons = new CellButton[ROW_SIZE][COLUMN_SIZE];
        startCustomGameButton.setVisible(true);

        initializeCellButtons();
        updateChessPieces();

        setVisible(true);
    }

    /**
     * Creates a chessboard composed of JButton cellButtons
     */
    private void initializeCellButtons(){
        Color firstColor = Color.LIGHT_GRAY;
        Color secondColor = Color.ORANGE;
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column += 2) {
                int rank = ROW_SIZE - row - 1;
                int file = column;

                ChessCell firstChessCell = CHESS_BOARD.getChessCell(rank, file);
                ChessCell secondChessCell = CHESS_BOARD.getChessCell(rank, file + 1);
                cellButtons[row][column] = createCell(firstChessCell, rank, file, firstColor);
                cellButtons[row][column+1] = createCell(secondChessCell, rank, file + 1, secondColor);
            }
            Color temp = firstColor;
            firstColor = secondColor;
            secondColor = temp;
        }
    }

    /**
     * Destroys all the cellButtons so they can be reinitialized for a new game.
     */
    private void destroyCellButtons(){
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column ++) {
                int rank = ROW_SIZE - row - 1;
                int file = column;

                chessBoardPanel.remove(cellButtons[row][column]);
            }
        }
    }

    /**
     * Creates a cell for the chessboard
     * @param color background color of the cell
     * @return JButton that represents a cell
     */
    private CellButton createCell(ChessCell chessCell, int rank, int file, Color color){
        CellButton cellButton = new CellButton(chessCell, rank, file);
        cellButton.setFont(new Font("Courier", Font.PLAIN,40));
        cellButton.setBackground(color);
        chessBoardPanel.add(cellButton);
        return cellButton;
    }

    /**
     * Sets the appropriate unicode chess symbol on each ChessCells.
     */
    public void updateChessPieces(){
        for (int rank = 0; rank < ROW_SIZE; rank++) {
            for (int file = 0; file < COLUMN_SIZE; file ++) {
                ChessPiece chessPiece = cellButtons[rank][file].getChessCell().getChessPiece();
                if(chessPiece == null){
                    cellButtons[rank][file].setText("");
                } else {
                    String chessPieceUnicode = getChessPieceUnicode(chessPiece);
                    cellButtons[rank][file].setText(chessPieceUnicode);
                }
            }
        }
    }

    /**
     * Calculates the unicode symbol for each chess piece.
     * @param chessPiece whose symbol will be calculated
     * @return appropriate unicode symbol
     */
    private String getChessPieceUnicode(ChessPiece chessPiece){
        if(chessPiece.getChessPieceColor() == ChessPiece.ChessPieceColor.WHITE){
            switch (chessPiece.getClass().getSimpleName()){
                case "Pawn":
                    return "\u2659";
                case "Rook":
                    return "\u2656";
                case "Knight":
                    return "\u2658";
                case "Bishop":
                    return "\u2657";
                case "Queen":
                    return "\u2655";
                case "King":
                    return "\u2654";
                case "Kirby":
                    return "\u2664";
                case "Tank":
                    return "\u2661";
                default:
                    return "?";
            }
        } else {
            switch (chessPiece.getClass().getSimpleName()) {
                case "Pawn":
                    return "\u265F";
                case "Rook":
                    return "\u265C";
                case "Knight":
                    return "\u265E";
                case "Bishop":
                    return "\u265D";
                case "Queen":
                    return "\u265B";
                case "King":
                    return "\u265A";
                case "Kirby":
                    return "\u2660";
                case "Tank":
                    return "\u2665";
                default:
                    return "?";
            }
        }
    }

    /**
     * CellButton class to be used for each ChessCells.
     */
    private class CellButton extends JButton{
        private final ChessCell chessCell;
        private final int rank;
        private final int file;

        CellButton(ChessCell chessCell, int rank, int file){
            super();
            this.chessCell = chessCell;
            this.rank = rank;
            this.file = file;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    startCustomGameButton.setVisible(false);
                    super.mouseClicked(e);
                    CHESS_GAME_CONTROLLER.chessCellClicked(rank, file);
                }
            });
        }

        public ChessCell getChessCell() {
            return chessCell;
        }
    }

    /**
     * Sets the border color of a ChessCell.
     * @param rank int of the ChessCell
     * @param file int of the ChessCell
     * @param color the new border color
     */
    public void setChessCellBorder(int rank, int file, Color color){
        cellButtons[ROW_SIZE - rank - 1][file].setBorder(BorderFactory.createLineBorder(color));
    }

    /**
     * Sets a text for turnLabel
     * @param text to be displayed
     */
    public void setTurnLabelText(String text){
        turnLabel.setText(text);
    }

    /**
     * Sets a text for previousMoveInfoLabel
     * @param moveInfo to be displayed
     */
    public void setPreviousMoveInfoLabelText(String moveInfo){
        previousMoveInfoLabel.setText(moveInfo);
    }

    /**
     * Clears the text for previousMoveInfoLabel
     */
    public void clearPreviousMoveInfoLabelText(){
        previousMoveInfoLabel.setText(" ");
    }

    /**
     * Sets the text for nextMoveInfoLabel.
     * @param moveInfo new text to be set
     */
    public void setNextMoveInfoLabelText(String moveInfo){
        nextMoveInfoLabel.setText(moveInfo);
    }

    /**
     * Clears the text for nextMoveInfoLabel
     */
    public void clearNextMoveInfoLabelText(){
        nextMoveInfoLabel.setText(" ");
    }

    /**
     * Sets the text for startGameButton.
     * @param text new text to be set
     */
    public void setStartGameButtonText(String text){
        startGameButton.setText(text);
    }

    /**
     * Sets the text for topPlayerPoints JLabel.
     * @param points new points to be set
     */
    public void setTopPlayerPoints(int points){
        topPlayerPoints.setText(Integer.toString(points));
    }

    /**
     * Sets the text for bottomPlayerPoints JLabel.
     * @param points new points to be set
     */
    public void setBottomPlayerPoints(int points){
        bottomPlayerPoints.setText(Integer.toString(points));
    }

    /**
     * Sets up a ChessBoard to the GUI.
     * @param chessBoard ChessBoard that will be connected.
     */
    public void setChessBoard(ChessBoard chessBoard) {
        CHESS_BOARD = chessBoard;
        destroyCellButtons();
        setUpChessBoard();
    }
}
