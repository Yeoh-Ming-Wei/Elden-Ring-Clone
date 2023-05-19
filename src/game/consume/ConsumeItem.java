package game.consume;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import edu.monash.fit2099.engine.positions.Location;
import game.player.Player;

public abstract class ConsumeItem extends Item{

    public int usesLeft;
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

    public int getUsesLeft() {
        return usesLeft;
    }

    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }


    public void use(Actor actor){

    }
}