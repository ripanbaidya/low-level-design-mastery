package com.solution.snake_and_ladder.entities;

public class Snake extends BoardEntity {

    public Snake(int start, int end) {
        super(start, end);
        if (start <= end) {
            throw new IllegalArgumentException("Snake head must be higher than its tail");
        }
    }
}
