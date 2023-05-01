package game;

import edu.monash.fit2099.engine.actors.Actor;

public class RuneManager {

    private static int rune ;
    
    public RuneManager() {
        rune = 0 ;
    }

    public static int returnRune() {
        return rune ;
    }

    public static void addRune(Actor player, int value) {
        rune += value ;
        System.out.println(menuDescription(player, "increased", value)) ;
    }

    public static void deductRune(Actor player, int value) {
        rune -= value ;
        System.out.println(menuDescription(player, "decreased", value)) ;
        
    }

    public static String menuDescription(Actor player, String verb, int value) {
        return String.format("%s's rune has %s by %d, now %s has $d runes.", player, verb, value, player, rune);
    }
}
