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
public class SellAction extends Action {

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
	public SellAction(Actor trader, String direction) {
		this.trader = trader;
		this.direction = direction;
	}


	/**
	 * Approach description:
	 * 		1) get the weapon inventory of the player
	 * 		2) loop through the weapon inventory of the player
	 * 		3) check if the weapon item is sellable by using the name as key and checking if it in the sellable map
	 * 			if returns an object, its sellable
	 * 			if returns null, its not sellable
	 * 		4) get the index of the weapon and the name of the weapon, store in the input mapping
	 * 		5) get the number input ( which is actually the index of the weapon item in the weapon inventory )
	 * 		6) go to the input mapping to find the weapon name
	 * 		7) use the weapon name to find the selling price in the static sellable mapping
	 * 		8) add the selling price to the player runes, and remove the weapon from the player weapon inventory using the
	 * 			user input as index to find the weapon in the player weapon inventory
	 *
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		// get the runemanager
		RuneManager runeManager = RuneManager.getInstance();

		// to make it more readable
		String sellableWeaponKey;
		Sellable sellableWeapon;

		int sellingPrice;

		// stores
		// 1) the index of the choice
		// 2) the position of the item in the player inventory
		int choice;


		// return
		// will be changed if player bought something
		String result;

		// to store a mapping of all available weapons that can be sold
		// from player inventory
		HashMap<Integer,String> inputMapping = new HashMap<>();

		// for every item in player inventory
		for ( int x = 0 ; x < actor.getWeaponInventory().size() ; x++ ){

			// get the name
			String key = actor.getWeaponInventory().get(x).toString();

			// check if it exist in the sellable map
			// if it dosent exist means its not sellable
			if ( WeaponPurchaseSellInfo.sellableWeaponMap.get(key) != null ) {

				// if the weapon is sellable it will be in the hashmap
				// so then only add it to the inputMapping
				inputMapping.put(x, key);
			}

		}

		// exit number
		// must use player weapon inventory size
		// cause what happens if there is duplicate weapons
		int exit = actor.getWeaponInventory().size() ;

		System.out.println("Please select what you want to sell:");

		// to print out all the available options that can be sold
		for ( int x = 0 ; x < actor.getWeaponInventory().size()  ; x++ ){

			// cause some weapons are not sellable but still in weapon inventory, they will be null
			if ( inputMapping.get(x) != null ) {
				// to print out the name of the weapon using the index of the item in the player inventory
				System.out.println("" + x + ") " + inputMapping.get(x));
			}
		}

		// telling what number to press to exit
		System.out.println("" + exit + ") Exit");

		// the choice will be
		//the index of the element in the player inventory and the item to delete
		choice = ChoiceInput.getChoiceInput(exit);

		if ( choice == exit ){
			result = "Sold nothing";
			return result;
		}

		// if the user input a number that is printed out
		if ( inputMapping.get(choice) != null ) {
			// get the weapon name which acts as a key
			sellableWeaponKey = inputMapping.get(choice);

			// use the name of the weapon to find the price of the weapon
			sellableWeapon = WeaponPurchaseSellInfo.sellableWeaponMap.get(sellableWeaponKey);
			sellingPrice = sellableWeapon.getSellingPrice();

			// add the runes
			runeManager.addRune(actor, sellingPrice);

			// remove the weapon based on the index of the weapon in the player weapon inventory
			actor.removeWeaponFromInventory(actor.getWeaponInventory().get(choice));
			result = actor + " sold " + sellableWeaponKey;
			// check the player runes here if they can buy or not
		}
		// if the number inputted was for an item that did not exist in the print
		else{
			result = "Inputted unknown item number";
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
		return actor + " sells to " + trader;
	}
}
