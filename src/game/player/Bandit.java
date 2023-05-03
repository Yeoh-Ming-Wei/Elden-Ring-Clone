package game.player;
import game.weapon.GreatKnife;

/**
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * @see PlayerRolesMap
 */
public class Bandit extends Player{
    // to check whether the class has been added to the static mapping or not
    private static boolean isAdded = false;

    public Bandit(){
        super("Bandit",'@',414);
        this.addWeaponToInventory(new GreatKnife());

        // adding to the static mapping if havent added
        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Bandit());
        }
    }
}
