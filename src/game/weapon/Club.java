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
 * A simple weapon that can be used to attack the enemy.
 * It deals 103 damage with 80% hit rate
 * Buying and selling are from the player's POV
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Lee Sing Yuan
 */
public class Club extends WeaponItem implements Purchasable,Sellable{
    // to allow getAllowableActions to check
    private Location currentLocation;

    private int buyingPrice;
    private int sellingPrice;

    /**
     * Constructor
     */
    public Club() {
        super("Club", '!', 103, "bonks", 80);

        // to not return hardcode values
        this.buyingPrice = 600;
        this.sellingPrice = 100;

        // to avoid the bug where in the first round
        // cannot get allowable actions
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * Gets the buying price
     * @return int buying price
     */
    @Override
    public int getPurchasePrice() {
        return buyingPrice;
    }

    /**
     * Gets the selling price
     * @return int selling price
     */
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
     *
     * @return a list of actions that the wielder can do with this weapon
     *          list will be empty if no actions are possible
     */
    @Override
    public List<Action> getAllowableActions(){

        // attack \\
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

        // get the target and exit information surrounding this actor ( whoHasThis )
        ArrayList<SurroundingExit> surroundingExitsTargets = NearMe.getSurroundingExitTargets(whoHasThis,currentLocation);

        // adding the actions to all the enemies around this actor
        for ( SurroundingExit s : surroundingExitsTargets ){
            res.add(new AttackAction(s.getTarget(), s.getExit().getName(), this));
        }

        /////////////////////////////////

        // trading \\

        // trader
        Location traderLocation = null;
        Actor trader = null;

        // player
        Location playerLocation = null;

        // this would be for the player to check if he is in the range of the trader
        traderLocation = NearMe.whoInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.TRADER);
        trader = Application.staticGameMap.getActorAt(traderLocation);

        // this would be for the trader to check if the player is in the range of the trader
        playerLocation = NearMe.whoInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.PLAYER);

        // if trader is null, means this method is called by the trader so must set the trader to itself
        if ( trader== null ){
            trader = whoHasThis;
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

            // use a new club because if use the "this", will have bug caused by reference
            // also use a new weapon because want to give the trader unlimited weapons
            res.add(new PurchaseAction(trader,new Club(),this.buyingPrice) ) ;
        }
        return res;
    }
}
