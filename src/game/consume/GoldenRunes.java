package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
import game.RandomNumberGenerator;
import game.player.Player;
import game.rune.RuneManager;

import java.util.ArrayList;
import java.util.List;

public class GoldenRunes extends ConsumeItem {

    private Item item;

    private static int startuse = 0;

    private int usesLeft;

    private Location currentLocation;


    int min = 200;
    int max = 10000;

    public int runesAdded = RandomNumberGenerator.getRandomInt(min, max);

    public GoldenRunes() {
        super("Golden Runes", '*', true, 1);
        this.addCapability(canbepick.CANBEPICK);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;

        if (actor == Player.getInstance() && currentLocation.containsAnActor()) {
            Player player = Player.getInstance();
            List<Item> items = currentLocation.getItems();
            for (Item item : items)
                if (item == GoldenRunes.this) { // Check if the current location have this item
                    PickUpAction pickUpAction = getPickUpAction(player);
                    pickUpAction.execute(player, currentLocation.map());
                    setUsesLeft(getUsesLeft() + 1);
                    player.addItemToInventory(new GoldenRunes());

                    this.removeCapability(canbepick.CANBEPICK);
                    break;
                }
        }
        System.out.println(Player.getInstance().getItemInventory());
    }

    /**
     * Get the number of uses remaining for this potion.
     */
    public int getUsesLeft() {
        return super.getUsesLeft();
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     *
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        Player player = Player.getInstance();
        if (getUsesLeft() == 0) {
            return;
        }
        RuneManager.getInstance().addRune(player, runesAdded);

    }

    /**
     * Set the number of uses remaining for this potion.
     *
     * @param usesLeft the number of uses remaining
     */
    public void setUsesLeft(int usesLeft) {
        super.setUsesLeft(usesLeft);
    }

    public static Class<? extends Item> runesCode() {
        return GoldenRunes.class;
    }

    @Override
    public List<Action> getAllowableActions() {

        List<Action> res = new ArrayList<>();
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if (whoHasThis == null) {
            return res;
        }



        for (Item item : whoHasThis.getItemInventory()) {
            if (item.getClass() == runesCode()) {
                res.add(new ConsumeAction<>(this, runesAdded, "Collected for", "Runes", super.getUsesLeft()));
                break;
            }
        }
        return res;
    }

    public void addGoldenRunesToRandomLocation(GameMap map, int count) {
        int xMax = map.getXRange().max();
        int yMax = map.getYRange().max();

        for (int i = 0; i < count; i++) {
            while (true) {
                int x = RandomNumberGenerator.getRandomInt(map.getXRange().min(), xMax);
                int y = RandomNumberGenerator.getRandomInt(map.getYRange().min(), yMax);

                Location location = map.at(x, y);
                if (location != null && location.getItems().isEmpty()) {
                    location.addItem(new GoldenRunes());
                    break;
                }
            }
        }
    }

    public void setStartingUse(int startingUse) {
        super.setUsesLeft(startingUse);
    }
}
