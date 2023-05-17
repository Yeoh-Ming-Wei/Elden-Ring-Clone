package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;
import game.weapon.HeavyCrossbow;

/**
 * a long range attacker
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class GodrickSoldier extends GodrickCastleInhabitants {

    private final int GODRICK_SOLDIER_MIN_RUNE = 38 ;
    private final int GODRICK_SOLDIER_MAX_RUNE = 70 ;

    public GodrickSoldier() {
        super("Godrick Soldier", 'p', 198);
        this.addWeaponToInventory(new HeavyCrossbow());

        RuneManager.addEnemyDropRune(name, GODRICK_SOLDIER_MIN_RUNE, GODRICK_SOLDIER_MAX_RUNE) ;
    }

}
