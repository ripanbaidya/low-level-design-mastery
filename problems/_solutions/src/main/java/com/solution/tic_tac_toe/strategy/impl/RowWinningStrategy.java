package com.solution.tic_tac_toe.strategy.impl;

import com.solution.tic_tac_toe.entities.Board;
import com.solution.tic_tac_toe.enums.Symbol;
import com.solution.tic_tac_toe.strategy.WinningStrategy;

public class RowWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int row, int col, Symbol symbol) {
        for (int c = 0; c < board.getSize(); c++) {
            if (board.getCell(row, c).getSymbol() != symbol)
                return false;
        }
        return true;
    }
}
