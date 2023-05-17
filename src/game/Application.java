package game;

import java.util.Arrays;
import java.util.List;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.enemy.*;
import game.environment.GustOfWind;
import game.environment.Dirt;
import game.environment.Floor;
import game.environment.Graveyard;
import game.environment.PuddleOfWater;
import game.environment.SiteOfLostGrace;
import game.environment.Wall;
import game.player.Player;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Application {
	public static GameMap staticGameMap;

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt() , new Floor() , new Wall() , new Graveyard(), new GustOfWind(), new PuddleOfWater(), new SiteOfLostGrace());

		List<String> map = Arrays.asList(
		    "..nnnn................................................~~~~~~~~~~~~~~~~~~~~~",
				"......................#####....######..................~~~~~~~~~~~~~~~~~~~~",
				"..nnnn................#..___....____#...................~~~~~~~~~~~~~~~~~~~",
				"..................................__#....................~~~~~~~~~~~~~~~~~~",
				"......................#............_#......................~~~~~~~~~~~~~~~~",
				"......................#...........###......................................",
				"...........................................................................",
				"...........................................................................",
				"~~~~~~~~~~~.......................###___###................................",
				"~~~~~~~~~~~~......................________#....nnnn........................",
				"~~~~~~~~~~~~~.....................#___U____................................",
				"~~~~~~~~~~~~......................#_______#....nnnn........................",
				"~~~~~~~~~~~.......................###___###................................",
				"~~~~~~~~~~..........................#___#..................................",
				"...........................................................................",
				"...........................................................................",
				"...........................................................................",
				"..####__##...........................................&&&......######..##...",
				"..#.....__...........................................&&&......#....____....",
				"..#___..............&&&..............................&&&........__.....#...",
				"..####__###.........&&&......................................._.....__.#...",
				"....................&&&.......................................###..__###...",
				"..........................................................................."
				) ;
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

		staticGameMap = gameMap;

		gameMap.at(15, 17).addActor(new Trader());
		
		// HINT: what does it mean to prefer composition to inheritance?
		// use x = 38 and y = 10 to spawn at site of lost grace
		int x = 15;
		int y = 15;
		Player player = Player.getInstance();
		world.addPlayer(player, gameMap.at(x, y));

		gameMap.at(10, 13).addActor(new LoneWolf());
		gameMap.at(10, 15).addActor(new GodrickSoldier());


		/*

				gameMap.at(27, 8).addActor(new HeavySkeletonSwordsman());
		gameMap.at(14, 15).addActor(new LoneWolf());
		gameMap.at(14, 14).addActor(new LoneWolf());


		gameMap.at(15, 14).addActor(new LoneWolf());

		gameMap.at(23, 8).addActor(new SkeletalBandit());
		gameMap.at(24, 7).addActor(new SkeletalBandit());
		gameMap.at(24, 8).addActor(new LoneWolf());

		gameMap.at(27, 8).addActor(new HeavySkeletonSwordsman());
		gameMap.at(24, 7).addActor(new HeavySkeletonSwordsman());
		gameMap.at(24, 8).addActor(new LoneWolf());
		gameMap.at(24, 9).addActor(new GiantCrab());
		gameMap.at(25, 7).addActor(new GiantCrayFish());
		gameMap.at(25, 8).addActor(new GiantDog());
		gameMap.at(25, 9).addActor(new LoneWolf());

		//gameMap.at(15, 14).addActor(new SkeletalBandit());
		//gameMap.at(15, 14).addActor(new HeavySkeletonSwordsman());
		//gameMap.at(15, 14).addActor(new GiantCrab());
		//gameMap.at(15, 14).addActor(new GiantCrayFish());
		//gameMap.at(15, 14).addActor(new GiantDog());
		gameMap.at(15, 14).addActor(new LoneWolf());
		 */




		world.run();
	}
}
