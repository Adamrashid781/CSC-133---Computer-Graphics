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

        defaultUpVector = new Vector3f(upVector);
        curUpVector = new Vector3f(upVector);

        initCamera();
        setCurLookAt();
        setCurLookFrom();
        setOrthoProjection();
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
        return curLookAt;
    }

    public void setCurLookAt(Vector3f cla){
        curLookAt = cla;
    }

    public void setCurLookFrom(Vector3f clf){
        curLookFrom = clf;
    }

    public Vector3f getCurLookFrom(){
        return curLookFrom;
    }

    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }


}
