package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.action.NearMe;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.weapon.WeaponSkill;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Random;


import java.util.List;

/**
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Lee Sing Yuan
 *
 */
public class AttackBehaviour implements Behaviour {
    private final Random random = new Random();
    private int skillChance = 100;

    /**
     * Decides whether an enemy should attack another actor or not
     *
     * Approach description:
     *      1) get the list of targets
     *      2) check if the actor performing the behaviour has the capability to perform the attack surrounding skill
     *              check everyone in the surrounding
     *              check if there is anyone in the surrounding that is of different type so that can have reason to execute skill
     *                  get the chance
     *                      if chance is yes, return AttackSurroundingAction
     *      3) else, just get a random actor to attack
     *      4) check the types, if not same, only return AttackAction
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // to attack using weapons with skills //
        // if the actor has the capability to use a skill, then check if he wants to use the skill
        if ( actor.getWeaponInventory().size() > 0 ) {
            if (random.nextInt(100) < skillChance) {
                WeaponItem w = actor.getWeaponInventory().get(0);
                List<Action> res = w.getAllowableActions();

                // to check if the weapon can give any actions
                // if cannot give, go to normal attack
                // actually can straight away return null, since if there is nothing the sword can do
                // means no attacking can be done
                if (res.size() > 0 ){
                    return res.get(0);
                }
            }
        }


        // to attack using intrinsic weapons without skills //
        List<Actor> targets = NearMe.getSurroundingActors(actor,map,1);
        // if an enemy has the skill but decides not to use the skill, can choose to attack
        // individual targets
        // this is to get a random action
        if (!targets.isEmpty()) {
            Actor target = targets.get(random.nextInt(targets.size()));

            // checks the arch roles
            // means only allow enemy to attack enemy or enemy to attack allies
            if ( ( actor.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ENEMIES) ) ||
                    ( actor.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ALLIES) )
            ) {
                // to loop through all the enum types
                // .values() make it become a list
                for (ActorTypes type : ActorTypes.values()) {
                    if ((actor.hasCapability(type) && !target.hasCapability(type))) {
                        // returns a new action with weapon which the actor will use on the targets if actor has weapons
                        return new AttackAction(target, "_",
                                actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                    }
                }
            }
        }
        return null;
    }
    public static int behaviorCode(){
        return 111;
    }

}
/*
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
                    if ( (actor.hasCapability(type) && !target.hasCapability(type)) && (!target.hasCapability(ActorTypes.TRADER)) ) {

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
        // this is to get a random action
        if (!targets.isEmpty()) {
            Actor target = targets.get(random.nextInt(targets.size()));

            // to loop through all the enum types
            // .values() make it become a list
            for ( ActorTypes type : ActorTypes.values() ) {
                if ( (actor.hasCapability(type) && !target.hasCapability(type)) && (!target.hasCapability(ActorTypes.TRADER)) ) {
                    // returns a new action with weapon which the actor will use on the targets if actor has weapons
                    return new AttackAction(target, "attackActorNearby",
                            actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                }
            }
        }

        return null;

 */