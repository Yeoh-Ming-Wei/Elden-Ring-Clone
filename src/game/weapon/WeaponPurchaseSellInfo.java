package game.weapon;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import java.util.HashMap;

/**
 * This class is to have the mapping of the weapons and their purchasable
 * Because we wanted to avoid downcasting and adhere to open close as much as possible
 * and because the child can go in both HashMaps
 */
public class WeaponPurchaseSellInfo {

    public static HashMap<String,WeaponItem> purchasableWeaponItem = new HashMap<>();
    public static HashMap<String,Purchasable> purchasableWeapon = new HashMap<>();
    public static HashMap<String,Sellable> sellableWeaponMap = new HashMap<>();



    public static void addPurchasableWeaponItem(WeaponItem w){
        purchasableWeaponItem.put(w.toString(),w);
    }

    public static void addPurchasableWeapon(Purchasable p){
        purchasableWeapon.put(p.toString(),p);
    }

    public static void addSellableWeapon(Sellable s){
        sellableWeaponMap.put(s.toString(),s);
    }


    public static HashMap<String,Purchasable> purchasableNameWeapon = new HashMap<>();

    public static void addPurchasableNameWeapon(Purchasable w){
        purchasableNameWeapon.put(w.toString(),w);
    }

}
