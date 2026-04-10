package pkgDriver;

import org.joml.Vector3f;
import pkgSrRenderEngine.*;
import pkgSrUtils.*;


public class Driver {
    public static void main(String[] strArgs) {
        SrCamera myCamera = new SrCamera(0, WIN_WIDTH, 0, WIN_HEIGHT, 0, -1.0f, new Vector3f(0, 1.0f, 0));
        SrWindowManager curWM = SrWindowManager.get(WIN_WIDTH, WIN_HEIGHT);
        SrShaderObject mySO = new SrShaderObject("vs_texture_color_1.glsl", "fs_texture_color_1.glsl");
        SrVertexDataReader myDReader = new SrVertexDataReader(strArgs[0]);

        SrLMRenderer currentScene = new SrLMRenderer(myDReader, curWM, myCamera, mySO);
        boolean retVal = currentScene.render();
        if (retVal) {
            curWM.destroyGlfwWindow();
        }  //  if (retVal)
    }  //  public static void main(String[] strArgs)
    
}  //  public class Driver.
