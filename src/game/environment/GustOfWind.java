package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantDog;
import game.enemy.LoneWolf;

public class GustOfWind extends Ground {
    
    public GustOfWind() {
		super('&');
	}

	@Override
	public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			int mid = location.map().getXRange().max() / 2 ;
			
			if (p < 33 && !location.map().isAnActorAt(location)) {
				if (location.x() < mid) {
					location.map().at(location.x(), location.y()).addActor(new LoneWolf()) ;
				} else {
					location.map().at(location.x(), location.y()).addActor(new GiantDog()) ;
				}
			}
	}
}

