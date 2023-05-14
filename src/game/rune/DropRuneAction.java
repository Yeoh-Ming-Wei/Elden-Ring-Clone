package game.rune;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.player.Player;

public class DropRuneAction extends Action {

    private Rune rune ;

    public DropRuneAction(int value) {
        this.rune = new Rune(value) ;
    }

    /**
     * When executed, add the dropped item to the game map
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of the action suitable for feedback in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = Player.getInstance() ;

        map.at(player.getLocation().x(), player.getLocation().y()).addItem(rune) ;

        String str =  String.format("%s with a value of %d", menuDescription(actor), rune.getRune()) ;
        return str;
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player drops the potato"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drops his rune";
    }
    
}
