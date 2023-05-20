package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.TradeManager;
import game.action.*;
import game.enemy.ActorTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A heavy crossbow
 * It deals 64 damage with 57% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class HeavyCrossbow extends WeaponItem implements Purchasable,Sellable{
    // to allow getAllowableActions to check
    private Location currentLocation;

    private int buyingPrice;
    private int sellingPrice;

    /**
     * Constructor
     */
    public HeavyCrossbow() {
        super("Heavy Crossbow", '}', 64, "shoots", 57);

        // to not return hardcode values
        this.buyingPrice = 1500;
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
        List<Actor> targets = NearMe.getSurroundingActors(whoHasThis,Application.staticGameMap,2);

        // adding the actions to all the enemies around this actor
        for ( Actor target : targets ){
            if ( isValid.isValidRole(whoHasThis,target) && isValid.isValidActorType(whoHasThis,target) ){
                res.add(new AttackAction(target,"target within range",this));
            }
        }

        /////////////////////////////////

        // trading \\

        // selling called by player
        res.addAll(TradeManager.getSellingAction(whoHasThis,this,this.getSellingPrice()));

        // purchasing called by trader
        res.addAll(TradeManager.getPurchasingAction(whoHasThis,new HeavyCrossbow(),this.getPurchasePrice()));

        return res;
    }
}
