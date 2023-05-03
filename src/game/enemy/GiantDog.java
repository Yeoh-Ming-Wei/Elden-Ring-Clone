package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.RuneManager;
import game.weapon.DogSlam;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class GiantDog extends Dog {

    private final int GIANT_DOG_MIN_RUNE = 313 ;
    private final int GIANT_DOG_MAX_RUNE = 1808 ;

    public GiantDog() {
        super("Giant Dog", 'G', 693);
        this.addWeaponToInventory(new DogSlam());

        RuneManager.addEnemyDropRune(name, GIANT_DOG_MIN_RUNE, GIANT_DOG_MAX_RUNE);
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(314, "bites", 90);  
    }

}
