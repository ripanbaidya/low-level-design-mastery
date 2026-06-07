package com.solution.tic_tac_toe;

import com.solution.tic_tac_toe.entities.Player;
import com.solution.tic_tac_toe.enums.Symbol;

public class TicTacToeTest {
    public static void main(String[] args) {
        TicTacToeSystem system = TicTacToeSystem.getInstance();

        Player john = new Player("John", Symbol.X);
        Player luke = new Player("Luke", Symbol.O);

        // Game 1: John wins - Rows/Horizontal
        System.out.println("========== GAME 1 ==========");
        system.createGame(john, luke);

        system.makeMove(john, 0, 0);  // X at (0,0)
        system.makeMove(luke, 1, 0);  // O at (1,0)
        system.makeMove(john, 0, 1);  // X at (0,1)
        system.makeMove(luke, 1, 1);  // O at (1,1)
        system.makeMove(john, 0, 2);  // X at (0,2) - John wins

        System.out.println("Game 1 Result: " + system.getGameStatus());

        // Game 2: Luke wins - Diagonals (anti-diagonal)
        System.out.println("\n========== GAME 2 ==========");
        system.createGame(john, luke);

        system.makeMove(john, 0, 0);  // X at (0,0)
        system.makeMove(luke, 1, 1);  // O at (1,1)
        system.makeMove(john, 0, 1);  // X at (0,1)
        system.makeMove(luke, 0, 2);  // O at (0,2)
        system.makeMove(john, 2, 1);  // X at (2,1)
        system.makeMove(luke, 2, 0);  // O at (2,0) - Luke wins

        System.out.println("Game 2 Result: " + system.getGameStatus());

        // Game 3: John wins - Column (Vertical)
        System.out.println("\n========== GAME 3 ==========");
        system.createGame(john, luke);

        system.makeMove(john, 0, 0);  // X at (0,0)
        system.makeMove(luke, 1, 1);  // O at (1,1)
        system.makeMove(john, 1, 0);  // X at (1,0)
        system.makeMove(luke, 0, 2);  // O at (0,2)
        system.makeMove(john, 2, 0);  // X at (2,0) - John wins

        System.out.println("Game 3 Result: " + system.getGameStatus());
    }
}
