package tic_tac_toe.entities;

import tic_tac_toe.enums.Symbol;

/**
 * Player who is responsible for playing the game.
 * Each player will have a name and a symbol.
 */
public class Player {
    private final String name;
    private final Symbol symbol;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
