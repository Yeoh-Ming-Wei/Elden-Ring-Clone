package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.PileOfBones;
import game.weapon.Club;
import game.weapon.GreatKnife;
import game.weapon.Purchasable;
import game.weapon.Uchigatana;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	 * When executed, the chance to hit of the weapon that the Actor used is computed to determine whether
	 * the actor will hit the target. If so, deal damage to the target and determine whether the target is killed.
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// to receive input
		Scanner sel = new Scanner(System.in);

		// the variable to store the choice
		int choice = 0;

		// starting number
		// this is so that we can make the available numbers to press only from start
		// if want to change the starting number change here only
		// everywhere else will change automatically
		int start = 0;

		// return
		// will be changed if player bought something
		String result = "Bought nothing";

		// to store the arrayList of weapons
		ArrayList<Purchasable> inventory = new ArrayList<>();

		// adding all the available weapons
		inventory.add(new Uchigatana());
		inventory.add(new GreatKnife());
		inventory.add(new Club());


		// exit number
		int exit = inventory.size() + start ;

		System.out.println("Please select what you want to buy:");

		// to print out all the available options
		for ( int x = 0 ; x < inventory.size()  ; x++ ){
			// x+start cause these are the numbers we allow
			System.out.println( "" + (x + start) + ") " + inventory.get(x) );
		}

		// telling what number to press to exit
		System.out.println("" + exit + ") Exit");

		// allows single buy  ( choice > exit || choice < start )
		do {
			// if the user did not put a number
			try {
				choice = Integer.parseInt(sel.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Please input a number");
			}

			// if number exceed options, tell the player to choose again
			if ( choice > exit || choice < start ){
				System.out.println("Please input a number that is available");
			}

			// if the player did select a weapon
			// will be moved to checking if the player can buy or not
			if ( choice != exit ) {
				result = "You bought: " + inventory.get(choice - start) + " for " + inventory.get(choice - start).getPurchasePrice();
			}

			// if we choose a number smaller than the available options or bigger then the exit, continue looping
		} while ( choice > exit || choice < start );

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
