package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import java.util.Random;


import java.util.ArrayList;
import java.util.List;

public class AttackBehaviour implements Behaviour{
    private final Random random = new Random();

    /**
     * A factory for creating actions. Chaining these together can result in an actor performing more complex tasks.
     * <p>
     * A Behaviour represents a kind of objective that an Actor can have.  For example
     * it might want to seek out a particular kind of object, or follow another Actor,
     * or run away and hide.  Each implementation of Behaviour returns an Action that the
     * Actor could take to achieve its objective, or null if no useful options are available.
     * method that determines which Behaviour to perform.  This allows the Behaviour's logic
     * to be reused in other Actors via delegation instead of inheritance.
     * <p>
     * An Actor's {@code playTurn()} method can use Behaviours to help decide which Action to
     * perform next.  It can also simply create Actions itself, and for simpler Actors this is
     * likely to be sufficient.  However, using Behaviours allows
     * us to modularize the code that decides what to do, and that means that it can be
     * reused if (e.g.) more than one kind of Actor needs to be able to seek, follow, or hide.
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author Riordan D. Alfredo
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // get the current location of the actor
        Location here = map.locationOf(actor);
        ArrayList<Action> actions = new ArrayList<>();

        // get the coordinates of the 8 surrounding tiles
        int currentX = here.x();
        int currentY = here.y();
        List<Location> surroundingLocations = new ArrayList<>();
        for (int i = currentX-1; i <= currentX+1; i++) {
            for (int j = currentY-1; j <= currentY+1; j++) {
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
                Actor otherActor = map.getActorAt(loc);
                if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    actions.add(new AttackAction(otherActor, "attackActorNearby"));
                }
            }
        }
        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }
        else {
            return null;
        }

    }

    public static int behaviorCode(){
        return 111;
    }

}