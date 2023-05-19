package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
import game.ResetManager;
import game.Resettable;

import java.util.ArrayList;
import java.util.List;

public class FlaskOfCrimsonTears extends ConsumeItem implements Resettable {
    static int maxUses = 2;
    static int action = 250;

    private Location currentLocation;

    /**
     * Constructor.
     */
    public FlaskOfCrimsonTears() {
        super("Flask Of Crimson Tears", 'C', false, maxUses) ;

        ResetManager.registerResettable(this);
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
     * Get the number of uses remaining for this potion.
     */
    public int getUsesLeft() {
        return super.getUsesLeft();
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        if (getUsesLeft() == 0) {
            return;
        }
        actor.heal(action);
    }

    /**
     * Set the number of uses remaining for this potion.
     * @param usesLeft the number of uses remaining
     */
    public void setUsesLeft(int usesLeft) {
        super.setUsesLeft(usesLeft);
    }

    /**
     * Returns the name of this potion.
     * @return the name of this potion
     */
    public static String getName() {
        return "Flask Of Crimson Tears";
    }

    /**
     * Returns a code representing this potion.
     * @return
     */
    public static Class<? extends Item> potionCode() {
        return FlaskOfCrimsonTears.class;
    }

    @Override
    public List<Action> getAllowableActions(){

        List<Action> res = new ArrayList<>();
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        for(Item item : whoHasThis.getItemInventory()){
            if (item.getClass() == potionCode()){
                res.add(new ConsumeAction(this,250, "Heal for", " Health", super.getUsesLeft()));
                break;
            }
        }
        return res;
    }

    @Override
    public void reset(GameMap map) {
        setUsesLeft(maxUses);
    }
}
