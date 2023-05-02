package potion;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import static potion.FlaskOfCrimsonTears.action;

public class ConsumeAction extends Action {
    private Item potion;

    public ConsumeAction(Item potion) {
        this.potion = potion;
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
        int usesLeft = FlaskOfCrimsonTears.getUsesLeft();
        if (usesLeft == 0) {
            System.out.println("This potion is empty.");
        } else {
            System.out.println(usesLeft);
            System.out.println(actor + " drinks " + FlaskOfCrimsonTears.getName() + ", restoring " + action + " health.");
            actor.heal(action);

            FlaskOfCrimsonTears.setUsesLeft(usesLeft - 1);
            System.out.println(FlaskOfCrimsonTears.getUsesLeft());
        }

        return menuDescription(actor);
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses " + potion;
    }
}
