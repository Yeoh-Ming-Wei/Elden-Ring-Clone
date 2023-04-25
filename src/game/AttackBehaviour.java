package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.weapons.WeaponSkill;

import java.lang.reflect.Array;
import java.util.Random;


import java.util.ArrayList;
import java.util.List;

public class AttackBehaviour implements Behaviour{
    private final Random random = new Random();

    /**
     * Decides whether an enemy should attack another actor or not
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

        // has the list of all actors around the actor ( calling the behaviour )
        ArrayList<Actor> targets = new ArrayList<>();

        // gets the current position
        int currentX = here.x();
        int currentY = here.y();

        // has the list of all possible locations
        List<Location> surroundingLocations = new ArrayList<>();

        // get the coordinates of the 8 surrounding tiles
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

                // if there is an actor at this position, find who is it and add to the list
                Actor otherActor = map.getActorAt(loc);
                targets.add(otherActor);
            }
        }

        // if the actor has the capability to use a skill, then check if he wants to use the skill
        if ( actor.hasCapability(WeaponSkill.AREA_ATTACK)){
            if ( random.nextInt(100) < 101 ) {

                // returns a new action with weapon which the actor will use on the targets if actor has weapons
                return new AttackSurroundingAction(targets,"surrounding area" ,
                        actor.getWeaponInventory().size() > 0? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon() );
            }
        }
        // this is to get a random activity
        else if (!targets.isEmpty()) {
            Actor target = targets.get(random.nextInt(targets.size()));
            if ( (actor.hasCapability(Status.SKELETON) && !target.hasCapability(Status.SKELETON)) ||
                    (actor.hasCapability(Status.WOLF) && !target.hasCapability(Status.WOLF)) ||
                    (actor.hasCapability(Status.CRAB) && !target.hasCapability(Status.CRAB)))
            {
                // returns a new action with weapon which the actor will use on the targets if actor has weapons
                return new AttackAction(target, "attackActorNearby",
                        actor.getWeaponInventory().size() > 0? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon() );
            }
        }

        return null;

    }
    public static int behaviorCode(){
        return 111;
    }

}