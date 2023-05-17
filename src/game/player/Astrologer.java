package game.player;
import game.weapon.AstrologerStaff;
import game.weapon.GreatKnife;

/**
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * @see PlayerRolesMap
 */
public class Astrologer extends Player{
    // to check whether the class has been added to the static mapping or not
    private static boolean isAdded = false;

    public Astrologer(){
        super("Astrologer",'@',396);
        this.addWeaponToInventory(new AstrologerStaff());

        // adding to the static mapping if havent added
        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Astrologer());
        }
    }
}
