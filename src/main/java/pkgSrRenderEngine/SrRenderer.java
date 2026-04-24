package pkgSrRenderEngine;
import pkgSrUtils.*;

public abstract class SrRenderer {
    protected SrWindowManager curWM;
    protected SrVertexDataManager myVDMgr;
    protected SrCamera cam;
    protected SrShaderObject shader;
    protected int vbo, ibo;

    public SrRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so){
        this.curWM = wm;
        this.cam = cam;
        this.shader = so;
    }

    protected abstract void fillVertexCoordinates();
}
