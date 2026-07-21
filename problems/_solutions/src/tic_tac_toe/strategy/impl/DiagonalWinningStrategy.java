package tic_tac_toe.strategy.impl;

import tic_tac_toe.entities.Board;
import tic_tac_toe.enums.Symbol;
import tic_tac_toe.strategy.WinningStrategy;

public class DiagonalWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int lastRow, int lastCol, Symbol symbol) {
        int size = board.getSize();

        // Check main diagonal (top-left to bottom-right)
        if (lastRow == lastCol) {
            boolean mainDiagonalWin = true;
            for (int i = 0; i < size; i++) {
                if (board.getCell(i, i).getSymbol() != symbol) {
                    mainDiagonalWin = false;
                    break;
                }
            }

            if (mainDiagonalWin) return true;
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (lastRow + lastCol == size - 1) {
            boolean antiDiagonalWin = true;
            for (int i = 0; i < size; i++) {
                if (board.getCell(i, size - 1 - i).getSymbol() != symbol) {
                    antiDiagonalWin = false;
                    break;
                }
            }
            if (antiDiagonalWin) return true;
        }

        return false;
    }
}
