package pkgSrRenderEngine;
import org.lwjgl.BufferUtils;
import pkgSrUtils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

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
        int vbo = glGenBuffers();
        int ibo = glGenBuffers();

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
}
