package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

public class GustOfWind extends Ground {
    
    public GustOfWind() {
		super('&');
	}

	@Override
	public void tick(Location location) {
		super.tick(location) ;

		int mid = location.map().getXRange().max() / 2 ;
		
		if (location.x() < mid) {
			WestMapEnemyFactory.getInstance().addEnemy(this, location) ;
		} else {
			EastMapEnemyFactory.getInstance().addEnemy(this, location) ;
		}
			
	}
}

