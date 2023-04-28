package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.enemy.GiantCrab;
import game.enemy.HeavySkeletonSwordsman;
import game.enemy.LoneWolf;
import game.environment.GustOfWind;
import game.environment.Graveyard;
import game.environment.PuddleOfWater;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Application {

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt() , new Floor() , new Wall() , new Graveyard(), new GustOfWind(), new PuddleOfWater());

		List<String> map = Arrays.asList(
		    "...........................................................................",
		 		"......................#####....######......................................",
				"......................#..___....____#......................................",
				"..................................__#......................................",
		 		"......................._____........#......................................",
		 		"......................#............_#......................................",
				"......................#...........###......................................",
		 		"...........................................................................",
		 		"...........................................................................",
		 		"..................................###___###................................",
		 		"..................................________#................................",
		 		"..................................#________................................",
		 		"..................................#_______#................................",
		 		"..................................###___###................................",
				"....................................#___#..................................",
		 		"...........................................................................",
		 		"...........................................................................",
		 		"...........................................................................",
		 		"..####__##....................................................######..##...",
				"..#.....__....................................................#....____....",
		 		"..#___..........................................................__.....#...",
		 		"..####__###..................................................._.....__.#...",
		 		"..............................................................###..__###...",
		 		"...........................................................................");
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);

		// BEHOLD, ELDEN RING
		for (String line : FancyMessage.ELDEN_RING.split("\n")) {
			new Display().println(line);
			try {
				Thread.sleep(200);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		gameMap.at(23, 17).addActor(new HeavySkeletonSwordsman());
		gameMap.at(23, 18).addActor(new HeavySkeletonSwordsman());
		gameMap.at(22, 18).addActor(new GiantCrab());

		//gameMap.at(23, 18).addActor(new LoneWolf());
		//gameMap.at(23, 16).addActor(new LoneWolf());
		//gameMap.at(22, 18).addActor(new LoneWolf());
		//gameMap.at(22, 15).addActor(new LoneWolf());
		
		// HINT: what does it mean to prefer composition to inheritance?
		Player player = new Player("Tarnished", '@', 300);
		world.addPlayer(player, gameMap.at(20, 17));

		world.run();
	}
}
