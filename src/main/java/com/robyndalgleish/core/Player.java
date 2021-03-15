package com.robyndalgleish.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private String username;
    private Boolean winner;

    public Player(String username) {
        this.username = username;
        this.winner = false;
    }
}
