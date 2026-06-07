package com.solution.tic_tac_toe.entities;

import com.solution.tic_tac_toe.enums.GameStatus;
import com.solution.tic_tac_toe.enums.Symbol;
import com.solution.tic_tac_toe.exception.InvalidMoveException;
import com.solution.tic_tac_toe.observer.GameObserver;
import com.solution.tic_tac_toe.strategy.WinningStrategy;
import com.solution.tic_tac_toe.strategy.impl.ColumnWinningStrategy;
import com.solution.tic_tac_toe.strategy.impl.DiagonnalWinningStrategy;
import com.solution.tic_tac_toe.strategy.impl.RowWinningStrategy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private final List<WinningStrategy> winningStrategies;
    private final List<GameObserver> observers;

    public Game(Player player1, Player player2, int boardSize) {
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.currentPlayerIndex = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.winningStrategies = initializeStrategies();
        this.observers = new CopyOnWriteArrayList<>();
    }

    /**
     * Make a move for the given player at the specified row and column.
     *
     * @param player The player making the move.
     * @param row The row where the move is being made.
     * @param col The column where the move is being made.
     */
    public synchronized void makeMove(Player player, int row, int col) {
        // Game is already over
        if (gameStatus != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is already over.");
        }

        // Validate it's the correct player's turn
        Player currentPlayer = players[currentPlayerIndex];
        if (!currentPlayer.equals(player)) {
            throw new InvalidMoveException("It's not " + player.getName() + "'s turn.");
        }

        // Check is cell is already occupied
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell is already occupied.");
        }

        // Place the symbol on the board
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        // check win
        if (checkWin(row, col, currentPlayer.getSymbol())) {
            gameStatus = currentPlayer.getSymbol() == Symbol.X
                    ? GameStatus.WINNER_X : GameStatus.WINNER_O;
            notifyObservers();
            return;
        }

        // Check draw
        if (board.isBoardFull()) {
            gameStatus = GameStatus.DRAW;
            notifyObservers();
            return;
        }

        // Switch to the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    public GameStatus getStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        if (gameStatus == GameStatus.WINNER_X) {
            return players[0].getSymbol() == Symbol.X ? players[0] : players[1];
        } else if (gameStatus == GameStatus.WINNER_O) {
            return players[0].getSymbol() == Symbol.O ? players[0] : players[1];
        }
        return null;
    }

    public void printBoard() {
        board.printBoard();
    }

    // Helper methods

    private boolean checkWin(int row, int col, Symbol symbol) {
        return winningStrategies.stream().anyMatch(strategy -> strategy.checkWin(board, row, col, symbol));
    }

    private List<WinningStrategy> initializeStrategies() {
        return List.of(new RowWinningStrategy(), new ColumnWinningStrategy(), new DiagonnalWinningStrategy());
    }

}
