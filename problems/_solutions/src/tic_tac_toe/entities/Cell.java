package tic_tac_toe.entities;

import tic_tac_toe.enums.Symbol;

/**
 * Tic-Tac-Toe game is played on a 3x3 or NxN grid.
 * To represent a cell in the grid, we use this class.
 * Each cell will have a symbol, which can be X, O, or Empty.
 */
public class Cell {
    private Symbol symbol;

    /**
     * We are intentionally not passing a symbol to the constructor.
     * Because we want the cell to be empty by default when it is created for the first
     * time.
     */
    public Cell() {
        this.symbol = Symbol.EMPTY;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Checks if the cell is empty.
     *
     * @return true if the cell is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.symbol == Symbol.EMPTY;
    }
}
