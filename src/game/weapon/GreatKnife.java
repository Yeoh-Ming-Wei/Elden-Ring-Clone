package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackAction;
import game.action.QuickStepAction;
import game.action.UnsheatheAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;

import java.util.ArrayList;
import java.util.List;

/**
 * A knife that can be used to attack the enemy.
 * It deals 75 damage with 70% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class GreatKnife extends WeaponItem implements Purchasable,Sellable{
    private Location currentLocation;
    private int buyingPrice;
    private int sellingPrice;

    // to check whether the class has been added to the static mapping or not
    private static boolean isPurchaseAdded = false;
    private static boolean isSellableAdded = false;
    /**
     * Constructor
     */
    public GreatKnife() {
        super("Great Knife", '/', 75, "slashes", 70);

        // to not return hardcode values
        this.buyingPrice = 3500;
        this.sellingPrice = 350;

        // adding the capability
        this.addCapability(WeaponSkill.QUICKSTEP);

        // adding to the static mapping if haven't added
        if ( isPurchaseAdded == false ){
            isPurchaseAdded = true;
            WeaponPurchaseSellInfo.addPurchasableWeapon(new GreatKnife());
            WeaponPurchaseSellInfo.addPurchasableWeaponItem(new GreatKnife());
        }

        // adding to the static mapping if haven't added
        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new GreatKnife());
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int getPurchasePrice() {
        return buyingPrice;
    }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     * @return a list of actions that the wielder can do with this weapon
     */
    @Override
    public List<Action> getAllowableActions(){

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
                        ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ALLIES) ) ) {

                    // to make sure that those who are of same type do not attack each other
                    for ( ActorTypes type : ActorTypes.values() ) {

                        // only execute, if the actor holding this weapon has a certain capability and the target does not have the same
                        // eg: whoHasThis == Player and target != Player
                        if ( (whoHasThis.hasCapability(type) && !target.hasCapability(type)) ) {
                            // returns a new action with weapon which the actor will use on the targets if actor has weapons
                            res.add(new QuickStepAction(target, exit.getName(), this));
                            res.add(new AttackAction(target, exit.getName(), this));
                        }
                    }
                }
            }
        }
        return res;
    }
}
