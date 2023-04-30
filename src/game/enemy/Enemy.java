package game.enemy;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;
import game.Status;
import game.action.QuickStepAction;
import game.action.UnsheatheAction;
import game.weapon.WeaponSkill;

public abstract class Enemy extends Actor {
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * The enemies can be attacked by actors with the PLAYER capability
     *
     * THIS FUNCTION IS ONLY USED BY PLAYER
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // to only allow player to use this function
        if (otherActor.hasCapability(ActorTypes.PLAYER)) {
                // let the player choose which weapon to use
                for ( int x = 0 ; x < otherActor.getWeaponInventory().size() ; x++ ) {

                    //getting the weapon
                    WeaponItem w = otherActor.getWeaponInventory().get(x);
                    actions.add(new AttackAction(this, direction, w));

                    // if the weapon has an attack surrounding capability need to add it to available actions
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.AREA_ATTACK) ){
                        // add the surrounding attack action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new AttackSurroundingAction(otherActor,"surrounding area" , w));
                    }

                    // checks if player has weapon that can do Unsheathe action
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.UNSHEATHE) ){
                        // add the Unsheathe action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new UnsheatheAction(this,direction , w));
                    }

                    // checks if player has weapon that can do Quick Step action
                    if ( otherActor.getWeaponInventory().get(x).hasCapability(WeaponSkill.QUICKSTEP) ){
                        // add the Quick step action with correct weapon, because can have multiple
                        // weapons of the same skill
                        actions.add(new QuickStepAction(this,direction , w));
                    }
                }
                // adding the intrinsic weapon choice
                actions.add(new AttackAction(this, direction));
            }
        return actions;
    }
}
