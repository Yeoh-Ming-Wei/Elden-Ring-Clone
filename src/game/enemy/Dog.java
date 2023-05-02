package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.weapon.DogSlam;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public abstract class Dog extends Enemy {
    public Dog(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);
        this.addCapability(ActorTypes.WOLF);
    }
}
