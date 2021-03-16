package com.robyndalgleish.core;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
// I decided to make this class abstract because it should never be instantiated as is; "board" is an abstract concept.
// I am assuming any games I would model would need at least 1 die, at least 1 player, and include a concept of tiles as well as player positions.
// I am also assuming the game can be won.
public abstract class Board {

    protected Map<Player, Integer> positions = new HashMap<>();
    protected Player winner;
    private List<Player> players;
    private List<Die> dice;
    private Boolean gameIsOver;

    /**
     * Construct a general game board
     *
     * @param dice    the dice to use in the game
     * @param players the list of players playing the game
     */
    public Board(List<Die> dice, List<Player> players) {
        this.dice = dice;
        this.players = players;
        this.gameIsOver = false;
    }

    /**
     * A method that determines the actions taken on behalf of players
     *
     * @param player the player which is to take an action
     */
    public abstract void takeAction(Player player);
}
