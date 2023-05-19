package game.consume;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;

public class PickUpConsumeItemAction extends PickUpAction {

    ConsumeItem item ;
    public PickUpConsumeItemAction(ConsumeItem item) {
        super(item);
        this.item = item ;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if(!actor.getItemInventory().contains(item)) {
		    actor.addItemToInventory(item);
        } 
        
        item.setUsesLeft(item.getUsesLeft() + 1);

		return super.execute(actor, map);
	}
}
