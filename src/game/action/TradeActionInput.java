package game.action;
import java.util.Scanner;

public class TradeActionInput {

    public static int getChoiceMenu(int start, int exit){
        // to receive input
        Scanner sel = new Scanner(System.in);

        int choice = start - 1;
        // allows single buy  ( choice > exit || choice < start )
        do {
            // if the user did not put a number
            try {
                String input = sel.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please input a number");
            }

            // if number exceed options, tell the player to choose again
            if ( choice > exit || choice < start ){
                System.out.println("Please input a number that is available");
            }

            // if we choose a number smaller than the available options or bigger then the exit, continue looping
        } while ( choice > exit || choice < start );
        return choice;
    }

}
