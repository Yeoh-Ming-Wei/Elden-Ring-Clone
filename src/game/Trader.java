package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.action.PurchaseAction;
import game.action.SellAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.weapon.*;

import java.util.ArrayList;

/**
 * A class for trader
 * All trading will be instantiated by player
 */
public class Trader extends Actor {
    /**
     * Constructor.
     *
     */
    public Trader() {
        super("Merchant Kale", 'K', 999);
        this.addCapability(ActorTypes.TRADER);
        this.addCapability(Roles.NEUTRAL);

        // have to instantiate the weapon here cause if not cannot get the option to buy
        this.addWeaponToInventory(new Uchigatana());
        this.addWeaponToInventory(new GreatKnife());
        this.addWeaponToInventory(new Club());
        this.addWeaponToInventory(new Grossmesser());
        this.addWeaponToInventory(new Scimitar());
    }


    /**
     * At each turn, do nothing
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * The trader can only be interacting with the actor with PLAYER capability
     *
     * Apporach description:
     *      1) gets the weapons in inventory
     *      2) tick all weapons and get their allowable actions
     *      3) return them to player
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        ActionList actions = new ActionList();
        // to tick every item just in case tick in world does not run
        for ( WeaponItem w : this.getWeaponInventory() )
        {
            w.tick(map.locationOf(this),this);
            actions.add(w.getAllowableActions());
        }
        return actions;
    }
}
