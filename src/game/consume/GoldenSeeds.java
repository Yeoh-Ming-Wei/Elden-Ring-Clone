package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
import game.RandomNumberGenerator;
import game.action.ConsumeAction;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GoldenSeeds extends ConsumeItem{

    private Location currentLocation;

    public GoldenSeeds() {
        super("Golden Seeds", 'S', true, 0);
    }

    /**
     * used to update the location so that getAllowableActions can use it
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
                res.add(new ConsumeAction<>(this,1, "Flask of Crimson Tears permanently increased by", " amount", super.getUsesLeft()));
                break;
            }
        }
        return res;
    }

    public void addGoldenSeedsToRandomLocation(GameMap map, int count) {
        int xMax = map.getXRange().max();
        int yMax = map.getYRange().max();

        for (int i = 0; i < count; i++) {
            while (true) {
                int x = RandomNumberGenerator.getRandomInt(map.getXRange().min(), xMax);
                int y = RandomNumberGenerator.getRandomInt(map.getYRange().min(), yMax);

                Location location = map.at(x, y);
                if (location != null && location.getItems().isEmpty()) {
                    location.addItem(new GoldenSeeds());
                    break;
                }
            }
        }
    }
}
