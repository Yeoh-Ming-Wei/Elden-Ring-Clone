package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.*;
import game.action.DespawnAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.weapon.Grossmesser;

import java.util.HashMap;
import java.util.Map;

/**
 *  Spooky, spooky skeleton
 *  Created by: Loo Li Shen
 *  Modified by: Lee Sing Yuan
 */
public class HeavySkeletonSwordsman extends Skeleton{
    public HeavySkeletonSwordsman() {
        super("Heavy Skeleton Swordsman", 'q', 153);
        this.addWeaponToInventory(new Grossmesser());
    }
}

