package game.player;

import game.weapon.Club;
import game.weapon.Uchigatana;

public class Samurai extends Player{
    private static boolean isAdded = false;

    public Samurai(){
        super("Samurai",'@',455);
        this.addWeaponToInventory(new Uchigatana());

        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Samurai());
        }
    }
}
