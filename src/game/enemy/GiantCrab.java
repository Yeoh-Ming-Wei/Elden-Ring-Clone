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

import java.util.HashMap;
import java.util.Map;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class GiantCrab extends Enemy {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    public GiantCrab() {
        super("Giant Crab", 'C', 407);
        behaviours.put(FollowBehaviour.behaviorCode(), new FollowBehaviour());
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());
        this.addCapability(ActorTypes.CRAB);
        this.addWeaponToInventory(new CrabSlam());
    }

    /**
     * At each turn, select a valid action to perform.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // follow has the highest precedence
        // checks if giant crab has this behaviour
        if(behaviours.containsKey(FollowBehaviour.behaviorCode())){
            Action action = behaviours.get(FollowBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like follow anyone or player
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // attack has the second highest precedence
        // checks if giant crab has this behaviour
        if(behaviours.containsKey(AttackBehaviour.behaviorCode())){
            Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like attack anyone,
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // wander is the lowest precedence
        // checks if giant crab has this behaviour
        if(behaviours.containsKey(WanderBehaviour.behaviorCode()))
        {
            Action action = behaviours.get(WanderBehaviour.behaviorCode()).getAction(this, map);
            if (action != null) {
                return action;
            }
        }

        return new DoNothingAction();
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(208, "slams", 90);
    }
}