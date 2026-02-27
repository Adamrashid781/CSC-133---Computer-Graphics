package pkgSrTTTBackend;

import java.util.Scanner;
import static java.lang.System.out;
public class SrIOManager {
    Scanner myScanner = new Scanner(System.in);
    void SrIOManager(){
        out.println("Welcome to your unproductive time of the day!");
    }

    protected void roColPrompt(){
        out.println("Enter valid row, column numbers separated by a space!\n");
    }

    protected void invalidEntryMessage(){
        out.println("Enter row col numbers (space separated) or \"q\" to quit:     ");
    }

    protected void initPrompt(){
        out.println("Invalid input! Try again.");
    }

    protected int[] readIntegerInput(int[] tmp){

        return tmp;
    }

    protected void playerWinMessage(){
        out.println("\nCongratulations you have outsmarted the computer!!\n");
    }

    protected void errorInPlayMessage(){
        out.println("Something went wrong - bailing out!");
    }

    void cellNotFreeMessage(int row, int col){
        out.println("cell  [" + row + ", " + col + "] is not available!");
    }

    protected boolean readQuitInput(){
            return true;
    }

    protected void gameDrawMessage(){
        out.println("That round was a draw; let's play another round!\n");
    }

    protected void machineWinMessage(){
        out.println("Try again - this time machine outsmarted you!\n");
    }

    protected void playAgainMessage(){
        out.println("Beginning another round; type \"q\" to quit!\n");
    }

    protected void printBoard(SrTTTBoard tt){

    }

    protected void quitGameMessage(){
        out.println("Good bye - game over, come again ASAP and waste more time!");
    }


}
