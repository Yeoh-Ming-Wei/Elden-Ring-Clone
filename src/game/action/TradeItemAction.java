package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.enemy.ActorTypes;
import game.player.RoleManager;
import game.rune.RuneManager;

import java.util.ArrayList;

/**
 * An Action to allow swapping of item/weaponItem for another item/weaponItem
 * requires you to set tradable list first before performing execute
 * This is because, when I was trying to overload the constructor I got
 * Java methods have the same erasure
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class TradeItemAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor trader;

	/**
	 * The weapon to trade
	 */
	private WeaponItem weaponItem;

	/**
	 * a list of all the items that this weapon can swap with
	 */
	private ArrayList<WeaponItem> tradableWeapons;

	/**
	 * The item to trade
	 */
	private Item item;

	/**
	 * a list of all the items that this item can swap with
	 */
	private ArrayList<Item> tradableItems;

	/**
	 * Constructor. for item swap with weapons
	 *
	 * @param trader the trader
	 * @param i the item to be traded
	 */
	public TradeItemAction(Actor trader , Item i , ArrayList<WeaponItem> tradableWeapons ) {
		this.trader = trader;
		this.item = i;
		this.tradableWeapons = tradableWeapons;
	}

	/**
	 * Approach description:
	 * 		1) checks if the list is
	 * 		2) check if this is called by the player
	 * 		3) if it is means player is the one who has this item
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = "";

		if (actor.hasCapability(ActorTypes.PLAYER)) {
			// if swapping for weapons
			if (tradableWeapons.size() > 0) {
				int exit = tradableWeapons.size();

				// the printing
				System.out.println("Please select a item/weapon: ");
				for (int y = 0; y < exit; y++) {
					System.out.println("" + y + ") " + tradableWeapons.get(y));
				}

				// get the user choice
				int choice = ChoiceInput.getChoiceInput(exit);

				WeaponItem w = tradableWeapons.get(choice);

				actor.addWeaponToInventory(w);
				actor.removeItemFromInventory(item);

			} else if (tradableItems.size() > 0) {

				int exit = tradableItems.size();

				// the printing
				System.out.println("Please select a item/weapon: ");
				for (int y = 0; y < exit; y++) {
					System.out.println("" + y + ") " + tradableItems.get(y));
				}

				// get the user choice
				int choice = ChoiceInput.getChoiceInput(exit);

				Item i = tradableItems.get(choice);

				actor.addItemToInventory(i);
				actor.removeItemFromInventory(item);
			} else {
				return "no tradable weapons/items set";
			}
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
		return actor + " trades " + (weaponItem != null ? weaponItem : item) + " to " + trader;
	}
}
