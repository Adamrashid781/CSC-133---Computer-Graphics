package pkgSrRenderEngine;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgSrUtils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15C.*;


public class SrKalosRenderer extends SrRenderer{

    private int currentSides = 3; // To Start with a triangle
    private double lastTime = 0; // Tracks 0.5s intervals
    private Random rand = new Random();





    public SrKalosRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so){
        super(wm, cam, so);
        // Max vertices: 100 polygons * (37 sides + 1 center) * 9 floats
        // Max indices: 100 polygons * 37 triangles * indices

        myVDMgr = new SrVertexDataManager(SrPolygonArrayData.GRID_ROWS * SrPolygonArrayData.GRID_COLS * SrPolygonArrayData.MAX_SIDES * 3 * 9,
                SrPolygonArrayData.GRID_ROWS * SrPolygonArrayData.GRID_COLS * SrPolygonArrayData.MAX_SIDES * 3);
    }

    @Override protected void fillVertexCoordinates(){

        System.out.println("fillVertexCoordinates called, sides: " + currentSides);
        System.out.println("WinWidth: " + SrWindowManager.getWinWidth() + " WinHeight: " + SrWindowManager.getWinHeight());

        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        for (int row = 0; row < SrPolygonArrayData.GRID_ROWS; row++) {

            for (int col = 0; col < SrPolygonArrayData.GRID_COLS; col++) {
                // draw polygon at this grid position
                // Calculate the center of each shape
                float cellWidth = (float) SrWindowManager.getWinWidth() / SrPolygonArrayData.GRID_COLS;
                float cellHeight = (float) SrWindowManager.getWinHeight() / SrPolygonArrayData.GRID_ROWS;
                // Calculate the spacing of all polygons
                float cx = col * cellWidth + cellWidth / 2;
                float cy = (SrPolygonArrayData.GRID_ROWS - 1 - row) * cellHeight + cellHeight / 2;

                // Generating random color for this polygon
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();

                // Draw polygon at (cx, cy)
                myVDMgr.setDefaultColor(new Vector4f(r,g,b, 1.0f));
                for(int i = 0; i < currentSides; i++){
                    double angle0 = 2 * Math.PI * i / currentSides;
                    double angle1 = 2 * Math.PI * (i + 1) / currentSides;

                    float vx0 = cx + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.cos(angle0);
                    float vy0 = cy + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.sin(angle0);
                    float vx1 = cx + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.cos(angle1);
                    float vy1 = cy + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.sin(angle1);

                    myVDMgr.fillTriangleVertexCoordinates(cx, cy, 0, 0,
                                                          vx0, vy0, 0, 0,
                                                          vx1, vy1, 0, 0);

                }
            }
        }
    }
    @Override protected void renderScene(){

        while(!curWM.isGlfwWindowClosed()){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // check if .5 seconds have passed
            double currentTime = glfwGetTime();
            if(currentTime - lastTime >= 0.5){
                lastTime = currentTime;
                currentSides++;
                if(currentSides > SrPolygonArrayData.MAX_SIDES){
                    currentSides = 3; // Reset back to the original triangle
                }
                // Regenerate geometry and re-upload to GPU
                fillVertexCoordinates();
                reuploadBuffers();
            }
            if(SrWindowManager.wasResized()) setupProjectionOnly();
            int usedIndexCount = currentSides * SrPolygonArrayData.GRID_ROWS * SrPolygonArrayData.GRID_COLS * 3;
            glDrawElements(GL_TRIANGLES, usedIndexCount, GL_UNSIGNED_INT, 0L);
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
