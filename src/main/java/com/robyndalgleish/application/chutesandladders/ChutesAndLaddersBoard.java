package com.robyndalgleish.application.chutesandladders;

import com.robyndalgleish.core.Board;
import com.robyndalgleish.core.Die;
import com.robyndalgleish.core.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ChutesAndLaddersBoard extends Board {

    Die die;
    List<ChutesAndLaddersTile> tiles;
    Map<String, Integer> positions = new HashMap<>();

    int winningTileNumber;

    public ChutesAndLaddersBoard(List<Die> dice, List<ChutesAndLaddersTile> tiles, List<Player> players) {
        super(dice, players);
        if(dice.size() == 1) {
            this.die = dice.get(0);
        } else {
            throw new IllegalArgumentException("Chutes and Ladders can only be played with 1 6-sided die.");
        }
        this.tiles = tiles;
        //All players start off the board, or at '0'.
        players.forEach(player -> positions.put(player.getUsername(), 0));
        this.winningTileNumber = tiles.size();
    }

    @Override
    public void takeAction(Player player) {

        int roll = die.roll();
        int currentPosition = positions.get(player.getUsername());
        int newPosition = currentPosition + roll > winningTileNumber
            ? currentPosition
            : tiles.get(currentPosition + roll -1).landedOn();

        

        positions.put(player.getUsername(), newPosition);
        if (positions.get(player.getUsername()) == winningTileNumber){
            player.setWinner(true);
            //TODO: Is this ok to do?
            setGameIsOver(true);
        }
    }
}
