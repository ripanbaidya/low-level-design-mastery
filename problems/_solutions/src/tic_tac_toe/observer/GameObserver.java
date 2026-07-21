package tic_tac_toe.observer;

import tic_tac_toe.entities.Player;
import tic_tac_toe.enums.GameStatus;

public interface GameObserver {

    /**
     * Update the UI or Console when the game ends.
     * This will help to track the game's progress, like - xWins, oWins, draws.
     *
     * @param status the status of the game
     * @param winner the winner of the game
     */
    void onGameOver(GameStatus status, Player winner);
}
