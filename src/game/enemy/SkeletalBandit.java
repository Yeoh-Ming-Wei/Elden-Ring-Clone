package game.enemy;

import game.weapon.Grossmesser;
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
    }
}

