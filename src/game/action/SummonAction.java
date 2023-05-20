package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.allies.Ally;
import game.player.Player;

/**
 * A summon action is an action that allows player to summon an Ally. It can
 * be called when the player is near the summon sign ground. 
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class SummonAction extends Action {

    /**
     * A function to execute the action.
     * A new ally will be summoned and located at the left side of the player.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Ally ally = Ally.getAllyInstance() ;
        Player player = Player.getInstance() ;
        map.at(player.getLocation().x() - 1, player.getLocation().y()).addActor(ally) ;

        return String.format("%s summoned %s", actor, ally) ;
    }

    /**
     * A method to show description about player is allowed to summon Ally at summon sign. 
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s perform summon at Summon Sign.", actor) ;
    }
    
}
