package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.player.Player;

import static game.consume.ConsumeItem.getUsesLeft;

public class ConsumeAction extends Action {
    private ConsumeItem consumable;
    private int action;
    private String verb;
    private String actionName;

    public ConsumeAction(ConsumeItem consumable, int action, String verb, String actionName) {
        this.consumable = consumable;
        this.action = action;
        this.verb = verb;
        this.actionName = actionName;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = Player.getInstance();

        if (ConsumeItem.getUsesLeft() != 0){
            String result = this.consumable + " " + this.verb + " for " + this.action + " " + this.actionName;

            //call ConsumeItem.use here
            consumable.use(player);

            ConsumeItem.setUsesLeft(getUsesLeft() - 1);
            return result;
        }

        else{
            String result = this.consumable + " is empty.";
            return result;
        }

    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " use " + this.consumable + " and have " + ConsumeItem.getUsesLeft() + " left ";
    }
}
