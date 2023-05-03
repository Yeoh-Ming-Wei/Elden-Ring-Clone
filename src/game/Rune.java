package game ;

import edu.monash.fit2099.engine.items.Item;

public class Rune extends Item {

    private int rune ;

    public Rune() {
        super("Rune", '$', false) ;
        rune = 0 ;
    }

    public void setRune(int value) {
        this.rune = value ;
    }

    public int getRune() {
        return this.rune ;
    }

}