package com.solution.tic_tac_toe;

import com.solution.tic_tac_toe.entities.Game;
import com.solution.tic_tac_toe.entities.Player;
import com.solution.tic_tac_toe.enums.GameStatus;
import com.solution.tic_tac_toe.observer.impl.Scoreboard;

/**
 * The system class is an entry point. External code only needs to know about the system class.
 * The system class is responsible for creating and managing the game.
 */
public class TicTacToeSystem {
    private static volatile TicTacToeSystem instance;
    private static final Object lock = new Object();

    private final Scoreboard scoreboard;
    private Game currentGame;

    private TicTacToeSystem() {
        this.scoreboard = new Scoreboard();
    }

    public static TicTacToeSystem getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new TicTacToeSystem();
                }
            }
        }
        return instance;
    }

    public Game createGame(Player player1, Player player2) {
        currentGame = new Game(player1, player2, 3);
        currentGame.addObserver(scoreboard);
        System.out.println("New Game Started: " + player1.getName() + " vs " + player2.getName());
        return currentGame;
    }

    public void makeMove(Player player, int row, int col) {
        if (currentGame == null) {
            throw new IllegalStateException("No active game");
        }
        System.out.println("Player " + player.getName() + " is making move." + "[" + row + ", " + col + "]");
        // Delegate the move to the game and validate the player/turn inside Game
        currentGame.makeMove(player, row, col);
        currentGame.printBoard();
    }

    public GameStatus getGameStatus() {
        if (currentGame == null) {
            throw new IllegalStateException("No active game");
        }
        return currentGame.getStatus();
    }
}
