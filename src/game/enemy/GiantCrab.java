package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;
import game.weapon.CrabSlam;

/**
 * Crab here and there and everywhere!
 *
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class GiantCrab extends ParentCrab {

    private final int GIANT_CRAB_MIN_RUNE = 318 ;
    private final int GIANT_CRAB_MAX_RUNE = 4961 ;

    public GiantCrab() {
        super("Giant Crab", 'C', 407);
        this.addWeaponToInventory(new CrabSlam());

        RuneManager.addEnemyDropRune(name, GIANT_CRAB_MIN_RUNE, GIANT_CRAB_MAX_RUNE) ;
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(208, "slams", 90);
    }
}