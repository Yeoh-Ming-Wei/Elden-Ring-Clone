package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.weapon.CrabSlam;
import game.weapon.CrayFishSlam;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class GiantCrayFish extends Crab {

    public GiantCrayFish() {
        super("Giant Cray Fish", 'R', 4803);
        this.addWeaponToInventory(new CrayFishSlam());
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(527, "slams", 100);
    }
}