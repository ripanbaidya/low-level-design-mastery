package com.solution.tic_tac_toe.observer.impl;

import com.solution.tic_tac_toe.entities.Game;
import com.solution.tic_tac_toe.entities.Player;
import com.solution.tic_tac_toe.observer.GameObserver;

import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard implements GameObserver {

    private final ConcurrentHashMap<String, Integer> scores;

    public Scoreboard() {
        this.scores = new ConcurrentHashMap<>();
    }

    @Override
    public void update(Game game) {
        Player player = game.getWinner();
        if (player != null) {
            scores.merge(player.getName(), 1, Integer::sum);
            System.out.println("Scoreboard updated: " + player.getName() + " Win the game!");
        }
    }

    public void printScoreboard() {
        System.out.println("\n====== Scoreboard ======");
        if (scores.isEmpty()) {
            System.out.println("No game played yet!!");
        } else {
            scores.forEach((name, score) -> {
                System.out.println(name + " : " + score);
            });
        }
        System.out.println("======================\n");
    }
}
