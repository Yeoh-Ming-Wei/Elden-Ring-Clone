package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.AttackAction;
import game.AttackSurroundingAction;
import game.Status;
import game.weapons.WeaponSkill;

import java.util.Random;


import java.util.List;

public class AttackBehaviour implements Behaviour {
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
        // has the list of all actors around the actor ( calling the behaviour )
        List<Actor> targets = AttackSurroundingAction.surroundingCoordinates(actor,map);

        // if the actor has the capability to use a skill, then check if he wants to use the skill
        if ( actor.hasCapability(WeaponSkill.AREA_ATTACK) ){

            // to make sure that skill is not executed if there is no enemies
            if ( targets.size() > 0 ) {
                if (random.nextInt(100) < 50) {
                    // returns a new action with weapon which the actor will use on the targets if actor has weapons
                    return new AttackSurroundingAction(targets, "surrounding area",
                            actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                }
            }
        }
        // if an enemy has the skill but decides not to use the skill, can choose to attack
        // individual targets
        // this is to get a random activity
        if (!targets.isEmpty()) {
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