package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import game.RandomNumberGenerator;
import edu.monash.fit2099.engine.positions.Location;
import game.enemy.HeavySkeletonSwordsman;
import game.enemy.SkeletalBandit;

public class Graveyard extends Ground {
	
    public Graveyard() {
		super('n');
	}

	@Override
	public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			int mid = location.map().getXRange().max() / 2 ;
			
			if (p < 27 && !location.map().isAnActorAt(location)) {
				if (location.x() < mid) {
					location.map().at(location.x(), location.y()).addActor(new HeavySkeletonSwordsman()) ;
				} else {
					location.map().at(location.x(), location.y()).addActor(new SkeletalBandit()) ;
				}
			}
				 
	}

	
}
