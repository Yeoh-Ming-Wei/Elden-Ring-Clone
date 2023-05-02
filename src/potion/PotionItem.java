package potion;


import edu.monash.fit2099.engine.items.Item;

import java.util.HashMap;

public abstract class PotionItem {

    public static HashMap<Class<? extends Item>, Integer> potionName = new HashMap<>();
}