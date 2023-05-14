package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackAction;
import game.action.AttackSurroundingAction;
import game.action.UnsheatheAction;
import game.enemy.ActorTypes;
import game.enemy.Roles;

import java.util.ArrayList;
import java.util.List;

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
    private Location currentLocation;

    private int sellingPrice;

    // to check whether the class has been added to the static mapping or not
    private static boolean isSellableAdded = false;

    /**
     * Constructor
     */
    public Grossmesser() {

        super("Grossmesser", '?', 115, "slashes", 85);
        addCapability(WeaponSkill.AREA_ATTACK);
        sellingPrice = 100;

        // adding to the static mapping if haven't added
        if ( isSellableAdded == false ) {
            isSellableAdded = true;
            WeaponPurchaseSellInfo.addSellableWeapon(new Grossmesser());
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     * @return a list of actions that the wielder can do with this weapon
     */
    @Override
    public List<Action> getAllowableActions(){
        // the resulting list of actions
        List<Action> res = AttackSurroundingAllowableAction.getAllowableActions(currentLocation,this);
        return res;
    }
}
