package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.RuneManager;
import game.weapon.Club;
import game.weapon.GreatKnife;
import game.weapon.Purchasable;
import game.weapon.Uchigatana;

import java.util.ArrayList;
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
	 * Random number generator
	 */
	private Random rand = new Random();

	private ArrayList<Purchasable> inventory;


	/**
	 * Constructor.
	 *
	 * @param trader the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 */
	public PurchaseAction(Actor trader, String direction, ArrayList<Purchasable> initInventory) {
		this.trader = trader;
		this.direction = direction;
		this.inventory = initInventory;
	}


	/**
	 * When executed, the chance to hit of the weapon that the Actor used is computed to determine whether
	 * the actor will hit the target. If so, deal damage to the target and determine whether the target is killed.
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

		// starting number
		// this is so that we can make the available numbers to press only from start
		// if want to change the starting number change here only
		// everywhere else will change automatically
		int start = 0;

		// return
		// will be changed if player bought something
		String result = "Insufficient Runes to buy";


		// exit number
		int exit = inventory.size() + start ;

		System.out.println("Please select what you want to buy:");

		// to print out all the available options
		for ( int x = 0 ; x < inventory.size()  ; x++ ){
			// x+start cause these are the numbers we allow
			System.out.println( "" + (x + start) + ") " + inventory.get(x) + ", price: " + inventory.get(x).getPurchasePrice() );
		}

		// telling what number to press to exit
		System.out.println("" + exit + ") Exit");

		choice = TradeActionInput.getChoiceMenu(start,exit);

		if ( choice == exit ){
			result = "Bought nothing";
			return result;
		}

		purchasableWeapon = inventory.get(choice);
		buyingPrice = purchasableWeapon.getPurchasePrice();
		playerRunes = runeManager.returnRune();

		if ( playerRunes > buyingPrice )
		{
			runeManager.deductRune(actor, purchasableWeapon.getPurchasePrice());
			actor.addWeaponToInventory( (WeaponItem) purchasableWeapon) ;
			result = actor + " bought " + inventory.get(choice);
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

/*
            // if the player did select a weapon
            // will be moved to checking if the player can buy or not
            if ( choice != exit ) {
                result = "You bought: " + inventory.get(choice - start) + " for " + inventory.get(choice - start).getPurchasePrice();
            }
 */