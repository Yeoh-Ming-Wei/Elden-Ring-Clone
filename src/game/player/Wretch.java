package game.player;

import game.weapon.Club;

public class Wretch extends Player{
    private static boolean isAdded = false;

    public Wretch(){
        super("Wretch",'@',414);
        this.addWeaponToInventory(new Club());

        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Wretch());
        }
    }
}
