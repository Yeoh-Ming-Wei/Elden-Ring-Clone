package game;

import edu.monash.fit2099.engine.actors.Actor;

public class RuneManager {

    private static int rune ;
    private static final int LONE_WOLF_MIN_RUNE = 55 ;
    private static final int LONE_WOLF_MAX_RUNE = 1470 ;
    
    private static final int GIANT_CRAB_MIN_RUNE = 318 ;
    private static final int GIANT_CRAB_MAX_RUNE = 4961 ;
    
    private static final int HSS_MIN_RUNE = 35 ;
    private static final int HSS_MAX_RUNE = 892 ;
    
    
    public RuneManager() {
        rune = 0 ;
    }

    public static int returnRune() {
        return rune ;
    }

    public static void addRune(Actor player, char enemy) {
        int value = 0 ;
        System.out.println(enemy) ;
        switch (enemy) {
            case 'h' -> value = RandomNumberGenerator.getRandomInt(LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
            case 'C' -> value = RandomNumberGenerator.getRandomInt(GIANT_CRAB_MIN_RUNE, GIANT_CRAB_MAX_RUNE) ;
            case 'X' -> value = RandomNumberGenerator.getRandomInt(HSS_MIN_RUNE, HSS_MAX_RUNE) ;
        }

        rune += value ;
        System.out.println(menuDescription(player, "increased", value)) ;
    }

    public static void deductRune(Actor player, int value) {
        rune -= value ;
        System.out.println(menuDescription(player, "decreased", value)) ;
        
    }

    public static String menuDescription(Actor player, String verb, int value) {
        return String.format("%s's rune has %s by %d, now %s has %d runes.", player, verb, value, player, rune);
    }
}
