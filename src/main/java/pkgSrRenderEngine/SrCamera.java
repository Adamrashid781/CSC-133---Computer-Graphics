package pkgSrRenderEngine;

import org.joml.*;
import org.joml.*;
import static pkgDriver.SrSpot.*;

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
        defaultLookAt = new Vector3f(LOOK_AT);
        defaultLookFrom = new Vector3f(LOOK_FROM);

        setCurLookAt(LOOK_AT);
        setCurLookFrom(LOOK_FROM);

        initCamera();

        setOrthoProjection();
    }

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;
    protected Vector3f curLookAt;
    protected Vector3f curLookFrom;
    protected Vector3f defaultLookAt;
    protected Vector3f defaultLookFrom;
    protected Vector3f defaultUpVector;
    protected Vector3f curUpVector;

    private float left, right, bottom, top, near, far ;


    public void setOrthoProjection(){
        projectionMatrix = new Matrix4f().setOrtho(left, right, bottom, top, near, far);
    }

    private void initCamera(){
        viewMatrix = new Matrix4f().lookAt(curLookFrom, curLookAt, curUpVector);
    }

    public Vector3f getCurLookAt(){
        return new Vector3f(curLookAt);
    }

    public void setCurLookAt(Vector3f cla){
        curLookAt = new Vector3f(cla);
    }

    public void setCurLookFrom(Vector3f clf){
        curLookFrom = new Vector3f(clf);
    }

    public Vector3f getCurLookFrom(){
        return new Vector3f(curLookFrom);
    }

    public Matrix4f getViewMatrix(){
        return new Matrix4f(viewMatrix);
    }

    public Matrix4f getProjectionMatrix(){
        return new Matrix4f(projectionMatrix);
    }


}
