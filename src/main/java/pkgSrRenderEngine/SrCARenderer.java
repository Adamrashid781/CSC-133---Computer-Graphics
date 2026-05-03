package pkgSrRenderEngine;

import org.joml.Vector4f;
import pkgSrUtils.*;
import java.util.Random;
import static pkgDriver.SrSpot.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL15C.*;

public class SrCARenderer extends SrRenderer {

    private SrGoLArray myGol;
    private int tileWidth, tileHeight;
    private int numRows, numCols;
    private int aliveCount;
    private Random rand = new Random();
    private double lastTime = 0;

    public SrCARenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so,
                        int tileWidth, int tileHeight, String fileName) {
        super(wm, cam, so);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        myGol = new SrGoLArray();
        myGol.loadFile(fileName);

        int[] rc = myGol.getNumRowsCols();
        numRows = rc[0];
        numCols = rc[1];

        myVDMgr = new SrVertexDataManager(numRows * numCols * 6 * 9,
                numRows * numCols * 6);
    }

    @Override
    protected void fillVertexCoordinates() {
        // next step

    }

    @Override
    protected void renderScene() {
        // next step
    }
}
