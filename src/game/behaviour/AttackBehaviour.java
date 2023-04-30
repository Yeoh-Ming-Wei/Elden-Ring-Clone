package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.action.NearMe;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;
import game.Status;
import game.enemy.ActorTypes;
import game.weapon.WeaponSkill;

import java.util.Random;


import java.util.List;

/**
 * Created by: Loo Li Shen
 * @author Riordan D. Alfredo
 * Modified by: Lee Sing Yuan
 *
 */
public class AttackBehaviour implements Behaviour {
    private final Random random = new Random();
    private int skillChance = 50;

    /**
     * Decides whether an enemy should attack another actor or not
     *
     * to use the skill:
     *      1) check if the actor has the skill
     *      2) check everyone in the surrounding
     *      3) check if there is anyone in the surrounding that is of different type
     *      4) get the chance
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @author Riordan D. Alfredo
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // has the list of all actors around the actor ( calling the behaviour )
        List<Actor> targets = NearMe.getSurroundingActors(actor,map,1);

        // if the actor has the capability to use a skill, then check if he wants to use the skill
        if ( actor.hasCapability(WeaponSkill.AREA_ATTACK) ) {

            // to check if there is a different type of actor so that can execute attack on surrounding
            for (Actor target : targets) {

                // to loop through all the enum types
                // .values() make it become a list
                for (ActorTypes type : ActorTypes.values()) {

                    // check if there exist an enemy which is of a different type
                    // so that this actor can use the attackSurrounding
                    if ((actor.hasCapability(type) && !target.hasCapability(type))) {

                        // check the chances
                        if (random.nextInt(100) < skillChance) {
                            // returns a new action with weapon which the actor will use on the targets if actor has weapons
                            return new AttackSurroundingAction(targets, "surrounding area",
                                    actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                        }
                    }
                }
            }
        }


        // if an enemy has the skill but decides not to use the skill, can choose to attack
        // individual targets
        // this is to get a random activity
        if (!targets.isEmpty()) {
            Actor target = targets.get(random.nextInt(targets.size()));

            // to loop through all the enum types
            // .values() make it become a list
            for ( ActorTypes type : ActorTypes.values() ) {
                if ( (actor.hasCapability(type) && !target.hasCapability(type)) ) {
                    // returns a new action with weapon which the actor will use on the targets if actor has weapons
                    return new AttackAction(target, "attackActorNearby",
                            actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                }
            }
        }

        return null;

    }
    public static int behaviorCode(){
        return 111;
    }

}