package game.player;

import java.util.HashMap;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.displays.Menu;
import game.ResetManager;
import game.Resettable;
import game.Status;
import game.action.ChoiceInput;
import game.enemy.ActorTypes;
import game.potion.ConsumeAction;
import game.potion.FlaskOfCrimsonTears;
import game.potion.Heal;
import game.rune.RuneManager;

/**
 * Class representing the Player. It implements the Resettable interface.
 *
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Lee Sing Yuan
 * @see PlayerRolesMap
 */
public abstract class Player extends Actor implements Resettable {
	private final Menu menu = new Menu();
	// the 1 and only player in the game
	private static Player player;

	private int[] lastSiteOfLostGrace ;


	/**
	 * Constructor that allows the children to use, but not the public to use
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	protected Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(ActorTypes.PLAYER);
		this.addItemToInventory(new FlaskOfCrimsonTears());
		this.lastSiteOfLostGrace = new int[2] ;
		this.lastSiteOfLostGrace[0] = -1 ; this.lastSiteOfLostGrace[1] = -1 ;
	}

	/**
	 * We are trying to make a singleton class because there can only be one player
	 * Approach description:
	 * 		1) instantiate all the classes to get their values in the static mapping
	 * 		2) have an input Mapping to map all the options that can be sellected to the names of the classes
	 * 		3) get the input of the user
	 * 		4) go to the input Mapping to get the name of the class using the choice as a key
	 * 		5) go to the player mapping to get the instance of the class using the name as a key
	 * @return
	 */
	public static Player getInstance(){
		ResetManager.registerResettable(player);
		if (player == null){
			// must be instantiated the first time or cannot use the static
			new Bandit();
			new Samurai();
			new Wretch();

			// store the input mapping
			HashMap<Integer,String> inputMapping = new HashMap<>();
			int x = 0;
			for (String k : PlayerRolesMap.playerRoles.keySet() ){
				inputMapping.put(x,k);
				x++;
			}

			// to get the exit number
			int exit = PlayerRolesMap.playerRoles.size();

			// the printing
			System.out.println("Please select a role: ");
			for ( int y = 0 ; y < exit ; y++ ){
				System.out.println("" + y + ") " + inputMapping.get(y));
			}

			// get the user choice
			int choice = ChoiceInput.getChoiceInput(exit);

			// get the name of the class
			String key = inputMapping.get(choice);

			// get the instance using the name
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

		// Create a new action to consume a potion if the player has one in their inventory
		for (Item item : this.getItemInventory()) {
			if (item.hasCapability(Heal.HEAL)){
				actions.add(new ConsumeAction(item));
			}

		}

		// to print the HP before printing all the available options
		System.out.printf("HP: %s, Rune: %d\n", this.printHp(), RuneManager.getInstance().returnRune()) ;
		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	@Override
	public String toString() {
		return name;
	}

	public int[] lastVisited() {
		return lastSiteOfLostGrace ;
	}

	public void setLastVisited(int x, int y) {
		lastSiteOfLostGrace[0] = x ;
		lastSiteOfLostGrace[1] = y ;
	}

	@Override
	public void reset(GameMap map) {
		player.heal(getMaxHp()) ;
		player.addCapability(Status.DEAD);
	}

}
