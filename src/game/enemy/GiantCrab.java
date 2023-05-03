package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.*;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.weapon.CrabSlam;
import game.RandomNumberGenerator;
import game.action.DespawnAction;

import java.util.HashMap;
import java.util.Map;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class GiantCrab extends Crab {

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