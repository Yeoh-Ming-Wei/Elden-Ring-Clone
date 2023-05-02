package potion;

import edu.monash.fit2099.engine.items.Item;

public abstract class PotionItem extends Item {
    private int usesLeft;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public PotionItem(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    /**
     * Sets the number of uses left for the potion.
     *
     * @param usesLeft the new number of uses left for the potion
     */
    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }

    public abstract String getName();
}