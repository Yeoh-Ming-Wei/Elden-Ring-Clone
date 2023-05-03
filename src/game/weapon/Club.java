package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A simple weapon that can be used to attack the enemy.
 * It deals 103 damage with 80% hit rate
 * Buying and selling are from the player's POV
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Lee Sing Yuan
 *
 */
public class Club extends WeaponItem implements Purchasable,Sellable{
    private int buyingPrice;
    private int sellingPrice;
    private static boolean isPurchaseAdded = false;
    private static boolean isSellableAdded = false;
    /**
     * Constructor
     */
    public Club() {
        super("Club", '!', 103, "bonks", 80);

        // to not return hardcode values
        this.buyingPrice = 600;
        this.sellingPrice = 100;

        if ( isPurchaseAdded == false ){
            isPurchaseAdded = true;
            WeaponPurchaseSellInfo.addPurchasableWeapon(new Club());
            WeaponPurchaseSellInfo.addPurchasableWeaponItem(new Club());
        }

        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new Club());
        }
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
