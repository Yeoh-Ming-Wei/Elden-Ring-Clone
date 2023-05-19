package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GodrickSoldier;

public class Barrack extends Ground {
    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Barrack() {
        super('B');
    }

    @Override
	public void tick(Location location) {
		super.tick(location) ;

        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 45 && !location.map().isAnActorAt(location)) {
            location.map().at(location.x(), location.y()).addActor(new GodrickSoldier()) ;
        }
	}
}
