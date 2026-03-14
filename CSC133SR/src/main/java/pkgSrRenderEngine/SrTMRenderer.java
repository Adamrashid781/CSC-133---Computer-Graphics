package pkgSrRenderEngine;
import pkgSrUtilities.SrWindowManager;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;


public class SrTMRenderer {
    private long glfwWindow;
    private int WIN_POS_X = 30, WIN_POX_Y = 90;
    private int projMatLocation = 0, viewMatLocation = 0;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWFramebufferSizeCallback fbCallback;

    private int IPT ;
    private float[] vertexArray;
    private int FPV;
    private int vArrayNextIndex;
    private int[] indexArray;
    private int iArrayNextIndex;
    private int VPT;

    private SrWindowManager windowManager ;
    private int getProjMatLocation = 0, getViewMatLocation = 0;




    public SrTMRenderer(int trianglesToRender) {
        initVertexIndexArrays(trianglesToRender);
        SrWindowManager.getWinWidth();
        SrWindowManager.getWinWidth() ;


    }
    // private int SrWindowManager.getWinWidth = 2600, SrWindowManager.getWinWidth() = 2000;

    public void render(){
        // 1. Create window
        windowManager = SrWindowManager.get(SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        // 2. initGlfwWindow() calls GL.createCapabilities() we can start OpenGL work:
        initOpenGL();

        // 3. filling the Java arrays (RAM)
        fillVertexArray();

        // 4. creating and binding buffers
        int vbo = glGenBuffers();
        int ibo = glGenBuffers();

        // uploading vertex data (positions, Colors)
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(vArrayNextIndex)
                .put(vertexArray, 0, vArrayNextIndex).flip(), GL_STATIC_DRAW);

        // Uploading Index Data
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(iArrayNextIndex)
                .put(indexArray, 0, iArrayNextIndex).flip(), GL_STATIC_DRAW);


        // 5. Tell OpenGL how to rad the data that was just passed (Attributes)
        setupVertexAttributes();

        // 6. Set your Matrices (projection/ View)
        setupMatrices();

        // system testing
        System.out.println("Expected Indices: " + (5 * 3)); // 5 triangles * 3 indices
        System.out.println("Actual indexArray length: " + indexArray.length);
        System.out.println("Current iArrayNextIndex: " + iArrayNextIndex);

        // Setting up the buffer
        while(!windowManager.isGlfwWindowClosed()){
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            if(SrWindowManager.wasResized()) {
                setupProjectionOnly();
            }
            glDrawElements(GL_TRIANGLES, indexArray.length, GL_UNSIGNED_INT, 0L);

            windowManager.swapBuffers();
        }

        windowManager.destroyGlfwWindow();

    } // void render()


    public void initOpenGL(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glViewport(0, 0, SrWindowManager.getWinWidth(), SrWindowManager.getWinHeight());
        // This  changes the color of the window
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
    private void initVertexIndexArrays(int trianglesToRender){
        FPV = 9;    // Floats per Vertex (pos 3 + tex 2 + color 4)
        VPT = 3;    // Vertices per triangle
        IPT = 3;    // Indices per triangle

        int totalVertices = trianglesToRender * VPT;
        vertexArray = new float[totalVertices * FPV];
        indexArray = new int[trianglesToRender * IPT];

        vArrayNextIndex = 0;
        iArrayNextIndex = 0;
    }


//    private void fillVertices(float x0, float y0, float x1, float y1, float x2, float y2,
//                              float cR, float cG, float cB, float cA){
//        int baseVertex = (vArrayNextIndex / FPV);
//    }
protected void fillVertices(float x0, float y0, float x1, float y1, float x2, float y2,
                  float cR, float cG, float cB, float cA){

        int baseVertex = (vArrayNextIndex / FPV);  // vertex number before adding 1

        // z = 0 and texcoords will have dummy values
        putVertex(x0, y0, 0f, 0f, 0f, cR, cG, cB, cA);
        putVertex(x1, y1, 0f, 0f, 0f, cR, cG, cB, cA);
        putVertex(x2, y2, 0f, 0f, 0f, cR, cG, cB, cA);

        indexArray[iArrayNextIndex++] = baseVertex;
        indexArray[iArrayNextIndex++] = baseVertex + 1;
        indexArray[iArrayNextIndex++] = baseVertex + 2;


    }
    private void putVertex(float x, float y, float z, float u, float v, float r, float g, float b, float a){
        vertexArray[vArrayNextIndex++] = x;
        vertexArray[vArrayNextIndex++] = y;
        vertexArray[vArrayNextIndex++] = z;
        //vertexArray[vArrayNextIndex++] = -1.0f;

        vertexArray[vArrayNextIndex++] = u;
        vertexArray[vArrayNextIndex++] = v;

        vertexArray[vArrayNextIndex++] = r;
        vertexArray[vArrayNextIndex++] = g;
        vertexArray[vArrayNextIndex++] = b;
        vertexArray[vArrayNextIndex++] = a;
    }
    private void fillVertexArray(){
        float xmin = 400f, ymin = 1200, roh = 150f, rw = 1100f,
                rh = 300f, bw = 800f, bh = 700f,
                dl = roh + 250f , dw = 150f, dh = 300f;

        SrShapeBuilder sb = new SrShapeBuilder(this);
        float x = 200f, y = 200f; // bottom left corner
        float size = 300f;

        int padding = SrWindowManager.getPadding(); // gap between squares
        int offset = SrWindowManager.getOffset(), // gap between window edge and square
                squareSize = 50,
                cols = 3,
                rows = 2;

        System.out.println("WinHeight: " + SrWindowManager.getWinHeight());
        System.out.println("Offset: " + SrWindowManager.getOffset());
        System.out.println("y value: " + ((SrWindowManager.getWinHeight() - offset) - 0 * (squareSize + padding) - squareSize));

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                x = offset + col * (squareSize + padding);
//                y = offset + row * (squareSize + padding);
                y = (SrWindowManager.getWinHeight() - offset) - row * (squareSize + padding) - squareSize;


                sb.rect(x, y, squareSize, squareSize, 0.2f, 0.4f, 0.9f, 1.0f);
            }
        }



        // other shapes
//        float x0 = x, y0 = y;                       // Bottom left
//        float x1 = x + size,    y1 = y;             // Bottom right
//        float x2 = x + size,    y2 = y + size;      // Top right
//        float x3 = x,           y3 = y + size;      // Top left
//
//        sb.rect(1200, 400, 1000, 950, 1, 1, 0, 1);
//        sb.rect(1450, 400, 150, 300, 1,0,1,1);
//        sb.triangle(1050, 1350, 1700, 1550, 2350, 1350, 1, 0, 0, 1);
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


    private void renderScene(){

    }
}
