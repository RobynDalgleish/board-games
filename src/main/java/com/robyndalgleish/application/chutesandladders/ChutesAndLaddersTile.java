package com.robyndalgleish.application.chutesandladders;

import lombok.Getter;

// Decided to not create a generic Tile interface in the core because it wouldn't have anything in it. It might semantically indicate a board should
// include tiles, but given tiles behave so differently, having an interface would become a hindrance and not aid development.
@Getter
public class ChutesAndLaddersTile {

    private final int tileNumber;
    private final Integer warp;

    /**
     * Construct a Chutes and Ladders tile without a linked chute/ladder
     *
     * @param tileNumber the number which identifies this tile
     */
    public ChutesAndLaddersTile(int tileNumber) {
        this.tileNumber = tileNumber;
        this.warp = null;
    }

    /**
     * Construct a Chutes and Ladders tile with a linked chute/ladder
     *
     * @param tileNumber the number which identifies this tile
     * @param warp       the number of the tile this tile will link to
     */
    public ChutesAndLaddersTile(int tileNumber, Integer warp) {
        this.tileNumber = tileNumber;
        this.warp = warp;
    }

    /**
     * Resolve landing on this tile
     *
     * @return this tile's number if there is not a linked tile, otherwise return the linked tile's number
     */
    int landedOn() {
        if (warp != null) {
            return warp;
        }
        return tileNumber;
    }
}
