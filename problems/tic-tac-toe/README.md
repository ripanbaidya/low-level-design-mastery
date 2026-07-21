# Design Tic-Tac-Toe

<p align="right">Last updated - 21.07.2026</p>

This article walks through the end-to-end Low-Level Design (LLD) for a **Tic-Tac-Toe** game following a structured, step-by-step interview approach.

![](/resources/images/problems/steps-to-solve-problem.png)

## Step 1: Requirement Gathering

In an interview setting, start by asking clarifying questions to define functional and non-functional bounds.

### Functional Requirements

- **Board Dimensions:** Configurable grid size (defaults to $3 \times 3$).
- **Players:** 2 players per game (Human vs. Human or Human vs. Bot).
- **Game Rules:**
  - Players take turns placing their assigned symbol (`X` or `O`) on empty cells.
  - A player wins if they fill an entire row, column, or diagonal with their symbol.
  - The game ends in a `DRAW` if all cells are filled and no player wins.

- **Game Mechanics:**
  - Prevent out-of-bound moves or placing symbols on already occupied cells.
  - Track active game state (`IN_PROGRESS`, `X_WINNER`, `O_WINNER`, `DRAW`).

### Non-Functional Requirements

- **Extensibility:** Support custom win conditions, bot difficulties, and event listening without modifying existing core code.
- **Concurrency:** Ensure safe move execution in multi-threaded/concurrent contexts (`synchronized`).

## Step 2: Core Entities & Enums

Based on the requirements, identify the fundamental domain entities and enums:

1. **`Symbol`**: Represents valid markers (`X`, `O`, `EMPTY`).
2. **`GameStatus`**: Represents current game state (`IN_PROGRESS`, `X_WINNER`, `O_WINNER`, `DRAW`).
3. **`Cell`**: Represents an individual spot on the grid holding a `Symbol`.
4. **`Board`**: Manages the 2D matrix of `Cell` objects and boundary validations.
5. **`Player`**: Represents a game participant.
6. **`WinningStrategy`**: Abstraction for evaluate-win algorithms.
7. **`Game`**: The primary orchestrator handling turns, state updates, and rule evaluation.

## Step 3: Class Diagram Overview

![](/resources/images/problems/tic-tac-toe/1.png)

## Step 4: Core Implementation

### Enums & Exceptions

```java
public enum Symbol {
    X('X'),
    O('O'),
    EMPTY('_');

    private final char displayChar;

    Symbol(char displayChar) {
        this.displayChar = displayChar;
    }
}
```

```java
public enum GameStatus {
    X_WINNER,
    O_WINNER,
    DRAW,
    IN_PROGRESS;
}
```

```java
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
```

### Core Data Models: Player, Cell, and Board

```java
public class Player {
    private final String name;
    private final Symbol symbol;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
```

```java
public class Cell {
    private Symbol symbol;

    public Cell() {
        this.symbol = Symbol.EMPTY;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isEmpty() {
        return this.symbol == Symbol.EMPTY;
    }
}
```

```java
public class Board {
    private final int size;
    private final Cell[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        initializeCells();
    }

    /**
     * Places a symbol at the specified position on the board.
     * Before placing the symbol, we are validating the position.
     * Please note that, While validating the position, we are not checking whether
     * that particular cell is already occupied by another symbol or not.
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
```

### Winning Strategies (Strategy Pattern)

```java
public interface WinningStrategy {
    boolean checkWin(Board board, int lastRow, int lastCol, Symbol symbol);
}

```

```java
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

```

```java
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
```

```java
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
```

### Game Class Orchestration

```java
import java.util.List;

public class Game {
    private final Board board;
    private final Player[] players;
    private GameStatus status;
    private int currentPlayerIndex;

    // List of active winning strategies
    private final List<WinningStrategy> winningStrategies;

    // Constructor accepts strategies or uses defaults
    public Game(Player player1, Player player2, int boardSize, List<WinningStrategy> winningStrategies) {
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.status = GameStatus.IN_PROGRESS;
        this.currentPlayerIndex = 0; // player1 will get the first turn
        this.winningStrategies = winningStrategies;
    }

    // Convenience constructor providing default standard strategies
    public Game(Player p1, Player p2, int boardSize) {
        this(p1, p2, boardSize, List.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy()
        ));
    }

    public synchronized void makeMove(int row, int col) {
        // Check if the game is still in progress
        if (status != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is over.");
        }

        // Check if the cell already has a symbol
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell is already occupied.");
        }

        // Place the symbol of the current player on the board
        Player currentPlayer = players[currentPlayerIndex];
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        // Check for win using the Strategy Pattern
        if (checkWin(row, col, currentPlayer.getSymbol())) {
            status = (currentPlayer.getSymbol() == Symbol.X)
                    ? GameStatus.X_WINNER
                    : GameStatus.O_WINNER;
            return;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            return;
        }

        // Switch turn
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    public GameStatus getStatus() {
        return status;
    }

    /**
     * Delegates win checking to all registered winning strategies.
     */
    private boolean checkWin(int row, int col, Symbol symbol) {
        for (WinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(board, row, col, symbol)) {
                // Return early on the first matching win condition
                return true;
            }
        }
        return false;
    }
}

```

