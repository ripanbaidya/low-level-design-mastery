package tic_tac_toe.exception;

/**
 * Custom exception class for invalid moves.
 */
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
