package pkgSrRenderEngine;

import org.joml.Vector4f;
import pkgDriver.SrSpot;
import pkgSrUtils.SrWindowManager;


public class SrLMRenderer extends SrRenderer{
    public SrLMRenderer(SrVertexDataReader vdr, SrWindowManager wm, SrCamera cam, SrShaderObject so){
        super(wm, cam, so);
        this.reader = vdr;
        myVDMgr = new SrVertexDataManager(reader);
    }

    private SrVertexDataReader reader;


    @Override protected void fillVertexCoordinates(){
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        int totalLines = reader.getNumVertices();
        final int NUMVERTS = totalLines - totalLines % SrSpot.VPST;
        float[] vec0, vec1, vec2;
        final int CX = 0, CY = 1, CU = 3, CV = 4, CO = 5;

        for (int curVert = 0; curVert < NUMVERTS; curVert += SrSpot.VPST) {
            vec0 = reader.getVertexCoordsArray(curVert);
            vec1 = reader.getVertexCoordsArray(curVert + 1);
            vec2 = reader.getVertexCoordsArray(curVert + 2);

            myVDMgr.setDefaultColor(new Vector4f(vec0[CO], vec0[CO+1], vec0[CO+2], vec0[CO+3]));
            myVDMgr.fillTriangleVertexCoordinates(
                    SrSpot.TILE_OFFSET_X + vec0[CX], SrSpot.TILE_OFFSET_Y + vec0[CY], vec0[CU], vec0[CV],
                    SrSpot.TILE_OFFSET_X + vec1[CX], SrSpot.TILE_OFFSET_Y + vec1[CY], vec1[CU], vec1[CV],
                    SrSpot.TILE_OFFSET_X + vec2[CX], SrSpot.TILE_OFFSET_Y + vec2[CY], vec2[CU], vec2[CV]);
        }  //  for(int curVert = 0; curVert < NUMVERTS; curVert += VPST)
    }






}
