package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrab;


public class PuddleOfWater extends Ground {
    
    public PuddleOfWater() {
        super('~') ;
    }

    public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			
			if (p < 2 && !location.map().isAnActorAt(location)) {
				location.map().at(location.x(), location.y()).addActor(new GiantCrab())  ;
			}
	}
}
