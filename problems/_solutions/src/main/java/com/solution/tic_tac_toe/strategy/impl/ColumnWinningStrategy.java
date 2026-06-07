package com.solution.tic_tac_toe.strategy.impl;

import com.solution.tic_tac_toe.entities.Board;
import com.solution.tic_tac_toe.enums.Symbol;
import com.solution.tic_tac_toe.strategy.WinningStrategy;

public class ColumnWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int row, int col, Symbol symbol) {
        for (int r = 0; r < board.getSize(); r++) {
            if (board.getCell(r, col).getSymbol() != symbol)
                return false;
        }
        return true;
    }
}
