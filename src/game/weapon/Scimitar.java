package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.*;
import game.enemy.ActorTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A weapon used by skeleton
 * It deals 118 damage with 88% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class Scimitar extends WeaponItem implements Purchasable,Sellable{
    // to allow getAllowableActions to check
    private Location currentLocation;

    private int buyingPrice;
    private int sellingPrice;


    /**
     * Constructor
     */
    public Scimitar() {
        super("Scimitar", 's', 118, "slashes", 88);
        addCapability(WeaponSkill.AREA_ATTACK);
        buyingPrice = 600;
        sellingPrice = 100;

    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;
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
     *      6) checks surrounding to see if there is a valid target to use the skill on
     *              eg: Lone Wolf is of type enemy and Dog, Heavy Skeleton Swordsman is of type enemy and Skeleton
     *                  they can attack each other
     *      7) loops once more to add the targets
     *      8) if all checks pass, add the actions to the resulting list
     *      9) this part is for player to get attackActions with this weapon
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     *
     * @return a list of actions that the wielder can do with this weapon
     *          list will be empty if no actions are possible
     */
    public List<Action> getAllowableActions(){
        boolean isSkill = false;

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

        // attacking //
        // checks all locations around me
        // check if there is someone of different type to initiate skill
        List<Actor> targets;
        isSkill = GetAllowableActions.canSkill(whoHasThis,currentLocation);

        // add all the targets around this actor
        targets = GetAllowableActions.getTargets(isSkill,currentLocation);

        // adding the attack surrounding actions after getting all the actors
        if ( targets.size() > 0 ) {
            res.add(new AttackSurroundingAction(targets, "attacks surrounding", this));
        }

        // if got targets nearby
        // return attack single enemies
        // used by player
        if (!targets.isEmpty()) {

            // use exit cause want the direction
            for (Exit exit : currentLocation.getExits()) {
                Location l = exit.getDestination();

                // if it has an actor and it is attackable
                if (l.containsAnActor()) {

                    // get that actor and add the skill action
                    Actor target = l.getActor();
                    if (isValid.isValidRole(whoHasThis, target) && isValid.isValidActorType(whoHasThis, target)) {
                        res.add(new AttackAction(target, exit.getName(), this));
                    }
                }
            }
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
        if ( traderLocation != null && trader != null && whoHasThis.hasCapability(ActorTypes.PLAYER) ){
            res.add(new SellAction(trader,this,this.getSellingPrice()));
        }

        // buying //
        // this means that the res is for trader
        // checks if the player is in the range of the trader
        if ( playerLocation != null && trader != null && whoHasThis.hasCapability(ActorTypes.TRADER) ){

            // use a new Scimitar because if use the "this", will have bug caused by reference
            res.add(new PurchaseAction(trader,new Scimitar(),this.buyingPrice) ) ;
        }
        return res;
    }
}
