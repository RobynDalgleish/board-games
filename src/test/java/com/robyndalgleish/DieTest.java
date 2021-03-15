package com.robyndalgleish;

import com.robyndalgleish.core.Die;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DieTest {

    @Test
    @DisplayName("Handles zero sided die elegantly")
    void throwsWhenZeroSidedDie(){
        assertThatThrownBy(()-> Die.ofNumberOfSides(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Cannot create a die with 0 sides.");
    }

    @Test
    @DisplayName("Creates appropriately sided die")
    void createsAppropriatelySidedDie(){
        assertThat(Die.ofNumberOfSides(3)).isNotNull();
    }

    //If my die does not roll every side in 1 seconds, I probably have a problem in my code.
    @Timeout(1)
    @Test
    @DisplayName("Rolls every number on chosen die")
    void rollsEveryNumberOnChosenDie(){
        var die = Die.ofNumberOfSidesWithRandom(12, new Random(1));
        Set<Integer> sidesToRoll = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toSet());
        Set<Integer> sidesRolled = new HashSet<>();

        while(!sidesRolled.equals(sidesToRoll)){
            var numberRolled = die.roll();
            System.out.println(numberRolled);
            sidesRolled.add(numberRolled);
        }
    }
}