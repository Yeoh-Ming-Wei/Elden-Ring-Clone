package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.Resettable;
import game.player.Player;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The FlaskOfCrimsonTears class represents a potion item called "Flask Of Crimson Tears" that can be consumed by actors in the game.
 * It extends the ConsumeItem class and implements the Resettable interface.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Loo Li Shen
 *
 */

public class FlaskOfCrimsonTears extends ConsumeItem implements Resettable {

    private int healAmount = 250;
    private int maxCapacity = 2 ;
    private static FlaskOfCrimsonTears flaskOfCrimsonTears = null ;

    /**
     * Constructor.
     */
    protected FlaskOfCrimsonTears() {
        super("Flask Of Crimson Tears", 'C', false, 2) ;

        
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    public static FlaskOfCrimsonTears getInstance() {
        ResetManager.registerResettable(flaskOfCrimsonTears) ;
        
        if(flaskOfCrimsonTears == null) {
            flaskOfCrimsonTears = new FlaskOfCrimsonTears() ;
        }

        return flaskOfCrimsonTears ;
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        actor.heal(healAmount);
    }

    public void addMaxCapacity(int value) {
        maxCapacity += value ;
    }

    /**
     * Returns a list of allowable actions for the flask.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions(){

        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this, 250, "Heal for", " Health", super.getUsesLeft())) ;
        }
        
        return res;
    }

    /**
     * Resets the flask to its initial state.
     *
     * @param map the game map
     */
    @Override
    public void reset(GameMap map) {
        setUsesLeft(maxCapacity);
    }
}
