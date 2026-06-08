package com.solution.snake_and_ladder;

import com.solution.snake_and_ladder.entities.Board;
import com.solution.snake_and_ladder.entities.BoardEntity;
import com.solution.snake_and_ladder.entities.Dice;
import com.solution.snake_and_ladder.entities.Player;
import com.solution.snake_and_ladder.enums.GameStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Queue<Player> players;
    private final Dice dice;

    private GameStatus gameStatus;
    private Player winner;

    public Game(Builder builder) {
        this.board = builder.board;
        this.players = new LinkedList<>(builder.players);
        this.dice = builder.dice;
        this.gameStatus = GameStatus.NOT_STARTED;
        this.winner = null;
    }

    public void play() {

        if (players.size() < 2) {
            System.out.println("At least 2 players are required to start the game.");
            return;
        }

        gameStatus = GameStatus.RUNNING;
        System.out.println("Game started.\n");

        while (gameStatus == GameStatus.RUNNING) {
            Player currentPlayer = players.poll();
            takeTurn(currentPlayer);

            if (gameStatus == GameStatus.RUNNING) {
                players.offer(currentPlayer);
            }
        }

        System.out.println("\nGame finished.");
        System.out.println("Winner: " + winner.getName());
    }

    private void takeTurn(Player player) {
        int roll = dice.roll();
        System.out.println(player.getName() + " rolls: " + roll);

        int currentPosition = player.getPosition();
        int nextPosition = currentPosition + roll;

        if (nextPosition > board.getSize()) {
            System.out.println(
                    player.getName()
                            + " needs an exact roll to reach "
                            + board.getSize()
                            + "."
            );
            return;
        }

        if (nextPosition == board.getSize()) {
            player.setPosition(nextPosition);
            winner = player;
            gameStatus = GameStatus.FINISHED;
            return;
        }

        int finalPosition = board.getFinalPosition(nextPosition);

        if (finalPosition > nextPosition) {
            System.out.println(
                    player.getName()
                            + " climbed a ladder: "
                            + nextPosition
                            + " -> "
                            + finalPosition
            );
        } else if (finalPosition < nextPosition) {
            System.out.println(
                    player.getName()
                            + " was bitten by a snake: "
                            + nextPosition
                            + " -> "
                            + finalPosition
            );
        } else {
            System.out.println(
                    player.getName()
                            + " moved: "
                            + currentPosition
                            + " -> "
                            + finalPosition
            );
        }

        player.setPosition(finalPosition);

        if (roll == 6 && gameStatus == GameStatus.RUNNING) {
            System.out.println(player.getName() + " gets another turn.\n");
            takeTurn(player);
        }
    }

    /**
     * Builder class.
     */
    public static class Builder {

        private Board board;
        private Queue<Player> players;
        private Dice dice;

        public Builder setBoard(int boardSize, List<BoardEntity> boardEntities) {
            this.board = new Board(boardSize, boardEntities);
            return this;
        }

        public Builder setPlayers(List<String> playerNames) {
            this.players = new LinkedList<>();

            playerNames.forEach(
                    playerName -> this.players.add(new Player(playerName))
            );

            return this;
        }

        public Builder setDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public Game build() {

            if (board == null) {
                throw new IllegalArgumentException("Board cannot be null.");
            }

            if (players == null || players.isEmpty()) {
                throw new IllegalArgumentException("Players cannot be null or empty.");
            }

            if (dice == null) {
                throw new IllegalArgumentException("Dice cannot be null.");
            }

            return new Game(this);
        }
    }
}