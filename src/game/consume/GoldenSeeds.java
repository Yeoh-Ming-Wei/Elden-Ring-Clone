package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
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

public class GoldenSeeds extends ConsumeItem{

    private Location currentLocation;

    /**
     * Constructs a new GoldenSeeds object.
     * The golden seeds are represented by the character 'S'.
     */
    public GoldenSeeds() {
        super("Golden Seeds", 'S', true, 0);
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * if the player is on it then the PickUpAction would be available
     * and the number of golden seeds in the player's inventory will increase
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */

    // NOTE: need make the usesLeft update when picked up
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;

        if (actor == Player.getInstance() && currentLocation.containsAnActor()) {
            Player player = Player.getInstance();
            List<Item> items = currentLocation.getItems();
            for (Item item : items)
                if (item == GoldenSeeds.this) { // Check if the current location have this item
                    PickUpAction pickUpAction = getPickUpAction(player);
                    pickUpAction.execute(player, currentLocation.map());
                    setUsesLeft(getUsesLeft() + 1);
                    player.addItemToInventory(new GoldenRunes());

                    break;
                }
        }
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
        FlaskOfCrimsonTears.maxUses += 1;
    }

    /**
     * Set the number of uses remaining for this potion.
     * @param usesLeft the number of uses remaining
     */
    public void setUsesLeft(int usesLeft) {
        super.setUsesLeft(usesLeft);
    }

    /**
     * Returns a code representing this potion.
     * @return
     */
    public static Class<? extends Item> seedsCode() {
        return GoldenSeeds.class;
    }

    /**
     * Returns a list of allowable actions for the golden seeds.
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
            if (item.getClass() == seedsCode()){
                res.add(new ConsumeAction(this,1, "increases Flask of Crimson Tears permanently by", " amount", super.getUsesLeft()));
                break;
            }
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
    public DropAction getDropAction(Actor actor) {
        return new DropConsumeItemAction(this) ;
    }

    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpConsumeItemAction(this) ;
    }

}