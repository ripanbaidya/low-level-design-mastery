package com.solution.snake_and_ladder.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final int size;
    private final Map<Integer, Integer> snakeAndLadders;

    public Board(int size, List<BoardEntity> entities) {
        this.size = size;
        this.snakeAndLadders = new HashMap<>();

        entities.forEach(entity -> snakeAndLadders.put(entity.getStart(), entity.getEnd()));
    }

    public int getSize() {
        return size;
    }

    /**
     * If the position is in the map (snake head or ladder bottom), return the mapped value.
     * Otherwise, return the original position.
     */
    public int getFinalPosition(int position) {
        return snakeAndLadders.getOrDefault(position, position);
    }
}
