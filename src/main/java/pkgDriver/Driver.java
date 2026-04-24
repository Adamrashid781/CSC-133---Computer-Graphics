package pkgDriver;

import org.joml.Vector3f;
import pkgSrRenderEngine.*;
import pkgSrUtils.*;
import static pkgDriver.SrSpot.*;


public class Driver {
    public static void main(String[] args) {
        final Vector3f myLFVec = new Vector3f(0, 0, 0.0f);
        final float camNear = 0.0f,  camFar = -1.0f;

        SrCamera myCamera = new SrCamera(0, WIN_WIDTH, 0, WIN_HEIGHT, camNear, camFar, myLFVec);
        SrWindowManager myWM = SrWindowManager.get(WIN_WIDTH, WIN_HEIGHT);
        SrShaderObject mySO = new SrShaderObject("vs_texture_color_1.glsl", "fs_texture_color_1.glsl" );
        int NUM_ROWS = 10, NUM_COLS = 10, MAX_SIDES = 36, RADIUS = 70;
        SlPolygonArrayData sceneData = new SlPolygonArrayData(NUM_ROWS, NUM_COLS, MAX_SIDES, RADIUS);

        SrRenderer currentScene = new SrKalosRenderer(myWM, myCamera, sceneData, mySO);

        boolean retVal =currentScene.renderScene();
        if (retVal) {
            myWM.destroyGlfwWindow();
        }  //  if (retVal)
    }  //  public static void main(String[] args)

}  //  public class Driver