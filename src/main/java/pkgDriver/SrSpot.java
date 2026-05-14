package pkgDriver;

import org.joml.Vector3f;

public class SrSpot {

    public static final int ERROR_VALUE = -1;

    public static final int OGL_MATRIX_SIZE = 16;
    public static final int OGL_VEC4_SIZE = 4;

    public static final int BOARD_ROWS = 9;
    public static final int BOARD_COLS = 7;
    public static final int NUM_MINES = 14;
    public static final int TILE_SIZE = 90;



    public static final Vector3f LOOK_UP = new Vector3f(0,1, 0);
    public static final Vector3f LOOK_FROM = new Vector3f(0,0, 0);
    public static final Vector3f LOOK_AT = new Vector3f(0,0, -1);


    // Coordinates Per Vertex, Vertices Per Single Triangle, Indexes Per Single Triangle, Floats Per Vertex ...
    public static final int CPV = 9, VPST = 3, IPST = 3, FPV = CPV * Float.BYTES, MIN_POLY_SIDES = 3;

    public static final int POSITION_STRIDE = 3, TEXTURE_STRIDE = 2, COLOR_STRIDE = 4;

    public static final int SLEEP_INTERVAL = 500;
    public static final int TILE_OFFSET_X  = 30, TILE_OFFSET_Y = 30;
    public static final int PADDING_X = 20, PADDING_Y = 20;

    public static final int WIN_WIDTH = (BOARD_COLS * TILE_SIZE) + (2 * TILE_OFFSET_X) + ((BOARD_COLS - 1) * PADDING_X);
    public static final int WIN_HEIGHT = (BOARD_ROWS * TILE_SIZE) + (2 * TILE_OFFSET_X) + ((BOARD_ROWS - 1) * PADDING_X);

    public record SlPolygonArrayData(int maxRows, int maxCols, int maxSides, int radialLength) {
    }  //  public record PolygonTileData


}  //  public class SlSpot
