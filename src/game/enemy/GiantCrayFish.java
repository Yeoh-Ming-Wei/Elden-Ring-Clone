package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.RuneManager;
import game.weapon.CrayFishSlam;

/**
 * Is crayFish yummy? I dont know, I only knows its dangerous
 *
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class GiantCrayFish extends Crab {

    private final int GIANT_CF_MIN_RUNE = 500 ;
    private final int GIANT_CF_MAX_RUNE = 2374 ;

    public GiantCrayFish() {
        super("Giant Cray Fish", 'R', 4803);
        this.addWeaponToInventory(new CrayFishSlam());

        RuneManager.addEnemyDropRune(name, GIANT_CF_MIN_RUNE, GIANT_CF_MAX_RUNE);
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(527, "slams", 100);
    }
}