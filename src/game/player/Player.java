package game.player;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.ResetManager;
import game.Resettable;
import game.action.ChoiceInput;
import game.consume.EscapeRope;
import game.consume.GoldenRunes;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.consume.FlaskOfCrimsonTears;
import game.rune.RuneManager;
import game.specialItems.RemembranceOfTheGrafted;
import game.weapon.*;

import java.util.Set;

/**
 * Class representing the Player. It implements the Resettable interface.
 *
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Lee Sing Yuan
 * @see RoleManager
 */
public class Player extends Actor implements Resettable {
	private final Menu menu = new Menu();
	// the 1 and only player in the game
	private static Player player;

	private int[] lastSiteOfLostGrace ;
	private Location location ;

	public static String playerName = "";


	/**
	 * Constructor that allows the children to use, but not the public to use
	 *
	 * @param name        Name to call the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	protected Player(String name, int hitPoints) {
		super(name, '@', hitPoints);
		this.addCapability(ActorTypes.PLAYER);
		this.addCapability(Roles.ALLIES);
		this.addItemToInventory(new FlaskOfCrimsonTears());
		this.addItemToInventory(new EscapeRope());
		this.lastSiteOfLostGrace = new int[2] ;
		this.lastSiteOfLostGrace[0] = -1 ; this.lastSiteOfLostGrace[1] = -1 ;

		playerName = name;

		// test
		this.addItemToInventory(new RemembranceOfTheGrafted());
	}

	/**
	 * The method to obtain an instance of this class,
	 *
	 * Note: the reason we need this method is because, the constructor has different parameters,
	 *      so we need another level before calling the constructor which will decide the parameters
	 *
	 * @return the player instance
	 */
	public static Player getInstance(){
		ResetManager.registerResettable(player);
		if (player == null){
			// instantiate the role manager
			RoleManager roleManager = new RoleManager();

			int exit = RoleManager.playerRoles.size();

			// the printing
			System.out.println("Please select a role: ");
			for ( int y = 0 ; y < exit ; y++ ){
				System.out.println("" + y + ") " + RoleManager.playerRoles.get(y).getName() );
			}

			// get the user choice
			int choice = ChoiceInput.getChoiceInput(exit);

			// get the Player role information
			PlayerRole wantedRole = RoleManager.playerRoles.get(choice);

			// create the player
			player = new Player(wantedRole.getName(),wantedRole.getHp());

			RoleManager.addCapabilityItemWeapon(player,wantedRole);
		}

		// if player already exist
		return player;
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		// because to use weapons' get allowableActions,
		// it needs to know the current locations
		// so need to tick first
		for ( WeaponItem w : this.getWeaponInventory() )
		{
			if ( w.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
				w.tick(map.locationOf(this), this);
				actions.add(w.getAllowableActions());
			}
		}

		// because to use items' get allowableActions,
		// it needs to know the current locations
		// so need to tick first
		for ( Item  i: this.getItemInventory() )
		{
			if ( i.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
				i.tick(map.locationOf(this), this);
				actions.add(i.getAllowableActions());
			}
		}

		this.location = map.locationOf(player) ;

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

	public int[] lastVisited() {
		return lastSiteOfLostGrace ;
	}

	public void setLastVisited(int x, int y) {
		lastSiteOfLostGrace[0] = x ;
		lastSiteOfLostGrace[1] = y ;
	}

	@Override
	public void reset(GameMap map) {
		player.resetMaxHp(getMaxHp()) ;
	}

	public Location getLocation() {
		return location ;
	}
}


