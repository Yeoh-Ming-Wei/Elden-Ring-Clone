package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;

public class ResetAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.run(map) ;
        return menuDescription(actor) ;
    }

    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s rests at Site of Lost Grace", actor) ;
    }
    
}
