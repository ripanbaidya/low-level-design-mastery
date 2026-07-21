package tic_tac_toe.entities;

import tic_tac_toe.enums.GameStatus;
import tic_tac_toe.enums.Symbol;
import tic_tac_toe.exception.InvalidMoveException;
import tic_tac_toe.observer.GameObserver;
import tic_tac_toe.strategy.WinningStrategy;
import tic_tac_toe.strategy.impl.ColumnWinningStrategy;
import tic_tac_toe.strategy.impl.DiagonalWinningStrategy;
import tic_tac_toe.strategy.impl.RowWinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final Player[] players;
    private GameStatus status;
    private int currentPlayerIndex;

    // List of active winning strategies
    private final List<WinningStrategy> winningStrategies;

    // Observer registry
    private final List<GameObserver> observers = new ArrayList<>();

    public Game(Player player1, Player player2, int boardSize,
                List<WinningStrategy> winningStrategies) {

        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.status = GameStatus.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    public Game(Player p1, Player p2, int boardSize) {
        this(p1, p2, boardSize, List.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy()
        ));
    }

    public void addObserver(GameObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        this.observers.remove(observer);
    }

    public synchronized void makeMove(int row, int col) {
        // Check if the game is still in progress
        if (status != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is over.");
        }

        // Check if the cell already has a symbol
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell is already occupied.");
        }

        // Place the symbol of the current player on the board
        Player currentPlayer = players[currentPlayerIndex];
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        // Check for win using the Strategy Pattern
        if (checkWin(row, col, currentPlayer.getSymbol())) {
            status = (currentPlayer.getSymbol() == Symbol.X)
                    ? GameStatus.X_WINNER
                    : GameStatus.O_WINNER;
            notifyGameOver(currentPlayer); // Notify observers
            return;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            notifyGameOver(null);
            return;
        }

        // Switch turn
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    public GameStatus getStatus() {
        return status;
    }

    /**
     * Delegates win checking to all registered winning strategies.
     */
    private boolean checkWin(int row, int col, Symbol symbol) {
        for (WinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(board, row, col, symbol)) {
                // Return early on the first matching win condition
                return true;
            }
        }
        return false;
    }

    private void notifyGameOver(Player winner) {
        observers.forEach(observer -> observer.onGameOver(this.status, winner));
    }
}
