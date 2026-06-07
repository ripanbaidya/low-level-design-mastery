package com.solution.tic_tac_toe.entities;

import com.solution.tic_tac_toe.enums.Symbol;

public class Board {
    private final int size;
    private final Cell[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        initializeBoard();
    }

    public void placeSymbol(int row, int col, Symbol symbol) {
        validatePosition(row, col);
        grid[row][col].setSymbol(symbol);
    }

    public boolean isCellEmpty(int row, int col) {
        validatePosition(row, col);
        return grid[row][col].isEmpty();
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j].getSymbol().getDisplayChar() + " ");
            }
            System.out.println();
        }
    }

    // Getters

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        validatePosition(row, col);
        return grid[row][col];
    }

    // Helpers

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void validatePosition(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Invalid position");
        }
    }
}
