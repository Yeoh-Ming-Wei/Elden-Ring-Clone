package game.weapon;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackAction;
import game.action.PurchaseAction;
import game.action.SellAction;
import game.action.UnsheatheAction;
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

    // to check whether the class has been added to the static mapping or not
    private static boolean isPurchaseAdded = false;
    /**
     * Constructor
     */
    public Club() {
        super("Club", '!', 103, "bonks", 80);

        // to not return hardcode values
        this.buyingPrice = 600;
        this.sellingPrice = 100;

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
                // to make sure that can if it is a player, can only attack enemies and vice versa for enemies if in the future they can use the weapon
                if ( ( whoHasThis.hasCapability(Roles.ALLIES) && target.hasCapability(Roles.ENEMIES) ) ||
                        ( whoHasThis.hasCapability(Roles.ENEMIES) && target.hasCapability(Roles.ALLIES) ) ) {

                    // to make sure that those who are of same type do not attack each other
                    for ( ActorTypes type : ActorTypes.values() ) {

                        // only execute, if the actor holding this weapon has a certain capability and the target does not have the same
                        // eg: whoHasThis == Player and target != Player
                        if ( (whoHasThis.hasCapability(type) && !target.hasCapability(type)) ) {
                            // returns a new action with weapon which the actor will use on the targets if actor has weapons
                            res.add(new AttackAction(target, exit.getName(), this));
                        }
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

            // use a new club because if use the "this", will have bug caused by reference 
            res.add(new PurchaseAction(trader,new Club(),this.buyingPrice) ) ;
        }
        return res;
    }
}
