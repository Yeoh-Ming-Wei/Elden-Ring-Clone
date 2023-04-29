package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import game.RandomNumberGenerator;
import edu.monash.fit2099.engine.positions.Location;
import game.enemy.HeavySkeletonSwordsman;

public class Graveyard extends Ground {
	
    public Graveyard() {
		super('n');
	}

	@Override
	public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			
			if (p < 27 && !location.map().isAnActorAt(location)) {
				location.map().at(location.x(), location.y()).addActor(new HeavySkeletonSwordsman())  ;
			}
				 
	}

	
}
