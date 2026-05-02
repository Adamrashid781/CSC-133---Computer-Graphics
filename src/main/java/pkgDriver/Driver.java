package pkgDriver;

import org.joml.Vector3f;
import pkgSrRenderEngine.*;
import pkgSrUtils.*;
import static pkgDriver.SrSpot.*;


public class Driver {
    public static void main(String[] args) {
        final Vector3f myLFVec = new Vector3f(0, 1.0f, 0.0f);
        final float camNear = 0.0f,  camFar = -1.0f;

        SrCamera myCamera = new SrCamera(0, WIN_WIDTH, 0, WIN_HEIGHT, camNear, camFar, myLFVec);
        SrWindowManager myWM = SrWindowManager.get(WIN_WIDTH, WIN_HEIGHT);
        SrShaderObject mySO = new SrShaderObject("vs_texture_color_1.glsl", "fs_texture_color_1.glsl" );

        SrRenderer currentScene = new SrKalosRenderer(myWM, myCamera, mySO, TILE_WIDTH, TILE_HEIGHT, strArgs[0]);

        boolean retVal = currentScene.render();
        if (retVal) {
            myWM.destroyGlfwWindow();
        }  //  if (retVal)
    }  //  public static void main(String[] args)

}  //  public class Driver