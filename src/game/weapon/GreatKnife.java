package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A knife that can be used to attack the enemy.
 * It deals 75 damage with 70% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class GreatKnife extends WeaponItem implements Purchasable,Sellable{
    private int buyingPrice;
    private int sellingPrice;
    private static boolean isPurchaseAdded = false;
    private static boolean isSellableAdded = false;
    /**
     * Constructor
     */
    public GreatKnife() {
        super("Great Knife", '/', 75, "slashes", 70);

        // to not return hardcode values
        this.buyingPrice = 3500;
        this.sellingPrice = 350;

        // adding the capability
        this.addCapability(WeaponSkill.QUICKSTEP);

        if ( isPurchaseAdded == false ){
            isPurchaseAdded = true;
            WeaponPurchaseSellInfo.purchasableWeapon.put("Club",new GreatKnife());
            WeaponPurchaseSellInfo.purchasableWeaponItem.put("Club",new GreatKnife());
        }

        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new GreatKnife());
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
