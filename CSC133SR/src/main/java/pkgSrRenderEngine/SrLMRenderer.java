package pkgSrRenderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgSrUtils.SrSpot;
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
        myVDMgr.setDefaultColor(new Vector4f(0.2f, 0.4f, 0.6f, 0.8f));
        final float xmin = 400f, ymin = 1300, roh = 150f, rw = 1100f, rh = 300f,
                bw = 800f, bh = 700f,
                dl = roh + 250f , dw = 150f, dh = 300f,
                x1 = xmin + rw, y1 = ymin, x2 = (int)((xmin + x1)/2), y2 = ymin +
                rh,
                x3 = xmin + roh, y3 = ymin - bh, x4 = xmin + roh + bw, y4 = y3,
                x5 = x4, y5 = ymin, x6 = xmin + roh, y6 = ymin,
                x7 = xmin + dl, y7 = y3, x8 = x7 + dw, y8 = y7,
                x9 = x8, y9 = y8 + dh, x10 = x7, y10 = y9;
        final float u0 = 0f, v0 = 0f, u1 = 1f, v1 = 1f, u2 = 1f, v2 = 1f;
        // Vertex Colors:
        Vector4f cRoof = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        Vector4f cWall = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
        Vector4f cDoor = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);
        myVDMgr.setDefaultColor(cRoof);
        myVDMgr.fillTriangleVertexCoordinates(xmin, ymin, u0, v0,
                x1, y1, u1, v1,
                x2, y2, u2, v2);
        myVDMgr.setDefaultColor(cWall);
        myVDMgr.fillTriangleVertexCoordinates(x3, y3, u0, v0,
                x4, y4, u1, v1,
                x5, y5, u2, v1);
        myVDMgr.fillTriangleVertexCoordinates(x3, y3, u0, v0,
                x5, y5, u1, v1,
                x6, y6, u2, v1);
        myVDMgr.setDefaultColor(cDoor);
        myVDMgr.fillTriangleVertexCoordinates(x7, y7, u0, v0,
                x8, y8, u1, v1,
                x9, y9, u2, v1);
        myVDMgr.fillTriangleVertexCoordinates(x7, y7, u0, v0,
                x9, y9, u1, v1,
                x10, y10, u2, v1);
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
