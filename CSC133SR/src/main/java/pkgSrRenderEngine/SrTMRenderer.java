package pkgSrRenderEngine;
import pkgSrUtils.SrWindowManager;

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
        SrWindowManager.getWinWidth();
        SrWindowManager.getWinWidth() ;


    }
    // private int SrWindowManager.getWinWidth = 2600, SrWindowManager.getWinWidth() = 2000;







//    private void fillVertices(float x0, float y0, float x1, float y1, float x2, float y2,
//                              float cR, float cG, float cB, float cA){
//        int baseVertex = (vArrayNextIndex / FPV);
//    }

    private void putVertex(float x, float y, float z, float u, float v, float r, float g, float b, float a){

    }
    private void fillVertexArray(){
        float xmin = 400f, ymin = 1200, roh = 150f, rw = 1100f,
                rh = 300f, bw = 800f, bh = 700f,
                dl = roh + 250f , dw = 150f, dh = 300f;

        // SrShapeBuilder sb = new SrShapeBuilder(this);
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


                // sb.rect(x, y, squareSize, squareSize, 0.2f, 0.4f, 0.9f, 1.0f);
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





    private void renderScene(){

    }
}
