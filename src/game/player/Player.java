package game.player;

import java.util.HashMap;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Resettable;
import game.RuneManager;
import game.action.ChoiceInput;
import game.enemy.ActorTypes;

/**
 * Class representing the Player. It implements the Resettable interface.
 * It carries around a club to attack a hostile creature in the Lands Between.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public abstract class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
	private static Player player;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	protected Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(ActorTypes.PLAYER);
		/*
		this.addWeaponToInventory(new Club());
		Grossmesser g = new Grossmesser();
		this.addWeaponToInventory(g);
		this.addWeaponToInventory(g);

		 */
	}

	public static Player getInstance(){
		if (player == null){
			// must be instantiated the first time or cannot use the static
			new Bandit();
			new Samurai();
			new Wretch();

			// execute asking which options
			HashMap<Integer,String> inputMapping = new HashMap<>();
			int x = 0;
			for (String k : PlayerRolesMap.playerRoles.keySet() ){
				inputMapping.put(x,k);
				x++;
			}
			int exit = PlayerRolesMap.playerRoles.size();

			System.out.println("Please select a role: ");
			for ( int y = 0 ; y < exit ; y++ ){
				System.out.println("" + y + ") " + inputMapping.get(y));
			}

			int choice = ChoiceInput.getChoiceMenu(exit);

			String key = inputMapping.get(choice);

			player = PlayerRolesMap.playerRoles.get(key);
		}

		// if player already exist
		return player;


	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// to print the HP before printing all the available options
		System.out.printf("HP: %s, Rune: %d\n", this.printHp(), RuneManager.getInstance().returnRune()) ;
		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void reset() {}
}
