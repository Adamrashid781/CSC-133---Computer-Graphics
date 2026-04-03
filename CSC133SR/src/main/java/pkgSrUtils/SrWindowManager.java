package pkgSrUtils;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;


public class SrWindowManager {
    // Class member variables
    private static SrWindowManager myWindow;
    private static GLFWKeyCallback keyCallback;

    private static long glfwWindow = NULL;
    private static int  winWidth  = 1800;
    private static int winHeight = 1800;
    private static boolean resized = false;



    private static GLFWFramebufferSizeCallback resizeWindow = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            if(width > 0 && height > 0){
                winWidth = width;
                winHeight = height;
                glViewport(0, 0, width, height);
                resized = true;
            }
        }
    };


    private static SrWindowManager get(){
        if(myWindow == null){
            myWindow = new SrWindowManager();
            initGlfwWindow();
        }
        return myWindow;
    }//  private static XyWindowManager get()
    public static SrWindowManager get(int w, int h){
        winWidth = w;
        winHeight = h;
        return get();
    }

    // Getter for the resized boolean
    public static boolean wasResized(){
        boolean r = resized;
        resized = false;
        return r;
    }

    public static int getWinWidth(){
        return winWidth;
    }
    public static int getWinHeight(){
        return winHeight;
    }

    // Offset between the shape and edge of window
    public static int getOffset(){
        return (int)(winWidth * 0.08f);
    }
    public static int getPadding(){
        return (int)(winWidth * 0.05f);
    }

    private static void initGlfwWindow(){
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindow = glfwCreateWindow(winWidth, winHeight, "CSC138", NULL, NULL);
        if(glfwWindow == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Set initial position
        glfwSetWindowPos(glfwWindow, 30, 30);

        myWindow.updateContextToThis();

        // Initialize OpenGL capabilities for this context
        GL.createCapabilities();

        glfwSwapInterval(1); //VSync
        glfwShowWindow(glfwWindow);

        // Enabling callbacks
        enableSetKeyCallback();
        enableResizeWindowCallback();
    }
    private static void enableSetKeyCallback(){
        glfwSetKeyCallback(glfwWindow, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
            }
        });
    }



    public void destroyGlfwWindow(){
        if(glfwWindow != NULL){
            glfwDestroyWindow(glfwWindow);
            keyCallback.free();
            resizeWindow.free();
        }
        glfwTerminate();
    }

    public void updateContextToThis(){
        glfwMakeContextCurrent(glfwWindow);
    }
    public void swapBuffers(){
        glfwSwapBuffers(glfwWindow);
    }
    public static void enableResizeWindowCallback(){
        glfwSetFramebufferSizeCallback(glfwWindow, resizeWindow);
    }

    public boolean isGlfwWindowClosed(){
        return glfwWindow != 0 && glfwWindowShouldClose(glfwWindow);
    }


}
