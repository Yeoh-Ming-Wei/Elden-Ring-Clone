package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A long katana that can be used to attack the enemy.
 * It deals 115 damage with 80% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Uchigatana extends WeaponItem implements Purchasable,Sellable{
    private int buyingPrice;
    private int sellingPrice;
    /**
     * Constructor
     */
    public Uchigatana() {
        super("Uchigatana", ')', 115, "slashes", 80);

        // to not return hardcode values
        this.buyingPrice = 5000;
        this.sellingPrice = 500;
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {}

    @Override
    public int getPurchasePrice() {
        return buyingPrice;
    }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }
}
