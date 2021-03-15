package com.robyndalgleish.application.chutesandladders;

import lombok.Getter;

//Decided to not create a generic Tile interface in the core because it wouldn't have anything in it. It might semantically indicate a board should
// include tiles, but given tiles behave so differently, having an interface would become a hindrance and not aid development.
@Getter
public class ChutesAndLaddersTile {

    private int tileNumber;
    private Integer warp;
    //TODO: Should know about chutes vs ladders?

    //TODO: Overloaded constructor or static factory? Chose this because the game would infer and does not need to be described for users of our
    // library (chutes and ladders is a specific implementation).
    public ChutesAndLaddersTile(int tileNumber) {
        this.tileNumber = tileNumber;
        this.warp = null;
    }

    public ChutesAndLaddersTile(int tileNumber, Integer warp) {
        this.tileNumber = tileNumber;
        this.warp = warp;
    }

    int landedOn() {
        if(warp != null){
            return warp;
        }
        return tileNumber;
    }
}
