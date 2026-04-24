package pkgSrRenderEngine;
import org.lwjgl.BufferUtils;
import pkgSrUtils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class SrRenderer {
    protected SrWindowManager curWM;
    protected SrVertexDataManager myVDMgr;
    protected SrCamera cam;
    protected SrShaderObject shader;
    protected int vbo, ibo;

    public SrRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so){
        this.curWM = wm;
        this.cam = cam;
        this.shader = so;
    }

    protected abstract void fillVertexCoordinates();

    public boolean render(){

        // 1. initGlfwWindow() calls GL.createCapabilities() we can start OpenGL work:
        initOpenGL();

        // 2. filling the Java arrays (RAM)
        fillVertexCoordinates();

        // 3. creating and binding buffers
        vbo = glGenBuffers();
        ibo = glGenBuffers();

        // uploading vertex data (positions, Colors)
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(myVDMgr.getVertexArrayLength())
                .put(myVDMgr.getVertexArray(), 0, myVDMgr.getVertexArrayLength()).flip(), GL_STATIC_DRAW);

        // Uploading Index Data
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(myVDMgr.getIndexArray().length)
                .put(myVDMgr.getIndexArray(), 0, myVDMgr.getIndexArray().length).flip(), GL_STATIC_DRAW);


        // 4. Tell OpenGL how to rad the data that was just passed (Attributes)
        setupVertexAttributes();

        // 5. Set your Matrices (projection/ View)
        setupMatrices();

//        // system testing
//        System.out.println("Expected Indices: " + (5 * 3)); // 5 triangles * 3 indices
//        System.out.println("Actual indexArray length: " + myVDMgr.getIndexArray().length);
//        System.out.println("Current iArrayNextIndex: " + myVDMgr.getIndexArray().length);


        renderScene();

        return true;
    }// void render()

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

    protected void setupVertexAttributes(){
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

    protected void setupMatrices() {
        shader.loadMatrix4f("uProjMatrix", cam.getProjectionMatrix());
        shader.loadMatrix4f("uViewMatrix", cam.getViewMatrix());
    }

    protected void setupProjectionOnly(){
        shader.loadMatrix4f("uProjMatrix", cam.getProjectionMatrix());
    }
}
