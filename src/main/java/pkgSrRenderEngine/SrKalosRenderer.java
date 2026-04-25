package pkgSrRenderEngine;

import pkgSrUtils.*;
import java.util.Random;


public class SrKalosRenderer extends SrRenderer{

    private int currentSides = 3; // To Start with a triangle
    private double lastTime = 0; // Tracks 0.5s intervals
    private Random rand = new Random();





    public SrKalosRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so){
        super(wm, cam, so);
        // Max vertices: 100 polygons * (37 sides + 1 center) * 9 floats
        // Max indices: 100 polygons * 37 triangles * indices

        myVDMgr = new SrVertexDataManager(SrPolygonArrayData.GRID_ROWS * SrPolygonArrayData.GRID_COLS * 38 * 9,
                SrPolygonArrayData.GRID_ROWS * SrPolygonArrayData.GRID_COLS * 37 * 3);
    }

    @Override protected void fillVertexCoordinates(){
        myVDMgr.resetNextIIndex();
        myVDMgr.resetNextVCIndex();
        myVDMgr.resetVertexArray();
        myVDMgr.resetIndexArray();

        for (int row = 0; row < SrPolygonArrayData.GRID_ROWS; row++) {
            for (int col = 0; col < SrPolygonArrayData.GRID_COLS; col++) {
                // draw polygon at this grid position
                // Calculate the center of each shape
                float cellWidth = (float) SrWindowManager.getWinWidth() / SrPolygonArrayData.GRID_COLS;
                float cellHeight = (float) SrWindowManager.getWinHeight() / SrPolygonArrayData.GRID_ROWS;
                // Calculate the spacing of all polygons
                float cx = col * cellWidth + cellWidth / 2;
                float cy = row * cellHeight + cellHeight / 2;

                // Generating random color for this polygon
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();


            }
        }
    }
}
