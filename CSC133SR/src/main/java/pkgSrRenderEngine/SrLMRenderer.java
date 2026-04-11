package pkgSrRenderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgDriver.SrSpot;
import pkgSrUtils.SrWindowManager;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SrLMRenderer {
    public SrLMRenderer(int trianglesToRender, SrWindowManager wm){
        curWM = wm;
        myVDMgr = new SrVertexDataManager(trianglesToRender * SrSpot.CPV * SrSpot.VPST , trianglesToRender * SrSpot.IPST);
    }


    private int viewMatLocation;
    private SrWindowManager curWM;
    private SrVertexDataManager myVDMgr;
    private int projMatLocation;
    private SrVertexDataReader reader;


    public void render(){

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

        // system testing
        System.out.println("Expected Indices: " + (5 * 3)); // 5 triangles * 3 indices
        System.out.println("Actual indexArray length: " + myVDMgr.getIndexArray().length);
        System.out.println("Current iArrayNextIndex: " + myVDMgr.getIndexArray().length);


        renderScene();


         // void render()
    }
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
    public void renderScene(){
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
        curWM.destroyGlfwWindow();
    }
    public void initOpenGL(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glViewport(0, 0, SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        // This  changes the color of the window
        glClearColor(0f, 0f, 1.0f, 1.0f);
        int shader_program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);

        if (glGetError() != GL_NO_ERROR) {
            String infoLog = glGetShaderInfoLog(vs, 500);
            System.err.println(infoLog);
        }  //  if (glError != GL_NO_ERROR)

        final String stringVS = "#version 410 core\n" +
                "layout (location=0) in vec3 aPos;" +
                "layout (location=1) in vec2 aTexCoords;" +
                "layout (location=2) in vec4 aColor;" +
                "uniform mat4 uProjMatrix;" +
                "uniform mat4 uViewMatrix;" +
                "out vec2 fTexCoords;" +
                "out vec4 fColor;" +
                "void main(void) {" +
                "   fTexCoords = aTexCoords;" +
                "   fColor = aColor;" +
                "   gl_Position = uProjMatrix * uViewMatrix * vec4(aPos,1.0);" +
                "}";
        glShaderSource(vs, stringVS);
        glCompileShader(vs);
        glAttachShader(shader_program, vs);
        int fs = glCreateShader(GL_FRAGMENT_SHADER);

        final String stringPS = "#version 410 core\n" +
                "in vec4 fColor;" +
                "out vec4 outColor;" +
                "void main(void) {" +
                "   outColor = fColor;" +
                "}";
        glShaderSource(fs, stringPS);
        glCompileShader(fs);
        glAttachShader(shader_program, fs);
        glLinkProgram(shader_program);
        glUseProgram(shader_program);
        projMatLocation = glGetUniformLocation(shader_program, "uProjMatrix");
        viewMatLocation = glGetUniformLocation(shader_program, "uViewMatrix");
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
        Matrix4f proj = new Matrix4f().setOrtho(0, SrWindowManager.getWinWidth(), 0, SrWindowManager.getWinHeight(), 0, 100);
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        glUniformMatrix4fv(projMatLocation, false, proj.get(fb));

        Matrix4f view = new Matrix4f().lookAt(
                new Vector3f(0, 0, 10.0f),
                new Vector3f(0, 0, 0),
                new Vector3f(0, 1, 0)
        );
        glUniformMatrix4fv(viewMatLocation, false, view.get(fb));
    }
    private void setupProjectionOnly(){
        Matrix4f proj = new Matrix4f().setOrtho(
                0, SrWindowManager.getWinWidth(),
                0, SrWindowManager.getWinHeight(),
                0, 100
        );
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        glUniformMatrix4fv(projMatLocation, false, proj.get(fb));
    }
}
