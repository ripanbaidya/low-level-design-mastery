package tic_tac_toe.strategy.impl;

import tic_tac_toe.entities.Board;
import tic_tac_toe.enums.Symbol;
import tic_tac_toe.strategy.WinningStrategy;

public class RowWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWin(Board board, int lastRow, int lastCol, Symbol symbol) {
        int size = board.getSize();

        for (int col = 0; col < size; col++) {
            if (board.getCell(lastRow, col).getSymbol() != symbol) {
                return false;
            }
        }

        return true;
    }
}
