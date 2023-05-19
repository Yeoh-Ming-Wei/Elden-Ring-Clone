package game;

import java.util.Arrays;
import java.util.List;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.consume.GoldenRunes;
import game.consume.GoldenSeeds;
import game.enemy.*;
import game.environment.*;
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
	public static GameMap limGrave;
	public static GameMap castle;
	public static GameMap table;
	public static GameMap boss;

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt() , new Floor() , new Wall() , new Graveyard(), new GustOfWind(), new PuddleOfWater(), new SiteOfLostGrace(), new Cliff(), new GoldenFogDoor(), new Cage(), new Barrack());

		List<String> map = Arrays.asList(
				"D.D...................#.............#..........................+++.........",
				"......................#.............#.......................+++++..........",
				"......................#..___....____#.........................+++++........",
				"......................#...........__#............................++........",
				"......................#_____........#.............................+++......",
				"......................#............_#..............................+++.....",
				"......................######...######......................................",
				"...........................................................................",
				"...........................................................................",
				"........++++......................###___###................................",
				"........+++++++...................________#................................",
				"..........+++.....................#________................................",
				"............+++...................#_______#................................",
				".............+....................###___###................................",
				"..U.........++......................#___#..................................",
				"DDD...........+.U.............................D............................",
				"..............++...........................................................",
				"..............................................++...........................",
				"..................++++......................+++...............######..##...",
				"#####___######....++...........................+++............#....____....",
				"_____________#.....++++..........................+..............__.....#...",
				"_____________#.....+....++........................++.........._.....__.#...",
				"_____________#.........+..+.....................+++...........###..__###...",
				"_____________#.............++.............................................."
				) ;
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);

		List<String> stormveilcastle = Arrays.asList(
				"D.D.......................................................................D",
				"..................<...............<........................................",
				"D.D........................................................................",
				"##############################################...##########################",
				"............................#................#.......B..............B......",
				".....B...............B......#................#.............................",
				"...............................<.........<.................................",
				".....B...............B......#................#.......B..............B......",
				"............................#................#.............................",
				"#####################..#############...############.####..#########...#####",
				"...............#++++++++++++#................#++++++++++++#................",
				"...............#++++++++++++...<.........<...#++++++++++++#................",
				"...............#++++++++++++..................++++++++++++#................",
				"...............#++++++++++++#................#++++++++++++#................",
				"#####...##########.....#############...#############..#############...#####",
				".._______........................B......B........................B.....B...",
				"_____..._..____....&&........<..............<..............................",
				".........____......&&......................................................",
				"...._______..................<..............<....................<.....<...",
				"#####....##...###..#####...##########___###############......##.....####...",
				"+++++++++++++++++++++++++++#...................#+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++....................#+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++#....................+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++#...................#+++++++++++++++++++++++++++"

		);
		GameMap newGameMap = new GameMap(groundFactory, stormveilcastle);
		world.addGameMap(newGameMap);

		List<String> roundtable  = Arrays.asList(
				"##################",
				"#_D______________#",
				"#_D______________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"########___#######"
		);
		GameMap newGameMap2 = new GameMap(groundFactory, roundtable);
		world.addGameMap(newGameMap2);

		List<String> bossroom = Arrays.asList(
				"+++++++++++++++++++++++++",
				".........................",
				".........................",
				".........................",
				".........................",
				".........................",
				".........................",
				".........................",
				"+++++++++++++++++++++++++"
		);
		GameMap newGameMap3 = new GameMap(groundFactory, bossroom);
		world.addGameMap(newGameMap3);

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
		limGrave = gameMap;
		castle = newGameMap;
		table = newGameMap2;
		boss = newGameMap3;

		// Adding golden runes
		GoldenRunes goldenRunes = new GoldenRunes();
		goldenRunes.addGoldenRunesToRandomLocation(gameMap, 5);
		goldenRunes.addGoldenRunesToRandomLocation(newGameMap, 5);

		// Adding golden seeds
		GoldenSeeds goldenSeeds = new GoldenSeeds();
		goldenSeeds.addGoldenSeedsToRandomLocation((gameMap), 1);
		goldenSeeds.addGoldenSeedsToRandomLocation((newGameMap), 1);

		gameMap.at(15, 13).addActor(new Trader());
		
		// HINT: what does it mean to prefer composition to inheritance?
		// use x = 38 and y = 10 to spawn at site of lost grace
		int x = 1;
		int y = 14;
		Location l = new Location(gameMap,x,y);
		Player player = Player.getInstance();
		world.addPlayer(player, gameMap.at(x, y));



/*		gameMap.at(9, 16).addActor(new SkeletalBandit());
		gameMap.at(27, 7).addActor(new LoneWolf());
		gameMap.at(28, 9).addActor(new LoneWolf());

		gameMap.at(27, 8).addActor(new HeavySkeletonSwordsman());
		gameMap.at(24, 7).addActor(new HeavySkeletonSwordsman());
		gameMap.at(24, 8).addActor(new LoneWolf());
		gameMap.at(24, 9).addActor(new GiantCrab());
		gameMap.at(25, 7).addActor(new GiantCrayFish());
		gameMap.at(25, 8).addActor(new GiantDog());
		gameMap.at(25, 9).addActor(new LoneWolf());*/





		world.run();
	}
}
