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
        if (mapIsAt == Application.staticGameMap && location.containsAnActor()) {
            if (x == 0) {
                GameMap currentGameMap = location.map();
                currentGameMap.removeActor(player);
                // player will enter castle
                Application.staticGameMap = Application.castle;
                Application.castle.addActor(player, Application.castle.at(1, 2));
            } else // if it's anywhere else 'D' then it will bring you to roundtable instead
            {
                GameMap currentGameMap = location.map();
                currentGameMap.removeActor(player);
                Application.staticGameMap = Application.table;
                Application.table.addActor(player, Application.table.at(1, 2));
            }
        }

        // this is when player in castle
        // if 'D' is on x == 0 of the map then will go to limgrave
        if (mapIsAt == Application.castle && location.containsAnActor()) {
            if (x == 0) {
                GameMap currentGameMap = location.map();
                currentGameMap.removeActor(player);
                //player return back to limgrave
                Application.staticGameMap = Application.limGrave;
                Application.staticGameMap.addActor(player, Application.staticGameMap.at(1, 15));
            }
            else{ // player will enter the boss room
                GameMap currentGameMap = location.map();
                currentGameMap.removeActor(player);
                Application.staticGameMap = Application.boss;
                Application.boss.addActor(player, Application.boss.at(3,4));
            }
        }

        // if player at roundtable then they will return to limgrave
        if (mapIsAt == Application.table && location.containsAnActor()){
            GameMap currentGameMap = location.map();
            currentGameMap.removeActor(player);
            Application.staticGameMap = Application.limGrave;
            Application.staticGameMap.addActor(player, Application.staticGameMap.at(1, 15));
        }
    }
}
