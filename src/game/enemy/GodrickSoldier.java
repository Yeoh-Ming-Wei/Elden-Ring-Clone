package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;

/**
 * a long range attacker
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class GodrickSoldier extends GodrickCastleInhabitants {

    private final int LONE_WOLF_MIN_RUNE = 38 ;
    private final int LONE_WOLF_MAX_RUNE = 70 ;

    public GodrickSoldier() {
        super("Godrick Soldier", 'p', 198);

        RuneManager.addEnemyDropRune(name, LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
    }

}
