package game.player;
import game.weapon.Club;

/**
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * @see PlayerRolesMap
 */
public class Wretch extends Player{
    // to check whether the class has been added to the static mapping or not
    private static boolean isAdded = false;

    public Wretch(){
        super("Wretch",'@',414);
        this.addWeaponToInventory(new Club());

        // adding to the static mapping if haven't added
        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Wretch());
        }
    }
}
