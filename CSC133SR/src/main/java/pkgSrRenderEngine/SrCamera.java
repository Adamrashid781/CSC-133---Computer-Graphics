package pkgSrRenderEngine;

import org.joml.*;
import org.joml.*;

public class SrCamera {
    public SrCamera(float left, float right, float bottom, float top, float near, float far, Vector3f upVector){
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;


    }

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f curLookAt;
    private Vector3f curLookFrom;
    private Vector3f defaultLookAt;
    private Vector3f defaultLookFrom;
    private Vector3f defaultUpVector;
    private Vector3f curUpVector;

    private float left, right, bottom, top, near, far ;


    public void setOrthoProjection(){

    }

    private void initCamera(){

    }

    public Vector3f getCurLookAt(){

    }

    public void setCurLookAt(Vector3f){

    }

    public void setCurLookFrom(Vector3f){

    }

    public Vector3f getCurLookFrom(){

    }

    public Matrix4f getViewMatrix(){

    }

    public Matrix4f getProjectionMatrix(){

    }


}
