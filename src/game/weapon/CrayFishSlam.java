package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.List;

/**
 * A weapon used by GiantCrayfish to slam the area
 * It deals 527 damage with 100% hit rate
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class CrayFishSlam extends WeaponItem {
    private Location currentLocation;

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
    public void tick(Location currentLocation, Actor actor) { this.currentLocation = currentLocation; }

    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     * @return a list of actions that the wielder can do with this weapon
     */
    @Override
    public List<Action> getAllowableActions(){
        // the resulting list of actions
        List<Action> res = GetAllowableActions.getSurroundingAttackAllowableActions(currentLocation,this);
        return res;
    }

}
