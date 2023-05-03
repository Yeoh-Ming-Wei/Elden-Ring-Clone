package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrab;
import game.enemy.GiantCrayFish;


public class PuddleOfWater extends Ground {
    
    public PuddleOfWater() {
        super('~') ;
    }

    public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			int mid = location.map().getXRange().max() / 2 ;
			
			if (p < 2 && !location.map().isAnActorAt(location)) {
				if (location.x() < mid) {
					location.map().at(location.x(), location.y()).addActor(new GiantCrab()) ;
				} else {
					location.map().at(location.x(), location.y()).addActor(new GiantCrayFish()) ;
				}
			}
	}
}
