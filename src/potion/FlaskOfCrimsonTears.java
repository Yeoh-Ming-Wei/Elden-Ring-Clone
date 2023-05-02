package potion;

import edu.monash.fit2099.engine.items.Item;
import game.Player;

public class FlaskOfCrimsonTears extends Item {
    private static int maxUses = 2;
    static int action = 250;

    private static int usesLeft;

    /**
     * Constructor.
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public FlaskOfCrimsonTears() {
        super("Flask Of Crimson Tears", 'C', false);
        usesLeft = maxUses;
        this.addCapability(Heal.HEAL);
    }

    /**
     * Get the number of uses remaining for this potion.
     * @return the number of uses remaining
     */
    public static int getUsesLeft() {
        return usesLeft;
    }

    /**
     * Use this potion, restoring the player's health by a fixed amount.
     * @param player the player who is using the potion
     */
    public void use(Player player) {
        if (usesLeft == 0) {
            return;
        }
        player.heal(action);
        usesLeft--;
    }

    /**
     * Set the number of uses remaining for this potion.
     * @param usesLeft the number of uses remaining
     */
    public static void setUsesLeft(int usesLeft) {
        FlaskOfCrimsonTears.usesLeft = Math.max(0, Math.min(maxUses, usesLeft));
    }

    /**
     * Returns the name of this potion.
     * @return the name of this potion
     */
    public static String getName() {
        return "Flask Of Crimson Tears";
    }

    /**
     * Returns a code representing this potion.
     * @return a code representing this potion
     */
    public static int potionCode() {
        return 123;
    }

}
