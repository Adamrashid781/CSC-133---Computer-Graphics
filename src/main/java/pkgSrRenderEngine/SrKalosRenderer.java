package pkgSrRenderEngine;

import org.joml.Vector4f;
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

                // Draw polygon at (cx, cy)
                myVDMgr.setDefaultColor(new Vector4f(r,g,b, 1.0f));
                for(int i = 0; i < currentSides; i++){
                    double angle0 = 2 * Math.PI * i / currentSides;
                    double angle1 = 2 * Math.PI * (i + 1) / currentSides;

                    float vx0 = cx + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.cos(angle0);
                    float vy0 = cy + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.sin(angle0);
                    float vx1 = cx + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.cos(angle1);
                    float vy1 = cy + SrPolygonArrayData.POLYGON_RADIUS * (float)Math.sin(angle1);

                    myVDMgr.fillTriangleVertexCoordinates(cx, cy, 0, 0,
                                                          vx0, vy0, 0, 0,
                                                          vx1, vy1, 0, 0);

                }
            }
        }


    }
}
