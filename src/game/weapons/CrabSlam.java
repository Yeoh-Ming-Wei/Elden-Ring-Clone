package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A weapon used by crab to slam the area
 * It deals 208 damage with 90% hit rate
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class CrabSlam extends WeaponItem {

    /**
     * Constructor
     */
    public CrabSlam() {

        super("CrabSlam", '_', 208, "slams", 90);

        // to make it not droppable and not pick up able
        togglePortability();
        addCapability(WeaponSkill.AREA_ATTACK);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {}

}
