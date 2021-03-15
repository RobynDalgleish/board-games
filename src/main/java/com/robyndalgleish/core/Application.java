package com.robyndalgleish.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.robyndalgleish.application.chutesandladders.ChutesAndLaddersBoard;
import com.robyndalgleish.application.chutesandladders.ChutesAndLaddersTile;
import io.micronaut.runtime.Micronaut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    private static int amountOfTiles = 100;

    public static void main(String[] args) throws IOException {
        Micronaut.run(Application.class, args);
        run();
    }

    public static void run() throws IOException {
        //TODO: Pass file path through args or config
        var warps = setUpWarpMappings("/Users/robyndalgleish/GroupBy/projects/board-games/src/main/resources/ChutesAndLaddersWarps.json");
        List<ChutesAndLaddersTile> tiles = new ArrayList<>();
        //TODO: Pass number of tiles through args or config
        for(int i = 1; i <= amountOfTiles; i++){
            if(warps.containsKey(i)){
                //TODO: Connect tiles to automatically validate instead of allowing a bad json file (a tile warping to a tile that warps back) to
                // break
                // the app
                tiles.add(new ChutesAndLaddersTile(i, warps.get(i)));
            } else {
                tiles.add(new ChutesAndLaddersTile(i));
            }
        }
        List<Player> players = new ArrayList<>();
        players.add(new Player("Robyn"));
        players.add(new Player("James"));
        Board game = new ChutesAndLaddersBoard(List.of(Die.standard()), tiles, players);
        while(!game.getGameIsOver()){
            for(Player player : players){
                game.takeAction(player);
            }
        }
        System.out.println("game is won");
    }

    private static Map<Integer, Integer> setUpWarpMappings(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //TODO: TypeFactory???
        Map<Integer, Integer> map = mapper.readValue(new File(filepath), TypeFactory.defaultInstance()
            //TODO: Go over options instead of ConcurrentHashMap
            .constructMapType(ConcurrentHashMap.class, Integer.class, Integer.class));
        return map;
    }

    //This would be added to a separate validation service if I were to continue developing this product.
    public static void validatePlayers(List<Player> players) {
        Set<String> uniquePlayers = new HashSet<>();
        for(Player player : players) {
            if(uniquePlayers.contains(player.getUsername())){
                throw new IllegalArgumentException(String.format("Username %s must be unique", player.getUsername()));
            }
            uniquePlayers.add(player.getUsername());
        }
    }
}
