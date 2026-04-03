package pkgDriver;

import pkgSrRenderEngine.SrLMRenderer;
import pkgSrUtils.*;


public class Driver {
    public static void main(String[] strArgs) {

        final int trianglesToRender = 20;
        SrWindowManager curWM = SrWindowManager.get(SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        SrLMRenderer currentScene = new SrLMRenderer(trianglesToRender, curWM);
        currentScene.render();




    }  //  public static void main(String[] strArgs)
    
}  //  public class Driver
