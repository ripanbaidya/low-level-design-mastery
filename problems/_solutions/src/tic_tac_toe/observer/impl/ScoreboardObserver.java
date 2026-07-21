package tic_tac_toe.observer.impl;

import tic_tac_toe.entities.Player;
import tic_tac_toe.enums.GameStatus;
import tic_tac_toe.observer.GameObserver;

public class ScoreboardObserver implements GameObserver {
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;

    @Override
    public void onGameOver(GameStatus status, Player winner) {
        // Implement the logic as per your business requirements;
        // To keep the things simple, I'm Counting the wins and draws.

        if (status == GameStatus.DRAW) draws++;
        else if (status == GameStatus.X_WINNER) xWins++;
        else if (status == GameStatus.O_WINNER) oWins++;

        System.out.println("Scoreboard: X Wins: " + xWins + ", O Wins: " + oWins + ", Draws: " + draws + "");
    }
}
