package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;

/**
 * BEHOLD, Awoooo!
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class LoneWolf extends ParentDog {

    private final int LONE_WOLF_MIN_RUNE = 55 ;
    private final int LONE_WOLF_MAX_RUNE = 1470 ;

    public LoneWolf() {
        super("Lone Wolf", 'h', 102);

        RuneManager.addEnemyDropRune(name, LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(97, "bites", 95);
    }
}
