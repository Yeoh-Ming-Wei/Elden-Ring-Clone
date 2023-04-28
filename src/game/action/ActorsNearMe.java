package game.action;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an actor, find all the actors near it
 * Created By: Lee Sing Yuan
 * @author Lee Sing Yuan
 */
public class ActorsNearMe {
    public static List<Actor> surroundingActors(Actor actor , GameMap map , int radius){
        Location here = map.locationOf(actor);

        // has the list of all actors around the actor ( calling the behaviour )
        ArrayList<Actor> targets = new ArrayList<>();

        // gets the current position
        int currentX = here.x();
        int currentY = here.y();

        // has the list of all possible locations
        List<Location> surroundingLocations = new ArrayList<>();

        // get the coordinates of the 8 surrounding tiles
        for (int i = currentX-radius; i <= currentX+radius; i++) {
            for (int j = currentY-radius; j <= currentY+radius; j++) {
                // skip the current location
                if (i == currentX && j == currentY) {
                    continue;
                }
                // get the location at (i,j) from the map
                Location loc = map.at(i, j);
                surroundingLocations.add(loc);
            }
        }

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
}
