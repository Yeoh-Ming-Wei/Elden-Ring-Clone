package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.weapon.CrabSlam;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public abstract class Crab extends Enemy {

    public Crab(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);
        this.addCapability(ActorTypes.CRAB);
    }
}