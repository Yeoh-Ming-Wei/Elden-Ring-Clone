package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * An Action to attack another Actor.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class AttackSurroundingAction extends Action {

    /**
     * The direction of incoming attack.
     */
    private String direction;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Weapon used for the attack
     */
    private Weapon weapon;

    /**
     * the coordinates of the surrounding area
     */
    private List<Location> surroundingLocations;

    /**
     * Constructor.
     *
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackSurroundingAction( String direction, Weapon weapon , List<Location> initSurroundingLocation) {
        this.direction = direction;
        this.weapon = weapon;
        this.surroundingLocations = initSurroundingLocation;
    }

    /**
     * Constructor with intrinsic weapon as default
     *
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackSurroundingAction( String direction, List<Location> initSurroundingLocation) {
        this.direction = direction;
        this.surroundingLocations = initSurroundingLocation;
    }

    /**
     * When executed, the chance to hit of the weapon that the Actor used is computed to determine whether
     * the actor will hit the target. If so, deal damage to the target and determine whether the target is killed.
     *
     * @param actor The actor performing the attack action.
     * @param map The map the actor is on.
     * @return the result of the attack, e.g. whether the target is killed, etc.
     * @see DeathAction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        String result = "";

        for (Location loc : surroundingLocations) {
            // check if there is an actor at the location
            if (map.isAnActorAt(loc)) {
                Actor otherActor = map.getActorAt(loc);
                result += new AttackAction(otherActor, "surrounding area",weapon).execute(actor,map) + " ";
            }
        }

        return result;
    }

    /**
     * Describes which target the actor is attacking with which weapon
     *
     * @param actor The actor performing the action.
     * @return a description used for the menu UI
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}

