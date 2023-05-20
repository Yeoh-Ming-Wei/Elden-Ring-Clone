package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.allies.Ally;
import game.player.Player;

public class SummonAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {

        Ally ally = Ally.getAllyInstance() ;
        Player player = Player.getInstance() ;
        map.at(player.getLocation().x() - 1, player.getLocation().y()).addActor(ally) ;

        return String.format("%s summoned %s", actor, ally) ;
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s perform summon at Summon Sign.", actor) ;
    }
    
}
