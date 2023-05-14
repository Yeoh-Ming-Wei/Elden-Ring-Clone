package game.rune ;

import edu.monash.fit2099.engine.items.Item;

/**
 * The currency of the game to buy weapons from the trader. 
 * It can be obtain through killing enemies. 
 * @author Yeoh Ming Wei
 */
public class Rune extends Item {

    /**
     * An integer attribute to represents the amount of rune available.
     */
    private int rune ;

    /**
     * A constructor for rune class.
     */
    public Rune(int value) {
        super("Rune", '$', false) ;
        setRune(value);
    }

    

    /**
     * A setter method to set the rune value.
     * @param value The value that will replaced the rune. 
     */
    public void setRune(int value) {
        this.rune = value ;
    }

    /**
     * A getter method to get the rune value.
     * @return An integer represents the amount of rune.
     */
    public int getRune() {
        return this.rune ;
    }

}