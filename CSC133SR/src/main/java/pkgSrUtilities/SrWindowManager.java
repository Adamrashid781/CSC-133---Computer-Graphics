package pkgSrUtilities;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class SrWindowManager {
    // Class member variables
    private static GLFWKeyCallback keyCallback;
    private static SrWindowManager myWindow;
    private static GLFWFramebufferSizeCallback resizeWindow;
    private static long glfwWindow;
    private static GLFWFramebufferSizeCallback fbCallBack;
    private static int  winWidth = 1800;
    private static int winHeight = 1200;


    public SrWindowManager(){
        get();
        get(int w, int h);
    }
    private static SrWindowManager get(){

    }
    private static SrWindowManager get(int w, int h){

    }

    private static void enableSetKeyCallback(){

    }

    public int[] getWindowSize(){
        return int[-1][-1];
    }

    public void destroyGlfwWindow(){

    }
    private static void initGlfwWindow(){

    }
    public void updateContextToThis(){

    }
    public static void setWinWidth(int width, int height){

    }
    public void swapBuffers(){

    }
    public static void enableResizeWindowCallback(){

    }
    public static boolean isGlfwWindowClosed(){

        return false;
    }


}
