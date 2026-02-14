package pkgSrRenderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

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




    public SrTMRenderer(int trianglesToRender) {
        initVertexIndexArrays(trianglesToRender);
    }
    private int WIN_WIDTH = 1800, WIN_HEIGHT = 1200;
    public void render(){
        initGLFWindow();
        initOpenGL();

        fillVertexArray();


        int vbo = glGenBuffers();
        int ibo = glGenBuffers();
        // float[] vertices = {20f,  20f,  0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
//                200f, 20f,  0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
//                200f, 200f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f,
//                20f, 200f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f};
        // int[] indices = {0, 1, 2, 0, 2, 3};

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.
                // createFloatBuffer(vertices.length).
                createFloatBuffer(vertexArray.length).
                put(vertexArray).flip(), GL_STATIC_DRAW);
                // put(indices).flip(), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.
                // createIntBuffer(indices.length).
                createIntBuffer(indexArray.length).
                put(indexArray).flip(), GL_STATIC_DRAW);
                // put(indices).flip(), GL_STATIC_DRAW);

        Matrix4f uProjMatrix = new Matrix4f();
        Matrix4f uViewMatrix = new Matrix4f();
        uProjMatrix.setOrtho(0.0f, WIN_WIDTH, 0.0f, WIN_HEIGHT, 0, 100.0f);

        final int OGL_MATRIX_SIZE = 16;
        FloatBuffer myFloatBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        glUniformMatrix4fv(projMatLocation, false,
                uProjMatrix.get(myFloatBuffer));

        final Vector3f defaultLookFrom = new Vector3f(0.0f, 0.0f, 00.0f);
        final Vector3f defaultLookAt = new Vector3f(0.0f, 0.0f, -1.0f);
        final Vector3f defaultUpVector = new Vector3f(0.0f, 1.0f, 0.0f);
        uViewMatrix.identity();
        uViewMatrix.lookAt(defaultLookFrom, defaultLookAt.add(defaultLookFrom), defaultUpVector);
        glUniformMatrix4fv(viewMatLocation, false,
                uViewMatrix.get(myFloatBuffer));

        glPolygonMode(GL_FRONT, GL_FILL);
        final int posStride = 3, texStride = 2, colStride = 4,
                vertexStride = posStride + texStride + colStride;
        final int vpoIndex = 0, vtoIndex = 1, vcoIndex = 2;

        glVertexAttribPointer(vpoIndex, posStride, GL_FLOAT, false, vertexStride * Float.BYTES, 0);
        glEnableVertexAttribArray(vpoIndex);
        glVertexAttribPointer(vtoIndex, texStride, GL_FLOAT, false, vertexStride * Float.BYTES,
                (long)(posStride * Float.BYTES));
        glEnableVertexAttribArray(vtoIndex);
        glVertexAttribPointer(vcoIndex, colStride, GL_FLOAT, false, vertexStride * Float.BYTES,
                (long)(posStride + texStride)*Float.BYTES) ;
        glEnableVertexAttribArray(vcoIndex);

        renderLoop();
    } // void render()

    private void renderLoop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glDrawElements(GL_TRIANGLES, indexArray.length, GL_UNSIGNED_INT, 0L);
            glfwSwapBuffers(glfwWindow);
        }
        try {
            glfwDestroyWindow(glfwWindow);
            keyCallback.free();
            fbCallback.free();
        } finally {
            glUseProgram(0);
            glfwTerminate();
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }
    }

    private void initGLFWindow(){

        glfwSetErrorCallback(errorCallback =
                GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); // Request OpenGL 3.3
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        // When we create the GLFW Window here, the OpenGL context is created
        // implicitly; but the context needs to be explicitly made current
        glfwWindow = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "CSC 133", NULL, NULL);
        if (glfwWindow == NULL)
            throw new RuntimeException("CSC133 Error: Failed to create the GLFW window");
        glfwSetKeyCallback(glfwWindow, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int
                    mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });
        glfwSetFramebufferSizeCallback(glfwWindow, fbCallback = new
                GLFWFramebufferSizeCallback() {
                    @Override
                    public void invoke(long window, int w, int h) {
                        if (w > 0 && h > 0) {
                            WIN_WIDTH = w;
                            WIN_HEIGHT = h;
                        }
                    }
                });
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow, WIN_POS_X, WIN_POX_Y);
        glfwMakeContextCurrent(glfwWindow);
        int VSYNC_INTERVAL = 1;
        glfwSwapInterval(VSYNC_INTERVAL);
        glfwShowWindow(glfwWindow);
    }

    public void initOpenGL(){
        GL.createCapabilities();
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        //glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);
        glViewport(0, 0, WIN_WIDTH, WIN_HEIGHT);
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
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
                dl = roh + 250f , dw = 150f, dh = 300f; //,
                // x1 = xmin + rw, y1 = ymin, x2 = (int)((xmin + x1)/2),
                // y2 = ymin + rh;

        SrShapeBuilder sb = new SrShapeBuilder(this);
        float x = 200f, y = 200f; // bottom left corner
        float size = 300f;

        float x0 = x, y0 = y;                       // Bottom left
        float x1 = x + size,    y1 = y;             // Bottom right
        float x2 = x + size,    y2 = y + size;      // Top right
        float x3 = x,           y3 = y + size;      // Top left


//        fillVertices(x0, y0, x1, y1, x2, y2, 1.0f, 0.0f, 0.0f, 1.0f);
//        fillVertices(x0, y0, x2, y2, x3, y3, 1.0f, 0.0f, 0.0f, 1.0f);
        sb.rect(1200, 400, 1000, 950, 1, 1, 0, 1);  // front wall
        sb.rect(1450, 400, 150, 300, 1,0,1,1);      // door
        sb.triangle(1050, 1350, 1700, 1550, 2350, 1350, 1, 0, 0, 0); // roof

    }
    private void renderScene(){

    }
}
