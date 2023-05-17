package game.consume;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;

public abstract class ConsumeItem extends Item{

    public static int usesLeft;
    protected Actor use;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public ConsumeItem(String name, char displayChar, boolean portable, int usesLeft) {
        super(name, displayChar, portable);
        this.usesLeft = usesLeft;
    }

    public static int getUsesLeft() {
        return usesLeft;
    }

    public static void setUsesLeft(int usesLeft) {
        ConsumeItem.usesLeft = usesLeft;
    }

    public void use(Actor actor){

    }
}