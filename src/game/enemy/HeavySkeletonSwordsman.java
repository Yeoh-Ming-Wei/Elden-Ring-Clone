package game.enemy;

import game.*;
import game.rune.RuneManager;
import game.weapon.Grossmesser;

/**
 * Spooky, spooky skeleton
 *
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Lee Sing Yuan
 */
public class HeavySkeletonSwordsman extends Skeleton{

    public HeavySkeletonSwordsman() {
        super("Heavy Skeleton Swordsman", 'q', 153);
        this.addWeaponToInventory(new Grossmesser());

        RuneManager.addEnemyDropRune(name, super.getMinRune(), super.getMaxRune()) ;
    }
}

