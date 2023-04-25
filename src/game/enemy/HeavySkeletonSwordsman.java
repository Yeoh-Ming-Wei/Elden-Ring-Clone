package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.*;
import game.weapons.Grossmesser;

import java.util.HashMap;
import java.util.Map;

public class HeavySkeletonSwordsman extends Enemy{
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();
    private int counter;

    /**
     * Constructor.
     *
     */
    public HeavySkeletonSwordsman() {
        super("Heavy Skeleton Swordsman", 'q', 153);
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());
        this.addCapability(PileOfBones.PILE_OF_BONES);
        this.addCapability(Status.SKELETON);
        this.addWeaponToInventory(new Grossmesser());
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // this means that, the HSS has used its skill of pile of bones
        // so its removed to allow checking of is it dead or still in pile of bones
        if (!this.hasCapability(PileOfBones.PILE_OF_BONES)) {

            // change the display and start the counter
            this.setDisplayChar('o');
            counter++;

            // if the HSS has been un - attacked for 3 rounds, revive
            if (counter == 3) {

                // give the HSS his skill back so that can be used again if he died again
                this.addCapability(PileOfBones.PILE_OF_BONES);

                // reset his display
                this.setDisplayChar('q');

                //resetting his HP
                this.resetMaxHp(getMaxHp());

                // reset the counter so can be used again
                counter = 0;

            }

        }
        else {
            if (behaviours.containsKey(AttackBehaviour.behaviorCode())) {
                Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);
                if (action != null) {
                    return action;
                }
            }

            if (behaviours.containsKey(WanderBehaviour.behaviorCode())) {
                Action action = behaviours.get(WanderBehaviour.behaviorCode()).getAction(this, map);
                if (action != null) {
                    return action;
                }
            }
        }
        return new DoNothingAction();
    }
    /**
     * The lone wolf can be attacked by any actor that has the HOSTILE_TO_ENEMY capability
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.PLAYER)) {
            if( otherActor.getWeaponInventory().size() > 0 ) {
                actions.add(new AttackAction(this, direction, otherActor.getWeaponInventory().get(0)));
            }
            else{
                actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
            }
        }
        return actions;
    }
}

