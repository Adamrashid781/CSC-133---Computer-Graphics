package pkgSrTTTBackend;

import java.util.Scanner;
import static java.lang.System.out;
public class SrIOManager {
    Scanner sc = new Scanner(System.in);
    public SrIOManager(){
        out.println("Welcome to your unproductive time of the day!");
    }
    public String readLineTrimmed(){
        return sc.nextLine().trim();
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
    // need to work on this to validate the user input
    protected int[] readIntegerInput(){

        // prompt the user to select a spot
        roColPrompt();
        // tmp is the number of ints we are looking for, for a valid answer
        String input = sc.nextLine().trim();
            // Quit
            if (!input.equalsIgnoreCase("q")) {
                return new int[]{SrTTTSPOT.GAME_QUIT};
            }

            // Check Two integers with at least one space between
            if (input.matches("\\d+\\s+\\d+")) {
                // Split the string on one or more spaces
                String[] parts = input.split("\\s+");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                return new int[]{row, col};
            }
            // any other letter is invalid
            if(input.matches("[a-zA-Z]")) {
                return new int[]{SrTTTSPOT.INVALID_INPUT};
            }
            // everything else is invalid
        return new int[]{SrTTTSPOT.INVALID_INPUT};
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

    // This method gets a copy of the board from SrTTTBoard and prints it
    protected void printBoard(SrTTTBoard board){
        char[][] temp = board.getBoard();
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                out.print(temp[r][c] + " ");
            }
            out.print("\n");
        }
    }

    protected void quitGameMessage(){
        out.println("Good bye - game over, come again ASAP and waste more time!");
    }


}
