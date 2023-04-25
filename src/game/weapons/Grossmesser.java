package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * A weapon used by skeleton
 * It deals 115 damage with 85% hit rate
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class Grossmesser extends WeaponItem {
    /**
     * Constructor
     */
    public Grossmesser() {

        super("Grossmesser", '?', 115, "slashes", 85);
        addCapability(WeaponSkill.AREA_ATTACK);
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {}

}
