package game.enemy;

/**
 * Castle inhabitants
 *
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public abstract class GodrickCastleInhabitants extends Enemy {

    /**
     * The constructor for Dog parent
     * @param initName name
     * @param initDisplay UI char
     * @param initHp hit points
     */
    public GodrickCastleInhabitants(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);
        this.addCapability(ActorTypes.GODRICK);
    }

}
