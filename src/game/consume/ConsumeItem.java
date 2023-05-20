package game.consume;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;

/**

 * The ConsumeItem class is an abstract class that represents an item that can be consumed in the game.
 * It extends the Item class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Loo Li Shen
 */

public abstract class ConsumeItem extends Item{

    public int usesLeft;
    protected Actor use;

    /**
     * Constructs a new ConsumeItem object.
     *
     * @param name        the name of the item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable    true if and only if the item can be picked up
     * @param usesLeft    the number of uses remaining for the item
     */
    public ConsumeItem(String name, char displayChar, boolean portable, int usesLeft) {
        super(name, displayChar, portable);
        this.usesLeft = usesLeft;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }

    public abstract void use(Actor actor) ;
}