## Step 5: Extensibility

Adding **Observer Pattern** and **Factory Pattern** alongside the existing **Strategy Pattern** provides a complete, scalable design.

### Pattern Overview

| Pattern      | Application Target | Purpose in System                                                                 |
| ------------ | ------------------ | --------------------------------------------------------------------------------- |
| **Strategy** | `WinningStrategy`  | Decouples win detection logic (`Row`, `Col`, `Diagonal`).                         |
| **Observer** | Game state events  | Decouples core game state updates from logging, score tracking, and UI listeners. |
| **Factory**  | `PlayerFactory`    | Encapsulates player instantiation logic (`Human` vs `AI`).                        |

---

### 1. The Observer Pattern

![](/resources/images/problems/tic-tac-toe/2.png)

#### Step 1: Observer Interface

```java
public interface GameObserver {
    void onMoveMade(int row, int col, Player player);
    void onGameOver(GameStatus status, Player winner);
}

```

#### Step 2: Concrete Observers

```java
// Observer 1: Manages persistent scores across games
public class ScoreboardObserver implements GameObserver {
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    @Override
    public void onMoveMade(int row, int col, Player player) {
        // Optional: track move metrics
    }

    @Override
    public void onGameOver(GameStatus status, Player winner) {
        if (status == GameStatus.X_WINNER) xWins++;
        else if (status == GameStatus.O_WINNER) oWins++;
        else if (status == GameStatus.DRAW) draws++;

        System.out.println("[Scoreboard] Score Updated -> X: " + xWins + " | O: " + oWins + " | Draws: " + draws);
    }
}

// Observer 2: Logs game events for auditing or console UI
public class ConsoleLoggerObserver implements GameObserver {
    @Override
    public void onMoveMade(int row, int col, Player player) {
        System.out.println("[Log] " + player.getName() + " placed " + player.getSymbol() + " at (" + row + ", " + col + ")");
    }

    @Override
    public void onGameOver(GameStatus status, Player winner) {
        if (status == GameStatus.DRAW) {
            System.out.println("[Log] Game ended in a DRAW!");
        } else {
            System.out.println("[Log] Game Over! Winner: " + winner.getName());
        }
    }
}

```

#### Step 3: Integrating Observers into `Game`

```java
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final Player[] players;
    private GameStatus status;
    private int currentPlayerIndex;
    private final List<WinningStrategy> winningStrategies;

    // Observer registry
    private final List<GameObserver> observers = new ArrayList<>();

    public Game(Player player1, Player player2, int boardSize, List<WinningStrategy> winningStrategies) {
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.status = GameStatus.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    public void addObserver(GameObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyMoveMade(int row, int col, Player player) {
        for (GameObserver observer : observers) {
            observer.onMoveMade(row, col, player);
        }
    }

    private void notifyGameOver(Player winner) {
        for (GameObserver observer : observers) {
            observer.onGameOver(this.status, winner);
        }
    }

    public synchronized void makeMove(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is over.");
        }

        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell is already occupied.");
        }

        Player currentPlayer = players[currentPlayerIndex];
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        // Notify observers of move
        notifyMoveMade(row, col, currentPlayer);

        // Check for Win
        if (checkWin(row, col, currentPlayer.getSymbol())) {
            status = (currentPlayer.getSymbol() == Symbol.X) ? GameStatus.X_WINNER : GameStatus.O_WINNER;
            notifyGameOver(currentPlayer);
            return;
        }

        // Check for Draw
        if (board.isFull()) {
            status = GameStatus.DRAW;
            notifyGameOver(null);
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private boolean checkWin(int row, int col, Symbol symbol) {
        for (WinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(board, row, col, symbol)) return true;
        }
        return false;
    }

    public GameStatus getStatus() { return status; }
}

```
---

### 2. The Factory Pattern & AI Integration

To seamlessly support AI/Bot functionality, refactor `Player` into an abstract class allowing player variants to encapsulate their turn logic.

![](/resources/images/problems/tic-tac-toe/3.png)

#### Step 1: Abstract `Player` Class

```java
public abstract class Player {
    private final String name;
    private final Symbol symbol;

    public Player(String name, Symbol symbol) {
        if (symbol == Symbol.EMPTY) {
            throw new IllegalArgumentException("Player symbol cannot be EMPTY.");
        }
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Determines the next move coordinate [row, col] for this player.
     *
     * @param board Current state of the game board
     * @return int array where index 0 is row and index 1 is col
     */
    public abstract int[] getNextMove(Board board);
}

```

