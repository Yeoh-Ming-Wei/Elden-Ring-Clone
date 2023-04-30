package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class DespawnAction extends Action {
    private Actor enemy ;

    public DespawnAction(Actor actor) {
        this.enemy = actor ;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
            map.removeActor(actor);
            return System.lineSeparator() + menuDescription(actor);
    }

    public String menuDescription(Actor actor) {
        return actor + " removed from the map.";
    }
}


