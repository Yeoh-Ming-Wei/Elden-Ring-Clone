package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A weapon used by skeleton
 * It deals 115 damage with 85% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class Grossmesser extends WeaponItem implements Sellable{
    private int sellingPrice;
    private static boolean isSellableAdded = false;

    /**
     * Constructor
     */
    public Grossmesser() {

        super("Grossmesser", '?', 115, "slashes", 85);
        addCapability(WeaponSkill.AREA_ATTACK);
        sellingPrice = 100;

        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new Grossmesser());
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {}

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }
}
