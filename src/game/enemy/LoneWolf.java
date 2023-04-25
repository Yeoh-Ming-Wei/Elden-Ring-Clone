package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.*;
import game.weapons.WeaponSkill;

import java.util.HashMap;
import java.util.Map;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public class LoneWolf extends Enemy {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    public LoneWolf() {
        super("Lone Wolf", 'h', 102);
        behaviours.put(FollowBehaviour.behaviorCode(), new FollowBehaviour());
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());
        this.addCapability(Status.WOLF);
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
        // checks if wolf has this behaviour
        if(behaviours.containsKey(FollowBehaviour.behaviorCode())){
            Action action = behaviours.get(FollowBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like follow anyone or player
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // attack has the second highest precedence
        // checks if wolf has this behaviour
        if(behaviours.containsKey(AttackBehaviour.behaviorCode())){
            Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like attack anyone,
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // wander is the lowest precedence
        // checks if wolf has this behaviour
        if(behaviours.containsKey(WanderBehaviour.behaviorCode()))
        {
            Action action = behaviours.get(WanderBehaviour.behaviorCode()).getAction(this, map);
            if (action != null) {
                return action;
            }
        }

        return new DoNothingAction();
    }


    /**
     * The lone wolf can be attacked by any actor that has the PLAYER capability
     *
     * ONLY USED BY PLAYER
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.PLAYER)) {
            if( otherActor.getWeaponInventory().size() > 0 ) {
                for ( int x = 0 ; x < otherActor.getWeaponInventory().size() ; x++ ) {
                    //getting the weapon
                    WeaponItem w = otherActor.getWeaponInventory().get(x);
                    actions.add(new AttackAction(this, direction, w));

                    // if the weapon has a attack surrounding capability need to add it to available actions

                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.AREA_ATTACK) ){
                        // add the surrounding attack action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new AttackSurroundingAction(otherActor,"surrounding area" , w));
                    }
                }
            }
            else{
                actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
            }

        }
        return actions;
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(97, "bites", 95);
    }
}
