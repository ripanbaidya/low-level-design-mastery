package tic_tac_toe.entities;

import tic_tac_toe.enums.Symbol;
import tic_tac_toe.exception.InvalidMoveException;

/**
 * Represents the Tic-Tac-Toe board, It encapsulates all grid operations.
 * It doesn't know about players, turns, or game rules.
 * It just manages a 2D array of cells.
 */
public class Board {
    private final int size; // Size of the board (N x N)
    private final Cell[][] grid; // 2D array representing the board

    public Board(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        initializeCells();
    }

    /**
     * Places a symbol at the specified position on the board.
     * Before placing the symbol, we are validating the position.
     * Please note that, While validating the position, we are not checking whether that particular cell
     * is already occupied by another symbol or not. This is intentional.
     * We will do that validation while making the move in {@link Game} class.
     */
    public void placeSymbol(int row, int col, Symbol symbol) {
        validatePosition(row, col);
        grid[row][col].setSymbol(symbol);
    }

    /**
     * Check if the cell at the specified position is empty or occupied by a symbol.
     */
    public boolean isCellEmpty(int row, int col) {
        validatePosition(row, col);
        return grid[row][col].isEmpty();
    }

    /**
     * Get the cell at the specified position.
     */
    public Cell getCell(int row, int col) {
        validatePosition(row, col);
        return grid[row][col];
    }

    /**
     * Check whether the board is full or not, If any of the cells is empty
     * means the board is not full.
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Print the board to the console.
     */
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.println(grid[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    // Helper methods

    /**
     * Initializes the cells of the board with empty symbols.
     */
    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * Validates the position of the cell by checking if it is within the bounds of the board.
     */
    private boolean validatePosition(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new InvalidMoveException("Invalid Move: Position out of bounds.");
        }
        return true;
    }
}
