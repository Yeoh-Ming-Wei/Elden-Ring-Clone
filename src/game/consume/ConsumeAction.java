package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Application;
import game.player.Player;

public class ConsumeAction<T extends ConsumeItem> extends Action {
    private T consumable;
    private int action;
    private String verb;
    private String actionName;
    private int usesLeft ;

    public ConsumeAction(T consumable, int action, String verb, String actionName, int usesLeft) {
        this.consumable = consumable;
        this.action = action;
        this.verb = verb;
        this.actionName = actionName;
        this.usesLeft = usesLeft;
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

        if (consumable.getUsesLeft() != 0){
            String result = this.consumable + " " + this.verb + " " + this.action + this.actionName;

            //call ConsumeItem.use here
            consumable.use(player);

            consumable.setUsesLeft(consumable.getUsesLeft() - 1);
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
        return actor + " use " + this.consumable + " and have " + this.usesLeft + " left ";
    }
}
