package game.environment;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.action.ResetAction;
import game.enemy.ActorTypes;
import game.player.Player;

public class SiteOfLostGrace extends Ground {

    public static boolean isVisited = false;

    public SiteOfLostGrace() {
        super('U') ;
    }

    @Override
    public void tick(Location location) {

        Player player = Player.getInstance() ;
        super.tick(location);
        
        if (player.lastVisited()[0] == -1 && player.lastVisited()[1] == -1) {
            player.setLastVisited(location.x(), location.y()) ;
        }

        // // if player is alive
        // if (Player.getInstance().isConscious()) {
        //     // Check if the player is standing on this ground
        //     if (location.containsAnActor()) {
        //         // Reset the game 
        //         isVisited = true;
        //         ResetManager.run(location.map());
        //     }
        // }
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        
        ActionList actions = new ActionList() ;

        if(actor.hasCapability(ActorTypes.PLAYER)) {
            actions.add(new ResetAction()) ;
        }

        return actions ;
    }
}