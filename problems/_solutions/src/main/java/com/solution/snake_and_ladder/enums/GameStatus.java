package com.solution.snake_and_ladder.enums;

/**
 * Three states cover the game lifecycle.
 * The game starts NOT_STARTED, transitions to RUNNING when play begins,
 * and ends as FINISHED when someone wins.
 */
public enum GameStatus {
    NOT_STARTED,
    RUNNING,
    FINISHED,
}
