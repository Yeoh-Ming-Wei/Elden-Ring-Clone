package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.RuneManager;
import game.weapon.*;

import java.util.HashMap;
import java.util.Random;

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
	 * The direction of incoming trade.
	 */
	private String direction;


	/**
	 * Constructor.
	 *
	 * @param trader the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 */
	public PurchaseAction(Actor trader, String direction) {
		this.trader = trader;
		this.direction = direction;
	}


	/**
	 * Apporach description:
	 * 		1) get all the purchasable weapons
	 * 		2) use a counter as a key and put the purchasable weapons as value in the input mapping
	 * 		3) get the user choice
	 * 		4) use the choice as key to find the name in the input mapping
	 * 		5) use the name to find the buying price
	 * 		6) check if player has sufficient runes to buy
	 * 		7) if can buy, use the name as key to find in the weapon item mapping,
	 * 		8) put the weapon inside the player inventory
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

		RuneManager runeManager = RuneManager.getInstance();
		Purchasable purchasableWeapon;

		int buyingPrice;
		int playerRunes;
		// the variable to store the choice
		int choice;


		// will be changed if player bought something
		String result = "Insufficient Runes to buy";


		// exit number
		// can be purchsableWeapon size or purchsableWeaponItem size
		// unless the trader has an inventory
		int exit = WeaponPurchaseSellInfo.purchasableWeapon.size() ;

		// the input mapping
		HashMap<Integer,String> inputMapping= new HashMap<>();

		System.out.println("Please select what you want to buy:");

		// to print out all the available options
		// keyset() is the name
		// values() is the Purchasable
		int x = 0;

		// k = the name / the key of the purchasable weapon
		for ( String k : WeaponPurchaseSellInfo.purchasableWeapon.keySet()) {

			// x is the number for this option
			// k is the name of purchasable weapon
			inputMapping.put(x,k);
			System.out.println( "" + x + ") " + k + ", price: " + WeaponPurchaseSellInfo.purchasableWeapon.get(k).getPurchasePrice() );

			x++;

		}

		// telling what number to press to exit
		System.out.println("" + exit + ") Exit");

		choice = ChoiceInput.getChoiceInput(exit);

		if ( choice == exit ){
			result = "Bought nothing";
			return result;
		}

		// cause the inputMapping and the purchasableWeapon will be the same size always
		// there is no need to check unless trader has its own inventory
		String name = inputMapping.get(choice);

		// use the name to get the purchasable weapon object in the map
		purchasableWeapon = WeaponPurchaseSellInfo.purchasableWeapon.get(name);

		// get the purchase price
		buyingPrice = purchasableWeapon.getPurchasePrice();

		// get the runes
		playerRunes = runeManager.returnRune();

		// see if player can buy the weapon
		if ( playerRunes > buyingPrice )
		{
			// to reduce the player's runes after buying
			runeManager.deductRune(actor, purchasableWeapon.getPurchasePrice());

			// to add the weapon using the weapon item in the map using the name
			actor.addWeaponToInventory( WeaponPurchaseSellInfo.purchasableWeaponItem.get(name) );

			result = actor + " bought " + name;
		}
		// check the player runes here if they can buy or not

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
		return actor + " buys from " + trader;
	}

}
