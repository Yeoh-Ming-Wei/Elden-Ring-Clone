package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.PileOfBones;

import java.util.ArrayList;
import java.util.Random;

/**
 * An Action to attack another Actor.
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Lee Sing Yuan
 *
 */
public class QuickStepAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor target;

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
	 * Constructor.
	 *
	 * @param target the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 */
	public QuickStepAction(Actor target, String direction, Weapon weapon) {
		this.target = target;
		this.direction = direction;
		this.weapon = weapon;
	}

	/**
	 * When executed, the chance to hit of the weapon that the Actor used is computed to determine whether
	 * the actor will hit the target. If so, deal damage to the target and determine whether the target is killed.
	 *
	 * @param actor The Player
	 * @param map The map the actor is on.
	 * @return the result of the attack, e.g. whether the target is killed, etc.
	 * @see DeathAction
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		// this is for the HSS, because if he dies the first time, he actually uses the skill
		if (!target.isConscious() && target.hasCapability(PileOfBones.PILE_OF_BONES)){
			target.removeCapability(PileOfBones.PILE_OF_BONES);
			System.out.println(target + " turns to a pile of bones!");

		}

		else if (!target.isConscious()) {
			result += new DeathAction(actor).execute(target, map);
		}

		// repositioning actor
		// code taken from wander behavior
		ArrayList<Action> actions = new ArrayList<>();

		// gets the possible exits
		for (Exit exit : map.locationOf(actor).getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
			}
		}

		// choosing a random position to go to after moving
		if (!actions.isEmpty()) {
			int chosen = rand.nextInt(actions.size());
			result += "\n" + actions.get(chosen).execute(actor,map);
		}
		else {
			result += "\n" + "Cant move anywhere";
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
		return actor + " attacks " + target + " at " + direction + " with " + weapon + " and moves away";
	}
}
