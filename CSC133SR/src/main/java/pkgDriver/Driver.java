package pkgDriver;

import pkgSrUtils.SrGoLArray;
import pkgSrUtils.SrWindowManager;

public class Driver {
    public static void main(String[] strArgs) {

        final int trianglesToRender = 20;
        SrWindowManager curWM = SlWindowManager.get(WIN_WIDTH, WIN_HEIGHT);
        SrLMRenderer currentScene = new SrLMRenderer(trianglesToRender, curWM);
        currentScene.render();




    }  //  public static void main(String[] strArgs)
    
}  //  public class Driver
