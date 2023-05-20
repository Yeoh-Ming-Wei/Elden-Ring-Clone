package game.environment;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.Application;
import game.action.DeathAction;
import game.enemy.ActorTypes;
import game.player.Player;

public class GoldenFogDoor extends Ground {
    /**
     * A constructor for the GoldenFogDoor class
     */
    public GoldenFogDoor() {
        super('D');

    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(ActorTypes.PLAYER);
    }

    @Override
    public void tick(Location location) {
        Player player = Player.getInstance();
        GameMap mapIsAt = location.map();
        int x = location.x();

        // staticGameMap is limgrave currently
        // if 'D' is on x == 0 of the map then will go to castle
        if (mapIsAt == Application.limGrave && location.containsAnActor() && Application.staticGameMap == Application.limGrave) {
            if (x == 0) {
                transitionToMap(Application.castle, player, location);
            } else {
                transitionToMap(Application.table, player, location);
            }
        }

        // this is when player in castle
        // if 'D' is on x == 0 of the map then will go to limgrave
        if (mapIsAt == Application.castle && location.containsAnActor() && Application.staticGameMap == Application.castle) {
            if (x == 0) {
                transitionToMap(Application.limGrave, player, location);
            } else {
                transitionToMap(Application.boss, player, location);
            }
        }

        // if player at roundtable then they will return to limgrave
        if (mapIsAt == Application.table && location.containsAnActor()){
            transitionToMap(Application.limGrave, player, location);
        }

    }

    /**
     * Transitions the player to a new map and updates the current game map.
     *
     * @param newMap  The new map to transition to.
     * @param player  The player actor.
     * @param location The location of the player.
     */
    public static void transitionToMap(GameMap newMap, Actor player, Location location) {
        GameMap currentGameMap = location.map();
        currentGameMap.removeActor(player);
        newMap.addActor(player, newMap.at(1, 2));
        Application.staticGameMap = newMap;
    }
}

