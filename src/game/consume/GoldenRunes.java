package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.Status;
import game.player.Player;
import game.rune.RuneManager;

import java.util.ArrayList;
import java.util.List;

/**

 * The GoldenRunes class represents a consumable item called "Golden Seeds" in the game.
 * It extends the ConsumeItem class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Yeoh Ming Wei
 *
 */

public class GoldenRunes extends ConsumeItem {

    private final int MIN = 200;
    private final int MAX = 10000;

    public int runesAdded = RandomNumberGenerator.getRandomInt(MIN, MAX);

    public GoldenRunes() {
        super("Golden Runes", '*', true, 0);
    }

    /**
     * Get the number of uses remaining for this potion.
     */
    public int getUsesLeft() {
        return super.getUsesLeft();
    }

    /**
     * Set the number of uses remaining for this potion.
     *
     * @param usesLeft the number of uses remaining
     */
    public void setUsesLeft(int usesLeft) {
        super.setUsesLeft(usesLeft);
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     *
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        Player player = Player.getInstance();
        RuneManager.getInstance().addRune(player, runesAdded);
    }

    /**
     * Returns a list of allowable actions for the golden runes.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions() {

        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this, runesAdded, "Collected for", "Runes", super.getUsesLeft())) ;
        }
        
        return res ;
  
    }

    /**
     * Adds golden runes to random locations on the given game map for the specified actor.
     * gets the size of the map and then the item is added if that map doesn't have any collusion
     *
     * @param map   the game map
     * @param actor the actor to add the seeds for
     */
    public void addGoldenRunesToRandomLocation(GameMap map, Actor actor) {

		int width = map.getXRange().max();
        int height = map.getYRange().max();

        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height ; j++) {

                int p = RandomNumberGenerator.getRandomInt(100) ;
                Location location = map.at(i, j) ;
                if (p < 2 && location.canActorEnter(actor) && !location.getGround().hasCapability(Status.ITEM_NOT_SPAWNABLE)) {
                    map.at(i, j).addItem(new GoldenRunes()); 
                }

            }
        }
    }
        

    @Override 
    public DropAction getDropAction(Actor actor) {
        return new DropConsumeItemAction(this) ;
    }

    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpConsumeItemAction(this) ;
    }

        

}
