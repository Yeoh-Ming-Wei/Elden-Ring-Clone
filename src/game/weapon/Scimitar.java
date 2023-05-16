package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;
import game.action.PurchaseAction;
import game.action.SellAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;

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
    private Location currentLocation;
    private int buyingPrice;
    private int sellingPrice;

    // to check whether the class has been added to the static mapping or not
    private static boolean isPurchaseAdded = false;
    private static boolean isSellableAdded = false;

    /**
     * Constructor
     */
    public Scimitar() {
        super("Scimitar", 's', 118, "slashes", 88);
        addCapability(WeaponSkill.AREA_ATTACK);
        buyingPrice = 600;
        sellingPrice = 100;

    }

    @Override
    public void tick(Location currentLocation, Actor actor) { this.currentLocation = currentLocation; }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }


    @Override
    public int getPurchasePrice() {
        return buyingPrice;
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
     * @return a list of actions that the wielder can do with this weapon
     *          list will be empty if no actions are possible
     */
    public List<Action> getAllowableActions(){
        boolean isSkill = false;

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


        List<Actor> targets = new ArrayList<>();
        // attacking //
        // checks all locations around me
        // check if there is someone of different type to initiate skill
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

                // checking if there is someone valid to use the skill on  //
                if ( isValid.isValidRole(whoHasThis,target) && isValid.isValidActorType(whoHasThis,target) ){
                    isSkill = true;
                    break;
                }
            }
        }

        // after checking if can skill, get all targets
        // if cannot skill
        if ( isSkill ) {
            for (Exit exit : currentLocation.getExits()) {
                Location l = exit.getDestination();

                // if it has an actor and it is attackable
                if (l.containsAnActor()) {

                    // get that actor and add the skill action
                    Actor target = l.getActor();
                    targets.add(target);
                }
            }
        }

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

            // use a new Scimitar because if use the "this", will have bug caused by reference
            res.add(new PurchaseAction(trader,new Scimitar(),this.buyingPrice) ) ;
        }
        return res;
    }
}
