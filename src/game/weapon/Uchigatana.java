package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackAction;
import game.action.UnsheatheAction;

import java.util.ArrayList;
import java.util.List;

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
    private Location currentLocation;

    private int buyingPrice;
    private int sellingPrice;

    // to check whether the class has been added to the static mapping or not
    private static boolean isPurchaseAdded = false;
    private static boolean isSellableAdded = false;
    /**
     * Constructor
     */
    public Uchigatana() {
        super("Uchigatana", ')', 115, "slashes", 80);

        // adding the ability
        this.addCapability(WeaponSkill.UNSHEATHE);

        // to not return hardcode values
        this.buyingPrice = 5000;
        this.sellingPrice = 500;

        // adding to the static mapping if haven't added
        if ( isPurchaseAdded == false ){
            isPurchaseAdded = true;
            WeaponPurchaseSellInfo.addPurchasableWeapon(new Uchigatana());
            WeaponPurchaseSellInfo.addPurchasableWeaponItem(new Uchigatana());
        }

        // adding to the static mapping if haven't added
        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new Uchigatana());
        }
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int getPurchasePrice() {
        return buyingPrice;
    }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    @Override
    public List<Action> getAllowableActions(){
        List<Action> res = new ArrayList<>();
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }


        for (Exit exit : currentLocation.getExits() ){
            Location l = exit.getDestination();

            if (l.containsAnActor()){
                Actor target = l.getActor();
                res.add(new UnsheatheAction(target,exit.getName(),this));
                res.add(new AttackAction(target,exit.getName(),this));
            }
        }


        return res;
    }
}
