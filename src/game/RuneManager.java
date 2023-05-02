package game;

import edu.monash.fit2099.engine.actors.Actor;

public class RuneManager{
    
    private Rune rune ;
    private static RuneManager rm = null;

    private static final int LONE_WOLF_MIN_RUNE = 55 ;
    private static final int LONE_WOLF_MAX_RUNE = 1470 ;
    
    private static final int GIANT_CRAB_MIN_RUNE = 318 ;
    private static final int GIANT_CRAB_MAX_RUNE = 4961 ;
    
    private static final int HSS_MIN_RUNE = 35 ;
    private static final int HSS_MAX_RUNE = 892 ;

    private RuneManager() {
        rune = new Rune() ;
    } ;

    public static RuneManager getInstance() {
        if (rm == null) 
            rm = new RuneManager() ;
        return rm ;
    }
    
    public void addRuneEnemy(Actor player, char enemy) {
        int value = 0 ;
        switch (enemy) {
            case 'h' -> value = RandomNumberGenerator.getRandomInt(LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
            case 'C' -> value = RandomNumberGenerator.getRandomInt(GIANT_CRAB_MIN_RUNE, GIANT_CRAB_MAX_RUNE) ;
            case 'X' -> value = RandomNumberGenerator.getRandomInt(HSS_MIN_RUNE, HSS_MAX_RUNE) ;
        }
        rune.setRune(rune.getRune() + value) ;
        System.out.println(menuDescription(player, "increased", value)) ;
    }

    public void deductRune(Actor player, int value) {
        rune.setRune(rune.getRune() - value) ;
        System.out.println(menuDescription(player, "decreased", value)) ;
        
    }

    public int returnRune() {
        return rune.getRune() ;
    }

    public String menuDescription(Actor player, String verb, int value) {
        return String.format("%s's rune has %s by %d, now %s has %d runes.", player, verb, value, player, rune.getRune());
    }
}
