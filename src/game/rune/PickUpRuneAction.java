package game.rune;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;


public class PickUpRuneAction extends PickUpAction {

    private final Item item;
    
    public PickUpRuneAction(Item item) {
        super(item) ;
        this.item = item ;
    }

    public String execute(Actor actor, GameMap map) {
        int value = RuneManager.dropRuneAmount.get(item) ;

        RuneManager.getInstance().addRune(actor, value) ;

        map.locationOf(actor).removeItem(item);
        
        return String.format("%s pick up the rune with a value of %d", actor, value) ;

        
    }

    @Override
    public String menuDescription(Actor actor) {
        int value = RuneManager.dropRuneAmount.get(item) ;
        return String.format("%s retrieves Rune (value: %d)", actor, value) ;
    }
    

}
