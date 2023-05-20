package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;

import game.environment.GoldenFogDoor;
import game.player.Player;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The EscapeRope class represents a potion item called "Teleport to RoundTable" that can be consumed only once by actors in the game.
 * It extends the ConsumeItem class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Loo Li Shen
 *
 */

public class EscapeRope extends ConsumeItem {

    private Location currentLocation;

    public EscapeRope() {
        super("Teleport to RoundTable", 'T', true, 1);
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
     * Get the number of uses remaining for this consumable.
     */
    public int getUsesLeft() {
        return super.getUsesLeft();
    }

    /**
     * Use this consumable, teleporting player to solg.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        if (getUsesLeft() == 0) {
            return;
        }
        GoldenFogDoor.transitionToMap(Application.table, Player.getInstance(), currentLocation);

    }

    /**
     * Returns a code representing this potion.
     * @return the EscapeRope class
     */
    public static Class<? extends Item> consumableCode() {
        return EscapeRope.class;
    }

    /**
     * Returns a list of allowable actions for the EscapeRope.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions(){

        List<Action> res = new ArrayList<>();
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        for(Item item : whoHasThis.getItemInventory()){
            if (item.getClass() == consumableCode()){
                res.add(new ConsumeAction(this,1, "teleport to", "st RoundTable", super.getUsesLeft()));
                break;
            }
        }
        return res;
    }
}