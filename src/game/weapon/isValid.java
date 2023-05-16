package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import game.enemy.ActorTypes;
import game.enemy.Roles;

public class isValid {
    public static boolean isValidRole(Actor whoHasThis, Actor target){
        return ( whoHasThis.hasCapability(Roles.ALLIES) && target.hasCapability(Roles.ENEMIES) ) ||
                ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ALLIES) ) ||
                ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ENEMIES) );
    }

    public static boolean isValidActorType(Actor whoHasThis, Actor target){
        // to make sure that can if it is a player, can only attack enemies
        // and vice versa for enemies if in the future they can use the weapon
        for ( ActorTypes type : ActorTypes.values() ) {

            // only execute, if the actor holding this weapon has a certain capability and the target does not have the same
            // eg: whoHasThis == Player and target != Player
            if (whoHasThis.hasCapability(type) && !target.hasCapability(type))
            {
                return true;
            }
        }
        return false;
    }
}

