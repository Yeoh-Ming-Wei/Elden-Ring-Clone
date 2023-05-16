package game.enemy;

/**
 * BEHOLD, DOG!
 *
 * Created by: Adrian Kristanto
 * Modified by:
 */
public abstract class ParentDog extends Enemy {

    /**
     * The constructor for Dog parent
     * @param initName name
     * @param initDisplay UI char
     * @param initHp hit points
     */
    public ParentDog(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);
        this.addCapability(ActorTypes.PARENTDOG);
    }

}
