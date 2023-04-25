package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
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

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Graveyard(), new GustOfWind(), new PuddleOfWater(), new Wall());

		List<String> map = Arrays.asList(
		//    "...........................................................................",
		// 		"......................#####....######......................................",
		// 		"......................#..___....____#......................................",
		// 		"..................................__#......................................",
		// 		"......................._____........#......................................",
		// 		"......................#............_#......................................",
		// 		"......................#...........###......................................",
		// 		"...........................................................................",
		// 		"...........................................................................",
		// 		"..................................###___###................................",
		// 		"..................................________#................................",
		// 		"..................................#________................................",
		// 		"..................................#_______#................................",
		// 		"..................................###___###................................",
		// 		"....................................#___#..................................",
		// 		"...........................................................................",
		// 		"...........................................................................",
		// 		"...........................................................................",
		// 		"..####__##....................................................######..##...",
		// 		"..#.....__....................................................#....____....",
		// 		"..#___..........................................................__.....#...",
		// 		"..####__###..................................................._.....__.#...",
		// 		"..............................................................###..__###...",
		// 		"...........................................................................");
		   "nnnn####", 
				"nnnnnnnn", 
				"&&&#~~~~",
				"&&&&&&&&") ;
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
		
		for(int i = 0 ; i < map.size() - 1 ; i++) {
			System.out.println(String.format("length: %d", map.get(i).length()));
			for(int j = 0 ; j <= map.get(i).length() - 1; j++) {
				int p = RandomNumberGenerator.getRandomInt(100) ;
				System.out.println(String.format("x: %d, y: %d", i, j ));
				if(map.get(i).charAt(j) == 'n' && p < 27 && !gameMap.isAnActorAt(gameMap.at(j, i))) gameMap.at(j, i).addActor(new LoneWolf()) ;

			}

		}
		
		// HINT: what does it mean to prefer composition to inheritance?
		Player player = new Player("Tarnished", '@', 300);
		world.addPlayer(player, gameMap.at(1, 2));

		world.run();
	}
}
