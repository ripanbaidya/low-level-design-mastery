package tic_tac_toe.enums;

/**
 * This enum represents the possible statuses of a Tic-Tac-Toe game.
 */
public enum GameStatus {
    X_WINNER, // Player with symbol X wins.
    O_WINNER, // Player with symbol Y wins.
    DRAW,     // The game ends in a draw.
    IN_PROGRESS; // The game is still in progress.
}
