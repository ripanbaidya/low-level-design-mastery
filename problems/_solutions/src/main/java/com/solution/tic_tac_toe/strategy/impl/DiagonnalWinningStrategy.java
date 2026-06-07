package com.solution.tic_tac_toe.strategy.impl;

import com.solution.tic_tac_toe.entities.Board;
import com.solution.tic_tac_toe.enums.Symbol;
import com.solution.tic_tac_toe.strategy.WinningStrategy;

public class DiagonnalWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int row, int col, Symbol symbol) {
        int size = board.getSize();

        // Main diagonal (top-left to bottom-right)
        boolean isMainDiagonalWin = true;
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, i).getSymbol() != symbol) {
                isMainDiagonalWin = false;
                break;
            }
        }
        if (isMainDiagonalWin) return true;

        // Anti-diagonal (top-right to bottom-left)
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, size - 1 - i).getSymbol() != symbol) {
                return false;
            }
        }

        return true;
    }
}
