package game.action;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.enemy.ActorTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * a class to reduce unwanted dependencies to AttackSurroundingAction
 * which was where this code was originally
 * and to also act as an utils
 * Created By: Lee Sing Yuan
 * @author Lee Sing Yuan
 */
public class NearMe {

    /**
     * Given an actor, find all the actors near it
     * @param actor the person we want to base on
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
     * @param actor the person we want to base on
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
     * if the player is in the actor's range, returns the location of the player
     * @param actor the list of possible locations
     * @param map the map itself
     * @param radius the radius of the search
     * @return  Location object which is the location of the player
     *          null if there is no player
     */
    public static Location playerInRangeLocation(Actor actor,GameMap map,int radius){
        Location playerLocation = null;

        List<Location> locations = getSurroundingLocations(actor,map,radius);

        // check the surrouding of the actor
        for (Location loc : locations) {
            // check if there is an actor at the location

            // if there is an actor at that location
            if (map.isAnActorAt(loc)) {

                //  find who is it
                Actor otherActor = map.getActorAt(loc);

                // if the actor is the player
                if ( otherActor.hasCapability(ActorTypes.PLAYER) ) {
                    playerLocation = loc;
                }
            }
        }
        return playerLocation;
    }
}
