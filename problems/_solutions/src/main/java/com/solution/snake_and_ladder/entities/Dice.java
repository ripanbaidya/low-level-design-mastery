package com.solution.snake_and_ladder.entities;

/**
 * The Dice encapsulates random number generation with a configurable range.
 * The formula {@code Math.random() * (max - min + 1) + min} generates a random
 * integer in the inclusive range [min, max]. For a standard die, this returns
 * values 1 through 6 with equal probability.
 */
public class Dice {
    private final int minValue;
    private final int maxValue;

    public Dice(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int roll() {
        return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
    }
}
