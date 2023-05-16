package game.player;
import game.weapon.Grossmesser;
import game.weapon.Scimitar;
import game.weapon.Uchigatana;

/**
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * @see PlayerRolesMap
 */
public class Samurai extends Player{
    // to check whether the class has been added to the static mapping or not
    private static boolean isAdded = false;

    public Samurai(){
        super("Samurai",'@',455);
        this.addWeaponToInventory(new Uchigatana());

        // adding to the static mapping if havent added
        if (isAdded == false) {
            isAdded = true;
            PlayerRolesMap.addPlayerRole(new Samurai());
        }
    }
}
