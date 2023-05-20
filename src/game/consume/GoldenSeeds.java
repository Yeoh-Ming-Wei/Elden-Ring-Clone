package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.Status;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**

 * The GoldenSeeds class represents a consumable item called "Golden Seeds" in the game.
 * It extends the ConsumeItem class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Loo Li Shen
 *
 */

public class GoldenSeeds extends ConsumeItem {


    /**
     * Constructs a new GoldenSeeds object.
     * The golden seeds are represented by the character 'S'.
     */
    public GoldenSeeds() {
        super("Golden Seeds", 'S', true, 0);
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        FlaskOfCrimsonTears.getInstance().addMaxCapacity(1) ;
    }

    /**
     * Returns a list of allowable actions for the golden seeds.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions(){

        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this,1, "increases Flask of Crimson Tears permanently by", " amount", super.getUsesLeft()));
        }

        return res;
    }

    /**
     * Adds golden seeds to random locations on the given game map for the specified actor.
     * gets the size of the map and then the item is added if that map doesn't have any collusion
     *
     * @param map   the game map
     * @param actor the actor to add the seeds for
     */
    public void addGoldenSeedsToRandomLocation(GameMap map, Actor actor) {

        int width = map.getXRange().max();
        int height = map.getYRange().max();

        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height ; j++) {

                int p = RandomNumberGenerator.getRandomInt(100) ;
                Location location = map.at(i, j) ;
                if (p < 2 && location.canActorEnter(actor) && !location.getGround().hasCapability(Status.ITEM_NOT_SPAWNABLE)) {
                    map.at(i, j).addItem(new GoldenSeeds());
                }

            }
        }
    }

    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpConsumeItemAction(this) ;
    }

}