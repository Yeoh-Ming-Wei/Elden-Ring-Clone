package game.behaviour;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import game.Status;
import game.action.NearMe;
import game.behaviour.Behaviour;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to check if the player is nearby and should the enemy follow the actor or not
 * @see edu.monash.fit2099.demo.mars.Application
 *
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by: Lee Sing Yuan
 *
 */
public class FollowBehaviour implements Behaviour {

	// null unless the player is present within the following range
	private Location playerLocation;

	/**
	 * Decide wheter to follow player or not
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return 	an Action object if the player is within range
	 * 			null if not in range
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// get the current location of the actor
		Location here = map.locationOf(actor);

		// to see if player is near this actor
		playerLocation = NearMe.playerInRangeLocation(actor,map,2);

		// if the player is within range, start following else return null
		if (playerLocation != null)
		{
			// get the total distance of the enemy to the player
			int currentDistance = distance(here,playerLocation);

			// loop through all the exits
			for (Exit exit : here.getExits()) {

				// get the location of the exit
				Location destination = exit.getDestination();

				// if the enemy can enter
				if (destination.canActorEnter(actor)) {

					// get the distance of the exit or position and check how far
					// is this position from the player
					int newDistance = distance(destination, playerLocation);

					// check if this exit is moving the enemy closer or further away from the player
					// if move closer this is the new destination
					if (newDistance < currentDistance) {
						return new MoveActorAction(destination, exit.getName());
					}
				}
			}
		}

		// if cannot follow player
		return null ;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}

	public static int behaviorCode(){
		return 666;
	}
}
