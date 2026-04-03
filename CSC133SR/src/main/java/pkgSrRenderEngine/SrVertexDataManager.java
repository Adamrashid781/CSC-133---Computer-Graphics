package pkgSrRenderEngine;
import org.joml.Vector4f;
import java.nio.*;
import java.util.Vector;

public class SrVertexDataManager {
    SrVertexDataManager(int vaSize, int iaSize){

        vertexArray = new float[vaSize];
        indexArray = new int[iaSize];

        nextVCIndex = 0;
        nextIIndex = 0;
    }
    private static final int vertexStride = 9;
    private int nextIIndex ;
    private IntBuffer intIBuffer;
    private float[] vertexArray ;
    private float[] defaultColors;
    private int nextVCIndex;
    private FloatBuffer floatVBuffer;
    private int[] indexArray;
    private int nextVCount;


    protected float[] getVertexArray(){

    }
    protected int[] getIndexArray(){

    }
    protected void resetNextIIndex(){

    }
    protected void resetNextVCIndex(){

    }
    protected void resetNextVcount(){

    }
    protected void resetIndexArray(){

    }
    protected int getVertexArrayLength(){
        return vertexArray.length;
    }
    protected void setDefaultColor(Vector4f myC){
        defaultColors[0] = myC.x ;
        defaultColors[1] = myC.y;
        defaultColors[2] = myC.z;
        defaultColors[3] = myC.w;
    }
    protected int fillTriangleVertexCoordinates(float x0, float y0, float u0, float v0,
                                                float x1, float y1, float u1, float v1,
                                                float x2, float y2, float u2, float v2){

        int baseVertex = (nextVCIndex / vertexStride);  // vertex number before adding 1

        // z = 0 and texcoords will have dummy values
        putVertex(x0, y0, 0f, u0, v0);
        putVertex(x1, y1, 0f, u1, v1);
        putVertex(x2, y2, 0f, u2, v2);

        indexArray[nextIIndex++] = baseVertex;
        indexArray[nextIIndex++] = baseVertex + 1;
        indexArray[nextIIndex++] = baseVertex + 2;



    }
    // private helper — not in spec, but clean to keep
    private void putVertex(float x, float y, float z, float u, float v) {
        vertexArray[nextVCIndex++] = x;
        vertexArray[nextVCIndex++] = y;
        vertexArray[nextVCIndex++] = z;
        vertexArray[nextVCIndex++] = u;
        vertexArray[nextVCIndex++] = v;
        vertexArray[nextVCIndex++] = defaultColors[0];
        vertexArray[nextVCIndex++] = defaultColors[1];
        vertexArray[nextVCIndex++] = defaultColors[2];
        vertexArray[nextVCIndex++] = defaultColors[3];
    }

    protected void resetVertexArray(){
        vertexArray = new float[vertexArray.length];
    }

}
