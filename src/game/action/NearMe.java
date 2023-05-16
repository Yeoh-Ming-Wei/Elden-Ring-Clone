package game.action;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.enemy.ActorTypes;
import game.weapon.SurroundingExit;
import game.weapon.isValid;

import java.util.ArrayList;
import java.util.List;

/**
 * A util class to get the surroundings of an actor
 * Created By: Lee Sing Yuan
 * @author Lee Sing Yuan
 */
public class NearMe {

    /**
     * Given an actor, find all the actors near it
     *
     * Approach description:
     *      1) gets the surrounding locations
     *      2) iterate through the surrounding locations
     *      3) check if an Actor exist at that location
     *      4) if an actor exist, add the actor the list
     *
     * @param actor the Actor we want the surrounding of
     * @param map the map
     * @param radius the radius of the surrounding
     * @return the list of actors around the actor
     */
    public static List<Actor> getSurroundingActors(Actor actor , GameMap map , int radius){

        // has the list of all actors around the actor ( calling the behaviour )
        ArrayList<Actor> targets = new ArrayList<>();

        // getting all the surrounding coordinates or locations
        List<Location> surroundingLocations = getSurroundingLocations(actor,map,radius);

        // check for other actors on the 8 surrounding tiles
        for (Location loc : surroundingLocations) {
            // check if there is an actor at the location
            if (map.isAnActorAt(loc)) {

                // if there is an actor at this position, find who is it and add to the list
                Actor otherActor = map.getActorAt(loc);
                targets.add(otherActor);
            }
        }
        return targets;
    }

    /**
     * given an actor, find all the locations around it
     *
     * Approach description:
     *      1) get the current Location of the actor calling this method
     *      2) get the boundaries of the map
     *      3) loop through all possible coordinates within the boundary
     *      4) add it to the list
     *
     * Assumptions: resulting list can only used to check surrounding, cannot move to that location cause no
     *              checking is done to ensure that area is accessible
     *
     * @param actor the Actor we want the surrounding of
     * @param map the map
     * @param radius the radius of the surrounding
     * @return a list of locations around the actor
     */
    public static List<Location> getSurroundingLocations(Actor actor , GameMap map , int radius){
        Location here = map.locationOf(actor);
        // gets the current position
        int currentX = here.x();
        int currentY = here.y();

        // border check
        int xLowerBound = map.getXRange().min();
        int xUpperBound = map.getXRange().max();
        int yLowerBound = map.getYRange().min();
        int yUpperBound = map.getYRange().max();

        // has the list of all possible locations
        List<Location> surroundingLocations = new ArrayList<>();

        // get the coordinates of the surrounding tiles
        for (int x = currentX-radius; x <= currentX+radius; x++) {
            for (int y = currentY-radius; y <= currentY+radius; y++) {
                // skip the current location
                if (x == currentX && y == currentY) {
                    continue;
                }
                // get the location at (x,y) from the map
                // only add them to the list if these are accessible locations
                // accessible as in, x and y is within range
                if ( ( ( x >= xLowerBound ) && ( x <= xUpperBound ) && ( y >= yLowerBound ) && ( y <= yUpperBound ) ) ) {
                    Location loc = map.at(x, y);
                    surroundingLocations.add(loc);
                }
            }
        }
        return surroundingLocations;
    }

    /**
     * if the wanted target is in the actor's range, returns the location of the wanted target
     *
     * Approach description:
     *      1) get the surrounding locations
     *      2) check if there is an actor there
     *              if yes, get the actor there
     *                  if the actor there is what we want
     *                         return this location
     *
     * @param actor the list of possible locations
     * @param map the map itself
     * @param radius the radius of the search
     * @return  Location object which is the location of the player
     *          null if there is no wanted target
     */
    public static Location whoInMyRange(Actor actor, GameMap map, int radius,ActorTypes type){
        Location wantedLocation = null;

        List<Location> locations = getSurroundingLocations(actor,map,radius);

        // check the surrounding of the actor
        for (Location loc : locations) {
            // check if there is an actor at the location

            // if there is an actor at that location
            if (map.isAnActorAt(loc)) {

                //  find who is it
                Actor otherActor = map.getActorAt(loc);

                // if the actor is the player
                if ( otherActor.hasCapability(type) ) {
                    wantedLocation = loc;
                }
            }
        }
        return wantedLocation;
    }

    /**
     * Approach description:
     *      1) get the exits around currentLocation
     *      2) if the exit contains an actor,
     *              get the actor
     *                  check whether the attacker and target is of correct types
     *                      if they are of correct types,
     *                          add them to the list
     *
     *
     * Note: why need a new object called SurroundingExit?
     *           cause, the whole reason of using exit rather than the above functions is to know the direction
     *
     * @param whoHasThis the attacker
     * @param currentLocation the current location of the attacker
     * @return a list of a new object which holds information regarding the target and exit
     */
    public static ArrayList<SurroundingExit> getSurroundingExitTargets( Actor whoHasThis , Location currentLocation ){
        ArrayList<SurroundingExit> res = new ArrayList<>();

        // checks all locations around the actor
        for (Exit exit : currentLocation.getExits() ){
            Location l = exit.getDestination();

            // if it has an actor
            if (l.containsAnActor()){

                // get that actor and add the skill action and normal action to the person holding this
                Actor target = l.getActor();

                // check if the attacker and target can attack each other
                if ( isValid.isValidRole(whoHasThis,target) && isValid.isValidActorType(whoHasThis,target) ){
                    SurroundingExit s = new SurroundingExit(target,exit);
                    res.add(s);
                }
            }
        }
        return res;
    }
}

