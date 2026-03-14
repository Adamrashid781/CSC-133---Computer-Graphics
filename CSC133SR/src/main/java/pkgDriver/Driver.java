package pkgDriver;

import pkgSrRenderEngine.SrTMRenderer;
import pkgSrTTTBackend.SrMachinePlayer;
import pkgSrUtilities.SrWindowManager;

import static pkgSrTTTBackend.SrTTTSPOT.*;
import static pkgSrTTTBackend.SrTTTSPOT.GAME_INCOMPLETE;
import static pkgSrTTTBackend.SrTTTSPOT.GAME_QUIT;

public class Driver {
    public static void main(String[] args) {
        final int trianglesToRender = 200;

        SrWindowManager.get(2600, 2000);

        SrTMRenderer currentScene = new SrTMRenderer(trianglesToRender);
        currentScene.render();
    }  //  public static void main(String[] args)

}  // public class Driver


