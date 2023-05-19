package game.consume;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.positions.GameMap;

public class DropConsumeItemAction extends DropAction {

    private ConsumeItem item ;

    public DropConsumeItemAction(ConsumeItem item) {
        super(item);
        this.item = item ;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).addItem(item);
        item.setUsesLeft(item.getUsesLeft() - 1);
        return menuDescription(actor);
    }
    
}
