package tic_tac_toe.strategy.impl;

import tic_tac_toe.entities.Board;
import tic_tac_toe.enums.Symbol;
import tic_tac_toe.strategy.WinningStrategy;

public class ColumnWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int lastRow, int lastCol, Symbol symbol) {
        int size = board.getSize();

        for (int row = 0; row < size; row++) {
            if (board.getCell(row, lastCol).getSymbol() != symbol) {
                return false;
            }
        }

        return true;
    }
}
