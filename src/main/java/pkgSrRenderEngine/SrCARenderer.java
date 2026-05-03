package pkgSrRenderEngine;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgSrUtils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
        aliveCount = 0;

        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                float xmin, ymin;
                xmin = TILE_OFFSET_X + (PADDING_X + tileWidth) * col;
                ymin = (SrWindowManager.getWinHeight() - TILE_OFFSET_Y - tileHeight) - (PADDING_Y + tileHeight) * row;

                // change color of block if cell is alive
                if (myGol.isCellAlive(row, col)) {
                    float r = rand.nextFloat();
                    float g = rand.nextFloat();
                    float b = rand.nextFloat();
                    myVDMgr.setDefaultColor(new Vector4f(r, g, b, 1.0f));

                    // triangle 1
                    myVDMgr.fillTriangleVertexCoordinates(
                            xmin, ymin, 0, 0,
                            xmin + tileWidth, ymin, 0, 0,
                            xmin + tileWidth, ymin + tileHeight, 0, 0);

                    // triangle 2
                    myVDMgr.fillTriangleVertexCoordinates(
                            xmin, ymin, 0, 0,
                            xmin + tileWidth, ymin + tileHeight, 0, 0,
                            xmin, ymin + tileHeight, 0, 0);
                    aliveCount++;
                }
            }
        }
    }

    @Override
    protected void renderScene() {
        // next step
        while(!curWM.isGlfwWindowClosed()){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            double currentTime = glfwGetTime();
            if(currentTime - lastTime >= .5){
                lastTime = currentTime;
                myGol.onTickUpdate();
                fillVertexCoordinates();
                reuploadBuffers();
            }
            if(SrWindowManager.wasResized()) setupProjectionOnly();

            glDrawElements(GL_TRIANGLES, aliveCount *6, GL_UNSIGNED_INT, 0L);
            curWM.swapBuffers();
        }
    }
    private void reuploadBuffers() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(myVDMgr.getVertexArrayLength())
                .put(myVDMgr.getVertexArray(), 0, myVDMgr.getVertexArrayLength()).flip(), GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(myVDMgr.getIndexArray().length)
                .put(myVDMgr.getIndexArray(), 0, myVDMgr.getIndexArray().length).flip(), GL_DYNAMIC_DRAW);

    }
}
