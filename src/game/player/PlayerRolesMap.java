package game.player;
import java.util.HashMap;

/**
 * A class to hold static mapping for the player roles
 * Its used to avoid downcasting and to adhere to open close as much as possible
 */
public class PlayerRolesMap {
    public static HashMap<String,Player> playerRoles= new HashMap<>();

    //to add the player to the mapping
    public static void addPlayerRole(Player player){
        playerRoles.put(player.toString(),player);
    }
}
