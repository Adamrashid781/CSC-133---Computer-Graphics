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
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){
                float xmin, ymin;
                xmin = TILE_OFFSET_X + (PADDING_X + tileWidth) * col;
                ymin = (SrWindowManager.getWinHeight() - TILE_OFFSET_Y - tileHeight) - (PADDING_Y + tileHeight) * row;

                // change color of block if cell is alive
                if(myGol.isCellAlive(row, col)){
                    float r = rand.nextFloat();
                    float g = rand.nextFloat();
                    float b = rand.nextFloat();
                    myVDMgr.setDefaultColor(new Vector4f(r, g, b, 1.0f));

                    // triangle 1
                    myVDMgr.fillTriangleVertexCoordinates(
                            xmin, ymin, 0,0,
                            xmin + tileWidth, ymin, 0, 0,
                            xmin + tileWidth, ymin + tileHeight, 0, 0);

                    // triangle 2
                    myVDMgr.fillTriangleVertexCoordinates(
                            xmin, ymin, 0,0,
                            xmin + tileWidth, ymin+ tileHeight, 0, 0,
                            xmin , ymin + tileHeight, 0, 0);
                    aliveCount++;
                }
            }
        }
    }

    @Override
    protected void renderScene() {
        // next step
    }
}
