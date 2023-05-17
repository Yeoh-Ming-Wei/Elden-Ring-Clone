package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
import game.RandomNumberGenerator;
import game.player.Player;
import game.rune.RuneManager;

import java.util.ArrayList;
import java.util.List;

public class GoldenRunes extends ConsumeItem{

    private Item item;

    private static int startingUse = 0;

    private Location currentLocation;


    int min = 200;
    int max = 10000;

    public int runesAdded = RandomNumberGenerator.getRandomInt(min, max);

    public GoldenRunes() {
        super("Golden Runes", '*', true, startingUse);
    }

    @Override
    public void tick(Location currentLocation, Actor actor){
        Player player = Player.getInstance();
        GameMap mapIsAt = currentLocation.map();
        if ((actor == player && currentLocation.containsAnActor() && mapIsAt == Application.staticGameMap)){
            player.addItemToInventory(new GoldenRunes());
            currentLocation.removeItem(item);
            ConsumeItem.setUsesLeft(usesLeft + 1);
        }
    }

    /**
     * Get the number of uses remaining for this potion.
     */
    public static int getUsesLeft() {
        return ConsumeItem.getUsesLeft();
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        Player player = Player.getInstance();
        RuneManager.getInstance().addRune(player, runesAdded);

    }

    /**
     * Set the number of uses remaining for this potion.
     * @param usesLeft the number of uses remaining
     */
    public static void setUsesLeft(int usesLeft) {
        ConsumeItem.setUsesLeft(usesLeft);
    }

    public static Class<? extends Item> runesCode() {
        return GoldenRunes.class;
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
            if (item.getClass() == runesCode()){
                res.add(new ConsumeAction(this, runesAdded, "Collected", "Runes"));
            }
        }
        return res;
    }
}
