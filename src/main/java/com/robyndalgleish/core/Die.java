package com.robyndalgleish.core;

import lombok.Getter;

import java.util.Random;

// Tried to make this class as flexible as possible so it can be used in as many different games as possible.
public class Die {

    private final int sides;
    private final Random random;

    @Getter
    private int currentRoll;

    // A zero sided die doesn't make sense, so do a check for that, but I'm not limiting number of sides because who knows what dice are out there?
    // I might want a predictable die roll for a particular game.

    /**
     * Construct a die with at least 1 side
     *
     * @param sides  the number of sides for the die
     * @param random the random seed to be used for the die
     */
    private Die(int sides, Random random) {
        if (sides > 0) {
            this.sides = sides;
        } else {
            throw new IllegalArgumentException("Cannot create a die with 0 sides.");
        }
        this.random = random;
    }

    /**
     * Roll the die, saving the result of the roll
     */
    public void roll() {
        currentRoll = random.nextInt(sides) + 1;
    }

    // Created a static factory method instead of an overloaded constructor to label 6 as standard.

    /**
     * Create a standard 6-sided die
     *
     * @return a standard 6-sided die
     */
    public static Die standard() {
        return new Die(6, new Random());
    }

    /**
     * Create a die with an arbitrary number of sides
     *
     * @param sides the number of sides for the die
     *
     * @return a die with the specified number of sides
     */
    public static Die ofNumberOfSides(int sides) {
        return new Die(sides, new Random());
    }

    /**
     * Create a die with an arbitrary number of sides and arbitrary seed
     *
     * @param sides  the number of sides for the die
     * @param random the random seed for the die
     *
     * @return a die with an arbitrary number of sides and arbitrary seed
     */
    public static Die ofNumberOfSidesWithRandom(int sides, Random random) {
        return new Die(sides, random);
    }
}