package game.weapon;

import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * weapons must be added to both arrays manually cause cant type cast
 * they must be in same order
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


}
