package com.robyndalgleish.core;

import lombok.Value;

@Value
public class Player {

    String username;

    /**
     * Construct a Player with a given username
     *
     * @param username the username to use for the player
     */
    public Player(String username) {
        this.username = username;
    }
}
