package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrab;
import game.enemy.HeavySkeletonSwordsman;
import game.enemy.LoneWolf;

public class WestMapEnemyFactory extends EnemyFactory {
    
    private static WestMapEnemyFactory eastFactory = null;

    private WestMapEnemyFactory() {
    }

    public static WestMapEnemyFactory getInstance() {
        if (eastFactory == null) 
            eastFactory = new WestMapEnemyFactory() ;
        return eastFactory ;
    }

    public void addEnemy(Ground ground, Location location) {
        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 27 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == 'n') {
            location.map().at(location.x(), location.y()).addActor(new HeavySkeletonSwordsman()) ;
        }
        
        if (p < 33 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '&') {
            location.map().at(location.x(), location.y()).addActor(new LoneWolf()) ;
        }

        if (p < 2 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '~') {
            location.map().at(location.x(), location.y()).addActor(new GiantCrab()) ;
        }

    }
}


