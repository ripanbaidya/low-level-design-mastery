package tic_tac_toe.strategy;

import tic_tac_toe.entities.Board;
import tic_tac_toe.enums.Symbol;

public interface WinningStrategy {

    /**
     * Checks if the last move resulted in a win according to this strategy.
     *
     * @param board   The current state of the board
     * @param lastRow Row index of the last placed symbol
     * @param lastCol Column index of the last placed symbol
     * @param symbol  The symbol placed (X or O)
     * @return true if this strategy detects a win
     */
    boolean checkWin(Board board, int lastRow, int lastCol, Symbol symbol);
}
