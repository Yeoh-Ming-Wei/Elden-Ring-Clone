package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.LoneWolf;

public class GustOfWind extends Ground {
    
    public GustOfWind() {
		super('&');
	}

	@Override
	public void tick(Location location) {
		super.tick(location) ;

			int p = RandomNumberGenerator.getRandomInt(100) ;
			
			if (p < 33 && !location.map().isAnActorAt(location)) {
				location.map().at(location.x(), location.y()).addActor(new LoneWolf())  ;
			}
	}
}

