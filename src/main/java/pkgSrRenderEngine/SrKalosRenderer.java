package pkgSrRenderEngine;

import pkgSrUtils.*;
import java.util.Random;


public class SrKalosRenderer extends SrRenderer{

    private int currentSides = 3; // To Start with a triangle
    private double lastTime = 0; // Tracks 0.5s intervals
    private Random rand = new Random();


    // Grid Constants
    private static final int GRID_COLS = 10;
    private static final int GRID_ROWS = 10;
    private static final float POLYGON_RADIUS = 60f;


    public SrKalosRenderer(SrWindowManager wm, SrCamera cam, SrShaderObject so){
        super(wm, cam, so);
        // Max vertices: 100 polygons * (37 sides + 1 center) * 9 floats
        // Max indices: 100 polygons * 37 triangles * indices

        myVDMgr = new SrVertexDataManager(100 * 38 * 9, 100 * 37 * 3);
    }


}
