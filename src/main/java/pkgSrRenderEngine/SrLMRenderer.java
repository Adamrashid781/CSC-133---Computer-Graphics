package pkgSrRenderEngine;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgDriver.SrSpot;
import pkgSrUtils.SrWindowManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SrLMRenderer {
    public SrLMRenderer(SrVertexDataReader vdr, SrWindowManager wm, SrCamera cam, SrShaderObject so){
        super();
    }

    private SrVertexDataReader reader;


    private void fillVertexCoordinates(){
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        int totalLines = reader.getNumVertices();
        final int NUMVERTS = totalLines - totalLines % SrSpot.VPST;
        float[] vec0, vec1, vec2;
        final int CX = 0, CY = 1, CU = 3, CV = 4, CO = 5;

        for (int curVert = 0; curVert < NUMVERTS; curVert += SrSpot.VPST) {
            vec0 = reader.getVertexCoordsArray(curVert);
            vec1 = reader.getVertexCoordsArray(curVert + 1);
            vec2 = reader.getVertexCoordsArray(curVert + 2);

            myVDMgr.setDefaultColor(new Vector4f(vec0[CO], vec0[CO+1], vec0[CO+2], vec0[CO+3]));
            myVDMgr.fillTriangleVertexCoordinates(
                    SrSpot.TILE_OFFSET_X + vec0[CX], SrSpot.TILE_OFFSET_Y + vec0[CY], vec0[CU], vec0[CV],
                    SrSpot.TILE_OFFSET_X + vec1[CX], SrSpot.TILE_OFFSET_Y + vec1[CY], vec1[CU], vec1[CV],
                    SrSpot.TILE_OFFSET_X + vec2[CX], SrSpot.TILE_OFFSET_Y + vec2[CY], vec2[CU], vec2[CV]);
        }  //  for(int curVert = 0; curVert < NUMVERTS; curVert += VPST)
    }
    protected void renderScene(){
        // Setting up the buffer
        while(!curWM.isGlfwWindowClosed()){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            if(SrWindowManager.wasResized()) {
                setupProjectionOnly();
            }
            glDrawElements(GL_TRIANGLES, myVDMgr.getIndexArray().length, GL_UNSIGNED_INT, 0L);

            curWM.swapBuffers();
        }
    }
    protected void initOpenGL(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glViewport(0, 0, SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        // This  changes the color of the window
        glClearColor(0f, 0f, 1.0f, 1.0f);

        shader.compileShader();
        shader.setShader();


    }

    private void setupVertexAttributes(){
        int stride = 9 * Float.BYTES ; // 9 floats per vertex
        // This part from AI
        // Position (Location 0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        // Texture (Location 1)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        // Color (Location 2)
        glVertexAttribPointer(2, 4, GL_FLOAT, false, stride, 5 * Float.BYTES);
        glEnableVertexAttribArray(2);
    }
    private void setupMatrices() {
        shader.loadMatrix4f("uProjMatrix", cam.getProjectionMatrix());
        shader.loadMatrix4f("uViewMatrix", cam.getViewMatrix());
    }
    private void setupProjectionOnly(){
        shader.loadMatrix4f("uProjMatrix", cam.getProjectionMatrix());
    }
}
