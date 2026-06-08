package com.solution.snake_and_ladder;

import com.solution.snake_and_ladder.entities.BoardEntity;
import com.solution.snake_and_ladder.entities.Dice;
import com.solution.snake_and_ladder.entities.Ladder;
import com.solution.snake_and_ladder.entities.Snake;

import java.util.Arrays;
import java.util.List;

public class SnakeAndLadderTest {
    public static void main(String[] args) {
        List<BoardEntity> boardEntities = List.of(
                new Snake(17, 7),
                new Snake(54, 34),
                new Snake(62, 19),
                new Snake(98, 79),
                new Ladder(3, 38),
                new Ladder(24, 33),
                new Ladder(42, 93),
                new Ladder(72, 84)
        );

        List<String> players = Arrays.asList("John", "Luke");
        Game game = new Game.Builder()
                .setPlayers(players)
                .setBoard(100, boardEntities)
                .setDice(new Dice(1, 6))
                .build();

        game.play();
    }
}
