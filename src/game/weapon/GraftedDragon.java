package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.TradeManager;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A weapon used by skeleton
 * It deals 115 damage with 85% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class GraftedDragon extends WeaponItem implements Sellable{
    private final Random random = new Random();
    // to allow getAllowableActions to check
    private Location currentLocation;

    private int sellingPrice;

    /**
     * Constructor
     */
    public GraftedDragon() {
        super("Grafted Dragon", 'N', 89, "scorch from the skies onto", 90);
        sellingPrice = 200;

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

        // attack \\
        boolean isSkill;

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


        List<Actor> targets;
        // attacking //
        // checks all locations around me
        // check if there is someone of different type to initiate skill
        isSkill = GetAllowableActions.canSkill(whoHasThis,currentLocation);

        // add all the targets around this actor
        targets = GetAllowableActions.getTargets(isSkill,currentLocation);

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

        // selling called by player
        res.addAll(TradeManager.getSellingAction(whoHasThis,this,this.getSellingPrice()));

        return res;
    }
}

