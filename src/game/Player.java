package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import game.enemy.ActorTypes;
import game.weapon.*;

/**
 * Class representing the Player. It implements the Resettable interface.
 * It carries around a club to attack a hostile creature in the Lands Between.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(ActorTypes.PLAYER);
		this.addWeaponToInventory(new Club());
		this.addWeaponToInventory(new Grossmesser());
		this.addWeaponToInventory(new Uchigatana());
		this.addWeaponToInventory(new GreatKnife());
		this.addWeaponToInventory(new Scimitar());
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// to print the HP before printing all the available options
		System.out.println(this.printHp());
		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public void reset() {}
}
