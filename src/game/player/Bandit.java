package game.player;

import game.weapon.Club;
import game.weapon.GreatKnife;

public class Bandit extends Player{
    private static boolean isAdded = false;

    public Bandit(){
        super("Bandit",'@',414);
        this.addWeaponToInventory(new GreatKnife());

        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Bandit());
        }
    }
}
