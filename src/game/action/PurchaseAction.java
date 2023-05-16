package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
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
public class PurchaseAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor trader;

	/**
	 * The weapon to sell
	 */
	private WeaponItem weaponItem;

	/**
	 * The price
	 */
	private int buyingPrice;

	/**
	 * Constructor.
	 *
	 * @param trader the Actor to attack
	 * @param w the weapon to be bought
	 * @param buyingPrice the price of the weapon
	 */
	public PurchaseAction(Actor trader, WeaponItem w , int buyingPrice) {
		this.trader = trader;
		this.weaponItem = w;
		this.buyingPrice = buyingPrice;
	}


	/**
	 * Apporach description:
	 * 		1) the class will be instantiated with the weapon that wants to be bought
	 * 		2) call the rune manager to get the player's runes
	 * 		3) check if the player can buy this weapon
	 * 		4) if can buy, add this weapon to the player's inventory and deduct the player's runes
	 *
	 * Assumption:
	 * 		the weapons are already instantiated once, in order to be available to buy
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result = "Bought nothing";

		RuneManager runeManager = RuneManager.getInstance();

		int playerRunes = runeManager.returnRune();

		if ( playerRunes >= this.buyingPrice )
		{
			runeManager.deductRune(actor,buyingPrice);
			actor.addWeaponToInventory(weaponItem);
			result = actor + " buys " + weaponItem;
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
		return actor + " buys " + weaponItem + " from " + trader;
	}

}

