package tic_tac_toe.enums;

/**
 * Represents a symbol used in The Tic-Tac-Toe game.
 * The symbols are X, O, and the Empty symbol.
 * Each symbol will belong to a player, and the Empty symbol will be used to represent
 * an empty cell in the game board.
 */
public enum Symbol {
    X('X'),
    O('O'),
    EMPTY('_');

    private final char displayChar;

    Symbol(char displayChar) {
        this.displayChar = displayChar;
    }
}
