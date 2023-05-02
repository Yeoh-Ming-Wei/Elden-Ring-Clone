package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.*;
import game.action.DespawnAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class LoneWolf extends Enemy {
    public LoneWolf() {
        super("Lone Wolf", 'h', 102);
        this.addCapability(ActorTypes.WOLF);
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(97, "bites", 95);
    }
}
