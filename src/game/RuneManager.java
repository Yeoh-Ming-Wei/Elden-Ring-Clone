package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.monash.fit2099.engine.actors.Actor;

public class RuneManager{
    
    private Rune rune ;
    private static RuneManager rm = null;
    
    public static HashMap<String, ArrayList<Integer>> runeRange = new HashMap<>();
    
    private RuneManager() {
        rune = new Rune() ;
    }

    public static RuneManager getInstance() {
        if (rm == null) 
            rm = new RuneManager() ;
        return rm ;
    }
    
    public void addRuneEnemy(Actor player, Actor enemy) {
        
        int value = 0 ;
        ArrayList<Integer> arr = RuneManager.runeRange.get(enemy.toString()) ;
        value = RandomNumberGenerator.getRandomInt(arr.get(0), arr.get(1)) ;
        addRune(player,value) ;
    }

    public void deductRune(Actor player, int value) {
        rune.setRune(rune.getRune() - value) ;
        System.out.println(menuDescription(player, "decreased by", value)) ;
        
    }

    public int returnRune() {
        return rune.getRune() ;
    }

    public void addRune(Actor player, int value){
        rune.setRune(rune.getRune() + value) ;
        System.out.println(menuDescription(player, "increased by", value)) ;
    }

    public String menuDescription(Actor player, String verb, int value) {
        return String.format("%s's rune has %s by %d, now %s has %d runes.", player, verb, value, player, rune.getRune());
    }

    public static void addEnemyDropRune(String name, int min, int max){
        ArrayList<Integer> temp = new ArrayList<>() ;
        temp.add(min) ;
        temp.add(max) ;
        runeRange.put(name, temp);
    }
}
