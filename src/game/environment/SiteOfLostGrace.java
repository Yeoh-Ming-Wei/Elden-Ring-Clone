package game.environment;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.action.RestAction;
import game.enemy.ActorTypes;
import game.player.Player;

public class SiteOfLostGrace extends Ground {

    public static boolean isVisited = false;

    public SiteOfLostGrace() {
        super('U') ;
    }

    @Override
    public void tick(Location location) {

        Player player = Player.getInstance();
        super.tick(location);
        
        if (player.lastVisited()[0] == -1 && player.lastVisited()[1] == -1) {
            player.setLastVisited(location.x(), location.y()) ;
        }

        if (player.hasCapability(Status.DEAD)) {
            location.map().moveActor(player, location);
            player.removeCapability(Status.DEAD);
        }
 
        player.removeCapability(Status.RESTING);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        
        ActionList actions = new ActionList() ;

        if(actor.hasCapability(ActorTypes.PLAYER)) {
            actions.add(new RestAction()) ;
        }

        return actions ;
    }
}