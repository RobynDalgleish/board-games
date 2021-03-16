package com.robyndalgleish.application.chutesandladders;

import com.robyndalgleish.core.Board;
import com.robyndalgleish.core.Die;
import com.robyndalgleish.core.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChutesAndLaddersBoard extends Board {

    private final Die die;
    private final List<ChutesAndLaddersTile> tiles;
    private final int winningTileNumber;


    /**
     * Create a Chutes and Ladders Board
     *
     * @param dice    the list of dice to be used in the game
     * @param tiles   the list of game tiles to be used
     * @param players the list of players to play the game
     */
    public ChutesAndLaddersBoard(List<Die> dice, List<ChutesAndLaddersTile> tiles, List<Player> players) {
        super(dice, players);
        if (dice.size() == 1) {
            this.die = dice.get(0);
        } else {
            throw new IllegalArgumentException("Chutes and Ladders can only be played with 1 6-sided die.");
        }
        if (players.size() > 4 || players.size() < 2) {
            throw new IllegalArgumentException("Chutes and Ladders can only be played with 2-4 players.");
        }
        this.tiles = new ArrayList<>(tiles);
        // All players start off the board, or at '0'.
        players.forEach(player -> positions.put(player, 0));
        this.winningTileNumber = tiles.size();
        this.winner = null;
    }

    /**
     * Roll the dice and resolve player movement
     *
     * @param player the player taking a turn
     */
    @Override
    public void takeAction(Player player) {
        die.roll();
        int numberRolled = die.getCurrentRoll();
        int currentPosition = positions.get(player);
        int newPosition = currentPosition + numberRolled > winningTileNumber
            ? currentPosition
            : tiles.get(currentPosition + numberRolled - 1).landedOn();

        positions.put(player, newPosition);
        if (positions.get(player) == winningTileNumber) {
            winner = player;
            setGameIsOver(true);
        }
    }
}
