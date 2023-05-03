package game.player;

import java.util.HashMap;

public class PlayerRolesMap {
    public static HashMap<String,Player> playerRoles= new HashMap<>();


    public static void addPlayerRole(Player player){
        playerRoles.put(player.toString(),player);
    }
}
