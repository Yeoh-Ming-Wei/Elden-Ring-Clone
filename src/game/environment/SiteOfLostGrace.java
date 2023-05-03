package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.player.Player;

public class SiteOfLostGrace extends Ground{

    public static boolean isVisited = false;

    public SiteOfLostGrace() {
        super('U');
    }


    @Override
    public void tick(Location location) {
        super.tick(location);
        int x = location.x();
        int y = location.y();
        // if player is alive
        if (Player.getInstance().isConscious()) {
            // Check if the player is standing on this ground
            if (location.containsAnActor()) {
                // Reset the game
                isVisited = true;
                ResetManager.run();
            }
        }
        ResetManager.run();

    }
}
