package game.environment;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.player.Player;

public class SiteOfLostGrace extends Ground{

    public SiteOfLostGrace() {
        super('U');
    }

    @Override
    public void tick(Location location) {
        super.tick(location);

        if (Player.counter() == 0) {
            // Check if the player is standing on this ground
            if (location.containsAnActor()) {
                // Reset the game
                ResetManager.run();
            }
        }
        ResetManager.run();

    }
}
