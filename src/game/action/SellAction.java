package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.rune.RuneManager;
import game.weapon.*;
import java.util.HashMap;

/**
 * An Action to allow trading between player and trader
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class SellAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor trader;

	/**
	 * The weapon to sell
	 */
	private WeaponItem weaponItem;

	/**
	 * The item to sell
	 */
	private Item item;

	/**
	 * The price
	 */
	private int sellingPrice;

	/**
	 * Constructor. for weapons to be sold
	 *
	 * @param trader the Actor to attack
	 * @param w the weapon to be sold
	 * @param sellingPrice the selling price
	 */
	public SellAction(Actor trader , WeaponItem w , int sellingPrice) {
		this.trader = trader;
		this.weaponItem = w;
		this.sellingPrice = sellingPrice;
	}

	/**
	 * Constructor. for weapons to be sold
	 *
	 * @param trader the Actor to attack
	 * @param i the item to be sold
	 * @param sellingPrice the selling price
	 */
	public SellAction(Actor trader , Item i , int sellingPrice) {
		this.trader = trader;
		this.item = i;
		this.sellingPrice = sellingPrice;
	}


	/**
	 * Approach description:
	 * 		1) the weapon that wants to be sold will be instantiated with this class
	 * 		2) add the selling price to the player's runes
	 * 		3) remove the weapon from the player inventory
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = actor + " sold " + weaponItem;

		// get the runemanager
		RuneManager runeManager = RuneManager.getInstance();

		// adding the runes
		runeManager.addRune(actor,sellingPrice);

		// checks if we are selling weapon
		if ( weaponItem != null ) {

			// removing the weapon from the inventory
			actor.removeWeaponFromInventory(weaponItem);
		}

		// checks if we are selling item
		if ( item != null ){

			// remove the item
			actor.removeItemFromInventory(item);
		}

		return result;
	}

	/**
	 * Describes which target the actor is attacking with which weapon
	 *
	 * @param actor The actor performing the action.
	 * @return a description used for the menu UI
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " sells " + (weaponItem != null ? weaponItem : item) + " to " + trader;
	}
}
