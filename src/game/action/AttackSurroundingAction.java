package game.action;

import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * An Action to attack another Actor.
 * Created by: Lee Sing Yuan
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class AttackSurroundingAction extends Action {

    /**
     * The direction of incoming attack. for UI purposes
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
    private List<Actor> targets;

    /**
     * if the player wants to use attack surrounding
     */
    private Actor player;

    /**
     * Constructor. for enemies attack behaviour
     *
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackSurroundingAction( List<Actor> initTargets, String direction, Weapon weapon ) {
        this.targets = initTargets;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor with intrinsic weapon as default
     *
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackSurroundingAction( List<Actor> initTargets, String direction) {
        this.targets = initTargets;
        this.direction = direction;
    }

    /**
     * Constructor. for player
     *
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackSurroundingAction( Actor player, String direction, Weapon weapon ) {
        this.player = player;
        this.direction = direction;
        this.weapon = weapon;
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

        // to allow player to do surrounding attack
        if ( player != null ){
            targets = NearMe.getSurroundingActors(player,map,1);
        }

        // just to make the UI prettier
        String result = actor.toString() + " attacks their surrounding! \n";

        // to loop through all the targets and attack them
        for (Actor tar : targets) {
            result += new AttackAction(tar, "surrounding area",weapon).execute(actor,map) + "\n" ;
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

