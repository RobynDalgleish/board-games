package com.robyndalgleish.core;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.util.List;

@Getter
@Setter
// I decided to make this class abstract because it should never be instantiated as is; "board" is an abstract concept.
// I am assuming any games I would model would need at least 1 die, at least 1 player, and include a concept of tiles.
public abstract class Board {

    private List<Player> players;
    private List<Die> dice;
    private Boolean gameIsOver;

    @Inject
    public Board(List<Die> dice, List<Player> players) {
        this.dice = dice;
        this.players = players;
        this.gameIsOver = false;
    }

    protected abstract void takeAction(Player player);
}
