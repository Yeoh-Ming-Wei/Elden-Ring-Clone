package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;

/**
 * a cuter awoo
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 */
public class Dog extends GodrickCastleInhabitants {

    private final int LONE_WOLF_MIN_RUNE = 52 ;
    private final int LONE_WOLF_MAX_RUNE = 1390 ;

    public Dog() {
        super("Dog", 'a', 104);

        RuneManager.addEnemyDropRune(name, LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(101, "bites", 93);
    }
}
