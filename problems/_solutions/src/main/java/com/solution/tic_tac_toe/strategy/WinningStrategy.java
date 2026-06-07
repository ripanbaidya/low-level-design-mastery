package com.solution.tic_tac_toe.strategy;

import com.solution.tic_tac_toe.entities.Board;
import com.solution.tic_tac_toe.enums.Symbol;

public interface WinningStrategy {

    boolean checkWin(Board board, int row, int col, Symbol symbol);
}
