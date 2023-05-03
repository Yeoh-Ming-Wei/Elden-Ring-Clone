package game.enemy;

import game.RuneManager;
import game.weapon.Scimitar;

/**
 *  Spooky, spooky skeleton
 *  Created by: Loo Li Shen
 *  Modified by: Lee Sing Yuan
 */
public class SkeletalBandit extends Skeleton{

    public SkeletalBandit() {
        super("Skeletal Bandit", 'b', 184);
        this.addWeaponToInventory(new Scimitar());

        RuneManager.addEnemyDropRune(name, super.getMinRune(), super.getMaxRune()) ;
    }
}

