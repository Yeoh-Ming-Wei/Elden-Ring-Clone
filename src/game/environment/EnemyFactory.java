package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An abstract class for EnemyFactory to manage the enemies
 * spawn. The EastMapEnemyFactory and WestMapEnemyFactory 
 * are the child for EnemyFactory class.
 * @author Yeoh Ming Wei
 */
public abstract class EnemyFactory {

    /**
     * An abstract method to add enemy into the map. 
     * @param ground The specific environement. 
     * @param location The location of the environment.
     */
    public abstract void addEnemy(Ground ground, Location location) ;

}
