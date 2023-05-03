package game.enemy;

import game.RuneManager;
import game.weapon.Scimitar;

/**
 * Cool guy
 *
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 */
public class SkeletalBandit extends Skeleton{

    public SkeletalBandit() {
        super("Skeletal Bandit", 'b', 184);
        this.addWeaponToInventory(new Scimitar());

        RuneManager.addEnemyDropRune(name, super.getMinRune(), super.getMaxRune()) ;
    }
}

