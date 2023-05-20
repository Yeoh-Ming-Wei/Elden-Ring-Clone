package game.specialItems;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.TradeManager;
import game.Trader;
import game.action.NearMe;
import game.action.SellAction;
import game.action.TradeItemAction;
import game.enemy.ActorTypes;
import game.weapon.AxeOfGodrick;
import game.weapon.GraftedDragon;
import game.weapon.Uchigatana;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

public class RemembranceOfTheGrafted extends Item {
    // to allow getAllowableActions to check
    private Location currentLocation;
    private ArrayList<WeaponItem> tradableWeapons;
    private ArrayList<String> nameOfTradableTraders;

    private int sellingPrice;

    public RemembranceOfTheGrafted(){
        super("Remembrance of the Grafted",'O',true);

        this.sellingPrice = 20000;

        tradableWeapons = new ArrayList<>();
        tradableWeapons.add(new AxeOfGodrick());
        tradableWeapons.add(new GraftedDragon());

        // to avoid the bug where in the first round
        // cannot get allowable actions
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);

        nameOfTradableTraders = new ArrayList<>();
        nameOfTradableTraders.add(Trader.nameFingerReaderEnia);
    }

    /**
     * Gets the selling price
     * @return int selling price
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {

        this.currentLocation = currentLocation;
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    @Override
    public List<Action> getAllowableActions(){

        // the resulting list of actions
        List<Action> res = new ArrayList<>();

        // checks who has this item
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        // selling called by player
        res.addAll(TradeManager.getSellingAction(whoHasThis,this,this.getSellingPrice()));

        // trading called by player
        res.addAll(TradeManager.getTradeAction(whoHasThis,this,nameOfTradableTraders,tradableWeapons));

        return res;
    }
}
/*
        // trader
        Location traderLocation = null;
        Actor trader = null;

        // checks who has this item
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        // this would be for the player to check if he is in the range of the trader
        traderLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis, Application.staticGameMap,1, ActorTypes.TRADER );
        trader = Application.staticGameMap.getActorAt(traderLocation);


        // if the trader is in range
        if ( traderLocation != null && trader != null && whoHasThis.hasCapability(ActorTypes.PLAYER) )
        {
            // check if can trade with this trader
            if ( trader.toString() == Trader.nameFingerReaderEnia ){
                TradeItemAction a = new TradeItemAction(trader,this);
                a.setTradableWeapons(tradableWeapons);
                res.add(a);
            }

            // adding the ability to sell to any trader
            res.add(new SellAction(trader,this,this.sellingPrice));

        }
 */