#### Step 2: Concrete Player Implementations (`HumanPlayer` & `BotPlayer`)

```java
import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name, Symbol symbol) {
        super(name, symbol);
    }

    @Override
    public int[] getNextMove(Board board) {
        System.out.print(getName() + " (" + getSymbol() + "), enter row and col (e.g., 0 1): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new int[]{row, col};
    }
}

```

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    private final Random random = new Random();

    public BotPlayer(String name, Symbol symbol) {
        super(name, symbol);
    }

    @Override
    public int[] getNextMove(Board board) {
        System.out.println(getName() + " (AI) is calculating its move...");

        // Find all empty cells on the board
        List<int[]> emptyCells = new ArrayList<>();
        int size = board.getSize();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.isCellEmpty(r, c)) {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            throw new IllegalStateException("No available moves on the board!");
        }

        // Pick a random available cell (Easy Bot Strategy)
        int randomIndex = random.nextInt(emptyCells.size());
        return emptyCells.get(randomIndex);
    }
}

```

#### Step 3: Implement `PlayerFactory`

```java
public enum PlayerType {
    HUMAN,
    EASY_BOT
}

public class PlayerFactory {

    public static Player createPlayer(PlayerType type, String name, Symbol symbol) {
        return switch (type) {
            case HUMAN -> new HumanPlayer(name, symbol);
            case EASY_BOT -> new BotPlayer(name + " (AI)", symbol);
            default -> throw new IllegalArgumentException("Unsupported PlayerType: " + type);
        };
    }
}

```

#### Step 4: Final Game Engine Execution Adapter

```java
public class Game {
    private final Board board;
    private final Player[] players;
    private GameStatus status;
    private int currentPlayerIndex;
    private final List<WinningStrategy> winningStrategies;

    public Game(Player player1, Player player2, int boardSize, List<WinningStrategy> winningStrategies) {
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.status = GameStatus.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    /**
     * Executes a single turn by requesting move coordinates from the current player.
     */
    public synchronized void playNextTurn() {
        if (status != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is over!");
        }

        Player currentPlayer = players[currentPlayerIndex];

        // The player (Human or AI) determines their own move
        int[] move = currentPlayer.getNextMove(board);
        int row = move[0];
        int col = move[1];

        // Execute move on the board
        makeMove(row, col);
    }

    public synchronized void makeMove(int row, int col) {
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell (" + row + ", " + col + ") is already occupied.");
        }

        Player currentPlayer = players[currentPlayerIndex];
        board.placeSymbol(row, col, currentPlayer.getSymbol());

        if (checkWin(row, col, currentPlayer.getSymbol())) {
            status = (currentPlayer.getSymbol() == Symbol.X) ? GameStatus.X_WINNER : GameStatus.O_WINNER;
            return;
        }

        if (board.isFull()) {
            status = GameStatus.DRAW;
            return;
        }

        // Switch turn
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private boolean checkWin(int row, int col, Symbol symbol) {
        for (WinningStrategy strategy : winningStrategies) {
            if (strategy.checkWin(board, row, col, symbol)) return true;
        }
        return false;
    }

    public Board getBoard() { return board; }
    public GameStatus getStatus() { return status; }
    public Player getCurrentPlayer() { return players[currentPlayerIndex]; }
}

```

---

### Step 6: Demo

```java
import java.util.List;

public class TicTacToeDemo {

    public static void main(String[] args) {
        // 1. FACTORY PATTERN: Create Human vs AI player using PlayerFactory
        Player human = PlayerFactory.createPlayer(PlayerType.HUMAN, "Alice", Symbol.X);
        Player bot = PlayerFactory.createPlayer(PlayerType.EASY_BOT, "Bot-3000", Symbol.O);

        // 2. STRATEGY PATTERN: Default win conditions
        List<WinningStrategy> strategies = List.of(
            new RowWinningStrategy(),
            new ColumnWinningStrategy(),
            new DiagonalWinningStrategy()
        );

        // 3. Initialize Game
        Game game = new Game(human, bot, 3, strategies);

        System.out.println("========== GAME START: " + human.getName() + " VS " + bot.getName() + " ==========");

        // Game Loop
        while (game.getStatus() == GameStatus.IN_PROGRESS) {
            game.getBoard().printBoard();
            try {
                game.playNextTurn();
            } catch (InvalidMoveException e) {
                System.out.println("Error: " + e.getMessage() + " Try again.");
            }
        }

        // Display Final Result
        game.getBoard().printBoard();
        System.out.println("\n========== GAME OVER ==========");
        System.out.println("Final Result: " + game.getStatus());
    }
}
```

## Reference

- https://algomaster.io/learn/lld/design-tic-tac-toe