package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackSurroundingAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;

import java.util.ArrayList;
import java.util.List;

public class AttackSurroundingAllowableAction {
    /**
     * To make the weapons return all the possible actions that can be done
     * ONLY FOR ENEMY WEAPONS THAT DONT HAVE PURCHASING AND SELLING
     * applies open close principle
     * Approach decription:
     *      1) use currentLocation which is updated by tick
     *      2) check if there is someone at the same location as the weapon
     *      3) if there is someone, proceed
     *              else, return nothing
     *      4) checks surrounding
     *      5) if it has an actor
     *              check type between the wielder of the weapon and the target which is in the surrounding
     *              eg: Lone Wolf is of type enemy and Dog, Heavy Skeleton Swordsman is of type enemy and Skeleton
     *                  they can attack each other
     *      6) if all checks pass, add the actions to the resulting list
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     * @return a list of actions that the wielder can do with this weapon
     */
    public static List<Action> getAllowableActions(Location currentLocation, WeaponItem w){
        // the resulting list of actions
        List<Action> res = new ArrayList<>();

        // checking to see if the weapon is held by someone or not
        // if weapon is on the ground and player is on top of it
        //      means player cannot use the actions which this weapon can give
        // if weapon is on the ground and player is not on it
        //      means should not be able to attack anyone or give the list of actions
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }


        List<Actor> targets = new ArrayList<>();
        // checks all locations around me
        for (Exit exit : currentLocation.getExits() ){
            Location l = exit.getDestination();

            // if it has an actor and it is attackable
            if (l.containsAnActor()){

                // get that actor and add the skill action and normal action to the person holding this
                Actor target = l.getActor();




                // attacking //
                // to make sure that can if it is a player, can only attack enemies and vice versa for enemies if in the future they can use the weapon
                if ( ( whoHasThis.hasCapability(Roles.ALLIES) && target.hasCapability(Roles.ENEMIES) ) ||
                        ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ALLIES) ) ||
                        ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ENEMIES) )
                ) {

                    // to make sure that those who are of same type do not attack each other
                    for ( ActorTypes type : ActorTypes.values() ) {

                        // only execute, if the actor holding this weapon has a certain capability and the target does not have the same
                        // eg: whoHasThis == Player and target != Player
                        if ( (whoHasThis.hasCapability(type) && !target.hasCapability(type)) ) {
                            // returns a new action with weapon which the actor will use on the targets if actor has weapons
                            targets.add(target);
                        }
                    }
                }
            }
        }
        // adding the attack surrounding actions after getting all the actors
        if ( targets.size() > 0 ) {
            res.add(new AttackSurroundingAction(targets, "attacks surrounding", w));
        }

        return res;
    }
}
