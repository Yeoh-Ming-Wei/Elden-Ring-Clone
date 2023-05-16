package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.*;
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
    // to allow getAllowableActions to check
    private Location currentLocation;

    private int buyingPrice;
    private int sellingPrice;

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
     *          list will be empty if no actions are possible
     */
    @Override
    public List<Action> getAllowableActions(){
        // trader
        Location traderLocation = null;
        Actor trader = null;

        // player
        Location playerLocation = null;
        Actor player = null;


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


        // checks all locations around the actor
        for (Exit exit : currentLocation.getExits() ){
            Location l = exit.getDestination();

            // if it has an actor
            if (l.containsAnActor()){

                // get that actor and add the skill action and normal action to the person holding this
                Actor target = l.getActor();

                // checks if trader is in range of the player
                if ( whoHasThis.hasCapability(ActorTypes.PLAYER) && target.hasCapability(ActorTypes.TRADER) ){
                    traderLocation = l;
                    trader = target;
                }

                // if player is in the range of the trader
                if ( whoHasThis.hasCapability(ActorTypes.TRADER) && target.hasCapability(ActorTypes.PLAYER)){
                    playerLocation = l;
                    trader = whoHasThis;
                }

                // attacking //
                if ( isValid.isValidRole(whoHasThis,target) && isValid.isValidActorType(whoHasThis,target) ){
                    res.add(new QuickStepAction(target, exit.getName(), this));
                    res.add(new AttackAction(target, exit.getName(), this));
                }
            }
        }

        // selling //
        // this res will be for the player, means this weapon is in the player
        // if the player has this weapon and trader is within range
        if ( whoHasThis.hasCapability(ActorTypes.PLAYER) && traderLocation != null )
        {
            res.add(new SellAction(trader,this,this.getSellingPrice()));
        }

        // buying //
        // this means that the res is for trader
        // checks if the player is in the range of the trader
        if ( whoHasThis.hasCapability(ActorTypes.TRADER) && playerLocation != null ){

            // use a new GreatKnife because if use the "this", will have bug caused by reference 
            res.add(new PurchaseAction(trader,new GreatKnife(),this.buyingPrice) ) ;
        }
        return res;
    }
}

