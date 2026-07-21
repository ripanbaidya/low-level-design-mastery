package tic_tac_toe;

import tic_tac_toe.entities.Game;
import tic_tac_toe.entities.Player;
import tic_tac_toe.enums.Symbol;
import tic_tac_toe.exception.InvalidMoveException;
import tic_tac_toe.strategy.WinningStrategy;
import tic_tac_toe.strategy.impl.ColumnWinningStrategy;
import tic_tac_toe.strategy.impl.DiagonalWinningStrategy;
import tic_tac_toe.strategy.impl.RowWinningStrategy;

import java.util.List;

public class TicTacToeTest {
    public static void main(String[] args) {
        // Test1: Win
        testWinningGame();

        // Test2: Invalid Move
        testInvalidMove();

        // Test3: Draw
        testDrawGame();
    }

    private static void testWinningGame() {
        Player alice = new Player("Alice", Symbol.X);
        Player bob = new Player("Bob", Symbol.O);

        Game game = new Game(alice, bob, 3);

        game.makeMove(0, 0); // Move 1: Alice (X) -> (0,0)
        game.makeMove(1, 0); // Move 2: Bob (O)   -> (1,0)
        game.makeMove(0, 1); // Move 3: Alice (X) -> (0,1)
        game.makeMove(1, 1); // Move 4: Bob (O)   -> (1,1)
        game.makeMove(0, 2); // Move 5: Alice (X) -> (0,2) [Winning Move!]

        // Expected Output: GameStatus.WINNER_X
        System.out.println("Game Status: " + game.getStatus());
    }

    private static void testInvalidMove() {
        Player alice = new Player("Alice", Symbol.X);
        Player bob = new Player("Bob", Symbol.O);

        // Custom injection of strategies
        List<WinningStrategy> strategies = List.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy()
        );
        Game game = new Game(alice, bob, 3, strategies);

        game.makeMove(0, 0); // Alice plays (0,0)

        try {
            System.out.println("Bob attempting to play on already occupied (0,0)...");
            game.makeMove(0, 0); // Bob attempts to play same cell
        } catch (InvalidMoveException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }
    }

    private static void testDrawGame() {
        Player alice = new Player("Alice", Symbol.X);
        Player bob = new Player("Bob", Symbol.O);
        Game game = new Game(alice, bob, 3);

        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(0, 2); // X
        game.makeMove(1, 1); // O
        game.makeMove(1, 0); // X
        game.makeMove(1, 2); // O
        game.makeMove(2, 1); // X
        game.makeMove(2, 0); // O
        game.makeMove(2, 2); // X

        System.out.println("Game Status: " + game.getStatus());
        // Expected Output: GameStatus.DRAW
    }
}
