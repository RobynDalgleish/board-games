package com.robyndalgleish.core;

import javax.inject.Singleton;
import java.util.Random;

//Tried to make this class as flexible as possible so it can be used in as many different games as possible.
@Singleton
public class Die {

    private final int sides;
    private final Random random;

    //A zero sided die doesn't make sense, so do a check for that, but I'm not limiting number of sides because who knows what dice are out there?
    //I might want a predictable die roll for a particular game.
    private Die(int sides, Random random) {
        if(sides > 0) {
            this.sides = sides;
        } else {
            throw new IllegalArgumentException("Cannot create a die with 0 sides.");
        }
        this.random = random;
    }

    //Created a static factory method instead of an overloaded constructor to label 6 as standard.
    public static Die standard(){
        return new Die(6, new Random());
    }

    public static Die ofNumberOfSides(int sides){
        return new Die(sides, new Random());
    }

    public static Die ofNumberOfSidesWithRandom(int sides, Random random){
        return new Die(sides, random);
    }

    public int roll() {
        return random.nextInt(sides) +1;
    }
}