package com.robyndalgleish;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.robyndalgleish.application.chutesandladders.ChutesAndLaddersBoard;
import com.robyndalgleish.application.chutesandladders.ChutesAndLaddersTile;
import com.robyndalgleish.core.Board;
import com.robyndalgleish.core.Die;
import com.robyndalgleish.core.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Add a CI/CD flow to run tests when pushing to a version control platform
public class Application {

    static String filePath;
    static int numTiles;

    public static void main(String[] args) throws IOException {
        filePath = args[0];
        numTiles = Integer.parseInt(args[1]);
        run();
    }

    /**
     * Run an instance of the game
     *
     * @throws IOException when the linked JSON file does not exist
     */
    public static void run() throws IOException {
        var warps = setUpWarps(filePath);
        List<ChutesAndLaddersTile> tiles = new ArrayList<>();
        for (int i = 1; i <= numTiles; i++) {
            if (warps.containsKey(i)) {
                // In the future I would take time to validate the warps of allowing a bad json file (a tile warping to a tile that warps back) to
                // break the app
                tiles.add(new ChutesAndLaddersTile(i, warps.get(i)));
            } else {
                tiles.add(new ChutesAndLaddersTile(i));
            }
        }

        List<Player> players = new ArrayList<>();
        players.add(new Player("Robyn"));
        players.add(new Player("James"));
        players.add(new Player("Jisoo"));
        players.add(new Player("Shishty"));

        validatePlayers(players);

        Board game = new ChutesAndLaddersBoard(List.of(Die.standard()), tiles, players);

        int numTurnsPlayed = 0;

        while (!game.getGameIsOver()) {
            for (Player player : players) {
                numTurnsPlayed++;
                int initialPosition = game.getPositions().get(player);
                game.takeAction(player);
                int currentRoll = game.getDice().get(0).getCurrentRoll();
                printToConsole(numTurnsPlayed, player.getUsername(), initialPosition, currentRoll, game.getPositions().get(player));
            }
        }
        System.out.println("The winner is " + game.getWinner().getUsername() + "!");
    }

    /**
     * Read chute/ladder locations from a JSON file and create a map
     *
     * @param filepath the location of the JSON file
     *
     * @return an Integer -> Integer map containing chute/ladder entrances/exits
     * @throws IOException when the file does not exist
     */
    private static Map<Integer, Integer> setUpWarps(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filepath), TypeFactory.defaultInstance()
            .constructMapType(ConcurrentHashMap.class, Integer.class, Integer.class));
    }

    // TODO: Add to a separate validation service.

    /**
     * Validate that the players in the game have unique usernames
     *
     * @param players the list of players in the game
     */
    public static void validatePlayers(List<Player> players) {
        Set<String> uniquePlayers = new HashSet<>();
        for (Player player : players) {
            if (uniquePlayers.contains(player.getUsername())) {
                throw new IllegalArgumentException(String.format("Username %s must be unique", player.getUsername()));
            }
            uniquePlayers.add(player.getUsername());
        }
    }

    /**
     * Determine whether a player has landed on a chute or ladder (or neither) for printing to console
     *
     * @param first the initial position of the player
     * @param last  the resolved final position of the player
     *
     * @return the location a player has landed on
     */
    private static String resolvePossibleWarpsToPrint(int first, int last) {
        if (first > last) {
            return " --CHUTE--> " + last;
        } else if (last > first) {
            return " --LADDER--> " + last;
        }
        return "";
    }

    /**
     * Handle printing output to console
     *
     * @param turnNumber      the number of elapsed turns
     * @param playerName      the name of the player taking action
     * @param initialPosition the initial location of the player
     * @param roll            the die roll performed by the player
     * @param finalPosition   the final location of the player after the roll is resolved
     */
    private static void printToConsole(int turnNumber, String playerName, int initialPosition, int roll, int finalPosition) {
        System.out.println(
            turnNumber +
                ": " +
                playerName +
                ": " +
                initialPosition +
                " --> " +
                (initialPosition + roll) +
                resolvePossibleWarpsToPrint(initialPosition + roll, finalPosition)
        );
    }
}
