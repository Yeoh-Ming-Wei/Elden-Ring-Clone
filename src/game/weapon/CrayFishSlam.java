package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A weapon used by GiantCrayfish to slam the area
 * It deals 527 damage with 100% hit rate
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class CrayFishSlam extends WeaponItem {

    /**
     * Constructor
     * display character _ means dont drop
     */
    public CrayFishSlam() {

        super("CrayFishSlam", '_', 527, "slams", 100);

        // to make it not droppable and not pick up able
        togglePortability();
        addCapability(WeaponSkill.AREA_ATTACK);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {}

}
