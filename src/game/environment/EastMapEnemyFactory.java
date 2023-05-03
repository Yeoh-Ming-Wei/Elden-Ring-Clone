package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrayFish;
import game.enemy.GiantDog;
import game.enemy.SkeletalBandit;

public class EastMapEnemyFactory extends EnemyFactory {
    
    private static EastMapEnemyFactory eastFactory = null;

    private EastMapEnemyFactory() {}

    public static EastMapEnemyFactory getInstance() {
        if (eastFactory == null) 
            eastFactory = new EastMapEnemyFactory() ;
        return eastFactory ;
    }

    public void addEnemy(Ground ground, Location location) {
        int p = RandomNumberGenerator.getRandomInt(100) ;


        if (p < 27 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == 'n') {
            location.map().at(location.x(), location.y()).addActor(new SkeletalBandit()) ;
        }
        
        if (p < 4 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '&') {
            location.map().at(location.x(), location.y()).addActor(new GiantDog()) ;
        }

        if (p < 1 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '~') {
            location.map().at(location.x(), location.y()).addActor(new GiantCrayFish()) ;
        }       
    }
}
