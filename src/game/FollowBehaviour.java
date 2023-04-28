package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actions.MoveActorAction;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to check if the player is nearby and should the enemy follow the actor or not
 * @see edu.monash.fit2099.demo.mars.Application
 *
 * Created by: Lee Sing Yuan
 * @author Riordan D. Alfredo
 * Modified by:
 *
 */
public class FollowBehaviour implements Behaviour {

	// null unless the player is present within the following range
	private Actor player;
	private Location playerLocation;

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// get the current location of the actor
		Location here = map.locationOf(actor);

		// gets the current position
		int currentX = here.x();
		int currentY = here.y();

		// has the list of all possible locations
		List<Location> surroundingLocations = new ArrayList<>();

		// get the coordinates of the 24 surrounding tiles
		for (int i = currentX-2; i <= currentX+2; i++) {
			for (int j = currentY-2; j <= currentY+2; j++) {
				// skip the current location
				if (i == currentX && j == currentY) {
					continue;
				}
				// get the location at (i,j) from the map
				Location loc = map.at(i, j);
				surroundingLocations.add(loc);
			}
		}

		// check for other actors on the 24 surrounding tiles
		for (Location loc : surroundingLocations) {
			// check if there is an actor at the location
			if (map.isAnActorAt(loc)) {

				// if there is an actor at this position, find who is it and add to the list
				Actor otherActor = map.getActorAt(loc);

				// if found the player
				if ( otherActor.hasCapability(Status.PLAYER) ) {
					player = otherActor;
					playerLocation = loc;
				}
			}
		}

		// if the player is within range, start following else return null
		if (player != null)
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
