package com.solution.snake_and_ladder.entities;

public class Ladder extends BoardEntity {

    public Ladder(int start, int end) {
        super(start, end);
        if (start >= end) {
            throw new IllegalArgumentException("Ladder bottom(start) must be lower than its top(end)");
        }
    }
}
