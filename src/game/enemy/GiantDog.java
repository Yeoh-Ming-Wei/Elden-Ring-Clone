package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.weapon.DogSlam;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class GiantDog extends Dog {
    public GiantDog() {
        super("Giant Dog", 'G', 693);
        this.addWeaponToInventory(new DogSlam());
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(314, "bites", 90);
    }
}
