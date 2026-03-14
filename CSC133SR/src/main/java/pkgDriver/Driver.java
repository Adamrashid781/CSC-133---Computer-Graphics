package pkgDriver;

import pkgSrTTTBackend.SrMachinePlayer;

import static pkgSrTTTBackend.SrTTTSPOT.*;
import static pkgSrTTTBackend.SrTTTSPOT.GAME_INCOMPLETE;
import static pkgSrTTTBackend.SrTTTSPOT.GAME_QUIT;

public class Driver {
    public static void main(String[] args) {
        SrMachinePlayer myPlayer = new SrMachinePlayer();

        int retVal = GAME_INCOMPLETE;
        while (retVal == GAME_INCOMPLETE) {
            retVal = myPlayer.play();


            if (retVal != GAME_QUIT) {
                retVal = GAME_INCOMPLETE;
                myPlayer.clearBoard();
                myPlayer.playAgainMessage();
            }  //  if (retVal != GAME_QUIT)
        }  // while (retVal == GAME_INCOMPLETE)
    }  //  public static void main(String[] args)

}  // public class Driver


