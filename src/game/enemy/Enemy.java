package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.RandomNumberGenerator;
import game.action.*;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.weapon.WeaponSkill;

import java.util.HashMap;
import java.util.Map;

public abstract class Enemy extends Actor {
    protected final Map<Integer, Behaviour> behaviours = new HashMap<>();
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        behaviours.put(FollowBehaviour.behaviorCode(), new FollowBehaviour());
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());
    }

    /**
     * The enemies can be attacked by actors with the PLAYER capability
     *
     * THIS FUNCTION IS ONLY USED BY PLAYER
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // to only allow player to use this function
        if (otherActor.hasCapability(ActorTypes.PLAYER)) {
                // let the player choose which weapon to use
                for ( int x = 0 ; x < otherActor.getWeaponInventory().size() ; x++ ) {

                    //getting the weapon
                    WeaponItem w = otherActor.getWeaponInventory().get(x);
                    actions.add(new AttackAction(this, direction, w));

                    // if the weapon has an attack surrounding capability need to add it to available actions
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.AREA_ATTACK) ){
                        // add the surrounding attack action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new AttackSurroundingAction(otherActor,"surrounding area" , w));
                    }

                    // checks if player has weapon that can do Unsheathe action
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.UNSHEATHE) ){
                        // add the Unsheathe action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new UnsheatheAction(this,direction , w));
                    }

                    // checks if player has weapon that can do Quick Step action
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.QUICKSTEP) ){
                        // add the Quick step action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new QuickStepAction(this,direction , w));
                    }
                }
                // adding the intrinsic weapon choice
                actions.add(new AttackAction(this, direction));
            }
        return actions;
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

        if (RandomNumberGenerator.getRandomInt(100) < 10) {
            return new DespawnAction(this) ;
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
}
